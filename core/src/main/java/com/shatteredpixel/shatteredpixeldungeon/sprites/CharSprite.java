/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.DarkBlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.EmoIcon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.IceBlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.IconFloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.RoseHalo;
import com.shatteredpixel.shatteredpixeldungeon.effects.ShieldHalo;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.TorchHalo;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FrostFlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.HalomethaneFlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeFlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SnowParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.CharHealthIndicator;
import com.watabou.glwrap.Matrix;
import com.watabou.glwrap.Vertexbuffer;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.tweeners.PosTweener;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.nio.Buffer;

public class CharSprite extends MovieClip implements Tweener.Listener, MovieClip.Listener {

	// Color constants for floating text
	// Color constants for floating text
	public static final int DEFAULT = 0xFFFFFF;
	public static final int POSITIVE = 0x00FF00;
	public static final int NEGATIVE = 0xFF0000;
	public static final int BLUETEXT = 0x00FFFF;
	public static final int PINKTEXT = 0xFF00FF;
	public static final int WARNING = 0xFF8800;
	public static final int NEUTRAL = 0xFFFF00;
	public static final int WATERDAMAGE = 0x00FFFF;

	public static final float DEFAULT_MOVE_INTERVAL = 0.1f;
	private static float moveInterval = DEFAULT_MOVE_INTERVAL;
	private static final float FLASH_INTERVAL	= 0.05f;

	//the amount the sprite is raised from flat when viewed in a raised perspective
	protected float perspectiveRaise    = 6 / 16f; //6 pixels

	//the width and height of the shadow are a percentage of sprite size
	//offset is the number of pixels the shadow is moved down or up (handy for some animations)
	protected boolean renderShadow  = false;
	protected float shadowWidth     = 1.2f;
	protected float shadowHeight    = 0.25f;
	protected float shadowOffset    = 0.25f;


	public enum State {
		BURNING, LEVITATING, INVISIBLE,TRUE_INVISIBLE,
		PARALYSED, FROZEN, ILLUMINATED, CHILLED,CHILLED_2, DARKENED, MARKED, HEALING, SHIELDED,
		ROSESHIELDED, HALOMETHANEBURNING, FROSTBURNING, BUTTER, SPINVISIBLE, SMOKER, HEARTS,
		MUTATION_1,MUTATION_2,MUTATION_3,MUTATION_4,MUTATION_5,MUTATION_6,MUTATION_7,MUTATION_8,MUTATION_9
	}
	private int stunStates = 0;

	protected Animation idle;
	protected Animation run;
	protected Animation attack;
	protected Animation operate;
	protected Animation toss;
	protected Animation zap;
	protected Animation die;

	protected Callback animCallback;

	protected PosTweener motion;

	protected Emitter burning;
	protected Emitter chilled;
	protected Emitter chilled_2;
	protected Emitter marked;
	protected Emitter levitation;
	protected Emitter healing;
	protected Emitter frostburning;
	protected Emitter haloburning;
	protected Emitter soling;
	protected RoseHalo roseshield;

	public void zaplink(int cell) {

		turnTo(ch.pos, cell);
		play(attack);

		MagicMissile.boltFromChar(parent,
				MagicMissile.SHAMAN_BLUE,
				this,
				cell,
				new Callback() {
					@Override
					public void call() {
						((Mob) ch).onZapComplete();
					}
				});
		Sample.INSTANCE.play(Assets.Sounds.ZAP);
	}

	protected Emitter hearts;

	protected Emitter mutation;
	protected Emitter mutation2;
	protected Emitter mutation3;
	protected Emitter mutation4;
	protected Emitter mutation5;
	protected Emitter mutation6;
	protected Emitter mutation7;
	protected Emitter mutation8;

	protected IceBlock iceBlock;
	protected DarkBlock darkBlock;
	protected TorchHalo light;
	protected ShieldHalo shield;
	protected AlphaTweener invisible;
	protected Flare aura;

	protected EmoIcon emo;
	protected CharHealthIndicator health;

	private Tweener jumpTweener;
	private Callback jumpCallback;

	protected float flashTime = 0;

	protected boolean sleeping = false;

	public Char ch;

	//used to prevent the actor associated with this sprite from acting until movement completes
	public volatile boolean isMoving = false;

	public CharSprite() {
		super();
		listener = this;
	}

	@Override
	public void play(Animation anim) {
		//Shouldn't interrupt the dieing animation
		if (curAnim == null || curAnim != die) {
			super.play(anim);
		}
	}

