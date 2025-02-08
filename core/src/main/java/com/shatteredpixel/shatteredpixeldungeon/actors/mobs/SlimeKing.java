/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.BGMPlayer.playBGM;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Boss;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlimeKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SlimeKing extends Boss {

    private int combo = 0;
    private static final float TIME_TO_ZAP	= 0.5f;

    private boolean PartCold = false;


    @Override
    public int defenseProc( Char enemy, int damage ) {

        if (HP >= damage + 2) {
            ArrayList<Integer> candidates = new ArrayList<>();

            int[] neighbours = {pos + 1, pos - 1, pos + Dungeon.level.width(), pos - Dungeon.level.width()};
            for (int n : neighbours) {
                if (!Dungeon.level.solid[n] && Actor.findChar( n ) == null
                        && (!properties().contains(Property.LARGE) || Dungeon.level.openSpace[n])) {
                    candidates.add( n );
                }
            }

            if (candidates.size() > 0 && HP < 60 && Random.Float() < 0.45f) {
            CausticSlime mini = new CausticSlime();
            mini.pos = this.pos;
            mini.state = mini.HUNTING;
            mini.HP /= 4;
            mini.maxLvl = -5;
            Dungeon.level.occupyCell(mini);
            GameScene.add( mini , 0f );
             Actor.addDelayed( new Pushing( mini, pos, mini.pos ), -1 );
            }
        }

        return super.defenseProc(enemy, damage);
    }


    private static final String partcold   = "partcold";
    private static final String chainsused = "chainsused";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(partcold, PartCold);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        PartCold = bundle.getBoolean(partcold);
    }

    {
        HP =190 * Dungeon.depth/5;
        HT= 190 * Dungeon.depth/5;

        EXP = 20;
        defenseSkill = 12;
        spriteClass = SlimeKingSprite.class;
        lootChance = 1;
        properties.add(Property.BOSS);
        baseSpeed = 0.6f;
    }

    @Override
    public boolean act() {
        playBGM(Assets.BGM_BOSSA, true);
        if(HP < 70 && !PartCold){
            baseSpeed = 1f;
            SummoningTrap var4 = new  SummoningTrap();
            var4.pos = super.pos;
            var4.activate();
            PartCold = true;
            GLog.n(Messages.get(SlimeKing.class,"fuck"));
        } else if (HP < 70) {
            baseSpeed = 1f;
        }

        return super.act();
    }

    private void zap() {
        spend( TIME_TO_ZAP );

        if (hit( this, enemy, true )) {
            //TODO would be nice for this to work on ghost/statues too
            if (enemy == Dungeon.hero && Random.Int( 2 ) == 0) {
                Buff.prolong( enemy, Blindness.class, Degrade.DURATION );
                Sample.INSTANCE.play( Assets.Sounds.DEBUFF );
            }

            int dmg = Random.NormalIntRange( 2, 4 );
            enemy.damage( dmg, new ColdMagicRat.DarkBolt() );

            if (enemy == Dungeon.hero && !enemy.isAlive()) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(this, "frost_kill") );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }

    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.attack( enemy.pos );
                return false;
            } else {
                zap();
                return true;
            }
        }
    }

    public void onZapComplete() {
        zap();
        next();
    }

    @Override
    public int damageRoll() {
        if(HP < 70 && Random.Float() > 0.10f) {
            return Random.NormalIntRange(8, 12);
        } else {
            return Random.NormalIntRange(4, 6);
        }
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(0, 10) > 7) {
            this.sprite.showStatus(0x009999, Messages.get(this,"attack_msg_"+Random.IntRange(1, 3)));
        }
        int damage2 = SlimeKing.super.attackProc(enemy, this.combo + damage);
        this.combo++;
//        int effect = Random.Int(3);
////        if (enemy.buff(Poison.class) == null && Random.Float() <= 0.25f) {
////            Buff.affect( enemy, Poison.class).set((effect-2) );
////        }
        if (this.combo > 3) {
            this.combo = 1;
        }
        return damage2;
    }

    @Override
    public int drRoll() {
        if(HP < 70 && !PartCold) {
            return 0;
        } else {
            return 5;
        }
    }


    public void move( int step ) {
        Dungeon.level.seal();
        super.move( step );
    }

    @Override
    public void notice() {
        super.notice();
        BossHealthBar.assignBoss(this);
        yell( Messages.get(this, "notice") );
    }



    @Override
    public void die( Object cause ) {

        super.die( cause );

        Dungeon.level.unseal();

        GameScene.bossSlain();
        Dungeon.level.drop( new SkeletonKey( Dungeon.depth ), pos ).sprite.drop();

        //60% chance of 2 blobs, 30% chance of 3, 10% chance for 4. Average of 2.5
        int blobs = Random.chances(new float[]{0, 0, 6, 3, 1});
        for (int i = 0; i < blobs; i++){
            int ofs;
            do {
                ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (!Dungeon.level.passable[pos + ofs]);
            Dungeon.level.drop( new GooBlob(), pos + ofs ).sprite.drop( pos );
        }
      if(Statistics.bossRushMode){

            GetBossLoot();
        }
        Badges.validateBossSlain();

        yell( Messages.get(this, "defeated") );
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])){
            if (	mob instanceof Swarm||
                    mob instanceof Crab||
                    mob instanceof Rat||
                    mob instanceof Slime ) {
                mob.die( cause );
            }
        }
    }


}
