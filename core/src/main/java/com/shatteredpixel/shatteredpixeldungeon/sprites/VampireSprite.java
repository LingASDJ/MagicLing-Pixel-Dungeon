package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.bosses.SakaFishBoss;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.hollow.Vampire;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class VampireSprite extends MobSprite {

    private Animation activeIdle;
    private Animation GodDied;

    public VampireSprite() {
        super();

        texture( Assets.Sprites.VAMPIRE );

        TextureFilm frames = new TextureFilm( texture, 17, 16 );

        idle = new MovieClip.Animation( 8, true );
        idle.frames( frames, 0,1 );

        run = new MovieClip.Animation( 12, true );
        run.frames( frames, 0,1 );

        activeIdle = new Animation( 6, true );
        activeIdle.frames( frames, 2,3,4,5,6);

        attack = new MovieClip.Animation( 11, false );
        attack.frames( frames, 7,8,9,10 );

        die = new MovieClip.Animation( 12, false );
        die.frames( frames, 11,12,13,14,15,16,17 );

        GodDied = new Animation( 13, true );
        GodDied.frames( frames, 18,19,20,21,22,23,24,25);

        play( idle );
    }

    public void activateIdle(){
        idle = activeIdle.clone();
        idle();
    }

    @Override
    public void link(Char ch) {
        super.link(ch);

        if (((Vampire) ch).hereEnenmy){
            activateIdle();
        }
        renderShadow = false;
    }

}