	//intended to be used for placing a character in the game world
	public void link( Char ch ) {
		linkVisuals( ch );

		this.ch = ch;
		ch.sprite = this;

		place( ch.pos );
		turnTo( ch.pos, Random.Int( Dungeon.level.length() ) );
		renderShadow = true;

		if (ch != Dungeon.hero) {
			if (health == null) {
				health = new CharHealthIndicator(ch);
			} else {
				health.target(ch);
			}
		}

		ch.updateSpriteState();
	}

	@Override
	public void destroy() {
		super.destroy();
		if (ch != null && ch.sprite == this){
			ch.sprite = null;
		}
	}

	//used for just updating a sprite based on a given character, not linking them or placing in the game
	public void linkVisuals( Char ch ){
		//do nothin by default
	}

	public PointF worldToCamera( int cell ) {

		final int csize = DungeonTilemap.SIZE;

		return new PointF(
				PixelScene.align(Camera.main, ((cell % Dungeon.level.width()) + 0.5f) * csize - width() * 0.5f),
				PixelScene.align(Camera.main, ((cell / Dungeon.level.width()) + 1.0f) * csize - height() - csize * perspectiveRaise)
		);
	}

	public void place( int cell ) {
		point( worldToCamera( cell ) );
	}

	public void showStatus(int color, String text, Object... args) {
		//TODO 实验性功能 ICONTYPE
		if(SPDSettings.V2IconDamage()){
			showStatusWithIcon(color, text, IconFloatingText.NO_ICON, args);
		} else {
			if (visible) {
				if (args.length > 0) {
					text = Messages.format( text, args );
				}
				float x = destinationCenter().x;
				float y = destinationCenter().y - height()/2f;
				if (ch != null) {
					FloatingText.show( x, y, ch.pos, text, color );
				} else {
					FloatingText.show( x, y, text, color );
				}
			}
		}
	}

	public void showStatusWithIcon(int color, String text, int icon, Object... args) {
		if (this.visible) {
			if (args.length > 0) {
				text = Messages.format(text, args);
			}
			float x = destinationCenter().x;
			float y = destinationCenter().y - (height() / 2.0f);
			if (ch != null) {
				IconFloatingText.show(x, y, ch.pos, text, color, icon, true);
			} else {
				IconFloatingText.show(x, y, -1, text, color, icon, true);
			}
		}
	}


	public void idle() {
		play(idle);
	}

	public void move( int from, int to ) {
		turnTo( from , to );

		play( run );

		motion = new PosTweener( this, worldToCamera( to ), moveInterval );
		motion.listener = this;
		parent.add( motion );

		isMoving = true;

		if (visible && Dungeon.level.water[from] && !ch.flying) {
			GameScene.ripple( from );
		}

	}

	public static void setMoveInterval( float interval){
		moveInterval = interval;
	}

	//returns where the center of this sprite will be after it completes any motion in progress
	public PointF destinationCenter(){
		PosTweener motion = this.motion;
		if (motion != null && motion.elapsed >= 0){
			return new PointF(motion.end.x + width()/2f, motion.end.y + height()/2f);
		} else {
			return center();
		}
	}

	public void interruptMotion() {
		if (motion != null) {
			motion.stop(false);
		}
	}

	public void attack( int cell ) {
		attack( cell, null );
	}

	public synchronized void attack( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( attack );
	}

	public void operate( int cell ) {
		operate( cell, null );
	}

	public synchronized void operate( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( operate );
	}

	public void zap( int cell ) {
		zap( cell, null );
	}

	//for missile attacks
	public void toss( int cell ) {
		turnTo( ch.pos, cell );
		play( toss );
	}

	public void toss( int cell, Callback callback ) {
		animCallback = callback;
		toss( cell );
	}

	public synchronized void zap( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( zap );
	}

	public void turnTo( int from, int to ) {
		int fx = from % Dungeon.level.width();
		int tx = to % Dungeon.level.width();
		if (tx > fx) {
			flipHorizontal = false;
		} else if (tx < fx) {
			flipHorizontal = true;
		}
	}

	public void jump( int from, int to, Callback callback ) {
		float distance = Math.max( 1f, Dungeon.level.trueDistance( from, to ));
		jump( from, to, distance * 2, distance * 0.1f, callback );
	}

	public void jump( int from, int to, float height, float duration,  Callback callback ) {
		jumpCallback = callback;

		jumpTweener = new JumpTweener( this, worldToCamera( to ), height, duration );
		jumpTweener.listener = this;
		parent.add( jumpTweener );

		turnTo( from, to );
	}

	public void die() {
		sleeping = false;
		remove( State.PARALYSED );
		play( die );

		hideEmo();

		if (health != null){
			health.killAndErase();
		}
	}

