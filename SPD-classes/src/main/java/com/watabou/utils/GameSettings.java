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

package com.watabou.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.watabou.noosa.Game;

public class GameSettings {
	
	public static final String DEFAULT_PREFS_FILE = "settings.xml";
	private static final String BUNDLABLE="b";

	public static <T extends Bundlable> T getBundlable(String key, T defValue){
		try {
			Bundle b = Bundle.fromString(getString(key,""));
			return (T)b.get(BUNDLABLE);
		} catch (Exception e) {
			return defValue;
		}
	}
	private static Preferences prefs;
	
	private static Preferences get() {
		if (prefs == null) {
			prefs = Gdx.app.getPreferences( DEFAULT_PREFS_FILE );
		}
		return prefs;
	}
	
	//allows setting up of preferences directly during game initialization
	public static void set( Preferences prefs ){
		GameSettings.prefs = prefs;
	}
	
	public static boolean contains( String key ){
		return get().contains( key );
	}
	
	public static int getInt( String key, int defValue ) {
		return getInt(key, defValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static int getInt( String key, int defValue, int min, int max ) {
		try {
			int i = get().getInteger( key, defValue );
			if (i < min || i > max){
				int val = (int)GameMath.gate(min, i, max);
				put(key, val);
				return val;
			} else {
				return i;
			}
		} catch (Exception e) {
			Game.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}

	public static long getLong( String key, long defValue ) {
		return getLong(key, defValue, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	public static long getLong( String key, long defValue, long min, long max ) {
		try {
			long i = get().getLong( key, defValue );
			if (i < min || i > max){
				long val = (long)GameMath.gate(min, i, max);
				put(key, val);
				return val;
			} else {
				return i;
			}
		} catch (Exception e) {
			Game.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}
	
	public static boolean getBoolean( String key, boolean defValue ) {
		try {
			return get().getBoolean(key, defValue);
		} catch (Exception e) {
			Game.reportException(e);
			return defValue;
		}
	}
	
	public static String getString( String key, String defValue ) {
		return getString(key, defValue, Integer.MAX_VALUE);
	}
	
	public static String getString( String key, String defValue, int maxLength ) {
		try {
			String s = get().getString( key, defValue );
			if (s != null && s.length() > maxLength) {
				put(key, defValue);
				return defValue;
			} else {
				return s;
			}
		} catch (Exception e) {
			Game.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}

	public static String[] getStringArray( String key ) {
		try {
			String s = get().getString( key, "" );
			if ( s != null && s.length() > Integer.MAX_VALUE ) {
				return new String[0];
			} else {
				return s.split( ";" );
			}
		} catch (Exception e) {
			Game.reportException(e);
			return new String[0];
		}
	}
	
	public static void put( String key, int value ) {
		get().putInteger(key, value);
		get().flush();
	}

	public static void put( String key, long value ) {
		get().putLong(key, value);
		get().flush();
	}
	
	public static void put( String key, boolean value ) {
		get().putBoolean(key, value);
		get().flush();
	}
	
	public static void put( String key, String value ) {
		get().putString(key, value);
		get().flush();
	}

	public static void put( String key, String[] value ) {
		int length = value.length;
		String str = get().getString( key, "" );
		StringBuilder stringArray = new StringBuilder( str );

		for(int i = 0; i < length; i++) {
			if( query(key, value[i].split(",")[0]) )
				continue;

			if (value[i].indexOf(";") != -1) {
				stringArray.append(value[i]);
			}else {
				stringArray.append(value[i]).append(";");
			}
		}

		get().putString(key, stringArray.toString());
		get().flush();
	}

	public static void delete( String key, String value ) {
		String[] itemArray = value.split( ";" );
		String str = get().getString( key, "" );
		StringBuilder stringArray = new StringBuilder( str );

		for( String target : itemArray ) {
			String[] result = exist(str, target);
			int start = Integer.valueOf(result[0]);
			int end = Integer.valueOf(result[1]);

			if (end > 0) {
				stringArray.delete( start, end );
			}
		}

		get().putString(key, stringArray.toString());
		get().flush();
	}

	public static void modifyArray( String key, String target, String value ) {
		String str = get().getString( key, "" );
		StringBuilder stringArray = new StringBuilder( str );
		String[] result = exist(str,target);
		int start = Integer.valueOf( result[0] );
		int end = Integer.valueOf( result[1] );

		if (end > 0) {
			StringBuilder tempStr = new StringBuilder(value);
			int length = tempStr.length() - 1;

			if ( value.lastIndexOf(",") == length )
				tempStr.deleteCharAt( length ).append(";");
			if ( tempStr.lastIndexOf(";") != length )
				tempStr.append(";");

			stringArray.replace(start, end, tempStr.toString());

			get().putString(key, stringArray.toString());
			get().flush();
		}
	}

	public static void modifyArrayElement( String key, String target, int index, String value ) {
		String str = get().getString( key, "" );
		StringBuilder stringArray = new StringBuilder( str );
		String[] result = exist(str,target);
		int start = Integer.valueOf( result[0] );
		int end = Integer.valueOf( result[1] );

		if( end > 0 ){
			int count = 0;
			int startIndex = 0;
			int endIndex = 0;
			StringBuilder tempStr = new StringBuilder( result[2] );

			for (int i = 0; i < ( end - start ); i++) {
				if (tempStr.charAt(i) == ',') {
					if (count == index) {
						break;
					} else {
						startIndex = i;
						count++;
					}
				}
			}

			endIndex = tempStr.indexOf(",", startIndex + 1 );
			if(endIndex == -1)
				endIndex = tempStr.length();
			startIndex = index == 0 ? 0 : startIndex + 1;

			tempStr.replace(startIndex, endIndex, value);
			stringArray.replace(start, end, tempStr.toString());

			get().putString(key, stringArray.toString());
			get().flush();
		}
	}

	public static boolean query( String key, String target ) {
		return Integer.valueOf( exist(key,target)[1] ) > 0;
	}

	public static String[] exist( String str, String target ) {
		StringBuilder stringArray = new StringBuilder( str );
		int start = -1;
		boolean check = false;
		String targetString = null;
		int end = -1;

		if (str != null && ((start = stringArray.indexOf(target)) != -1)) {
			while ( !check ) {
				end = stringArray.indexOf(";", start) + 1;
				if( end <= 0 )
					break;

				targetString = stringArray.substring(start, end);

				if( target.equals( targetString.split(",")[0] ) )
					check = true;
				else
					start = end;
			}
		}

		return new String[]{ String.valueOf( start ), String.valueOf( end ), targetString };
	}
}
