package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NTNPC;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.IceLingSword;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DreamSprite;
import com.watabou.utils.Bundle;

public class DreamLezi extends NTNPC {

    {
        spriteClass = DreamSprite.class;
    }

    private boolean first=true;
    private boolean secnod=true;
    private boolean rd=true;

    private static final String FIRST = "first";
    private static final String SECNOD = "secnod";
    private static final String RD = "rd";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(FIRST, first);
        bundle.put(SECNOD, secnod);
        bundle.put(RD, rd);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        first = bundle.getBoolean(FIRST);
        secnod = bundle.getBoolean(SECNOD);
        rd = bundle.getBoolean(RD);
    }

    @Override
    public boolean interact(Char c) {
        if (c != hero) return true;

        //TODO 赶时间 潘多拉别鲨我
        if(first){
            Dungeon.gold -= 720;
            yell("没收720金币");
            first = false;
        } else if(secnod){
            secnod = false;
            switch (hero.heroClass){
                case WARRIOR:
                    yell("我寻思这个有用！");
                    if (Statistics.zeroItemLevel >= 4 && Dungeon.depth == 0) {
                        Dungeon.level.drop(new Gold(1), hero.pos);
                    } else {
                        Dungeon.level.drop(new IceLingSword(), hero.pos);
                    }
                    break;
                case MAGE:
                    if (Statistics.zeroItemLevel >= 4 && Dungeon.depth == 0) {
                        Dungeon.level.drop(new Gold(1), hero.pos);
                    } else {
                        Dungeon.level.drop(new PotionOfToxicGas(), hero.pos);
                    }
                    yell("这个大概能救你");
                    break;
                case ROGUE:
                    if (Statistics.zeroItemLevel >= 4 && Dungeon.depth == 0) {
                        Dungeon.level.drop(new Gold(1), hero.pos);
                    } else {
                        Dungeon.level.drop(new Dirk(), hero.pos);
                    }
                    yell("你应该喜欢这个！");
                    break;
                case HUNTRESS:
                    if (Statistics.zeroItemLevel >= 4 && Dungeon.depth == 0) {
                        Dungeon.level.drop(new Gold(1), hero.pos);
                    } else {
                        Dungeon.level.drop(new WandOfRegrowth(), hero.pos);
                    }
                    yell("我觉得这个很有乐子！");
                    break;
                case DUELIST:
                    yell("算了吧，你很强，无需了……");
                    break;
            }
        } else {
            yell("新年快乐，我的动物园好看吗？");
        }




        return true;
    }
}
