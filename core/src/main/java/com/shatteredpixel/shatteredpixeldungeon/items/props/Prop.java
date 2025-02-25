package com.shatteredpixel.shatteredpixeldungeon.items.props;

import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Prop extends Item {

    {
        levelKnown = true;

        unique = true;
    }

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

    public static Prop randomPropA(int rare){

        Prop prop = new Prop();
        if(rare >2) rare =2;

        if(Statistics.hasAllRarenessProp(rare,0)){
            if(rare != 0) {
                return randomPropA(rare - 1);
            }else return new Trash();
        }

        switch (rare){

            case 1:
                int index =Random.Int(0,Statistics.propPositive1.size());
                prop = Statistics.propPositive1.get(index);
                Statistics.propPositive1.remove(index);
                break;
            case 2:
                index =Random.Int(0,Statistics.propPositive2.size());
                prop = Statistics.propPositive2.get(index);
                Statistics.propPositive2.remove(index);
                break;
            case 0:
                index =Random.Int(0,Statistics.propPositive0.size());
                prop = Statistics.propPositive0.get(index);
                Statistics.propPositive0.remove(index);
                break;
        }
        return prop;
    }

    public static Prop randomPropB(){
        return randomPropB(0);
    }

    public static Prop randomPropB(int rare){
        Prop prop = new Prop();
        if(rare >2) rare =2;

        if(Statistics.hasAllRarenessProp(rare,1)){
            if(rare != 0) {
                return randomPropB(rare - 1);
            }else return new Trash();
        }

        switch (rare){
            case 1:
                int index =Random.Int(0,Statistics.propNegative1.size());
                prop = Statistics.propNegative1.get(index);
                Statistics.propNegative1.remove(index);
                break;
            case 2:
                index =Random.Int(0,Statistics.propNegative2.size());
                prop = Statistics.propNegative2.get(index);
                Statistics.propNegative2.remove(index);
                break;
            case 0:
                index =Random.Int(0,Statistics.propNegative0.size());
                prop = Statistics.propNegative0.get(index);
                Statistics.propNegative0.remove(index);
                break;
        }

        if(prop instanceof TerrorDoll){
            if(Random.Float()>0.75f){
                prop = new TerrorDollB();
            }
        }
        return prop;
    }
}
