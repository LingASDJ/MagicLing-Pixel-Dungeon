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

package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Regrowth;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GoldenMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.WondrousResin;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.io.IOException;
import java.util.ArrayList;

//helper class to contain all the cursed wand zapping logic, so the main wand class doesn't get huge.
public class CursedWand {

	public static void cursedZap(final Item origin, final Char user, final Ballistica bolt, final Callback afterZap){

		boolean positiveOnly = user == Dungeon.hero && Random.Float() < WondrousResin.positiveCurseEffectChance();
		CursedEffect effect = randomValidEffect(origin, user, bolt, positiveOnly);
		//CursedEffect effect = new InterFloorTeleport();

		effect.FX(origin, user, bolt, new Callback() {
			@Override
			public void call() {
				effect.effect(origin, user, bolt, positiveOnly);
				afterZap.call();
			}
		});
	}

	public static void tryForWandProc( Char target, Item origin ){
		if (target != null && target != Dungeon.hero && origin instanceof Wand){
			Wand.wandProc(target, origin.buffedLvl(), 1);
		}
	}

	//*** Cursed Effects ***

	public static abstract class CursedEffect {

		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
			return true;
		}

		public void FX(final Item origin, final Char user, final Ballistica bolt, final Callback callback){
			MagicMissile.boltFromChar(user.sprite.parent,
					MagicMissile.RAINBOW,
					user.sprite,
					bolt.collisionPos,
					callback);
			Sample.INSTANCE.play( Assets.Sounds.ZAP );
		}

