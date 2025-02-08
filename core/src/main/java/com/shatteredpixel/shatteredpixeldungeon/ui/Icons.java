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

package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.Image;

public enum Icons {

	//title screen icons, variable sizes, spacing for 17x16
	ENTER,
	GOLD,
	RANKINGS,
	BADGES,
	NEWS,
	CHANGES,
	PREFS,
	SHPX,
	THANKS,

	//rankings and hero select icons, spacing for 16x16
	STAIRS,
	WARRIOR,
	MAGE,
	ROGUE,
	HUNTRESS,
	DUELIST,

	LEFTBUTTON,
	RIGHTBUTTON,

	SEED,
	LEFTARROW,
	RIGHTARROW,
	CALENDAR,

	//grey icons, mainly used for buttons, spacing for 16x16
	EXIT,
	DISPLAY, //2 separate images, changes based on orientation
	DISPLAY_LAND,
	DISPLAY_PORT,
	DATA,
	AUDIO,
	LANGS,
	CONTROLLER,
	STATS,
	CHALLENGE_OFF,
	CHALLENGE_ON,

	RENAME_OFF,
	RENAME_ON,

	//misc icons, mainly used for buttons, spacing for 16x16 until the smaller icons at the end
	UNCHECKED,
	CHECKED,
	CLOSE,
	PLUS,
	ARROW,
	INFO,
	WARNING,
	BACKPACK_LRG,
	TALENT,
	MAGNIFY,
	BUFFS,
	ENERGY,
	COIN_SML,
	ENERGY_SML,
	BACKPACK,
	B_BACKPACK,
	SEED_POUCH,
	SCROLL_HOLDER,
	WAND_HOLSTER,
	POTION_BANDOLIER,

	//icons that appear in the game itself, variable spacing
	TARGET,
	SKULL,
	BUSY,
	COMPASS,
	SLEEP,
	ALERT,
	LOST,
	DEPTH,
	DEPTH_CHASM,
	DEPTH_WATER,
	DEPTH_GRASS,
	DEPTH_DARK,
	DEPTH_LARGE,
	DEPTH_TRAPS,
	DEPTH_SECRETS,

	DEPTH_BTRAPS,

	DEPTH_WELLS,

	DEPTH_LINK_ROOM,

	DEPTH_DIED,

	DEPTH_BIGROOM,

	DEPTH_BLOOD,

	DEPTH_SKYCITY,
	//Base on SHPD 2.4.2
	REPEAT,
	MISSON_ON,
	MISSON_OFF,

	CHAL_COUNT,

	HAPPY_ICON,

	LOVE,

	//icons that appear in the about screen, variable spacing
	LIBGDX,
	ALEKS,
	WATA,
	CELESTI,
	KRISTJAN,
	CUBE_CODE,
	PURIGRO,
	ARCNOR,

	PASTE,

	COPY,

	SKIP,

	//slightly larger title screen icons, spacing for 17x16
	JOURNAL,

	//grey icons, mainly used for buttons, spacing for 16x16
	KEYBOARD,

	CHALLENGE_GREY,
	SCROLL_GREY,

	CHEVRON,

	//misc larger icons, mainly used for buttons, tabs, and journal, spacing for 16x16

	CHALLENGE_COLOR,
	SCROLL_COLOR,

	SNAKE,

	CATALOG,
	ALCHEMY,
	GRASS,

	STAIRS_CHASM,
	STAIRS_WATER,
	STAIRS_GRASS,
	STAIRS_DARK,
	STAIRS_LARGE,
	STAIRS_TRAPS,
	STAIRS_SECRETS,
	WELL_HEALTH,
	WELL_AWARENESS,
	SACRIFICE_ALTAR,
	DISTANT_WELL;

	public Image get() {
		return get( this );
	}

