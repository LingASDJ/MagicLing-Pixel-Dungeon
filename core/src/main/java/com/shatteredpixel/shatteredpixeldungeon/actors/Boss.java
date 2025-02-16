package com.shatteredpixel.shatteredpixeldungeon.actors;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.isDLC;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.VenomGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.SmallLeafHardDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin;
import com.shatteredpixel.shatteredpixeldungeon.items.KingGold;
import com.shatteredpixel.shatteredpixeldungeon.items.KingsCrown;
import com.shatteredpixel.shatteredpixeldungeon.items.TengusMask;
import com.shatteredpixel.shatteredpixeldungeon.items.props.Prop;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

abstract public class Boss extends Mob {
        {
            if(Statistics.difficultyDLCEXLevel==4){
                immunities.add(Terror.class);
            }
        }
        protected boolean SprintableModeBoolean = (Statistics.bossRushMode || Dungeon.isDLC(Conducts.Conduct.DEV));

        protected static float baseMin; //最小伤害
        protected static float baseMax; //最大伤害
        protected static float baseAcc; //命中率
        protected static float baseEva; //闪避率
        protected static float baseHT; //生命值
        protected static float baseMinDef; //最小防御
        protected static float baseMaxDef; //最大防御

        protected void initProperty() {
            properties.add(Property.BOSS); //添加BOSS属性
            immunities.add(Grim.class); //添加Grim类
            immunities.add(ScrollOfPsionicBlast.class); //添加ScrollOfPsionicBlast类
            immunities.add(ScrollOfRetribution.class); //添加ScrollOfRetribution类
            immunities.add(Corruption.class);
        }

    /**
     *
     * @param min 最小伤害
     * @param max 最大伤害
     * @param acc 命中率
     * @param eva 闪避率
     * @param ht 生命值
     * @param mid 最小防御
     * @param mad 最大防御
     */
        protected void initBaseStatus(float min, float max, float acc, float eva, float ht, float mid, float mad) {
            baseMin = min;
            baseMax = max;
            baseAcc = acc;
            baseEva = eva;
            baseHT = ht;
            baseMinDef = mid;
            baseMaxDef = mad;
        }

    public void die( Object cause ) {
        super.die(cause);

        if(Statistics.NightDreamLoop){
            Statistics.NightDreamLoop = false;
        }

            ArrayList<IceCyanBlueSquareCoin> ice = hero.belongings.getAllItems(IceCyanBlueSquareCoin.class);
            if(ice != null){
                for (IceCyanBlueSquareCoin w : ice.toArray(new IceCyanBlueSquareCoin[0])){
                    w.detachAll(hero.belongings.backpack);
                    if(SPDSettings.Cheating()){
                        //盗版蓝币只有正版的十分之一
                        SPDSettings.iceCoin(w.quantity/10);
                    } else {
                        SPDSettings.iceCoin(w.quantity);
                    }

                }
            }

            if((isDLC(Conducts.Conduct.HARD) || isDLC(Conducts.Conduct.DEV)) && DeviceCompat.isMidTest() && (new ArrayList<>(Arrays.asList(5,10,15,20)).contains(Dungeon.depth) && Dungeon.branch ==0 )){
                SmallLeafHardDungeon smallLeafHardDungeon = new SmallLeafHardDungeon();
                smallLeafHardDungeon.pos = pos;
                Dungeon.level.mobs.add(smallLeafHardDungeon);
                GameScene.add( smallLeafHardDungeon );
                Dungeon.level.occupyCell( smallLeafHardDungeon );

                if(Dungeon.depth == 10){
                    Prop p1 = Prop.randomPropA();
                    Prop p2 = Prop.randomPropB();
                    p1.collect();
                    p2.collect();
                    GLog.i(Messages.get(hero, "you_now_have", p1.name()));
                    GLog.i(Messages.get(hero, "you_now_have", p2.name()));
                }

                if(Dungeon.depth == 20){
                    Prop p1 = Prop.randomPropA(1);
                    Prop p2 = Prop.randomPropB(1);
                    p1.collect();
                    p2.collect();
                    GLog.i(Messages.get(hero, "you_now_have", p1.name()));
                    GLog.i(Messages.get(hero, "you_now_have", p2.name()));
                }
            }

            if(Statistics.RandMode && Dungeon.depth == 10){
                Dungeon.level.drop(new TengusMask(),pos);
            }

            if(Statistics.RandMode && Dungeon.depth == 20){
                Dungeon.level.drop(new KingsCrown(),pos);
            }

            if(Statistics.bossRushMode){
                Dungeon.level.drop(new KingGold(Random.NormalIntRange(3+Dungeon.depth/5,5+Dungeon.depth/5)),pos);
            }

            final Calendar calendar = Calendar.getInstance();
            boolean holiday = false;

            if (calendar.get(Calendar.MONTH) == Calendar.MAY) {
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                if (dayOfMonth >= 2 && dayOfMonth <= 20)
                    holiday = true;
            }

            if(!Statistics.bossRushMode){
                if(Challenges.activeChallenges()>9){

                    if(holiday){
                        Dungeon.level.drop(new IceCyanBlueSquareCoin(((5*(Dungeon.depth/5)) * (Challenges.activeChallenges() / 5)) * 2),pos);
                    } else {
                        Dungeon.level.drop(new IceCyanBlueSquareCoin(((5*(Dungeon.depth/5)) * (Challenges.activeChallenges() / 5))),pos);
                    }

                } else {

                    if(holiday){
                        Dungeon.level.drop(new IceCyanBlueSquareCoin(5*(Dungeon.depth/5) * 2),pos);
                    } else {
                        Dungeon.level.drop(new IceCyanBlueSquareCoin(5*(Dungeon.depth/5)),pos);
                    }
                }
            }
    }

