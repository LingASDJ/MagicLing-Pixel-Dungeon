package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ElectricalSmokeBlob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ElectricalSmoke;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Smoking extends Buff {

    {
        type = buffType.POSITIVE;
    }

    private ElectricalSmoke artifact;

    @Override
    public boolean act(){
        spend(TICK);

        Buff.affect(Dungeon.hero, Stamina.class,1f);

        artifact.reduceCharge(100/(10+artifact.level()));
        if(artifact.getCharge()<100/(10+artifact.level())){
            detach();
            return true;
        }
        GameScene.add(Blob.seed(Dungeon.hero.pos, 100, ElectricalSmokeBlob.class));
        if(((ElectricalSmokeBlob)Dungeon.level.blobs.get(ElectricalSmokeBlob.class)).artifact == null){
            ((ElectricalSmokeBlob)Dungeon.level.blobs.get(ElectricalSmokeBlob.class)).artifact = artifact;
        };
        return true;
    }

    public void setArtifact(ElectricalSmoke smoke){
        artifact = smoke;
    }

    private static final String ARTIFACT = "artifact";

    @Override
    public int icon() {
        return BuffIndicator.BARKSKIN;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(ARTIFACT,artifact);
    }

    @Override
    public void restoreFromBundle( Bundle bundle){
        super.restoreFromBundle(bundle);
        artifact = (ElectricalSmoke) bundle.get(ARTIFACT);
    }

}
