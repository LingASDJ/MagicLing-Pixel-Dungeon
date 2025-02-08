/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.windows;

import static com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost.Quest.food;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FetidRatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollTricksterSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreatCrabSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;

public class WndSadGhost extends Window {

	private static final int WIDTH		= RegularLevel.holiday == RegularLevel.Holiday.ZQJ ? 150 : 120;
	private static final int BTN_SIZE	= 32;
	private static final int BTN_GAP	= 5;
	private static final int GAP		= 2;

	Ghost ghost;
	
	public WndSadGhost( final Ghost ghost, final int type ) {

		super();

		this.ghost = ghost;
		
		IconTitle titlebar = new IconTitle();
		RenderedTextBlock message;
		switch (type){
			case 1:default:
				titlebar.icon( new FetidRatSprite() );
				titlebar.label( Messages.get(this, "rat_title") );
				message = PixelScene.renderTextBlock( Messages.get(this, "rat")+"\n\n"+(RegularLevel.holiday == RegularLevel.Holiday.ZQJ ? Messages.get(this, "ask") :Messages.get(this,
						"give_item")),6 );
				break;
			case 2:
				titlebar.icon( new GnollTricksterSprite() );
				titlebar.label( Messages.get(this, "gnoll_title") );
				message = PixelScene.renderTextBlock( Messages.get(this, "gnoll")+"\n\n"+(RegularLevel.holiday == RegularLevel.Holiday.ZQJ ? Messages.get(this, "ask") :Messages.get(this,
						"give_item")),6 );
				break;
			case 3:
				titlebar.icon( new GreatCrabSprite());
				titlebar.label( Messages.get(this, "crab_title") );
				message = PixelScene.renderTextBlock( Messages.get(this, "crab")+"\n\n"+(RegularLevel.holiday == RegularLevel.Holiday.ZQJ ? Messages.get(this, "ask") :Messages.get(this,
						"give_item")),6 );
				break;
		}

		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

		/** 中秋节布局变成三栏*/
		RewardButton btnWeapon = new RewardButton(Ghost.Quest.weapon);
		if(RegularLevel.holiday == RegularLevel.Holiday.ZQJ){
			btnWeapon.setRect((WIDTH - BTN_GAP) / 3f - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE, BTN_SIZE);
			add(btnWeapon);

			RewardButtonDouble btnFood = new RewardButtonDouble(food);
			btnFood.setRect((WIDTH - BTN_GAP) / 3f * 1.5f - BTN_SIZE/2f, message.top() + message.height() + BTN_GAP,
					BTN_SIZE, BTN_SIZE);
			add(btnFood);

			RewardButton btnArmor = new RewardButton(Ghost.Quest.armor);
			btnArmor.setRect((WIDTH - BTN_GAP) / 3f * 2.3f - BTN_SIZE/2f, message.top() + message.height() + BTN_GAP,
					BTN_SIZE, BTN_SIZE);
			add(btnArmor);
			resize(WIDTH, (int) btnArmor.bottom() + BTN_GAP);
		} else {
			btnWeapon.setRect( (WIDTH - BTN_GAP) / 2f - BTN_SIZE, message.top() + message.height() + BTN_GAP,
					BTN_SIZE, BTN_SIZE );
			add( btnWeapon );

			RewardButton btnArmor = new RewardButton( Ghost.Quest.armor );
			btnArmor.setRect( btnWeapon.right() + BTN_GAP, btnWeapon.top(), BTN_SIZE, BTN_SIZE );
			add(btnArmor);
			resize(WIDTH, (int) btnArmor.bottom() + BTN_GAP);
		}
	}
	
	private void selectReward( Item reward ) {
		
		hide();
		
		if (reward == null) return;

		if (reward instanceof Weapon && Ghost.Quest.enchant != null){
			((Weapon) reward).enchant(Ghost.Quest.enchant);
		} else if (reward instanceof Armor && Ghost.Quest.glyph != null){
			((Armor) reward).inscribe(Ghost.Quest.glyph);
		}
		
		reward.identify(false);
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
			if(reward.level() == 4 || reward.level() == 3){
				Badges.GhostDageCollected();
			}
		} else {
			Dungeon.level.drop( reward, ghost.pos ).sprite.drop();
		}

		//准备作为奖励区域
		if(RegularLevel.holiday == RegularLevel.Holiday.ZQJ){
			Dungeon.level.drop( food, ghost.pos ).sprite.drop();
		}
		
		ghost.yell( Messages.get(this, "farewell") );
		ghost.die( null );
		
		Ghost.Quest.complete();

		if(Statistics.RandMode){
			Statistics.goldRefogreCount++;
		}
	}

	private class RewardButton extends Component {

		protected NinePatch bg;
		protected ItemSlot slot;

		public RewardButton( Item item ){
			bg = Chrome.get( Chrome.Type.RED_BUTTON);
			add( bg );

			slot = new ItemSlot( item ){
				@Override
				protected void onPointerDown() {
					bg.brightness( 1.2f );
					Sample.INSTANCE.play( Assets.Sounds.CLICK );
				}
				@Override
				protected void onPointerUp() {
					bg.resetColor();
				}
				@Override
				protected void onClick() {
					GameScene.show(new RewardWindow(item));
				}
			};
			add(slot);
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;
			bg.size( width, height );

			slot.setRect( x + 2, y + 2, width - 4, height - 4 );
		}
	}

	private class RewardButtonDouble extends Component {

		protected NinePatch bg;
		protected ItemSlot slot;

		public RewardButtonDouble( Item item ){
			bg = Chrome.get( Chrome.Type.WINDOW);
			add( bg );

			slot = new ItemSlot( item ){
				@Override
				protected void onPointerDown() {
					bg.brightness( 1.2f );
					Sample.INSTANCE.play( Assets.Sounds.CLICK );
				}
				@Override
				protected void onPointerUp() {
					bg.resetColor();
				}
				@Override
				protected void onClick() {
					GameScene.show(new RewardWindowDouble(item));
				}
			};
			add(slot);
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x;
			bg.y = y;
			bg.size( width, height );

			slot.setRect( x + 2, y + 2, width - 4, height - 4 );
		}
	}

	private class RewardWindowDouble extends WndInfoItem {

		public RewardWindowDouble( Item item ) {
			super(item);

			RedButton btnCancel = new RedButton(Messages.get(WndSadGhost.class, "look")){
				@Override
				protected void onClick() {
					RewardWindowDouble.this.hide();
				}
			};
			btnCancel.setRect(0, height+2, width, 16);
			add(btnCancel);

			resize(width, (int)btnCancel.bottom());
		}
	}

	private class RewardWindow extends WndInfoItem {

		public RewardWindow( Item item ) {
			super(item);

			RedButton btnConfirm = new RedButton(Messages.get(WndSadGhost.class, "confirm")){
				@Override
				protected void onClick() {
					RewardWindow.this.hide();

					WndSadGhost.this.selectReward( item );
				}
			};
			btnConfirm.setRect(0, height+2, width/2-1, 16);
			add(btnConfirm);

			RedButton btnCancel = new RedButton(Messages.get(WndSadGhost.class, "cancel")){
				@Override
				protected void onClick() {
					RewardWindow.this.hide();
				}
			};
			btnCancel.setRect(btnConfirm.right()+2, height+2, btnConfirm.width(), 16);
			add(btnCancel);

			resize(width, (int)btnCancel.bottom());
		}
	}
}