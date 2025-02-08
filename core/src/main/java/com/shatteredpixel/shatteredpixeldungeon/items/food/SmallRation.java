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

package com.shatteredpixel.shatteredpixeldungeon.items.food;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.EXSG;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SmallRation extends Food {

	public static class BlackMoon extends SmallRation{
		{
			image = ItemSpriteSheet.BLACKMOON;
		}
	}


	@Override
	protected void satisfy( Hero hero ){
		if (Dungeon.isChallenged(Challenges.NO_FOOD)){
			Buff.affect(hero, Hunger.class).satisfy(energy/3f);
		} else {
			Buff.affect(hero, Hunger.class).satisfy(energy);
		}
	}

	{
		image = ItemSpriteSheet.OVERPRICED;
		energy = Hunger.HUNGRY/2f;
	}

	@Override
	public int value() {
		return 10 * quantity;
	}
	@Override
	public String desc() {
		//三元一次逻辑运算
		return Dungeon.isChallenged(EXSG) ? Messages.get(this, "descx") : Messages.get(this, "desc");
	}
}