	public Emitter emitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( this );
		return emitter;
	}

	public Emitter centerEmitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( center() );
		return emitter;
	}

	public Emitter bottomEmitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( x, y + height, width, 0 );
		return emitter;
	}

	public void burst( final int color, int n ) {
		if (visible) {
			Splash.at( center(), color, n );
		}
	}

	public void bloodBurstA( PointF from, int damage ) {
		if (visible) {
			PointF c = center();
			int n = (int)Math.min( 9 * Math.sqrt( (double)damage / ch.HT ), 9 );
			Splash.at( c, PointF.angle( from, c ), 3.1415926f / 2, blood(), n );
		}
	}

	public int blood() {
		return 0xFFBB0000;
	}

	public void flash() {
		ra = ba = ga = 1f;
		flashTime = FLASH_INTERVAL;
	}

	public void add( State state ) {
		switch (state) {
			case BURNING:
				burning = emitter();
				burning.pour( FlameParticle.FACTORY, 0.06f );
				if (visible) {
					Sample.INSTANCE.play( Assets.Sounds.BURNING );
				}
				break;
			case LEVITATING:
				levitation = emitter();
				levitation.pour( Speck.factory( Speck.JET ), 0.02f );
				break;
			case INVISIBLE:
				if (invisible != null) {
					invisible.killAndErase();
				}
				invisible = new AlphaTweener( this, 0.4f, 0.4f );
				if (parent != null){
					parent.add(invisible);
				} else
					alpha( 0.4f );
				break;
			case TRUE_INVISIBLE:
				if (invisible != null) {
					invisible.killAndErase();
				}
				invisible = new AlphaTweener( this, 0f, 0f );
				if (parent != null){
					parent.add(invisible);
				} else
					alpha( 0f );
				break;
			case PARALYSED:
				paused = true;
				break;
			case FROZEN:
				iceBlock = IceBlock.freeze( this );
				break;
			case ILLUMINATED:
				GameScene.effect( light = new TorchHalo( this ) );
				break;
			case CHILLED:
				chilled = emitter();
				chilled.pour(SnowParticle.FACTORY, 0.1f);
				break;
			case CHILLED_2:
				chilled_2 = emitter();
				chilled_2.pour(SnowParticle.FACTORY, 0.1f);
				break;
			case DARKENED:
				darkBlock = DarkBlock.darken( this );
				break;
			case MARKED:
				marked = emitter();
				marked.pour(ShadowParticle.UP, 0.1f);
				break;
			case HEALING:
				healing = emitter();
				healing.pour(Speck.factory(Speck.HEALING), 0.5f);
				break;
			case SHIELDED:
				if (shield != null) {
					shield.killAndErase();
				}
				GameScene.effect(shield = new ShieldHalo(this));
				break;
			case HEARTS:
				hearts = emitter();
				hearts.pour(Speck.factory(Speck.HEART), 0.5f);
				break;
			case HALOMETHANEBURNING:
				haloburning = emitter();
				haloburning.pour(HalomethaneFlameParticle.FACTORY, 0.06f);
				if (visible) {
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
				}
				break;
			case FROSTBURNING:
				frostburning = emitter();
				frostburning.pour(FrostFlameParticle.FACTORY, 0.06f);
				if (visible) {
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
				}
				break;
			case SMOKER:
				soling = emitter();
				soling.pour(SmokeFlameParticle.FACTORY, 0.06f);
				break;
			case ROSESHIELDED:
				GameScene.effect(roseshield = new RoseHalo(this));
				break;
			case MUTATION_1:
				mutation = emitter();
				mutation.pour(Speck.factory(Speck.MUTATION_1), 0.5f);
				break;
			case MUTATION_2:
				mutation2 = emitter();
				mutation2.pour(Speck.factory(Speck.MUTATION_2), 0.5f);
				break;
			case MUTATION_3:
				mutation3 = emitter();
				mutation3.pour(Speck.factory(Speck.MUTATION_3), 0.5f);
				break;
			case MUTATION_4:
				mutation4 = emitter();
				mutation4.pour(Speck.factory(Speck.MUTATION_4), 0.5f);
				break;
			case MUTATION_5:
				mutation5 = emitter();
				mutation5.pour(Speck.factory(Speck.MUTATION_5), 0.5f);
				break;
			case MUTATION_6:
				mutation6 = emitter();
				mutation6.pour(Speck.factory(Speck.MUTATION_6), 0.5f);
				break;
			case MUTATION_7:
				mutation7 = emitter();
				mutation7.pour(Speck.factory(Speck.MUTATION_7), 0.5f);
				break;
			case MUTATION_8:
				mutation8 = emitter();
				mutation8.pour(Speck.factory(Speck.MUTATION_8), 0.1f);
				break;
		}
	}

	public void remove( State state ) {
		switch (state) {
			case BURNING:
				if (burning != null) {
					burning.on = false;
					burning = null;
				}
				break;
			case LEVITATING:
				if (levitation != null) {
					levitation.on = false;
					levitation = null;
				}
				break;
			case INVISIBLE:case TRUE_INVISIBLE:
				if (invisible != null) {
					invisible.killAndErase();
					invisible = null;
				}
				alpha( 1f );
				break;
			case PARALYSED:
				paused = false;
				break;
			case FROZEN:
				if (iceBlock != null) {
					iceBlock.melt();
					iceBlock = null;
				}
				break;
			case ILLUMINATED:
				if (light != null) {
					light.putOut();
				}
				break;
			case CHILLED:
				if (chilled != null){
					chilled.on = false;
					chilled = null;
				}
				break;
			case CHILLED_2:
				if (chilled_2 != null){
					chilled_2.on = false;
					chilled_2 = null;
				}
				break;
			case DARKENED:
				if (darkBlock != null) {
					darkBlock.lighten();
					darkBlock = null;
				}
				break;
			case MARKED:
				if (marked != null){
					marked.on = false;
					marked = null;
				}
				break;
			case HEALING:
				if (healing != null){
					healing.on = false;
					healing = null;
				}
				break;
			case SHIELDED:
				if (shield != null){
					shield.putOut();
				}
				break;
			case HEARTS:
				if (hearts != null){
					hearts.on = false;
					hearts = null;
				}
				break;

			case HALOMETHANEBURNING:
				if (haloburning != null) {
					haloburning.on = false;
					haloburning = null;
				}
				break;
			case FROSTBURNING:
				if (frostburning != null) {
					frostburning.on = false;
					frostburning = null;
				}
				break;
			case SMOKER:
				if (soling != null) {
					soling.on = false;
					soling = null;
				}
				break;
			case SPINVISIBLE:
				if (invisible != null) {
					invisible.killAndErase();
					invisible = null;
				}
				alpha(4f);
				break;
			case ROSESHIELDED:
				if(roseshield != null) {
					roseshield.putOut();
				}
				break;
			case MUTATION_1:
				if (mutation != null){
					mutation.on = false;
					mutation = null;
				}
				break;
			case MUTATION_2:
				if(mutation2 != null){
					mutation2.on =  false;
					mutation2 = null;
				}
				break;
			case MUTATION_3:
				if(mutation3 != null){
					mutation3.on =  false;
					mutation3 = null;
				}
				break;
			case MUTATION_4:
				if(mutation4 != null){
					mutation4.on =  false;
					mutation4 = null;
				}
				break;
			case MUTATION_5:
				if(mutation5 != null){
					mutation5.on =  false;
					mutation5 = null;
				}
				break;
			case MUTATION_6:
				if(mutation6 != null){
					mutation6.on =  false;
					mutation6 = null;
				}
				break;
			case MUTATION_7:
				if(mutation7 != null){
					mutation7.on =  false;
					mutation7 = null;
				}
				break;
			case MUTATION_8:
				if(mutation8 != null){
					mutation8.on =  false;
					mutation8 = null;
				}
				break;
		}
	}

	public void aura( int color ){
		if (aura != null){
			aura.killAndErase();
		}
		float size = Math.max(width(), height());
		size = Math.max(size+4, 16);
		aura = new Flare(5, size);
		aura.angularSpeed = 90;
		aura.color(color, true);
		aura.visible = visible;

		if (parent != null) {
			aura.show(this, 0);
		}
	}

	public void clearAura(){
		if (aura != null){
			aura.killAndErase();
			aura = null;
		}
	}

	@Override
	public void update() {
		if (paused && ch != null && curAnim != null && !curAnim.looped && !finished){
			listener.onComplete(curAnim);
			finished = true;
		}

		super.update();

		if (flashTime > 0 && (flashTime -= Game.elapsed) <= 0) {
			resetColor();
		}

		if (burning != null) {
			burning.visible = visible;
		}
		if (levitation != null) {
			levitation.visible = visible;
		}
		if (iceBlock != null) {
			iceBlock.visible = visible;
		}
		if (chilled != null) {
			chilled.visible = visible;
		}
		if (chilled_2 != null) {
			chilled_2.visible = visible;
		}
		if (marked != null) {
			marked.visible = visible;
		}
		if (healing != null){
			healing.visible = visible;
		}
		if (hearts != null){
			hearts.visible = visible;
		}
		if (mutation != null){
			mutation.visible = visible;
		}
		if (mutation2 != null){
			mutation2.visible = visible;
		}
		if (mutation3 != null){
			mutation3.visible = visible;
		}
		if (mutation4 != null){
			mutation4.visible = visible;
		}
		if (mutation5 != null){
			mutation5.visible = visible;
		}
		if (mutation6 != null){
			mutation6.visible = visible;
		}
		if (mutation7 != null){
			mutation7.visible = visible;
		}
		if (mutation8 != null){
			mutation8.visible = visible;
		}
		if (aura != null){
			if (aura.parent == null){
				aura.show(this, 0);
			}
			aura.visible = visible;
			aura.point(center());
		}
		if (sleeping) {
			showSleep();
		} else {
			hideSleep();
		}
		synchronized (EmoIcon.class) {
			if (emo != null && emo.alive) {
				emo.visible = visible;
			}
		}
	}

	@Override
	public void resetColor() {
		super.resetColor();
		if (invisible != null){
			alpha(0.4f);
		}
	}

	public void showSleep() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Sleep)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Sleep(this);
				emo.visible = visible;
			}
		}
		idle();
	}

	public void hideSleep() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Sleep) {
				emo.killAndErase();
				emo = null;
			}
		}
	}

	public void showAlert() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Alert)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Alert(this);
				emo.visible = visible;
			}
		}
	}

	public void hideAlert() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Alert) {
				emo.killAndErase();
				emo = null;
			}
		}
	}

	public void showLost() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Lost)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Lost(this);
				emo.visible = visible;
			}
		}
	}

	public void hideLost() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Lost) {
				emo.killAndErase();
				emo = null;
			}
		}
	}

	public void hideEmo(){
		synchronized (EmoIcon.class) {
			if (emo != null) {
				emo.killAndErase();
				emo = null;
			}
		}
	}

	@Override
	public void kill() {
		super.kill();

		hideEmo();

		for( State s : State.values()){
			remove(s);
		}

		if (health != null){
			health.killAndErase();
		}
	}

	private float[] shadowMatrix = new float[16];

	@Override
	protected void updateMatrix() {
		super.updateMatrix();
		Matrix.copy(matrix, shadowMatrix);
		Matrix.translate(shadowMatrix,
				(width * (1f - shadowWidth)) / 2f,
				(height * (1f - shadowHeight)) + shadowOffset);
		Matrix.scale(shadowMatrix, shadowWidth, shadowHeight);
	}

	@Override
	public void draw() {
		if (texture == null || (!dirty && buffer == null))
			return;

		if (renderShadow) {
			if (dirty) {
				((Buffer)verticesBuffer).position(0);
				verticesBuffer.put(vertices);
				if (buffer == null)
					buffer = new Vertexbuffer(verticesBuffer);
				else
					buffer.updateVertices(verticesBuffer);
				dirty = false;
			}

			NoosaScript script = script();

			texture.bind();

			script.camera(camera());

			updateMatrix();

			script.uModel.valueM4(shadowMatrix);
			script.lighting(
					0, 0, 0, am * .6f,
					0, 0, 0, aa * .6f);

			script.drawQuad(buffer);
		}

		super.draw();

	}

	@Override
	public void onComplete( Tweener tweener ) {
		if (tweener == jumpTweener) {

			if (visible && Dungeon.level.water[ch.pos] && !ch.flying) {
				GameScene.ripple( ch.pos );
			}
			if (jumpCallback != null) {
				jumpCallback.call();
			}

		} else if (tweener == motion) {

			synchronized (this) {
				isMoving = false;

				motion.killAndErase();
				motion = null;
				ch.onMotionComplete();

				GameScene.sortMobSprites();
				notifyAll();
			}

		}
	}

	@Override
	public synchronized void onComplete( Animation anim ) {

		if (animCallback != null) {
			Callback executing = animCallback;
			animCallback = null;
			executing.call();
		} else {

			if (anim == attack) {

				idle();
				ch.onAttackComplete();

			} else if (anim == operate) {

				idle();
				ch.onOperateComplete();

			}

		}
	}

	private static class JumpTweener extends Tweener {

		public CharSprite visual;

		public PointF start;
		public PointF end;

		public float height;

		public JumpTweener( CharSprite visual, PointF pos, float height, float time ) {
			super( visual, time );

			this.visual = visual;
			start = visual.point();
			end = pos;

			this.height = height;
		}

		@Override
		protected void updateValues( float progress ) {
			float hVal = -height * 4 * progress * (1 - progress);
			visual.point( PointF.inter( start, end, progress ).offset( 0, hVal ) );
			visual.shadowOffset = 0.25f - hVal*0.8f;
		}
	}
}