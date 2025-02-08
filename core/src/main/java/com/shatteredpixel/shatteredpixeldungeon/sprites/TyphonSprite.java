package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class TyphonSprite extends MobSprite {

    public TyphonSprite() {
        super();

        texture( Assets.Sprites.TYPHON );

        TextureFilm frames = new TextureFilm( texture, 16, 21 );

        idle = new Animation( 6, true );
        idle.frames( frames, 0, 1, 2, 3, 4 );

        run = new Animation( 12, true );
        run.frames( frames, 14,15,16,17 );

        attack = new Animation( 18, false );
        attack.frames( frames, 4,5,6,7,8,9,0 );

        die = new Animation( 18, false );
        die.frames( frames, 0, 1, 2, 3, 4 );

        play( idle );
    }

}

