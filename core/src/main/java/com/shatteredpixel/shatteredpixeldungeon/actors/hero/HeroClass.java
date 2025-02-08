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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.PaswordBadges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ClearBleesdGoodBuff.BlessLing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RandomBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.CustomPlayer;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.CustomWeapon;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.LevelTeleporter;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.MobPlacer;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnArmor;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnArtifact;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnMisc;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnMissile;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnRingOrWand;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnWeapon;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TerrainPlacer;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.BookBag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.HerbBag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.KingBag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.TestBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.WaterSoul;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.BlessingNecklace;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DevItem.CrystalLing;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.SakaFishSketon;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.SmallLightHeader;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfFlameCursed;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Image;
import com.watabou.utils.DeviceCompat;

import java.util.List;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public String[] perks() {
		switch (this) {
			case WARRIOR: default:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk1"),
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
				};
		}
	}

	public void initHero( Hero hero ) {

		if (Challenges.activeChallenges() >= 10) {
			hero.lanterfire = 100 - Challenges.activeChallenges() * 4;
		}

//		if(SPDSettings.Cheating()){
//			hero.HT = hero.HP = 114514;
//			hero.exp = -1919810;
//			hero.lvl = 100;
//		}
		//Buff.affect(hero, BlessImmune.class, ChampionHero.DURATION*123456f);

		//GLog.n(String.valueOf(Statistics.commonrelaycall));

		if(RegularLevel.birthday == RegularLevel.DevBirthday.DEV_BIRTHDAY){
			new CrystalLing().quantity(1).identify().collect();
			Buff.affect(hero, BlessLing.class).set( (100), 1 );
		}

		if (Dungeon.isChallenged(Challenges.AQUAPHOBIA)) {
			new WaterSoul().quantity(4).identify().collect();
		}

		if ( Badges.isUnlocked(Badges.Badge.NYZ_SHOP)){
			Dungeon.gold += 320;
			Buff.affect(hero, RandomBuff.class).set( (5), 1 );
		}

		//Buff.affect(hero, ScaryDamageBuff.class).set((50),1);
		PaswordBadges.loadGlobal();
		List<PaswordBadges.Badge> passwordbadges = PaswordBadges.filtered( true );
		if(passwordbadges.contains(PaswordBadges.Badge.EXSG)){
			Dungeon.gold += 400;
			if(!Dungeon.isDLC(Conducts.Conduct.DEV)) {
				new IceCyanBlueSquareCoin().quantity(3).identify().collect();
			}
		}

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		new HerbBag().quantity(1).identify().collect();
		Dungeon.LimitedDrops.HERB_BAG.drop();
		new KingBag().quantity(1).identify().collect();
		Dungeon.LimitedDrops.KING_BAG.drop();
		Waterskin waterskin = new Waterskin();
		waterskin.collect();
		//new Amulet().quantity(1).identify().collect();
		new ScrollOfIdentify().identify();
		//new KingGold().quantity(1).identify().collect();
		if (Dungeon.isDLC(Conducts.Conduct.DEV)){
			new PotionBandolier().collect();
			Dungeon.LimitedDrops.POTION_BANDOLIER.dropped();
			new ScrollHolder().collect();
			Dungeon.LimitedDrops.SCROLL_HOLDER.dropped();
			new MagicalHolster().collect();
			Dungeon.LimitedDrops.MAGICAL_HOLSTER.dropped();
			new BookBag().collect();
			Dungeon.LimitedDrops.BOOK_BAG.dropped();
			new BlessingNecklace().quantity(1).identify().collect();
			Dungeon.LimitedDrops.BLESSING_NECKLACE.dropped();

			new SpawnMisc().quantity(1).identify().collect();
			new LevelTeleporter().quantity(1).identify().collect();
			new SakaFishSketon().quantity(1).identify().collect();
			new SmallLightHeader().quantity(1).identify().collect();
			new SpawnWeapon().quantity(1).identify().collect();
			new SpawnArmor().quantity(1).identify().collect();
			new SpawnArtifact().quantity(1).identify().collect();
			new SpawnRingOrWand().quantity(1).identify().collect();
			new SpawnMissile().quantity(1).identify().collect();
			new CustomPlayer().quantity(1).identify().collect();

			CustomWeapon customWeapon = new CustomWeapon();
			customWeapon.adjustStatus();
			customWeapon.identify().collect();

			new CrystalLing().quantity(1).identify().collect();
			new TerrainPlacer().quantity(1).identify().collect();

			new MobPlacer().quantity(1).identify().collect();
			new Pickaxe().quantity(1).identify().collect();
			new PotionOfMindVision().quantity(50).identify().collect();
			new PotionOfHealing().quantity(50).identify().collect();
			new PotionOfLiquidFlame().quantity(50).identify().collect();
			new ScrollOfMagicMapping().quantity(100).identify().collect();
			new ScrollOfUpgrade().quantity(100).identify().collect();

			new TestBooks().quantity(1).identify().collect();

			new ScrollOfFlameCursed().quantity(50).identify().collect();

			Dungeon.gold = 600000000;
			hero.STR = 30;
			hero.lvl = 30;
			hero.HP=hero.HT=120;
			hero.exp=-1;
		}

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;
		}

