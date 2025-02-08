/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.CS;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.branch;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.levels.minilevels.DragonCaveLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.minilevels.DragonFestivalMiniLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.minilevels.HotelLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.nosync.DeepShadowLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.nosync.ForestHardBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.nosync.SkyGooBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.spical.GalaxyKeyBossLevel;
import com.watabou.utils.Random;

//Level Rules
public class LevelRules {

    public static Level createStandardLevel() {

            if(Statistics.bossRushMode){
                switch (depth) {
                    case 1: return new AncityLevel();

                    case 2: return new ForestBossLevel();

                    case 4:
                        return new ForestHardBossLevel();

                    case 6: return new SLMKingLevel();

                    case 7:
                        if(Statistics.difficultyDLCEXLevel >=2){
                            return new SkyGooBossLevel();
                        } else {
                            return new ItemLevel();
                        }

                    case 9: return new PrisonBossLevel();

                    case 11: return new ColdChestBossLevel();

                    case 13: return new DimandKingLevel();

                    case 14:
                        if(Statistics.difficultyDLCEXLevel >=2){
                            return new DeepShadowLevel();
                        } else {
                            return new ItemLevel();
                        }

                        //御三家 最难时刻
                    case 16: return new CavesBossLevel();
                    case 17: return new CaveTwoBossLevel();
                    case 18: return new CavesGirlDeadLevel();

                    case 21: return new ShopBossLevel();

                    case 23: return new AncientMysteryCityBossLevel();
                    case 24: return new NewCityBossLevel();

                    case 26: return new CerDogBossLevel();

                    case 27: return new DwarfMasterBossLevel();

                    case 29: return new HallsBossLevel();

                    case 31: return new YogGodHardBossLevel();

                    //补给层 T1
                    case 3:  case 5: case 8:  case 10:
                        //补给层 T2
                    case 12: case 15:  case 19: case 20:
                        //补给层 T3
                    case 22: case 25: case 28: case 30:
                        return new ItemLevel();

                    default:
                        Statistics.deepestFloor--;
                        return new DeadEndLevel();
                }
            } else {
                switch (depth) {
                    case 0:
                        if(Dungeon.isChallenged(CS)){
                            return new ZeroLevel();
                        } else {
                            return new ZeroCityLevel();
                        }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return new SewerLevel();
                    case 5:
                        if(Statistics.ExFruit){
                            return new ForestHardBossLevel();
                        } else if(Challenges.activeChallenges()>8){
                            if (!Badges.isUnlocked(Badges.Badge.KILL_CLSISTER)){
                                Statistics.ExFruit = true;
                                return new ForestHardBossLevel();
                            } else if (Random.Float()<=0.7f){
                                Statistics.ExFruit = true;
                                return new ForestHardBossLevel();
                            } else {
                                return new ForestBossLevel();
                            }
                        } else if(Badges.isUnlocked(Badges.Badge.KILL_CLSISTER) && Random.Float()<=0.2f) {
                            Statistics.ExFruit = true;
                            return new ForestHardBossLevel();
                        } else {
                            return new ForestBossLevel();
                        }
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return new PrisonLevel();
                    case 10:
                       if(Statistics.RandMode){
                           switch (Random.Int(9)){
                               case 1: return new SLMKingLevel();
                               default:
                               case 3: return new DeepShadowLevel();
                               case 4: return new ColdChestBossLevel();
                               case 5: return new DimandKingLevel();
                               case 6: return new CavesBossLevel();
                               case 7: return new CaveTwoBossLevel();
                               case 8: return new PrisonBossLevel();
                           }
                        } else if(Statistics.mimicking && !Statistics.mustTengu){
                            return new ColdChestBossLevel();
                        } else if ((Statistics.boss_enhance & 0x2) != 0 && !Statistics.mustTengu) {
                            return new ColdChestBossLevel();
                        } else {
                            return new PrisonBossLevel();
                       }
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        return new CavesLevel();
                    case 15:
                        if(Statistics.RandMode){
                            switch (Random.Int(5)){
                                default:
                                case 1: return new AncientMysteryCityBossLevel();
                                case 2: return new CavesGirlDeadLevel();
                                case 3: return new CaveTwoBossLevel();
                                case 4: return new DwarfMasterBossLevel();
                            }
                        } else if ((Statistics.boss_enhance & 0x4) != 0) {
                            return new CavesGirlDeadLevel();
                        } else {
                            if(Random.Float() <= 0.4f && !Statistics.dm300Fight || Statistics.dm720Fight){
                                return new CaveTwoBossLevel();
                            } else {
                                return new CavesBossLevel();
                            }
                        }
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        return new CityLevel();
                    case 20:
                        if(Statistics.RandMode){
                            switch (Random.Int(4)){
                                case 1: return new AncientMysteryCityBossLevel();
                                case 2: return new DwarfMasterBossLevel();
                                default:
                                case 3: return new CerDogBossLevel();
                                case 4: return new ShopBossLevel();
                            }
                        } else {
                            return new NewCityBossLevel();
                        }
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                        return new HallsLevel();
                    case 25:
                        if ((Statistics.boss_enhance & 0x10) != 0 || Dungeon.isChallenged(CS) || Statistics.RandMode ) {
                            return new YogGodHardBossLevel();
                        } else {
                            return new HallsBossLevel();
                        }
                    case 26:
                        return new LastLevel();
                    default:
                        Statistics.deepestFloor--;
                        return new DeadEndLevel();
                }
            }
    }

