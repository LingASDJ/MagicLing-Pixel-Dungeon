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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LighS;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.lightblack.OilLantern;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagicTorch;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

public class Torch extends Item {

	public static final String AC_LIGHT	= "LIGHT";
	
	public static final float TIME_TO_LIGHT = 1;
	
	{
		image = ItemSpriteSheet.TORCH;
		
		stackable = true;
		
		defaultAction = AC_LIGHT;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_LIGHT );
		return actions;
	}

	public void Refill(OilLantern torch) {
		torch.plingks++;
		detach(Dungeon.hero.belongings.backpack);
	}

	@Override
	public boolean doPickUp(Hero hero, int pos) {
		if (super.doPickUp(hero, pos) && Statistics.AutoOilPotion) {
			OilLantern lantern = Dungeon.hero.belongings.getItem(OilLantern.class);
			if(lantern!=null){
				Refill(lantern);
				GLog.p(Messages.get(Torch.class,"lanterfireactive",Math.min(Math.max(55 - (10 * Statistics.deepestFloor / 5) - Challenges.activeChallenges() / 4, 10), 100)));
			} else {
				GLog.p(Messages.get(Torch.class,"youmustload"));
			}
        }
        return true;
    }
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );
		
		if (action.equals( AC_LIGHT )) {

			if(Statistics.lanterfireactive){
				OilLantern lantern = Dungeon.hero.belongings.getItem(OilLantern.class);
				if(lantern!=null){
					Refill(lantern);
					GLog.p(Messages.get(Torch.class,"lanterfireactive",Math.min(Math.max(55 - (10 * Statistics.deepestFloor / 5) - Challenges.activeChallenges() / 4, 10), 100)));
				} else {
					GLog.p(Messages.get(Torch.class,"youmustload"));
				}

			} else if (Dungeon.hero.buff(LighS.class) != null || Dungeon.hero.buff(MagicTorch.MagicLight.class) != null) {
				GLog.n(Messages.get(Torch.class,"mustload"));
			} else {
				hero.spend( TIME_TO_LIGHT );
				hero.busy();

				hero.sprite.operate( hero.pos );

				detach( hero.belongings.backpack );

				Buff.affect(hero, Light.class, Light.DURATION);
				Sample.INSTANCE.play(Assets.Sounds.BURNING);

				Emitter emitter = hero.sprite.centerEmitter();
				emitter.start( FlameParticle.FACTORY, 0.2f, 3 );
			}
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int value() {
		return 8 * quantity;
	}

}