//		if (SPDSettings.quickslotWaterskin()) {
//			for (int s = 0; s < QuickSlot.SIZE; s++) {
//				if (Dungeon.quickslot.getItem(s) == null) {
//					Dungeon.quickslot.setSlot(s, waterskin);
//					break;
//				}
//			}
//		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
//			case DUELIST:
//				return Badges.Badge.MASTERY_DUELIST;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);
		new PotionOfHealing().quantity(1).identify().collect();
		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());
		staff.upgrade();
		(hero.belongings.weapon = staff).identify().level(1);
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().quantity(1).identify().collect();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();

		new PotionOfInvisibility().quantity(1).identify().collect();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().quantity(1).identify().collect();

		new ScrollOfLullaby().identify();
	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().quantity(1).identify().collect();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
//			case DUELIST:
//				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
		}
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

	public String GetSkinAssest(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.AVATARS_WARRIOR;
			case MAGE:
				return Assets.Sprites.AVATARS_MAGE;
			case ROGUE:
				return Assets.Sprites.AVATARS_ROGUE;
			case HUNTRESS:
				return Assets.Sprites.AVATARS_HUNTRESS;
			case DUELIST:
				return Assets.Sprites.AVATARS_DUELIST;
		}
	}

	private static boolean onlyMode = false;

	public void SetSkin(int skinIndex){
		boolean isSkinUnlock = false;
		Image img = new Image(this.GetSkinAssest());
		int skinCount = img.texture.width/64;

		if(skinIndex==0){
			isSkinUnlock = true;
		}else {
			while ( skinIndex < skinCount ) {
				switch (this) {
					case WARRIOR:
					default:
						isSkinUnlock = SPDSettings.isItemUnlock("avatars_warrior_" + skinIndex);
						break;
					case MAGE:
						isSkinUnlock = SPDSettings.isItemUnlock("avatars_mage_" + skinIndex);
						break;
					case ROGUE:
						isSkinUnlock = SPDSettings.isItemUnlock("avatars_rogue_" + skinIndex);
						break;
					case HUNTRESS:
						isSkinUnlock = SPDSettings.isItemUnlock("avatars_huntress_" + skinIndex);
						break;
					case DUELIST:
						isSkinUnlock = SPDSettings.isItemUnlock("avatars_duelist_" + skinIndex);
						break;
				}
				if(!isSkinUnlock){
					skinIndex++;
				}else {
					break;
				}
			}
		}

		if(!isSkinUnlock){
			skinIndex=0;
			if(!onlyMode){
				ShatteredPixelDungeon.scene().addToFront(new WndMessage(Messages.get(HeroClass.class,"switch_skin2")));
				onlyMode = true;
			}
		}


		SPDSettings.setHeroSkin(this.ordinal(),skinIndex);
	}

	public int GetSkin(){
		return SPDSettings.getHeroSkin(this.ordinal());
	}
}
