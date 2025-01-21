package com.shatteredpixel.shatteredpixeldungeon;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeArtifact;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeSeed;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeStaff;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeStone;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeTippedDart;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeTrinket;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeWand;
import static com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation.changeWeapon;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionHero;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ClearBleesdGoodBuff.BlessBossRushLow;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.WaloKe;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TestItem;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.TestBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.DLCItem;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.RushMobScrollOfRandom;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.lightblack.OilLantern;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.Trinket;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.TippedDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndError;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

import java.util.ArrayList;
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
                        }
                    }
                });
            }

        });
    }

    /**
     * 百变模式的嬗变方法
     */
    public static void RandMode_ItemMode() {
        Statistics.RandModeCount++;
        if (Statistics.RandMode) {
            ArrayList<Item> items = Dungeon.hero.belongings.getAllItems(Item.class);
            Item result = items.get(0);

            // 处理武器
            handleWeapon();

            // 遍历物品并进行转换
            for (Item item : items) {
                result = processItem(item, result);
                if (result != null && !shouldSkipItem(item)) {
                    item.detachAll(Dungeon.hero.belongings.backpack);
                }
                dropItemIfNecessary(result);
            }

            // 显示转换效果
            Transmuting.show(Dungeon.hero, new RushMobScrollOfRandom(), new RushMobScrollOfRandom());
            Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
            GLog.p(Messages.get(RushMobScrollOfRandom.class, "recycled"));
        }
    }

    private static void handleWeapon() {
        if (Dungeon.hero.belongings.weapon instanceof Weapon) {
            hero.belongings.weapon = changeWeapon((Weapon) hero.belongings.weapon);
            Dungeon.hero.belongings.weapon.detachAll(Dungeon.hero.belongings.backpack);
            hero.belongings.weapon.identify();
            hero.belongings.weapon.upgrade();
        }
    }

    private static Item processItem(Item item, Item currentResult) {
        Item result = currentResult;

        if (item instanceof MagesStaff) {
            result = changeStaff((MagesStaff) item);
            item.upgrade();
            Dungeon.quickslot.setSlot(0, result);
        } else if (item instanceof TippedDart) {
            result = changeTippedDart((TippedDart) item);
            item.upgrade();
        } else if (item instanceof Wand) {
            item.upgrade();
            result = changeWand((Wand) item);
        } else if (item instanceof Plant.Seed) {
            result = changeSeed((Plant.Seed) item);
        } else if (item instanceof Trinket) {
            result = processTrinket(item);
        } else if (item instanceof Runestone) {
            result = changeStone((Runestone) item);
        } else if (item instanceof Artifact) {
            result = processArtifact(item);
        }



        return result;
    }

    private static Item processTrinket(Item item) {
        if (item.level() < 6) {
            item.detach(Dungeon.hero.belongings.backpack);
            Item result = changeTrinket((Trinket) item);
            result.upgrade();
            return result;
        }
        return item;
    }

    private static Item processArtifact(Item item) {
        Artifact artifact = changeArtifact((Artifact) item);
        if (artifact == null) {
            if (item instanceof OilLantern) {
                Item result = item;
                result.level(0);
                return result;
            } else {
                // 如果没有合适的Artifact，生成一个随机+0戒指
                Item result = Generator.randomUsingDefaults(Generator.Category.RING);
                result.levelKnown = item.levelKnown;
                result.cursed = item.cursed;
                result.cursedKnown = item.cursedKnown;
                result.level(0);
                return result;
            }
        }
        return artifact;
    }

    private static boolean shouldSkipItem(Item item) {
        return item instanceof Bag || item instanceof DLCItem || item instanceof TestItem ||
                item instanceof TestBooks || item instanceof SpiritBow || item instanceof Potion ||
                item instanceof Scroll || item instanceof Waterskin || item instanceof Food ||
                item instanceof Ankh || item instanceof MagesStaff || item.unique;
    }

    private static void dropItemIfNecessary(Item result) {
        if (result != null && !result.collect()) {
            Dungeon.level.drop(result, Dungeon.hero.pos).sprite.drop();
        }
    }


    public static String BannersRules() {
        final Calendar calendar = Calendar.getInstance();
        String banners;

        // 判断是否为节假日

            // 获取当前月份
            int month = calendar.get(Calendar.MONTH); // 注意，月份是从0开始的，0表示1月，11表示12月

            // 根据月份选择季节对应的横幅
            if (month == 2 || month == 3 || month == 4) { // 春季：3, 4, 5月
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
