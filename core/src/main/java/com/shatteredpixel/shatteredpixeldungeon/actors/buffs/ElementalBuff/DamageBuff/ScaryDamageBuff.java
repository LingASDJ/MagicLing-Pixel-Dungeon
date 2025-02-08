package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElementalBuff.DamageBuff;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElementalBuff.BaseBuff.ScaryBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElementalBuff.ElementalBaseBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElementalBuff.Immunities.ScaryImmunitiesBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ScaryDamageBuff extends ElementalBaseBuff {

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    public static void beckonEnemies(){
        if (hero.buff(ScaryDamageBuff.class) != null){
            for (Mob m : Dungeon.level.mobs){
                if (m.alignment == Char.Alignment.ENEMY && m.distance(hero) > 8) {
                    m.beckon(hero.pos);
                }
            }
        }
    }

    {
        immunities.add( ScaryBuff.class );
    }

    public static final float DURATION	= 50f;
    private float damageInc = 0;


    private int WithDamage;

    @Override
    public boolean act() {
        if (target.isAlive()) {

            WithDamage++;

            if (--level <= 0) {
                detach();
            }

            damageInc = Random.Int(1,5) + WithDamage/5F;
            target.damage((int)damageInc, this);
            damageInc -= (int)damageInc;
            beckonEnemies();
            spend(interval);

            if (target == hero && !target.isAlive()){
                GLog.n(Messages.get(this, "on_kill"));
            }


        } else {
            detach();
        }

        return true;
    }

    public void detach() {
        super.detach();
        Buff.affect(target, ScaryImmunitiesBuff.class, ScaryImmunitiesBuff.DURATION);
    }

    @Override
    public int icon() {
        return BuffIndicator.SCARY_RED;
    }

    public static final String DAMAGE = "damage_inc";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DAMAGE, damageInc);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        damageInc = bundle.getFloat(DAMAGE);
    }
}

