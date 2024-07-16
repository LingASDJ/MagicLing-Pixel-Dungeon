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

package com.shatteredpixel.shatteredpixeldungeon.items.trinkets;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SaltCube extends Trinket {

	{
		image = ItemSpriteSheet.SALT_CUBE;
	}

	@Override
	protected int upgradeEnergyCost() {
		//5 -> 8(13) -> 10(23) -> 12(35)
		return 6+2*level();
	}

	@Override
	public String statsDesc() {
		if (isIdentified()){
			return Messages.get(this,
					"stats_desc",
					Messages.decimalFormat("#.##", 100*(1f-hungerGainMultiplier(buffedLvl()))),
					Messages.decimalFormat("#.##", 100*(1f-healthRegenMultiplier(buffedLvl()))));
		} else {
			return Messages.get(this,
					"typical_stats_desc",
					Messages.decimalFormat("#.##", 100*(1f-hungerGainMultiplier(buffedLvl()))),
					Messages.decimalFormat("#.##", 100*(1f-healthRegenMultiplier(buffedLvl()))));
		}
	}

	public static float hungerGainMultiplier(){
		return hungerGainMultiplier(trinketLevel(SaltCube.class));
	}

	public static float hungerGainMultiplier( int level ){
		if (level == -1){
			return 1;
		} else {
			return 1f - 0.125f*(level+1);
		}
	}

	public static float healthRegenMultiplier(){
		return healthRegenMultiplier(trinketLevel(SaltCube.class));
	}

	public static float healthRegenMultiplier( int level ){
		if (level == -1){
			return 1;
		} else {
			return 1f - 0.25f*(level+1);
		}
	}

}
