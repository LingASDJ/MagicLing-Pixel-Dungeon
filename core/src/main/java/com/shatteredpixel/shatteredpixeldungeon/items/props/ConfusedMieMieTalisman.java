package com.shatteredpixel.shatteredpixeldungeon.items.props;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PropBuff;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ConfusedMieMieTalisman extends Prop{
    {
        rareness = 2;
        image = ItemSpriteSheet.CONfUSEDMIEMIETALISMAN;
    }

    @Override
    public boolean collect() {
        if(Dungeon.hero.buff(PropBuff.class)==null) Buff.affect(Dungeon.hero, PropBuff.class);
        return super.collect();
    }
}