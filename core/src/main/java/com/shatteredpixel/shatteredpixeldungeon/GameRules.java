package com.shatteredpixel.shatteredpixeldungeon;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionHero;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ClearBleesdGoodBuff.BlessBossRushLow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.status.QuestGold;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.WaloKe;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndError;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.Calendar;
import java.util.List;

public class GameRules {

    /**
     * BossRush模式
     */
    public static void BossRush() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndOptions(new ShopkKingSprite(),
                        Messages.titleCase(Messages.get(WaloKe.class, "name")),
                        Messages.get(WaloKe.class, "quest_start_prompt"),
                        Messages.get(WaloKe.class, "easy"),
                        Messages.get(WaloKe.class, "normal"),
                        Messages.get(WaloKe.class, "hard"),
                        Messages.get(WaloKe.class, "hell")) {
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0) {
                            TimekeepersHourglass.timeFreeze timeFreeze = hero.buff(TimekeepersHourglass.timeFreeze.class);
                            if (timeFreeze != null) timeFreeze.disarmPresses();
                            Swiftthistle.TimeBubble timeBubble = hero.buff(Swiftthistle.TimeBubble.class);
                            if (timeBubble != null) timeBubble.disarmPresses();
                            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                            InterlevelScene.curTransition = new LevelTransition();
                            InterlevelScene.curTransition.destDepth = depth+1;
                            InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.destBranch = 0;
                            InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.centerCell = -1;
                            Game.switchScene(InterlevelScene.class);
                            Buff.affect(hero, BlessBossRushLow.class, ChampionHero.DURATION*123456f);
                            Statistics.difficultyDLCEXLevel = 1;
                            Statistics.bossRushMode = true;
                            Dungeon.gold = 0;
                            Dungeon.rushgold = 16;
                        } else if (index == 1) {
                            TimekeepersHourglass.timeFreeze timeFreeze = hero.buff(TimekeepersHourglass.timeFreeze.class);
                            if (timeFreeze != null) timeFreeze.disarmPresses();
                            Swiftthistle.TimeBubble timeBubble = hero.buff(Swiftthistle.TimeBubble.class);
                            if (timeBubble != null) timeBubble.disarmPresses();
                            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                            InterlevelScene.curTransition = new LevelTransition();
                            InterlevelScene.curTransition.destDepth = depth + 1;
                            InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.destBranch = 0;
                            InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.centerCell = -1;
                            Game.switchScene(InterlevelScene.class);
                            Statistics.difficultyDLCEXLevel = 2;
                            Statistics.bossRushMode = true;
                            Dungeon.gold = 0;
                            Dungeon.rushgold = 16;
                        } else if (index == 2) {
                            TimekeepersHourglass.timeFreeze timeFreeze = hero.buff(TimekeepersHourglass.timeFreeze.class);
                            if (timeFreeze != null) timeFreeze.disarmPresses();
                            Swiftthistle.TimeBubble timeBubble = hero.buff(Swiftthistle.TimeBubble.class);
                            if (timeBubble != null) timeBubble.disarmPresses();
                            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                            InterlevelScene.curTransition = new LevelTransition();
                            InterlevelScene.curTransition.destDepth = depth + 1;
                            InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.destBranch = 0;
                            InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_EXIT;
                            InterlevelScene.curTransition.centerCell = -1;
                            Game.switchScene(InterlevelScene.class);
                            Statistics.difficultyDLCEXLevel = 3;
                            Statistics.bossRushMode = true;
                            Dungeon.gold = 0;
                            Dungeon.rushgold = 16;
                        } else if (index == 3) {
                            PaswordBadges.loadGlobal();
                            List<PaswordBadges.Badge> passwordbadges = PaswordBadges.filtered(true);
                            if (passwordbadges.contains(PaswordBadges.Badge.BRCLER)) {
                                TimekeepersHourglass.timeFreeze timeFreeze = hero.buff(TimekeepersHourglass.timeFreeze.class);
                                if (timeFreeze != null) timeFreeze.disarmPresses();
                                Swiftthistle.TimeBubble timeBubble = hero.buff(Swiftthistle.TimeBubble.class);
                                if (timeBubble != null) timeBubble.disarmPresses();
                                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                                InterlevelScene.curTransition = new LevelTransition();
                                InterlevelScene.curTransition.destDepth = depth + 1;
                                InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_EXIT;
                                InterlevelScene.curTransition.destBranch = 0;
                                InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_EXIT;
                                InterlevelScene.curTransition.centerCell = -1;
                                Game.switchScene(InterlevelScene.class);
                                Statistics.difficultyDLCEXLevel = 4;
                                Statistics.bossRushMode = true;
                                Dungeon.gold = 0;
                                Dungeon.rushgold = 16;
                            } else {
                                Game.scene().add( new WndError( Messages.get(WaloKe.class, "br_no_clear") ) );
                            }
                        }
                    }
                });
            }

        });
    }

    /**
     * RandMode模式
     */
    public static void RandMode() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndOptions(new ShopkKingSprite(),
                        Messages.titleCase(Messages.get(WaloKe.class, "name")),
                        Messages.get(WaloKe.class, "quest_start2_prompt"),
                        Messages.get(WaloKe.class, "randmode")) {
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0) {
                            TimekeepersHourglass.timeFreeze timeFreeze = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
                            if (timeFreeze != null) timeFreeze.disarmPresses();
                            Swiftthistle.TimeBubble timeBubble = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
                            if (timeBubble != null) timeBubble.disarmPresses();
                            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                            InterlevelScene.curTransition = new LevelTransition();
                            InterlevelScene.curTransition.destDepth = depth+1;
                            InterlevelScene.curTransition.destType = LevelTransition.Type.REGULAR_ENTRANCE;
                            InterlevelScene.curTransition.destBranch = 0;
                            InterlevelScene.curTransition.type = LevelTransition.Type.REGULAR_ENTRANCE;
                            InterlevelScene.curTransition.centerCell = -1;
                            Game.switchScene(InterlevelScene.class);
                            Statistics.RandMode = true;
                            Buff.affect(hero, QuestGold.class).set((100), 1);
                        }
                    }
                });
            }

        });
    }

    public static String BannersRules() {
        final Calendar calendar = Calendar.getInstance();
        String banners;

        // 判断是否为节假日

            // 获取当前月份
            int month = calendar.get(Calendar.MONTH); // 注意，月份是从0开始的，0表示1月，11表示12月
        if(RegularLevel.holiday == RegularLevel.Holiday.YX) {
            boolean year = Random.Float() <= 0.5f;
            if (!SPDSettings.ClassUI()) {
                banners = Assets.Interfaces.BANNERS_YX;
            } else {
                banners = Assets.Interfaces.BANNERS_BD;
            }
        }else if(RegularLevel.holiday == RegularLevel.Holiday.CJ) {
            banners = Assets.Interfaces.BANNERS_CJ;
        } else if (month == 2 || month == 3 || month == 4) { // 春季：3, 4, 5月
                banners = Assets.Interfaces.BANNERS_SR;
            } else if (month == 5 || month == 6 || month == 7) { // 夏季：6, 7, 8月
                banners = Assets.Interfaces.BANNERS_SM;
            } else if (month == 8 || month == 9 || month == 10) { // 秋季：9, 10, 11月
                banners = Assets.Interfaces.BANNERS_AT;
            } else { // 冬季：12, 1, 2月
                banners = Assets.Interfaces.BANNERS_WT;
            }


        return banners;
    }



}
