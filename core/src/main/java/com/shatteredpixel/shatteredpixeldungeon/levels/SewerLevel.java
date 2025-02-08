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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.BGMPlayer;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.AoReadyDragon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Goo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.bosses.notsync.DiedClearElemet;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Nxhy;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.Gudazi;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfAnmy;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.JunglePainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.AlarmTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ChillingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ConfusionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlockTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GatewayTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OozeTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TeleportationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WornDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NxhySprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SewerLevel extends RegularLevel {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
	}

	@Override
	public void playLevelMusic(){
		BGMPlayer.playBGM(Assets.BGM_1, true);
	}
	
	@Override
	protected int standardRooms(boolean forceMax) {
		if (forceMax) return 6;
		//4 to 6, average 5
		return 4+Random.chances(new float[]{1, 3, 1});
	}
	
	@Override
	protected int specialRooms(boolean forceMax) {
		if (forceMax) return 2;
		//1 to 2, average 1.8
		return 1+Random.chances(new float[]{1, 4});
	}
	
	@Override
	protected Painter painter() {
		return new JunglePainter()
				.setWater(feeling == Feeling.WATER ? 0.85f : 0.30f, 5)
				.setGrass(feeling == Feeling.GRASS ? 0.80f : 0.20f, 4)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_SEWERS;
	}
	
	@Override
	public String waterTex() {
		return Assets.Environment.WATER_SEWERS;
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return Dungeon.depth == 1 ?
				new Class<?>[]{ WornDartTrap.class } :
				new Class<?>[]{
						ChillingTrap.class, ShockingTrap.class, ToxicTrap.class, WornDartTrap.class,
						AlarmTrap.class, OozeTrap.class,
						ConfusionTrap.class, FlockTrap.class, SummoningTrap.class, TeleportationTrap.class, GatewayTrap.class };
}

	@Override
	protected float[] trapChances() {
		return Dungeon.depth == 1 ?
				new float[]{1} :
				new float[]{
						4, 4, 4, 4,
						2, 2,
						1, 1, 1, 1, 1};
	}

	private Mob Clearly() {
		List<Class<? extends Mob>> mobTypes = new ArrayList<>();
		mobTypes.add(DiedClearElemet.ClearElemetalBlood.class);
		mobTypes.add(DiedClearElemet.ClearElemetalDark.class);
		mobTypes.add(DiedClearElemet.ClearElemetalGreen.class);
		mobTypes.add(DiedClearElemet.ClearElemetalPure.class);
		mobTypes.add(DiedClearElemet.ClearElemetalGold.class);
		Random.shuffle(mobTypes);
		Class<? extends Mob> selectedMobType = mobTypes.get(0);
		Mob mob = null;
        try {
            mob = selectedMobType.getDeclaredConstructor().newInstance();
			Buff.affect(mob, WandOfAnmy.AllyToRestart.class);
			mob.alignment = Char.Alignment.ALLY;
			mob.pos = entrance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException ignored) {
        }

		return mob;
	}

	@Override
	protected void createMobs() {
		Ghost.Quest.spawn( this,roomExit );
		super.createMobs();
		if(depth == 4 && Badges.isUnlocked(Badges.Badge.KILL_FIRE_DRAGON)){
			for (int i = 0; i < 2; i++) {
				Mob testActor = Clearly();
				mobs.add(testActor);
			}
			AoReadyDragon aoReadyDragon = new AoReadyDragon();
			aoReadyDragon.pos = exit();
			mobs.add(aoReadyDragon);
		}

		if(Dungeon.depth == 4 && Dungeon.branch == 0 && Statistics.gdzHelpDungeon == 1){
			Gudazi npc20 = new Gudazi();
			npc20.pos = entrance()-1;
			mobs.add(npc20);
		}
	}

	@Override
	protected void createItems() {
		if(Dungeon.depth == 1){
			drop(new ScrollOfUpgrade(), exit()-1 );
		}
		super.createItems();
	}
	
	@Override
	public boolean activateTransition(Hero hero, LevelTransition transition) {
		if (transition.type == LevelTransition.Type.BRANCH_EXIT) {


			if (Statistics.gooFight){
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GLog.w(Messages.get(Goo.class, "cant_enter_a"));
					}
				});
				return false;
			}

			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show( new WndOptions( new NxhySprite(),
							Messages.titleCase(Messages.get(Nxhy.class, "name")),
							Messages.get(Goo.class, "reason"),
							Messages.get(Goo.class, "enter_yes"),
							Messages.get(Goo.class, "enter_no")){
						@Override
						protected void onSelect(int index) {
							if (index == 0){
								SewerLevel.super.activateTransition(hero, transition);
							}
						}
					} );
				}
			});
		} else if(transition.type == LevelTransition.Type.SURFACE) {
			if (hero.belongings.getItem(Amulet.class) == null) {
				Game.runOnRenderThread(new Callback() {
					@Override
					public void call() {
						GameScene.show(new WndMessage(Messages.get(hero, "leave")));
					}
				});
            } else {
				TimekeepersHourglass.timeFreeze timeFreeze = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
				if (timeFreeze != null) timeFreeze.disarmPresses();
				Swiftthistle.TimeBubble timeBubble = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
				if (timeBubble != null) timeBubble.disarmPresses();
				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				InterlevelScene.curTransition = new LevelTransition();
				InterlevelScene.curTransition.destDepth = depth-1;
				InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_EXIT;
				InterlevelScene.curTransition.destBranch = 0;
				InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_EXIT;
				InterlevelScene.curTransition.centerCell = -1;
				Game.switchScene(InterlevelScene.class);
            }
            return false;
        } else {
			return super.activateTransition(hero,transition);
		}
		return false;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			case Terrain.STATUE:case Terrain.STATUE_SP:
				return Messages.get(SewerLevel.class, "statue_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(SewerLevel.class, "statue_desc");
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
}