	public static Image get( Icons type ) {
		Image icon = new Image(SPDSettings.ClassUI() ? Assets.Interfaces.ICONS : Assets.Interfaces.ICONS_NORMAL );
		switch (type) {
			case JOURNAL:
				icon.frame( icon.texture.uvRectBySize( 136, 0, 17, 15 ) );
				break;
			case ALCHEMY:
				icon.frame( icon.texture.uvRectBySize( 96, 48, 16, 16 ) );
				break;
			case CATALOG:
				icon.frame( icon.texture.uvRectBySize( 80, 48, 13, 16 ) );
				break;
			case ENTER:
				icon.frame( icon.texture.uvRectBySize( 0, 0, 16, 16 ) );
				break;
			case GOLD:
				icon.frame( icon.texture.uvRectBySize( 17, 0, 17, 16 ) );
				break;
			case RANKINGS:
				icon.frame( icon.texture.uvRectBySize( 34, 0, 17, 16 ) );
				break;
			case BADGES:
				icon.frame( icon.texture.uvRectBySize( 51, 0, 16, 16 ) );
				break;
			case NEWS:
				icon.frame( icon.texture.uvRectBySize( 68, 0, 16, 15 ) );
				break;
			case CHANGES:
				icon.frame( icon.texture.uvRectBySize( 85, 0, 15, 15 ) );
				break;
			case PREFS:
				icon.frame( icon.texture.uvRectBySize( 102, 0, 14, 14 ) );
				break;
			case SHPX:
				icon.frame( icon.texture.uvRectBySize( 119, 0, 16, 16 ) );
				break;
			case THANKS:
				icon.frame( icon.texture.uvRectBySize( 135, 0, 16, 16 ) );
				break;
			case DUELIST:
				icon.frame( icon.texture.uvRectBySize( 176, 0, 16, 16 ));
				break;

			case STAIRS:
				icon.frame( icon.texture.uvRectBySize( 0, 16, 13, 16 ) );
				break;
			case WARRIOR:
				icon.frame( icon.texture.uvRectBySize( 16, 16, 9, 15 ) );
				break;
			case MAGE:
				icon.frame( icon.texture.uvRectBySize( 32, 16, 15, 14 ) );
				break;
			case ROGUE:
				icon.frame( icon.texture.uvRectBySize( 48, 16, 9, 15 ) );
				break;
			case HUNTRESS:
				icon.frame( icon.texture.uvRectBySize( 64, 16, 16, 16 ) );
				break;
			case LEFTBUTTON:
				icon.frame( icon.texture.uvRectBySize( 80, 16, 9, 9 ) );
				break;
			case RIGHTBUTTON:
				icon.frame( icon.texture.uvRectBySize( 97, 16, 9, 9 ) );
				break;
			case PASTE:
				icon.frame( icon.texture.uvRectBySize( 113, 16, 13, 13 ) );
				break;
			case COPY:
				icon.frame( icon.texture.uvRectBySize( 129, 16, 13, 13 ) );
				break;
			case SKIP:
				icon.frame( icon.texture.uvRectBySize( 145, 16, 25, 16 ) );
				break;

			case EXIT:
				icon.frame( icon.texture.uvRectBySize( 0, 32, 15, 11 ) );
				break;
			case DISPLAY:
				if (!PixelScene.landscape()){
					return get(DISPLAY_PORT);
				} else {
					return get(DISPLAY_LAND);
				}
			case DISPLAY_PORT:
				icon.frame( icon.texture.uvRectBySize( 16, 32, 12, 16 ) );
				break;
			case DISPLAY_LAND:
				icon.frame( icon.texture.uvRectBySize( 32, 32, 16, 12 ) );
				break;
			case DATA:
				icon.frame( icon.texture.uvRectBySize( 48, 32, 16, 15 ) );
				break;
			case AUDIO:
				icon.frame( icon.texture.uvRectBySize( 64, 32, 14, 14 ) );
				break;
			case LANGS:
				icon.frame( icon.texture.uvRectBySize( 80, 32, 14, 11 ) );
				break;
			case CONTROLLER:
				icon.frame( icon.texture.uvRectBySize( 96, 32, 16, 12 ) );
				break;
			case STATS:
				icon.frame( icon.texture.uvRectBySize( 112, 32, 16, 13 ) );
				break;
			case CHALLENGE_OFF:
				icon.frame( icon.texture.uvRectBySize( 128, 32, 14, 12 ) );
				break;
			case CHALLENGE_ON:
				icon.frame( icon.texture.uvRectBySize( 144, 32, 14, 12 ) );
				break;
			case RENAME_OFF:
				icon.frame( icon.texture.uvRectBySize( 160, 32, 15, 14 ) );
				break;
			case RENAME_ON:
				icon.frame( icon.texture.uvRectBySize( 176, 32, 15, 14 ) );
				break;
			case LEFTARROW:
				icon.frame( icon.texture.uvRectBySize( 192, 32, 14, 8 ) );
				break;
			case RIGHTARROW:
				icon.frame( icon.texture.uvRectBySize( 208, 32, 14, 8 ) );
				break;
			case SEED:
				icon.frame( icon.texture.uvRectBySize( 208, 32, 15, 10 ) );
				break;
			case CALENDAR:
				icon.frame( icon.texture.uvRectBySize( 240, 16, 15, 12 ) );
				break;
			case UNCHECKED:
				icon.frame( icon.texture.uvRectBySize( 0, 48, 12, 12 ) );
				break;
			case CHECKED:
				icon.frame( icon.texture.uvRectBySize( 16, 48, 12, 12 ) );
				break;
			case CLOSE:
				icon.frame( icon.texture.uvRectBySize( 32, 48, 11, 11 ) );
				break;
			case PLUS:
				icon.frame( icon.texture.uvRectBySize( 48, 48, 11, 11 ) );
				break;
			case ARROW:
				icon.frame( icon.texture.uvRectBySize( 64, 48, 11, 11 ) );
				break;
			case INFO:
				icon.frame( icon.texture.uvRectBySize( 80, 48, 14, 14 ) );
				break;
			case WARNING:
				icon.frame( icon.texture.uvRectBySize( 96, 48, 14, 14 ) );
				break;
			case BACKPACK_LRG:
				icon.frame( icon.texture.uvRectBySize( 112, 48, 16, 16 ) );
				break;
			case TALENT:
				icon.frame( icon.texture.uvRectBySize( 128, 48, 13, 13 ) );
				break;
			case MAGNIFY:
				icon.frame( icon.texture.uvRectBySize( 144, 48, 14, 14 ) );
				break;
			case BUFFS:
				icon.frame( icon.texture.uvRectBySize( 160, 48, 16, 15 ) );
				break;
			case ENERGY:
				icon.frame( icon.texture.uvRectBySize( 176, 48, 16, 16 ) );
				break;
			case COIN_SML:
				icon.frame( icon.texture.uvRectBySize( 192, 48, 7, 7 ) );
				break;
			case ENERGY_SML:
				icon.frame( icon.texture.uvRectBySize( 192, 56, 8, 7 ) );
				break;
			case BACKPACK:
				icon.frame( icon.texture.uvRectBySize( 201, 48, 10, 10 ) );
				break;
			case B_BACKPACK:
				icon.frame( icon.texture.uvRectBySize( 201, 59, 10, 10 ) );
				break;
			case SCROLL_HOLDER:
				icon.frame( icon.texture.uvRectBySize( 211, 48, 10, 10 ) );
				break;
			case SEED_POUCH:
				icon.frame( icon.texture.uvRectBySize( 221, 48, 10, 10 ) );
				break;
			case WAND_HOLSTER:
				icon.frame( icon.texture.uvRectBySize( 231, 48, 10, 10 ) );
				break;
			case POTION_BANDOLIER:
				icon.frame( icon.texture.uvRectBySize( 241, 48, 10, 10 ) );
				break;

			case TARGET:
				icon.frame( icon.texture.uvRectBySize( 0, 64, 16, 16 ) );
				break;
			case SKULL:
				icon.frame( icon.texture.uvRectBySize( 16, 64, 8, 8 ) );
				break;
			case BUSY:
				icon.frame( icon.texture.uvRectBySize( 24, 64, 8, 8 ) );
				break;
			case COMPASS:
				icon.frame( icon.texture.uvRectBySize( 16, 72, 7, 5 ) );
				break;
			case SLEEP:
				icon.frame( icon.texture.uvRectBySize( 32, 64, 9, 8 ) );
				break;
			case ALERT:
				icon.frame( icon.texture.uvRectBySize( 32, 72, 8, 8 ) );
				break;
			case LOST:
				icon.frame( icon.texture.uvRectBySize( 40, 72, 8, 8 ) );
				break;
			case DEPTH:
				icon.frame( icon.texture.uvRectBySize( 48, 64, 6, 7 ) );
				break;
			case DEPTH_CHASM:
				icon.frame( icon.texture.uvRectBySize( 56, 64, 7, 7 ) );
				break;
			case DEPTH_WATER:
				icon.frame( icon.texture.uvRectBySize( 64, 64, 7, 7 ) );
				break;
			case DEPTH_GRASS:
				icon.frame( icon.texture.uvRectBySize( 72, 64, 7, 7 ) );
				break;
			case DEPTH_DARK:
				icon.frame( icon.texture.uvRectBySize( 80, 64, 7, 7 ) );
				break;
			case DEPTH_LARGE:
				icon.frame( icon.texture.uvRectBySize( 88, 64, 7, 7 ) );
				break;
			case DEPTH_TRAPS:
				icon.frame( icon.texture.uvRectBySize( 96, 64, 7, 7 ) );
				break;
			case DEPTH_SECRETS:
				icon.frame( icon.texture.uvRectBySize( 104, 64, 7, 7 ) );
				break;
			case DEPTH_BTRAPS:
				icon.frame( icon.texture.uvRectBySize( 112, 64, 7, 7 ) );
				break;
			case DEPTH_WELLS:
				icon.frame( icon.texture.uvRectBySize( 120, 64, 7, 7 ) );
				break;
			case DEPTH_LINK_ROOM:
				icon.frame( icon.texture.uvRectBySize( 128, 64, 7, 7 ) );
				break;
			case DEPTH_DIED:
				icon.frame( icon.texture.uvRectBySize( 136, 64, 7, 7 ) );
				break;
			case DEPTH_BIGROOM:
				icon.frame( icon.texture.uvRectBySize( 80, 72, 7, 7 ) );
				break;
			case DEPTH_BLOOD:
				icon.frame( icon.texture.uvRectBySize( 88, 72, 7, 7 ) );
				break;
			case DEPTH_SKYCITY:
				icon.frame( icon.texture.uvRectBySize( 96, 72, 7, 7 ) );
				break;
			case MISSON_OFF:
				icon.frame( icon.texture.uvRectBySize( 144, 64, 32, 14 ) );
				break;
			case REPEAT:
				icon.frame( icon.texture.uvRectBySize( 192, 80, 11, 11 ) );
				break;
			case MISSON_ON:
				icon.frame( icon.texture.uvRectBySize( 144, 80, 32, 14 ) );
				break;
			case CHAL_COUNT:
				icon.frame( icon.texture.uvRectBySize( 48, 72, 7, 7 ) );
				break;
			case HAPPY_ICON:
				icon.frame( icon.texture.uvRectBySize( 56, 72, 7, 5 ) );
				break;
			case LOVE:
				icon.frame( icon.texture.uvRectBySize( 65, 72, 10, 6 ) );
				break;

			case LIBGDX:
				icon.frame( icon.texture.uvRectBySize( 0, 96, 16, 13 ) );
				break;
			case ALEKS:
				icon.frame( icon.texture.uvRectBySize( 16, 96, 16, 13 ) );
				break;
			case WATA:
				icon.frame( icon.texture.uvRectBySize( 0, 112, 17, 12 ) );
				break;

			//large icons are scaled down to match game's size
			case CELESTI:
				icon.frame( icon.texture.uvRectBySize( 32, 96, 32, 32 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case KRISTJAN:
				icon.frame( icon.texture.uvRectBySize( 64, 96, 32, 32 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case ARCNOR:
				icon.frame( icon.texture.uvRectBySize( 96, 96, 32, 32 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case PURIGRO:
				icon.frame( icon.texture.uvRectBySize( 128, 96, 32, 32 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case CUBE_CODE:
				icon.frame( icon.texture.uvRectBySize( 160, 96, 27, 30 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;

		}
		return icon;
	}

	public static Image get( HeroClass cl ) {
		switch (cl) {
			case WARRIOR:
				return get( WARRIOR );
			case MAGE:
				return get( MAGE );
			case ROGUE:
				return get( ROGUE );
			case HUNTRESS:
				return get( HUNTRESS );
			case DUELIST:
				return get( DUELIST );
			default:
				return null;
		}
	}

	public static Image get(Level.Feeling feeling){
		switch (feeling){
			case NONE: default:
				return get(DEPTH);
			case CHASM:
				return get(DEPTH_CHASM);
			case WATER:
				return get(DEPTH_WATER);
			case GRASS:
				return get(DEPTH_GRASS);
			case DARK:
				return get(DEPTH_DARK);
			case LARGE:
				return get(DEPTH_LARGE);
			case TRAPS:
				return get(DEPTH_TRAPS);
			case BIGTRAP:
				return get(DEPTH_BTRAPS);
			case THREEWELL:
				return get(DEPTH_WELLS);
			case DIEDROOM:
				return get(DEPTH_DIED);
			case LINKROOM:
				return get(DEPTH_LINK_ROOM);
			case SECRETS:
				return get(DEPTH_SECRETS);
			case SKYCITY:
				return get(DEPTH_SKYCITY);
			case BIGROOMS:
				return get(DEPTH_BIGROOM);
			case BLOOD:
				return get(DEPTH_BLOOD);
		}
	}

	public static Image getLarge(Level.Feeling feeling){
		switch (feeling){
			case NONE: default:
				return get(STAIRS);
			case CHASM:
				return get(STAIRS_CHASM);
			case WATER:
				return get(STAIRS_WATER);
			case GRASS:
				return get(STAIRS_GRASS);
			case DARK:
				return get(STAIRS_DARK);
			case LARGE:
				return get(STAIRS_LARGE);
			case TRAPS:
				return get(STAIRS_TRAPS);
			case SECRETS:
				return get(STAIRS_SECRETS);
		}
	}

}