    @Override
    protected boolean act(){
        if(Blob.volumeAt(pos, VenomGas.class) == 0 && venodamage!=0) {
            venodamage = 0;
        }
        return super.act();
    }

    @Override
    public float attackDelay() {
        if (Statistics.gameNight) {
            return 0.75f;
        } else {
            return 1f;
        }
    }

        protected void initStatus(int exp) {
            defenseSkill = Math.round(baseEva); //闪避率
            EXP = exp; //经验值
            HP = HT = Math.round(baseHT); //生命值


        }

        @Override
        public int damageRoll() {
            if(Statistics.gameNight){
                return Math.round(Random.NormalFloat( baseMin*1.25f, baseMax*1.25f )); //随机伤害
            } else {
                return Math.round(Random.NormalFloat( baseMin, baseMax )); //随机伤害
            }

        }

        @Override
        public int attackSkill( Char target ) {
            return Math.round(baseAcc); //命中率
        }

        @Override
        public int drRoll() {

            return Math.round(Random.NormalFloat(baseMinDef, baseMaxDef)); //随机防御
        }
    private boolean first=false;

    private static final String FIRST = "first";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(FIRST, first);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        first = bundle.getBoolean(FIRST);
        if (state != SLEEPING) BossHealthBar.assignBoss(this);
        if ((HP*2 <= HT)) BossHealthBar.bleed(true);
    }

    public void RollCS(){
        Class<?extends ChampionEnemy> buffCls;
        switch (Random.Int(4)){
            case 0: default:    buffCls = ChampionEnemy.Blazing.class;      break;
            case 1:             buffCls = ChampionEnemy.Projecting.class;   break;
            case 2:             buffCls = ChampionEnemy.Blessed.class;      break;
            case 3:             buffCls = ChampionEnemy.Halo.class;      	break;
        }
        Buff.affect(this, buffCls);
        this.state = this.WANDERING;
    }

    public void RollEX(){
        Class<?extends ChampionEnemy> buffCls2;
        float roll = Random.Float(); // 生成 0.0~1.0 的随机数

        if (roll < 0.05f) { // 5% 概率生成酸液体
            buffCls2 = ChampionEnemy.Sider.class;
        } else {
            // 剩余95%概率由其他三种类型均分（各约31.67%）
            switch (Random.Int(3)){
                case 0: default: buffCls2 = ChampionEnemy.Middle.class;   break;
                case 1:         buffCls2 = ChampionEnemy.LongSider.class;break;
                case 2:         buffCls2 = ChampionEnemy.Big.class;      break;
            }
        }

        Buff.affect(this, buffCls2);
        this.state = this.WANDERING;
    }



    @Override
    public void notice() {
        super.notice();
        if (Statistics.bossRushMode && !(Dungeon.depth == 2 || Dungeon.depth == 4 || Dungeon.depth == 24 || Dungeon.depth == 27)){
            if(!first){
                if(Statistics.difficultyDLCEXLevel >= 3){
                    RollEX();
                    RollCS();
                } else if (Statistics.difficultyDLCEXLevel == 2){
                    RollCS();
                }
                first = true;
            }
        }
    }


}
