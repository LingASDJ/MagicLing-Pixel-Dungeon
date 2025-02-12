/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SzSprites;
import com.watabou.utils.Random;

public class Slime_Sz extends Slime {
    {
        spriteClass = SzSprites.class;
        maxLvl = -200;
        properties.add(Property.ACIDIC);
        HP = HT = 50;
    }

    @Override
    public int attackSkill( Char target ) {
        return 60;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(20, 25);
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 5);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        if (Random.Int( 2 ) == 0) {
            Buff.affect( enemy, Slow.class ).set( Slow.DURATION/5 );
            enemy.sprite.burst( 0x000000, 5 );
        }

        return super.attackProc( enemy, damage );
    }
}