		public abstract boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly);

	}

	// common/uncommon/rare/v.rare have a 60/30/9/1% chance respectively
	private static float[] EFFECT_CAT_CHANCES = new float[]{60, 30, 9, 1};

	public static CursedEffect randomEffect(){
		switch (Random.chances(EFFECT_CAT_CHANCES)){
			case 0: default:
				return randomCommonEffect();
			case 1:
				return randomUncommonEffect();
			case 2:
				return randomRareEffect();
			case 3:
				return randomVeryRareEffect();
		}
	}

	public static CursedEffect randomValidEffect(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
		switch (Random.chances(EFFECT_CAT_CHANCES)){
			case 0: default:
				return randomValidCommonEffect(origin, user, bolt, positiveOnly);
			case 1:
				return randomValidUncommonEffect(origin, user, bolt, positiveOnly);
			case 2:
				return randomValidRareEffect(origin, user, bolt, positiveOnly);
			case 3:
				return randomValidVeryRareEffect(origin, user, bolt, positiveOnly);
		}
	}

	//**********************
	//*** Common Effects ***
	//**********************

	private static ArrayList<CursedEffect> COMMON_EFFECTS = new ArrayList<>();
	static {
		COMMON_EFFECTS.add(new BurnAndFreeze());
		COMMON_EFFECTS.add(new SpawnRegrowth());
		COMMON_EFFECTS.add(new RandomTeleport());
		COMMON_EFFECTS.add(new RandomGas());
	}

	public static CursedEffect randomCommonEffect(){
		return Random.element(COMMON_EFFECTS);
	}

	public static CursedEffect randomValidCommonEffect(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
		CursedEffect effect;
		do {
			effect = Random.element(COMMON_EFFECTS);
		} while (!effect.valid(origin, user, bolt, positiveOnly));
		return effect;
	}

	public static class BurnAndFreeze extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Char target = Actor.findChar(bolt.collisionPos);
			//doesn't affect caster if positive only
			if (Random.Int(2) == 0) {
				if (target != null) Buff.affect(target, Burning.class).reignite(target);
				if (!positiveOnly) Buff.affect(user, Frost.class, Frost.DURATION);
			} else {
				if (!positiveOnly)Buff.affect(user, Burning.class).reignite(user);
				if (target != null) Buff.affect(target, Frost.class, Frost.DURATION);
			}
			tryForWandProc(target, origin);
			return true;
		}
	}

	public static class SpawnRegrowth extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean postiveOnly) {
			if (Actor.findChar(bolt.collisionPos) == null){
				Dungeon.level.pressCell(bolt.collisionPos);
			}
			GameScene.add( Blob.seed(bolt.collisionPos, 30, Regrowth.class));
			tryForWandProc(Actor.findChar(bolt.collisionPos), origin);
			return true;
		}
	}

	public static class RandomTeleport extends CursedEffect {
		@Override
		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Char target = Actor.findChar(bolt.collisionPos);
			if (positiveOnly && (target == null || Char.hasProp(target, Char.Property.IMMOVABLE))){
				return false;
			}
			return true;
		}

		//might be nice to have no fx if self teleports?

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Char target = Actor.findChar( bolt.collisionPos );
			//can only teleport target if positive only
			if (target != null && !Char.hasProp(target, Char.Property.IMMOVABLE) && (positiveOnly || Random.Int(2) == 0)){
				ScrollOfTeleportation.teleportChar(target);
				tryForWandProc(target, origin);
				return true;
			} else {
				if (positiveOnly || user == null || Char.hasProp(user, Char.Property.IMMOVABLE)){
					return false;
				} else {
					ScrollOfTeleportation.teleportChar(user);
					return true;
				}
			}
		}
	}

	public static class RandomGas extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Sample.INSTANCE.play( Assets.Sounds.GAS );
			tryForWandProc(Actor.findChar(bolt.collisionPos), origin);
			if (Actor.findChar(bolt.collisionPos) == null){
				Dungeon.level.pressCell(bolt.collisionPos);
			}
			switch (Random.Int(3)) {
				case 0: default:
					GameScene.add( Blob.seed( bolt.collisionPos, 800, ConfusionGas.class ) );
					return true;
				case 1:
					GameScene.add( Blob.seed( bolt.collisionPos, 500, ToxicGas.class ) );
					return true;
				case 2:
					GameScene.add( Blob.seed( bolt.collisionPos, 200, ParalyticGas.class ) );
					return true;
			}
		}
	}

	//************************
	//*** Uncommon Effects ***
	//************************

	private static ArrayList<CursedEffect> UNCOMMON_EFFECTS = new ArrayList<>();
	static {
		UNCOMMON_EFFECTS.add(new RandomPlant());
		UNCOMMON_EFFECTS.add(new HealthTransfer());
		UNCOMMON_EFFECTS.add(new Explosion());
		UNCOMMON_EFFECTS.add(new ShockAndRecharge());
	}

	public static CursedEffect randomUncommonEffect(){
		return Random.element(UNCOMMON_EFFECTS);
	}

	public static CursedEffect randomValidUncommonEffect(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
		CursedEffect effect;
		do {
			effect = Random.element(UNCOMMON_EFFECTS);
		} while (!effect.valid(origin, user, bolt, positiveOnly));
		return effect;
	}

	public static class RandomPlant extends CursedEffect {

		@Override
		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			int pos = bolt.collisionPos;

			if (Dungeon.level.map[pos] != Terrain.ALCHEMY
					&& !Dungeon.level.pit[pos]
					&& Dungeon.level.traps.get(pos) == null
					&& !Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			if (valid(origin, user, bolt, positiveOnly)) {
				Dungeon.level.plant((Plant.Seed) Generator.randomUsingDefaults(Generator.Category.SEED), bolt.collisionPos);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class HealthTransfer extends CursedEffect {

		@Override
		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			return Actor.findChar( bolt.collisionPos ) != null;
		}

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			final Char target = Actor.findChar( bolt.collisionPos );
			if (target != null) {
				int damage = Dungeon.scalingDepth() * 2;
				Char toHeal, toDamage;

				//can only harm target if positive only
				if (positiveOnly || Random.Int(2) == 0){
					toHeal = user;
					toDamage = target;
				} else {
					toHeal = target;
					toDamage = user;
				}
				toHeal.HP = Math.min(toHeal.HT, toHeal.HP + damage);
				toHeal.sprite.emitter().burst(Speck.factory(Speck.HEALING), 3);
				toHeal.sprite.showStatusWithIcon( CharSprite.POSITIVE, Integer.toString(damage), FloatingText.HEALING );

				toDamage.damage(damage, new CursedWand());
				toDamage.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);

				if (toDamage == Dungeon.hero){
					Sample.INSTANCE.play(Assets.Sounds.CURSED);
					if (!toDamage.isAlive()) {
						if (user == Dungeon.hero && origin != null) {
							Badges.validateDeathFromFriendlyMagic();
							Dungeon.fail( origin );
							GLog.n( Messages.get( CursedWand.class, "ondeath", origin.name() ) );
						} else {
							Badges.validateDeathFromEnemyMagic();
							Dungeon.fail( toHeal );
						}
					}
				} else {
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
				}
				tryForWandProc(target, origin);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class Explosion extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			new Bomb.ConjuredBomb().explode(bolt.collisionPos);
			tryForWandProc(Actor.findChar(bolt.collisionPos), origin);
			return true;
		}
	}

	public static class ShockAndRecharge extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			//no shock if positive only
			if (!positiveOnly) new ShockingTrap().set( user.pos ).activate();
			Buff.prolong(user, Recharging.class, Recharging.DURATION);
			ScrollOfRecharging.charge(user);
			SpellSprite.show(user, SpellSprite.CHARGE);
			return true;
		}
	}

	//********************
	//*** Rare Effects ***
	//********************

	private static ArrayList<CursedEffect> RARE_EFFECTS = new ArrayList<>();
	static {
		RARE_EFFECTS.add(new SheepPolymorph());
		RARE_EFFECTS.add(new CurseEquipment());
		RARE_EFFECTS.add(new InterFloorTeleport());
		RARE_EFFECTS.add(new SummonMonsters());
	}

	public static CursedEffect randomRareEffect(){
		return Random.element(RARE_EFFECTS);
	}

	public static CursedEffect randomValidRareEffect(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
		CursedEffect effect;
		do {
			effect = Random.element(RARE_EFFECTS);
		} while (!effect.valid(origin, user, bolt, positiveOnly));
		return effect;
	}

	public static class SheepPolymorph extends CursedEffect {

		@Override
		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Char ch = Actor.findChar( bolt.collisionPos );
			if (ch != null && !(ch instanceof Hero)
					//ignores bosses, questgivers, rat king, etc.
					&& !ch.properties().contains(Char.Property.BOSS)
					&& !ch.properties().contains(Char.Property.MINIBOSS)
					&& !(ch instanceof NPC && ch.alignment == Char.Alignment.NEUTRAL)){
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			if (valid(origin, user, bolt, positiveOnly)){
				Char ch = Actor.findChar( bolt.collisionPos );
				Sheep sheep = new Sheep();
				sheep.lifespan = 10;
				sheep.pos = ch.pos;
				ch.destroy();
				ch.sprite.killAndErase();
				Dungeon.level.mobs.remove(ch);
				TargetHealthIndicator.instance.target(null);
				GameScene.add(sheep);
				CellEmitter.get(sheep.pos).burst(Speck.factory(Speck.WOOL), 4);
				Sample.INSTANCE.play(Assets.Sounds.PUFF);
				Sample.INSTANCE.play(Assets.Sounds.SHEEP);
				Dungeon.level.occupyCell(sheep);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class CurseEquipment extends CursedEffect {

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			//hexes target if positive only or user isn't hero
			if (positiveOnly || !(user instanceof Hero)){
				Char ch = Actor.findChar( bolt.collisionPos );
				if (ch != null){
					Buff.affect(ch, Hex.class, Hex.DURATION);
				}
				return true;
			} else {
				CursingTrap.curse( (Hero) user );
				return true;
			}
		}
	}

	public static class InterFloorTeleport extends CursedEffect {

		@Override
		public void FX(Item origin, Char user, Ballistica bolt, Callback callback) {
			callback.call(); //no vfx
		}

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			if (!positiveOnly && Dungeon.depth > 1 && Dungeon.interfloorTeleportAllowed() && user == Dungeon.hero) {

				//each depth has 1 more weight than the previous depth.
				float[] depths = new float[Dungeon.depth-1];
				for (int i = 1; i < Dungeon.depth; i++) depths[i-1] = i;
				int depth = 1+Random.chances(depths);

				Level.beforeTransition();
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = depth;
				InterlevelScene.returnBranch = 0;
				InterlevelScene.returnPos = -1;
				Game.switchScene(InterlevelScene.class);

			//scroll of teleportation if positive only, or inter-floor teleport disallowed
			} else {
				ScrollOfTeleportation.teleportChar(user);

			}
			return true;
		}
	}

	public static class SummonMonsters extends CursedEffect {

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			//mirror images if positive only and user is hero
			if (positiveOnly && user == Dungeon.hero){
				ScrollOfMirrorImage.spawnImages(Dungeon.hero, bolt.collisionPos, 2);
			} else {
				new SummoningTrap().set(bolt.collisionPos).activate();
			}
			return true;
		}
	}

	//*************************
	//*** Very Rare Effects ***
	//*************************

	private static ArrayList<CursedEffect> VERY_RARE_EFFECTS = new ArrayList<>();
	static {
		VERY_RARE_EFFECTS.add(new ForestFire());
		VERY_RARE_EFFECTS.add(new SpawnGoldenMimic());
		VERY_RARE_EFFECTS.add(new AbortRetryFail());
		VERY_RARE_EFFECTS.add(new RandomTransmogrify());
	}

	public static CursedEffect randomVeryRareEffect(){
		return Random.element(VERY_RARE_EFFECTS);
	}

	public static CursedEffect randomValidVeryRareEffect(Item origin, Char user, Ballistica bolt, boolean positiveOnly){
		CursedEffect effect;
		do {
			effect = Random.element(VERY_RARE_EFFECTS);
		} while (!effect.valid(origin, user, bolt, positiveOnly));
		return effect;
	}

	public static class ForestFire extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			for (int i = 0; i < Dungeon.level.length(); i++){
				GameScene.add( Blob.seed(i, 15, Regrowth.class));
			}

			new Flare(8, 32).color(0xFFFF66, true).show(user.sprite, 2f);
			Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
			GLog.p(Messages.get(CursedWand.class, "grass"));
			//only grass, no fire, if positive only
			if (!positiveOnly) {
				GLog.w(Messages.get(CursedWand.class, "fire"));
				do {
					GameScene.add(Blob.seed(Dungeon.level.randomDestination(null), 10, Fire.class));
				} while (Random.Int(5) != 0);
			}
			return true;
		}
	}

	public static class SpawnGoldenMimic extends CursedEffect {
		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			Char ch = Actor.findChar(bolt.collisionPos);
			int spawnCell = bolt.collisionPos;
			if (ch != null){
				ArrayList<Integer> candidates = new ArrayList<Integer>();
				for (int n : PathFinder.NEIGHBOURS8) {
					int cell = bolt.collisionPos + n;
					if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
						candidates.add( cell );
					}
				}
				if (!candidates.isEmpty()){
					spawnCell = Random.element(candidates);
				} else {
					return false;
				}
			}

			Mimic mimic = Mimic.spawnAt(spawnCell, GoldenMimic.class, false);
			mimic.stopHiding();
			mimic.alignment = Char.Alignment.ENEMY;
			//play vfx/sfx manually as mimic isn't in the scene yet
			Sample.INSTANCE.play(Assets.Sounds.MIMIC, 1, 0.85f);
			CellEmitter.get(mimic.pos).burst(Speck.factory(Speck.STAR), 10);
			mimic.items.clear();
			GameScene.add(mimic);

			//mimic is enthralled, but also contains no extra reward, if positive only
			if (positiveOnly){
				Buff.affect(mimic, ScrollOfSirensSong.Enthralled.class);
			} else {
				Item reward;
				do {
					reward = Generator.randomUsingDefaults(Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR,
							Generator.Category.RING, Generator.Category.WAND));
				} while (reward.level() < 1);
				mimic.items.add(reward);
			}

			Dungeon.level.occupyCell(mimic);

			return true;
		}
	}

	public static class AbortRetryFail extends CursedEffect {

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			//appears to crash the game (actually just closes it)
			try {
				Dungeon.saveAll();
				if(Messages.lang() != Languages.ENGLISH){
					//Don't bother doing this joke to none-english speakers, I doubt it would translate.
					//we still consider the effect valid here though as it's cosmetic anyway
					return false;
				} else {
					ShatteredPixelDungeon.runOnRenderThread(
							new Callback() {
								@Override
								public void call() {
									GameScene.show(
											new WndOptions(Icons.get(Icons.WARNING),
													"CURSED WAND ERROR",
													"this application will now self-destruct",
													"abort",
													"retry",
													"fail") {

												@Override
												protected void onSelect(int index) {
													Game.instance.finish();
												}

												@Override
												public void onBackPressed() {
													//do nothing
												}
											}
									);
								}
							}
					);
					return false;
				}
			} catch(IOException e){
				ShatteredPixelDungeon.reportException(e);
				//maybe don't kill the game if the save failed, just do nothing
				return false;
			}
		}
	}

	public static class RandomTransmogrify extends CursedEffect {

		@Override
		public boolean valid(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			if (positiveOnly){
				return true;
			} else if (origin == null || user != Dungeon.hero || !Dungeon.hero.belongings.contains(origin)){
				return false;
			} else {
				return true;
			}
		}

		@Override
		public boolean effect(Item origin, Char user, Ballistica bolt, boolean positiveOnly) {
			//triggers metamorph effect if positive only
			if (positiveOnly){
				GameScene.show(new ScrollOfMetamorphosis.WndMetamorphChoose());
				return true;
			}

			//skips this effect if there is no item to transmogrify
			if (origin == null || user != Dungeon.hero || !Dungeon.hero.belongings.contains(origin)){
				return false;
			}
			origin.detach(Dungeon.hero.belongings.backpack);
			Item result;
			do {
				result = Generator.randomUsingDefaults(Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR,
						Generator.Category.RING, Generator.Category.ARTIFACT));
			} while (result.cursed);
			if (result.isUpgradable()) result.upgrade();
			result.cursed = result.cursedKnown = true;
			if (origin instanceof Wand){
				GLog.w( Messages.get(CursedWand.class, "transmogrify_wand") );
			} else {
				GLog.w( Messages.get(CursedWand.class, "transmogrify_other") );
			}
			Dungeon.level.drop(result, user.pos).sprite.drop();
			return true;
		}
	}

}
