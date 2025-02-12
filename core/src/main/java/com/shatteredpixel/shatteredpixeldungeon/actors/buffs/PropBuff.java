package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Sheep;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.props.ConfusedMieMieTalisman;
import com.shatteredpixel.shatteredpixeldungeon.items.props.RapidEarthRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.props.WenStudyingPaperOne;
import com.shatteredpixel.shatteredpixeldungeon.items.props.WenStudyingPaperTwo;
import com.shatteredpixel.shatteredpixeldungeon.items.props.YanStudyingPaperOne;
import com.shatteredpixel.shatteredpixeldungeon.items.props.YanStudyingPaperTwo;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PropBuff extends Buff{

    {
        type = buffType.POSITIVE;
    }

    public int timeA = 0,timeB = 0,timeC = 0, timeD = 0, timeE =0;

    public boolean potionLost = false;

    @Override
    public boolean act() {

        spend(1);

        if(Dungeon.depth>0){

            Hero hero = Dungeon.hero;

            if(Dungeon.hero.belongings.getItem(RapidEarthRoot.class)!=null) {
                timeA ++;
                if(timeA >= 250) Buff.affect(hero, Barkskin.class).set((int) hero.getZone() * 2, 10);
            }
            if(Dungeon.hero.belongings.getItem(WenStudyingPaperOne.class)!=null) {
                timeB ++;
                if(timeC >= 125) Buff.affect(hero, Swiftthistle.TimeBubble.class).setLeft(5f);
            }
            if(Dungeon.hero.belongings.getItem(YanStudyingPaperTwo.class)!=null) {
                timeC ++;
                if(timeC >= 125) Buff.affect(hero, Haste.class,5f);
            }

            if(Dungeon.hero.belongings.getItem(ConfusedMieMieTalisman.class)!=null) {
                if(timeD>0) timeD--;
                if(Random.Int(1,100)<=2 && timeD==0){
                    GLog.n(Messages.get(ConfusedMieMieTalisman.class,"sheep"));
                    timeD = 75;
                    int cell = hero.pos;
                    PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
                    ArrayList<Integer> spawnPoints = new ArrayList<>();
                    for (int i = 0; i < PathFinder.distance.length; i++) {
                        if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                            spawnPoints.add(i);
                        }
                    }

                    for (int i : spawnPoints){
                        if (Dungeon.level.insideMap(i)
                                && Actor.findChar(i) == null
                                && !(Dungeon.level.pit[i])) {
                            Sheep sheep = new Sheep();
                            sheep.lifespan = 8;
                            sheep.pos = i;
                            GameScene.add(sheep);
                            Dungeon.level.occupyCell(sheep);
                            CellEmitter.get(i).burst(Speck.factory(Speck.WOOL), 4);
                        }
                    }

                    CellEmitter.get(cell).burst(Speck.factory(Speck.WOOL), 4);
                    Sample.INSTANCE.play(Assets.Sounds.PUFF);
                    Sample.INSTANCE.play(Assets.Sounds.SHEEP);
                }
            }

            if(Dungeon.hero.belongings.getItem(WenStudyingPaperTwo.class)!=null) {
                timeE ++;
                if(timeE >= 30) Buff.affect(hero, Hex.class,25);
            }
        }

        if(potionLost){
            GLog.n(Messages.get(YanStudyingPaperOne.class,"lost"));
            potionLost = false;
        }

        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.BARKSKIN;
    }

    private static final String TIMEA = "timeA";
    private static final String TIMEB = "timeB";
    private static final String TIMEC = "timeC";
    private static final String TIMED = "timeD";
    private static final String TIMEE = "timeE";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( TIMEA, timeA );
        bundle.put( TIMEB, timeB );
        bundle.put( TIMEC, timeC );
        bundle.put( TIMED, timeD );
        bundle.put( TIMEE, timeE );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        timeA = bundle.getInt( TIMEA );
        timeB = bundle.getInt( TIMEB );
        timeC = bundle.getInt( TIMEC );
        timeD = bundle.getInt( TIMED );
        timeE = bundle.getInt( TIMEE );
    }

}
