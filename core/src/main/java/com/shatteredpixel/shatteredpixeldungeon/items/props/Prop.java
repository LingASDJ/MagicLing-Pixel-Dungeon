package com.shatteredpixel.shatteredpixeldungeon.items.props;

import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
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

        if(Props.hasAllRarenessProp(rare,0)){
            GLog.n(rare+"轮空");
            if(rare != 0) return randomPropA(rare-1);
            else return new Trash();
        }

        switch (rare){

            case 1:
                int index =Random.Int(0,Props.propPositive1.size());
                prop = Props.propPositive1.get(index);
                Props.propPositive1.remove(index);
                break;
            case 2:
                index =Random.Int(0,Props.propPositive2.size());
                prop = Props.propPositive2.get(index);
                Props.propPositive2.remove(index);
                break;
            case 0:
                index =Random.Int(0,Props.propPositive0.size());
                prop = Props.propPositive0.get(index);
                Props.propPositive0.remove(index);
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

        if(Props.hasAllRarenessProp(rare,1)){
            GLog.n(rare+"轮空");
            if(rare != 0) return randomPropB(rare-1);
            else return new Trash();
        }

        switch (rare){
            case 1:
                int index =Random.Int(0,Props.propNegative1.size());
                prop = Props.propNegative1.get(index);
                Props.propNegative1.remove(index);
                break;
            case 2:
                index =Random.Int(0,Props.propNegative2.size());
                prop = Props.propNegative2.get(index);
                Props.propNegative2.remove(index);
                break;
            case 0:
                index =Random.Int(0,Props.propNegative0.size());
                prop = Props.propNegative0.get(index);
                Props.propNegative0.remove(index);
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
