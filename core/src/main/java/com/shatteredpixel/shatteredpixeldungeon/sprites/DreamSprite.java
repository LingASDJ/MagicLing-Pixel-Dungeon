package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class DreamSprite extends MobSprite {

    public DreamSprite() {

        texture( Assets.Sprites.LEZI );

        TextureFilm ren = new TextureFilm(this.texture, 10, 16);

        idle = new MovieClip.Animation(9, true);
        idle.frames(ren, 0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,3,4,5,6,6,6,6,6,6,6,6,6,6,7,8);

        run = new MovieClip.Animation(10, true);
        run.frames(ren, 0);

        die = new MovieClip.Animation(10, false);
        die.frames(ren, 0);

        play(this.idle);
    }

}
