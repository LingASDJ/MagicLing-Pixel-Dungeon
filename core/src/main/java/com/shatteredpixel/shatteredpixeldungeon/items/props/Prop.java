package com.shatteredpixel.shatteredpixeldungeon.items.props;

import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Prop extends Item {

    {
        levelKnown = true;

        unique = true;
    }

    public Class<?>[] prop_T0_A = {
            ArmorScalesOfBzmdr.class,
            StarSachet.class,
            PortableWhetstone.class

    };

    public Class<?>[] prop_T1_A = {
            DeliciousRecipe.class,
            NewStem.class,
            RapidEarthRoot.class,
            WenStudyingPaperOne.class,
            YanStudyingPaperTwo.class

    };

    public Class<?>[] prop_T2_A = {

            LuckyGlove.class,
            EmotionalAggregation.class,
            Monocular.class

    };

    public Class<?>[] prop_T3_A = {

            KnightStabbingSword.class

    };

    //既然都准备做分级了对吧……  generation
    public int rareness = 0;
    public int kind = 0;
    //0积极 1 消极;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        //开发者模式不去掉
        if(Dungeon.isDLC(Conducts.Conduct.DEV)){
            return super.actions(hero);
        }
        //去掉"放下"与"扔出"
        return new ArrayList<>();
    }


    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public static Prop randomPropA(){
        return randomPropA(0);
    }
   /* public static Prop randomPropB(){
        return randomPropB(0);
    }
    */

    public static Prop randomPropA(int rare){
        Prop prop = new Prop();

        switch (rare){

            case 1:
                switch (Random.Int(0,4)){
                    case 0:
                        prop = new DeliciousRecipe();
                        break;
                    case 1:
                        prop = new NewStem();
                        break;
                    case 2:
                        prop = new RapidEarthRoot();
                        break;
                    case 3:
                        prop = new WenStudyingPaperOne();
                        break;
                    case 4:
                        prop = new YanStudyingPaperTwo();
                        break;
                }
                return prop;
            case 2:
                switch (Random.Int(0,2)){
                    case 0:
                        prop = new Monocular();
                        break;
                    case 1:
                        prop = new LuckyGlove();
                        break;
                    case 2:
                        prop = new EmotionalAggregation();
                        break;
                }
                return prop;
            case 3:
                switch (Random.Int(0,2)){
                    case 0:
                        prop = new KnightStabbingSword();
                        break;
                    case 1:
                        prop = new LuckyGlove();
                        break;
                    case 2:
                        prop = new EmotionalAggregation();
                        break;
                }
                return prop;
            case 0:
            default:
                switch (Random.Int(0,2)){
                    case 0:
                        prop = new StarSachet();
                        break;
                    case 1:
                        prop = new PortableWhetstone();
                        break;
                    case 2:
                        prop = new ArmorScalesOfBzmdr();
                        break;
                }
                return prop;
        }
    }
    /*
    public static Prop randomPropB(int rare){
        return
    }
     */
}
