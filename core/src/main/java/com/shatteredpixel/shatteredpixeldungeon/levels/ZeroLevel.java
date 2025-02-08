/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.CS;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GameRules;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.PaswordBadges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NxhyNpc;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Nyz;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.REN;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Slyl;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.obSir;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.BzmdrLand;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.WaloKe;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.YetYog;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.BossRushBloodGold;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.DLCItem;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.RushMobScrollOfRandom;
import com.shatteredpixel.shatteredpixeldungeon.items.journal.Guidebook;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.RandomChest;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.SakaFishSketon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHardNotification;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class ZeroLevel extends Level {
    private static final int[] pre_map = {190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 161, 4, 4, 4, 0, 0, 4, 0, 0, 0, 2, 0, 161, 190, 0, 0, 0, 0, 0, 0, 0, 190, 161, 0, 2, 0, 0, 0, 0, 4, 0, 0, 0, 0, 161, 190, 190, 0, 4, 16, 4, 0, 0, 4, 0, 0, 0, 2, 0, 0, 190, 0, 0, 0, 0, 0, 0, 0, 190, 0, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 4, 0, 4, 0, 0, 4, 0, 2, 0, 2, 0, 2, 190, 0, 0, 4, 4, 0, 0, 0, 190, 2, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 0, 190, 190, 0, 4, 0, 4, 0, 0, 4, 0, 0, 2, 2, 2, 0, 190, 0, 4, 0, 1, 4, 0, 0, 190, 0, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 4, 0, 4, 0, 0, 4, 0, 0, 0, 2, 0, 0, 190, 0, 4, 2, 3, 4, 0, 0, 190, 0, 0, 2, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 4, 0, 4, 0, 0, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 190, 190, 0, 4, 0, 4, 0, 0, 4, 0, 1, 4, 0, 0, 0, 190, 0, 4, 0, 1, 4, 0, 0, 190, 0, 0, 0, 4, 0, 0, 0, 1, 4, 4, 4, 4, 4, 190, 190, 0, 4, 4, 4, 0, 0, 4, 2, 3, 4, 0, 0, 0, 190, 0, 4, 2, 3, 4, 0, 0, 190, 0, 0, 0, 4, 0, 1, 1, 1, 4, 0, 0, 0, 0, 190, 190, 4, 4, 4, 2, 4, 4, 4, 4, 4, 4, 0, 0, 0, 190, 0, 0, 4, 4, 0, 0, 0, 190, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 190, 190, 0, 0, 0, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 0, 4, 0, 0, 0, 190, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 2, 2, 2, 2, 2, 2, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 4, 0, 4, 0, 0, 190, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 0, 0, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 4, 0, 4, 0, 4, 0, 190, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 161, 0, 0, 2, 0, 0, 4, 0, 0, 0, 0, 0, 161, 190, 4, 0, 0, 0, 0, 0, 4, 190, 161, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 161, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 190, 123, 123, 123, 123, 123, 123, 123, 190, 4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 0, 4, 0, 1, 4, 4, 0, 0, 0, 0, 4, 0, 190, 123, 98, 4, 10, 4, 98, 123, 190, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 0, 0, 4, 2, 3, 4, 4, 0, 0, 0, 4, 0, 0, 190, 123, 4, 0, 85, 0, 4, 123, 190, 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 0, 80, 123, 10, 85, 17, 85, 10, 123, 80, 0, 4, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 190, 190, 0, 1, 4, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 190, 123, 4, 0, 85, 0, 4, 123, 190, 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 2, 3, 4, 0, 0, 0, 4, 0, 0, 0, 0, 4, 0, 190, 123, 98, 4, 10, 4, 98, 123, 190, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 4, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 190, 123, 123, 123, 123, 123, 123, 123, 190, 4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 80, 190, 190, 190, 190, 190, 190, 190, 124, 124, 124, 124, 124, 124, 4, 124, 124, 124, 124, 124, 124, 190, 4, 0, 0, 0, 0, 0, 4, 190, 161, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 161, 190, 190, 124, 124, 124, 124, 124, 124, 4, 124, 124, 124, 124, 124, 124, 190, 0, 4, 0, 4, 0, 4, 0, 190, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 4, 0, 4, 0, 0, 190, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 0, 4, 0, 0, 0, 190, 0, 0, 0, 0, 4, 0, 1, 4, 0, 1, 4, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 0, 4, 0, 0, 0, 190, 0, 0, 0, 0, 4, 2, 3, 4, 2, 3, 4, 0, 0, 190, 190, 124, 124, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 19, 4, 4, 4, 4, 4, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 0, 0, 0, 4, 0, 0, 0, 190, 0, 0, 2, 0, 4, 0, 1, 4, 0, 1, 4, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 4, 4, 4, 4, 4, 4, 4, 190, 0, 0, 2, 0, 4, 2, 3, 4, 2, 3, 4, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 4, 0, 1, 4, 0, 1, 4, 190, 0, 0, 2, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 4, 2, 3, 4, 2, 3, 4, 190, 0, 0, 2, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 124, 124, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 190, 4, 4, 4, 4, 4, 4, 4, 190, 2, 0, 2, 0, 2, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 190, 0, 0, 0, 4, 0, 0, 0, 190, 0, 2, 2, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0, 190, 190, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 124, 190, 0, 0, 0, 4, 0, 0, 0, 190, 161, 0, 2, 0, 0, 0, 0, 4, 0, 0, 0, 0, 161, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190};

    {
        color1 = 5459774;
        color2 = 12179041;
    }

    public ZeroLevel() {
        this.viewDistance = SPDSettings.intro() ? 4 : 15;
    }

    private int mapToTerrain(int var1) {
        if (var1 == 1 || var1 == 2 || var1 == 3) {
            return 29;
        }
        if (var1 != 4) {
            if (var1 == 16) {
                return 7;
            }
            if (var1 == 17) {
                return 8;
            }
            switch (var1) {
                case -2147483644:
                    break;
                case -2147483584:
                case 64:
                case 190:
                    return 4;
                case -2147483550:
                case 98:
                    return 25;
                case -2147483524:
                case 124:
                case 140:
                    return 27;
                case 69:
                case 161:
                    return 12;
                case 80:
                    return SPDSettings.intro() ? Terrain.SECRET_DOOR : Terrain.DOOR;
                case 85:
                    return 11;
                case 96:
                    return 23;
                case 120:
                    return 20;
                case 123:
                    return 29;
                default:
                    return 1;
            }
        }
        return 14;
    }

    protected boolean build() {
        setSize(37, 37);

        int exitCell = (this.width * 18 + 18);
        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        int enterCell = (this.width * 2) + 3;
        LevelTransition enter = new LevelTransition(this, enterCell, LevelTransition.Type.SURFACE);
        transitions.add(enter);

        for (int var1 = 0; var1 < this.map.length; var1++) {
            this.map[var1] = mapToTerrain(pre_map[var1]);
        }
        return true;
    }



    protected void createItems() {

        //places the first guidebook page on floor 1
        if (Dungeon.depth == 0 &&
                (!Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_INTRO) || SPDSettings.intro() )){
            drop( new Guidebook(),  (this.width * 2) + 4 );
        }


            drop( ( Generator.randomUsingDefaults( Generator.Category.POTION ) ), this.width * 17 + 16 );

            drop( ( Generator.randomUsingDefaults( Generator.Category.SCROLL ) ), this.width * 20 + 17 );

            drop( new RandomChest(), this.width * 19 + 20  ).type = Heap.Type.FOR_ICE;

            drop( ( Generator.randomUsingDefaults( Generator.Category.FOOD ) ), this.width * 16 + 19 );
    }

    public Mob createMob() {
        return null;
    }

    public static int[] SALEPOS_ONE = new int[]{
        970,1008
    };

    public static int[] SALEPOS_TWO = new int[]{
            1004,968
    };

    public static int[] SALEPOS_THREE = new int[]{
            1116,1078
    };

    public static int[] SALEPOS_FOUR = new int[]{
            1118,1082
    };

    public static int[] POSSALE = new int[]{
           969,1041,1117,1045
    };

    protected void createMobs() {
        if(!Dungeon.isChallenged(CS)) {
            REN n = new REN();
            n.pos = (this.width * 18 + 16);
            mobs.add(n);

            Slyl npc1 = new Slyl();
            npc1.pos = (this.width * 16 + 18);
            mobs.add(npc1);

            obSir npc2 = new obSir();
            npc2.pos = (this.width * 20 + 18);
            mobs.add(npc2);

            NxhyNpc npc3 = new NxhyNpc();
            npc3.pos = (this.width * 18 + 20);
            mobs.add(npc3);
        }

        WaloKe god1= new WaloKe();
        god1.pos = 289;
        mobs.add(god1);

        BzmdrLand god2= new BzmdrLand();
        god2.pos = 340;
        mobs.add(god2);

        if (Badges.isUnlocked(Badges.Badge.NYZ_SHOP)){

            YetYog npc3 = new YetYog();
            npc3.pos = 1066;
            mobs.add(npc3);

            Nyz npc4= new Nyz();
            npc4.pos = (this.width * 28 + 7);
            mobs.add(npc4);
            for (int i : SALEPOS_ONE) {
                drop((Generator.random(Generator.Category.MISSILE)), i).type =
                        Heap.Type.FOR_SALE;
            }
            for (int i : SALEPOS_TWO) {
                drop((Generator.random(Generator.Category.POTION)), i).type =
                        Heap.Type.FOR_SALE;
            }
            for (int i : SALEPOS_THREE) {
                drop((Generator.random(Generator.Category.WEAPON)), i).type =
                        Heap.Type.FOR_SALE;
            }
            for (int i : SALEPOS_FOUR) {
                drop((Generator.random(Generator.Category.SCROLL)), i).type =
                        Heap.Type.FOR_SALE;
            }
            for (int i : POSSALE) {
                drop((Generator.random(Generator.Category.SEED)), i).type =
                        Heap.Type.FOR_SALE;
            }
       }
    }

    @Override
    public boolean activateTransition(Hero hero, LevelTransition transition) {
        if (transition.type == LevelTransition.Type.BRANCH_ENTRANCE) {
            TimekeepersHourglass.timeFreeze timeFreeze = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
            if (timeFreeze != null) timeFreeze.disarmPresses();
            Swiftthistle.TimeBubble timeBubble = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
            if (timeBubble != null) timeBubble.disarmPresses();
            InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
            InterlevelScene.curTransition = new LevelTransition();
            InterlevelScene.curTransition.destDepth = depth;
            InterlevelScene.curTransition.destType = LevelTransition.Type.BRANCH_EXIT;
            InterlevelScene.curTransition.destBranch = 1;
            InterlevelScene.curTransition.type = LevelTransition.Type.BRANCH_EXIT;
            InterlevelScene.curTransition.centerCell = -1;
            Game.switchScene(InterlevelScene.class);
            return false;
        } else if (transition.type == LevelTransition.Type.SURFACE){

            if (hero.belongings.getItem( Amulet.class ) == null) {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show( new WndMessage( Messages.get(hero, "leave") ) );
                    }
                });
                return false;
            } else {
                Statistics.ascended = true;
                Badges.silentValidateHappyEnd();
                Dungeon.win( Amulet.class );
                Dungeon.deleteGame( GamesInProgress.curSlot, true );
                Game.switchScene( SurfaceScene.class );
                if (hero.belongings.getItem(SakaFishSketon.class) != null) {
                    PaswordBadges.REHOMESKY();
                }
                return true;
            }
        } else if (transition.type == LevelTransition.Type.REGULAR_EXIT) {

            if (hero.belongings.getItem( DLCItem.class ) == null) {

                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show( new WndHardNotification(new ItemSprite(ItemSpriteSheet.DLCBOOKS),
                                Messages.get(hero, "dlc_name"),
                                Messages.get(hero, "leave_more_dead"),
                                "OK",
                                0));
                    }
                });
                return false;
            } else if(hero.belongings.getItem( BossRushBloodGold.class ) != null && Statistics.deepestFloor == 0) {
                GameRules.BossRush();
                return false;
            } else if(hero.belongings.getItem( RushMobScrollOfRandom.class ) != null && Statistics.deepestFloor == 0) {
                GameRules.RandMode();
                return false;
            } else {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(hero, "leave_more_dead")));
                    }
                });
                return false;
            }

        } else {
            return super.activateTransition(hero, transition);
        }
    }

    public int randomRespawnCell() {
        return this.entrance - width();
    }

    public String tilesTex() {
        return Assets.Environment.RELOAD;
    }

    public String waterTex() {
        return Assets.Environment.WATER_PRISON;
    }
//    public void occupyCell(Char ch) {
//        super.occupyCell(ch);
//        GLog.p(String.valueOf(hero.pos));
//        GLog.b(String.valueOf(Statistics.zeroItemLevel));
//    }
}
