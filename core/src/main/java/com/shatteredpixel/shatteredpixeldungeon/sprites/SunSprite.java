package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class SunSprite extends MobSprite {

    public SunSprite() {
        super();

        texture( Assets.Sprites.ANIMATIONS_SUN );

        TextureFilm frames = new TextureFilm( texture, 24, 24 );

        idle = new Animation( 14, true );
        idle.frames( frames, 0, 1, 2, 3,4,5,6,7,8,9 );

        run = idle.clone();
        run.frames( frames, 0, 1 );

        attack = idle.clone();
        attack.frames( frames, 2, 3, 0, 1 );

        die = idle.clone();
        die.frames( frames, 4, 5, 6 );

        play( idle );
    }
}

