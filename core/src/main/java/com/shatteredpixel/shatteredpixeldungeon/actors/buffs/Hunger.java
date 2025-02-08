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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.IconFloatingText;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.SaltCube;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class Hunger extends Buff implements Hero.Doom {
	public static final float STARVINGR	= 20f;
	private static final float STEP	= 10f;

	public static final float HUNGRY	= 300f;
	public static final float STARVING	= 450f;

	public static final float ARVING	= 300f;
	private float level;
	private float partialDamage;

	private static final String LEVEL			= "level";
	private static final String PARTIALDAMAGE 	= "partialDamage";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( LEVEL, level );
		bundle.put( PARTIALDAMAGE, partialDamage );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getFloat( LEVEL );
		partialDamage = bundle.getFloat(PARTIALDAMAGE);
	}

	/** 额外饥饿加剧
	 * Author:JDSA Ling
	 * Date:2023-12-24
	 * @param value 传额外饥饿整形值
	 */
	public void damgeExtraHungry(int value) {
		float newLevel = level + STEP;
		newLevel -= Math.min(newLevel + value, 450f);
		target.sprite.showStatusWithIcon(CharSprite.NEGATIVE, String.valueOf(value), IconFloatingText.HUNGRY_EXTRA);
		level -= newLevel;
	}

	@Override
	public boolean act() {

		if (Dungeon.level.locked
				|| target.buff(WellFed.class) != null
				|| target.buff(ScrollOfChallenge.ChallengeArena.class) != null || Dungeon.depth == 0){
			spend(STEP);
			return true;
		}

//		//TODO 高于90% +2 血量
//		if(hero.buff(BlessGoRead.class) != null && level >= 405f && hero.HP != hero.HT){
//			Buff.affect(hero, HealingXP.class).setHeal((int) (2), 0, 0);
//			spend(2f);
//		}

		if (target.isAlive() && target instanceof Hero) {

			Hero hero = (Hero)target;

			if (isStarving()) {

				partialDamage += STEP * target.HT/1000f;

				if (partialDamage > 1){
					target.damage( (int)partialDamage, this);
					partialDamage -= (int)partialDamage;
				}
				
			} else {

				float newLevel = level + STEP;
				if (newLevel >= STARVING) {

					GLog.n( Messages.get(this, "onstarving") );
					hero.resting = false;

					if(!Statistics.noGoReadHungry) Statistics.noGoReadHungry = true;

					hero.damage( 1, this );

					hero.interrupt();

				} else if (newLevel >= HUNGRY && level < HUNGRY) {

					GLog.w( Messages.get(this, "onhungry") );

					if (!Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_FOOD)){
						GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_FOOD);
					}

				}

				level = newLevel;

			}

			float hungerDelay = STEP;
			if (target.buff(Shadows.class) != null){
				hungerDelay *= 1.5f;
			}
			hungerDelay /= SaltCube.hungerGainMultiplier();
			
			spend( hungerDelay );

		} else {

			diactivate();

		}

		return true;
	}

	public void satisfy( float energy ) {

		Artifact.ArtifactBuff buff = target.buff( HornOfPlenty.hornRecharge.class );
		if (buff != null && buff.isCursed()){
			energy *= 0.67f;
			GLog.n( Messages.get(this, "cursedhorn") );
		}

		affectHunger( energy, false );
	}

	public void affectHunger(float energy ){
		affectHunger( energy, false );
	}

	public void affectHunger(float energy, boolean overrideLimits ) {

		if (energy < 0 && target.buff(WellFed.class) != null){
			target.buff(WellFed.class).left += energy;
			BuffIndicator.refreshHero();
			return;
		}

		level -= energy;
		if (level < 0 && !overrideLimits) {
			level = 0;
		} else if (level > STARVING) {
			float excess = level - STARVING;
			level = STARVING;
			partialDamage += excess * (target.HT/1000f);
		}

		BuffIndicator.refreshHero();
	}

	public boolean isStarving() {
		return level >= STARVING;
	}

	public boolean isDied() {
		float newLevel = level + STEP;
		return newLevel >= HUNGRY && level < HUNGRY;
	}

	public int hunger() {
		return (int)Math.ceil(level);
	}

	public int hungerDamage() {
		int hunger;
		Hunger hungerBuff = hero.buff(Hunger.class);
		if(hungerBuff != null){
			hunger = (int) Math.max(0, STARVING - hunger());
		} else {
			hunger = 100;
		}

		return hunger/50;
	}

	public int hungerNoWEDamage(){
		int hunger;
		Hunger hungerBuff = hero.buff(Hunger.class);
		if(hungerBuff != null){
			hunger = (int) Math.max(0, STARVING - hunger());
		} else {
			hunger = 75;
		}
		return hunger/75;
	}

	@Override
	public int icon() {
		if (level < HUNGRY) {
			if(Statistics.noGoReadHungry) Statistics.noGoReadHungry = false;
			return BuffIndicator.NONE;
		} else if (level < STARVING) {
			if(Statistics.noGoReadHungry) Statistics.noGoReadHungry = false;
			return BuffIndicator.HUNGER;
		} else {
			if(!Statistics.noGoReadHungry) Statistics.noGoReadHungry = true;
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String name() {
		if (level < STARVING) {
			return Messages.get(this, "hungry");
		} else {
			return Messages.get(this, "starving");
		}
	}

	@Override
	public String desc() {
		String result;
		if (level < STARVING) {
			result = Messages.get(this, "desc_intro_hungry");
		} else {
			result = Messages.get(this, "desc_intro_starving");
		}

		result += Messages.get(this, "desc");

		return result;
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromHunger();

		Dungeon.fail( getClass() );
		GLog.n( Messages.get(this, "ondeath") );
	}
}
