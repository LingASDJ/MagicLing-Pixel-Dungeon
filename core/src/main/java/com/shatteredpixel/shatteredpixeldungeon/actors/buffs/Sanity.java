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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Sanity extends Buff {

    @Override
    //改变buff样子
    public int icon() {
        if(sanity >= 180) {
            return BuffIndicator.BLESS;
        } else if(sanity >= 120) {
            return BuffIndicator.VERTIGO;
        } else {
            return BuffIndicator.CORRUPT;
        }
    }

    {
        type = buffType.POSITIVE;
    }
    public int pos;
    public static int sanity = 0;
    private int interval = 1;

    @Override
    public boolean act() {
        if (target.isAlive()) {

            spend(interval);
            /*
            * San值系统
            * Copy Right 2022 JDSALing
            * GPLV3
            * AnsdoShip Stuido
            * */
            //San值检测系统 V1
            if ( (hero.buff(Light.class)==null) ) {
                if (sanity >= 180) {
                    //-1-(1/10)+2=-1-0.1+2=0
                    sanity -= Dungeon.depth/5+1;
                    if(sanity == 179) {
                        //GLog.w("我感到身体十分的沉重……奇怪的话语在你耳边环绕……");
                        GLog.w(Messages.get(Sanity.class,"bad"));
                    }
                } else if (sanity >= 120) {
                    sanity -= Dungeon.depth/5+2;
                    if(sanity == 119) {
                        //GLog.n("我在干什么……邪恶的灵魂正在蚕食你……");
                        GLog.n(Messages.get(Sanity.class,"tobad"));
                    }
                } else {
                    //彻底san为0生成暗影，扣除金币导入暗影库存并击中暗影将背包给予他
                    if(--sanity-(Dungeon.depth/5) == 0){
                        detach();
                        target.die(this);
                        //GLog.n("你彻底失去理智了……你的灵魂彻底裂开了……");
                        GLog.n(Messages.get(Sanity.class,"diedsoul"));
                    }
                }
            } else {
                //San值冷却系统
                if((Dungeon.hero.buff(SanityColdDown.class)==null && sanity!=240)){
                    Buff.affect(hero, SanityColdDown.class).set( (6-Dungeon.depth/5),1);
                    sanity+=Dungeon.depth/5+1;
                }
            }
        }

        return true;
    }

    public void set( int value, int time ) {
        //decide whether to override, preferring high value + low interval
        if (Math.sqrt(interval)*sanity <= Math.sqrt(time)*value) {
            sanity = value;
            interval = time;
            spend(time - cooldown() - 1);
        }
    }

    @Override
    public String iconTextDisplay() {
        return Integer.toString(sanity);
    }

    public static final float STARVING	= 240f;
    @Override
    public float iconFadePercent() {
        return Math.max(0, (Sanity.STARVING - sanity) / Sanity.STARVING);
    }

    @Override
    public String toString() {

        if (sanity >= 180) {
            return Messages.get(this, "name");
        } else if (sanity >= 120) {
            return Messages.get(this, "name2");
        } else {
            return Messages.get(this, "name3");
        }
    }



    @Override
    public String desc() {
        if (sanity >= 180) {
            return Messages.get(this, "normal_desc", sanity, dispTurns(visualcooldown()));
        } else if (sanity >= 120) {
            return Messages.get(this, "crazy_desc", sanity, dispTurns(visualcooldown()));
        } else {
            return Messages.get(this, "died_desc", sanity, dispTurns(visualcooldown()));
        }
    }

    private static final String LEVEL	    = "level";
    private static final String INTERVAL    = "interval";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( INTERVAL, interval );
        bundle.put( LEVEL, sanity );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        interval = bundle.getInt( INTERVAL );
        sanity = bundle.getInt( LEVEL );
    }


    @Override
    public void tintIcon(Image icon) {

        if (sanity >= 180) {
            icon.hardlight(0x00ff00);
        } else if (sanity >= 120) {
            icon.hardlight(0xffff00);
        } else {
            icon.hardlight(0xff0000);
        }
    }

}