    public static Level createBranchLevel() {
        switch (branch) {
            default:
            case 1:
                switch (depth) {
                    case 0:
                        return new HotelLevel();
                    case 5:
                        return new DragonCaveLevel();
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        return new MiningLevel();
                    case 17:
                    case 18:
                        return new AncientMysteryCityLevel();
                    case 20:
                        return new DwarfGeneralBossLevel();
                    default:
                        return new DeadEndLevel();
                }

            case 2:
                switch (depth) {
                    case 4:
                    case 14:
                        return new MiniBossLevel();
                    case 5:
                        return new DragonCaveLevel();
                    case 17:
                    case 18:
                        return new AncientMysteryCityLevel();
                    default:
                        return new DeadEndLevel();
                }

            case 3:
                switch (depth) {
                    case 5:
                        return new LaveCavesBossLevel();
                    case 17:
                    case 18:
                        return new AncientMysteryCityBossLevel();
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        return new DragonFestivalMiniLevel();
                    default:
                        return new DeadEndLevel();
                }

            case 4:
                switch (depth) {
                    case 25:
                        return new OpenLastLevel();
                    case 17:
                    case 18:
                        return new GardenLevel();
                    default:
                        return new DeadEndLevel();
                }

            case 5:
                switch (depth) {
                    case 17:
                        return new GardenLevel();
                    default:
                        return new DeadEndLevel();
                }

            case 6:
                return new LinkLevel();

            case 7:
                return new ShopBossLevel();

            case 8:
                switch (depth) {
                    case 1:
                        return new AncityLevel();

                    case 2:
                        return new ForestBossLevel();

                    case 4:
                        return new ForestHardBossLevel();

                    case 6:
                        return new SLMKingLevel();

                    case 7:
                        if (Statistics.difficultyDLCEXLevel >= 2) {
                            return new SkyGooBossLevel();
                        } else {
                            return new ItemLevel();
                        }

                    case 9:
                        return new PrisonBossLevel();

                    case 11:
                        return new ColdChestBossLevel();

                    case 13:
                        return new DimandKingLevel();

                    case 14:
                        if (Statistics.difficultyDLCEXLevel >= 2) {
                            return new DeepShadowLevel();
                        } else {
                            return new ItemLevel();
                        }

                        //御三家 最难时刻
                    case 16:
                        return new CavesBossLevel();
                    case 17:
                        return new CaveTwoBossLevel();
                    case 18:
                        return new CavesGirlDeadLevel();

                    case 21:
                        return new ShopBossLevel();

                    case 23:
                        return new AncientMysteryCityBossLevel();
                    case 24:
                        return new NewCityBossLevel();

                    case 26:
                        return new CerDogBossLevel();

                    case 27:
                        return new DwarfMasterBossLevel();

                    case 29:
                        return new HallsBossLevel();

                    case 31:
                        return new YogGodHardBossLevel();

                    //补给层 T1
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        //补给层 T2
                    case 12:
                    case 15:
                    case 19:
                    case 20:
                        //补给层 T3
                    case 22:
                    case 25:
                    case 28:
                    case 30:
                        return new ItemLevel();

                    default:
                        Statistics.deepestFloor--;
                        return new DeadEndLevel();
                }

            case 10:
                switch (depth) {
                    default:
                        Statistics.deepestFloor--;
                        return new DeadEndLevel();
                    case 26:
                        return new GalaxyKeyBossLevel();
                }
        }
    }


}
