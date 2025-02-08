package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.hollow;

import static com.shatteredpixel.shatteredpixeldungeon.BGMPlayer.playBGM;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ElementalBuff.BaseBuff.ScaryBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SlimeKing;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.MimicTooth;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SakaFishBossSprites;
import com.shatteredpixel.shatteredpixeldungeon.sprites.VampireSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Vampire extends Mob {

    public boolean hereEnenmy;

    {
        spriteClass = VampireSprite.class;

        HP = HT = 120;

        defenseSkill = 15;

        baseSpeed = 2f;

        EXP = 15;
        maxLvl = 35;

        flying = true;

        properties.add( Property.HOLLOW );
        properties.add(Char.Property.DEMONIC);
        properties.add(Char.Property.UNDEAD);
    }

    @Override
    public int attackSkill(Char target) {
        return 40;
    }

    @Override
    public boolean act() {
        if (!hereEnenmy && Dungeon.level.heroFOV[pos] && Dungeon.level.distance(pos, hero.pos) <= 1) {
            hereEnenmy = true;
        }

        if(hereEnenmy){
            activate();
        }

        return super.act();
    }


    public void activate(){
        ((VampireSprite) sprite).activateIdle();
    }

    @Override
    public String name() {
        if (hereEnenmy){
            return Messages.get(this, "true_name");
        } else {
            return super.name();
        }
    }

    @Override
    public String description() {
        if (hereEnenmy){
            return Messages.get(this, "true_desc");
        } else {
            return super.description();
        }
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(36, 52);
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(2, 4);
    }

    /***
     吸血鬼：每次攻击有33.3%概率附带中毒，有33.3%概率附带虚弱，
     有33.3%概率附带80%效率的吸血，有100%概率增加心魔损伤。3
     3.3%概率的几个效果可以同时触发，纯看脸，也可能都不触发。
     死亡后有概率掉落1个不纯净血袋（
     使用时50%概率中毒50%概率一次性回复少量生命（不触发恐药））
     */
    @Override
    public int attackProc( Char enemy, int damage ) {
        //33% 中毒
        if(Random.Float() <= 33f){
            Buff.affect( enemy, Poison.class ).set(Random.Int(5, 7) );
        }
        //33% 虚弱
        if(Random.Float() <= 33f){
            Buff.affect( enemy, Weakness.class ).set(Random.Int(5, 7) );
        }
        int reg = (int) Math.min( damage * 0.8f, HT - HP );
        //33% 吸血
        if(Random.Float() <= 33f){
            HP += reg;
            sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(reg), FloatingText.HEALING);
        }
        //100% 心魔损伤
        if(enemy!=null && enemy == hero) {
            for (Buff buff : hero.buffs()) {
                if (buff instanceof ScaryBuff) {
                    ((ScaryBuff) buff).damgeScary( Random.NormalIntRange(6,15));
                } else {
                    Buff.affect( enemy, ScaryBuff.class ).set( (100), 1 );
                }
            }
        }
        return damage;
    }

    private static final String HERE_ENEMY   = "hereEnemy";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(HERE_ENEMY, hereEnenmy);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        hereEnenmy = bundle.getBoolean(HERE_ENEMY);
    }

}

