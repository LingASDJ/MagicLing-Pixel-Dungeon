/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Rivals Pixel Dungeon
 * Copyright (C) 2019-2020 Marshall M.
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

package com.shatteredpixel.shatteredpixeldungeon.levels.arenas;

import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;

import com.watabou.utils.Random;

public class ArenasB extends LastLevelArenas {
	
	//phase 2
	protected static final int[] MAP_2_1 = {
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,W,W,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,w,w,s,s,s,s,s,s,s,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,B,c,c,w,c,c,c,c,c,c,c,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,c,B,c,w,c,B,s,B,c,c,s,e,e,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,c,c,B,e,B,c,c,c,B,s,s,s,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,e,e,m,g,G,g,s,s,s,c,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,c,B,m,g,G,G,G,g,s,B,c,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,B,c,g,G,G,B,G,G,g,c,B,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,s,c,G,G,B,W,B,G,G,c,s,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,B,c,g,G,G,B,G,G,g,c,B,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,c,B,s,g,G,G,G,g,m,B,c,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,c,s,s,s,g,G,g,m,e,e,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,s,s,s,B,c,c,c,B,e,B,c,c,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,e,e,s,c,c,B,s,B,c,w,c,B,c,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,c,c,c,c,c,c,c,w,c,c,B,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,s,s,s,s,s,s,s,w,w,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,W,W,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c
	};
	
	protected static final int[] MAP_2_2 = {
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,W,W,W,W,W,W,W,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,w,w,s,s,e,e,e,e,e,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,w,w,w,c,c,c,c,c,E,e,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,w,w,w,m,g,G,G,G,c,c,c,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,e,c,c,c,g,G,G,G,c,c,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,m,c,e,e,c,c,c,g,G,G,c,E,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,g,c,e,e,e,E,c,c,g,G,c,c,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,G,c,c,e,s,s,s,c,c,G,G,c,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,c,G,g,c,E,s,p,s,E,c,g,G,c,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,c,G,G,c,c,s,s,s,e,c,c,G,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,c,c,G,g,c,c,E,e,e,e,c,g,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,E,c,G,G,g,c,c,c,e,e,c,m,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,c,c,G,G,G,g,c,c,c,e,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,c,c,c,G,G,G,g,m,w,w,w,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,e,E,c,c,c,c,c,w,w,w,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,e,e,e,e,e,s,s,w,w,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,W,W,W,W,W,W,W,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c
	};
	
	protected static final int[] MAP_2_3 = {
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,W,W,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,G,G,g,m,w,w,w,w,c,c,c,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,G,G,G,G,g,m,w,w,w,s,s,s,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,G,G,G,G,W,W,w,w,w,c,B,c,e,e,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,g,G,G,G,B,c,c,s,c,c,B,c,c,s,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,m,g,W,B,B,B,B,G,B,B,B,B,B,s,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,m,W,c,B,B,G,G,G,B,B,c,c,s,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,c,B,G,G,G,G,G,B,c,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,s,G,G,G,G,G,G,G,s,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,w,w,w,c,B,G,G,G,G,G,B,c,w,w,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,s,c,c,B,B,G,G,G,B,B,c,W,m,w,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,s,B,B,B,B,B,G,B,B,B,B,W,g,m,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,s,c,c,B,c,c,s,c,c,B,G,G,G,g,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,e,e,c,B,c,w,w,w,W,W,G,G,G,G,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,s,s,s,w,w,w,m,g,G,G,G,G,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,c,c,c,w,w,w,w,m,g,G,G,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,W,W,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c
	};
	
	protected static final int[] MAP_2_4 = {
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,W,W,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,e,s,s,s,c,G,B,B,B,c,c,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,m,G,c,s,G,G,B,G,G,s,s,e,e,e,W,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,G,G,B,s,B,B,B,G,c,s,B,e,e,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,c,B,B,s,c,c,c,c,c,s,B,B,s,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,s,s,s,s,s,s,s,s,s,s,s,s,s,s,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,B,G,c,c,s,S,w,w,w,S,s,c,B,G,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,B,G,G,c,s,w,w,w,w,w,s,c,B,G,G,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,B,B,B,c,s,w,w,p,w,w,s,c,B,B,B,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,G,G,B,c,s,w,w,w,w,w,s,c,G,G,B,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,G,B,c,s,S,w,w,w,S,s,c,c,G,B,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,s,s,s,s,s,s,s,s,s,s,s,s,s,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,s,B,B,s,c,c,c,c,c,s,B,B,c,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,e,e,e,B,s,c,G,B,B,B,s,B,G,G,s,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,s,s,G,G,B,G,G,s,c,G,m,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,e,e,e,c,c,B,B,B,G,c,s,s,s,e,e,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		W,W,W,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,  c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,
		c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c,c
	};
}