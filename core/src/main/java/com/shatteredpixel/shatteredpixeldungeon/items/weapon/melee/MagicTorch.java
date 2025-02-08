package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Daze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LighS;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.MagicFlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;


/**
 * 魔法火把
 */
public class MagicTorch extends MeleeWeapon {

    /**
     * 重写父类的属性
     * @param tier 等级
     * @param image 图片
     * @param ACC 命中率
     */ {
        image = ItemSpriteSheet.MAGIC_TORCH;
        tier = 3;
        ACC = 0.8f;
    }

    /**
     * 重写父类的方法
     *
     * @param lvl 等级
     * @return 最小伤害
     */
    @Override
    public int max(int lvl) {
        return 5 * (tier) +    //base
                lvl * (tier + 2);   //level scaling
    }

    /**
     * 重写父类的方法
     *
     * @param lvl 等级
     * @return 最大伤害
     */
    @Override
    public int STRReq(int lvl) {
        int req = (6 + tier * 3) - (int) (Math.sqrt(8 * lvl + 1) - 1) / 2;
        if (masteryPotionBonus){
            req -= 2;
        }
        return req;
    }

    /**
     * 重写父类的方法
     *
     * @param hero   英雄
     * @param action 动作
     */
    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (AC_EQUIP.equals(action)) {
            if (Dungeon.hero.buff(LighS.class) != null || Dungeon.hero.buff(Light.class) != null) {
                GLog.n(Messages.get(this,"mustload"));
            } else {
                Buff.affect(hero, MagicLight.class).set((100), 1);
            }

        }
    }

    /**
     * 重写父类的proc方法
     *
     * @param attacker 攻击者
     * @param defender 防御者
     * @param damage   伤害
     */
    @Override
    public int proc(Char attacker, Char defender, int damage) {
        /**
         * @param defender 防御者
         * @param Burning.class 伤害类型
         * @param hero 伤害来源
         * @param 4f + level() / 5f 伤害值
         */
        Buff.affect(defender, Burning.class).reignite(hero, 4f + level() / 5f);

        return super.proc(attacker, defender, damage);
    }


    public static class MagicLight extends FlavourBuff {
        /**
         * @param target 目标来源
         * @param type 类型
         * @param DISTANCE 照明范围
         * @param act 停止回滚
         */
        {
            type = buffType.POSITIVE;
        }

        @Override
        public void detach() {
            target.viewDistance = Dungeon.level.viewDistance;
            Dungeon.observe();
            super.detach();
        }

        public static int level = 0;
        private int interval = 1;
        public static final int DISTANCE	= 4;
        @Override
        public boolean attachTo( Char target ) {
            if (super.attachTo( target )) {
                if (Dungeon.level != null) {
                    target.viewDistance = Math.max( Dungeon.level.viewDistance, DISTANCE );
                    Dungeon.observe();
                }
                return true;
            } else {
                return false;
            }
        }
//        MagicTorch item = Dungeon.hero.belongings.getItem(MagicTorch.class);
        @Override
        public boolean act() {
            if (target.isAlive()) {

                spend(interval);
                if (level <= 0 || !(hero.belongings.weapon instanceof MagicTorch)) {
                    detach();
                }

            }

            return true;
        }

        public int level() {
            return level;
        }

        public void set( int value, int time ) {
            //decide whether to override, preferring high value + low interval
            if (Math.sqrt(interval)*level <= Math.sqrt(time)*value) {
                level = value;
                interval = time;
                spend(time - cooldown() - 1);
            }
        }

        @Override
        public float iconFadePercent() {
            return 1f;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public void fx(boolean on) {
            if (on) target.sprite.add(CharSprite.State.ILLUMINATED);
            else target.sprite.remove(CharSprite.State.ILLUMINATED);
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", level, dispTurns(visualcooldown()));
        }

        private static final String LEVEL	    = "level";
        private static final String INTERVAL    = "interval";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( INTERVAL, interval );
            bundle.put( LEVEL, level );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            interval = bundle.getInt( INTERVAL );
            level = bundle.getInt( LEVEL );
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x808080);
        }

        @Override
        public int icon() {
            return BuffIndicator.LIGHT;
        }

    }

    @Override
    public Emitter emitter() {
        Emitter emitter = new Emitter();
        emitter.pos(12.5f, 3);
        emitter.fillTarget = false;
        emitter.pour(StaffParticleFactory, 0.1f);
        return emitter;
    }


    private final Emitter.Factory StaffParticleFactory = new Emitter.Factory() {
        /**
         * @param emitter 目标来源
         * @param index 特效来源
         * @param x,y 位置
         */
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((MagicFlameParticle)emitter.recycle( MagicFlameParticle.class )).reset( x, y+3 );
        }
        @Override
        public boolean lightMode() {
            return true;
        }
    };

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected int baseChargeUse(Hero hero, Char target){
        if (target == null || (target instanceof Mob && ((Mob) target).surprisedBy(hero))) {
            return 1;
        } else {
            return 4;
        }
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        MagicTorch.heavyFireBlowAbility(hero, target, 1.30f, this);
    }

    public static void heavyFireBlowAbility(Hero hero, Integer target, float dmgMulti, MeleeWeapon wep){
        if (target == null) {
            return;
        }

        Char enemy = Actor.findChar(target);
        if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(wep, "ability_no_target"));
            return;
        }

        hero.belongings.abilityWeapon = wep;
        if (!hero.canAttack(enemy)){
            GLog.w(Messages.get(wep, "ability_bad_position"));
            hero.belongings.abilityWeapon = null;
            return;
        }
        hero.belongings.abilityWeapon = null;

        //need to separately check charges here, as non-surprise attacks cost 2
        if (enemy instanceof Mob && !((Mob) enemy).surprisedBy(hero)){
            Charger charger = Buff.affect(hero, Charger.class);
            if (Dungeon.hero.belongings.weapon == wep) {
                if (charger.charges + charger.partialCharge < wep.abilityChargeUse(hero, enemy)){
                    GLog.w(Messages.get(wep, "ability_no_charge"));
                    return;
                }
            } else {
                if (charger.secondCharges + charger.secondPartialCharge < wep.abilityChargeUse(hero, enemy)){
                    GLog.w(Messages.get(wep, "ability_no_charge"));
                    return;
                }
            }
        }

        hero.sprite.attack(enemy.pos, new Callback() {
            @Override
            public void call() {
                wep.beforeAbilityUsed(hero, enemy);
                AttackIndicator.target(enemy);
                if (hero.attack(enemy, dmgMulti, 0, Char.INFINITE_ACCURACY)) {
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                    if (enemy.isAlive()){
                        Buff.affect(enemy, Daze.class, Daze.DURATION+1f);
                    } else {
                        wep.onAbilityKill(hero, enemy);
                    }
                }
                Invisibility.dispel();
                hero.spendAndNext(hero.attackDelay());
                wep.afterAbilityUsed(hero);
            }
        });
    }


}
