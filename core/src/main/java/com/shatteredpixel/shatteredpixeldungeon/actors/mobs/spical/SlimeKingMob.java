package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.spical;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Conducts;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CausticSlime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ColdMagicRat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Crab;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Slime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SlimeKing;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlimeKingMobSprites;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SlimeKingMob extends Mob {

    private final String[] attackCurse = {"雕虫小技", "班门弄斧",
            "GAMEOVER"};
    private int combo = 0;
    private static final float TIME_TO_ZAP	= 0.5f;

    private boolean PartCold = false;




    @Override
    public int defenseProc(Char enemy, int damage ) {

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
        bundle.put(chainsused, chainsUsed);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        PartCold = bundle.getBoolean(partcold);
        chainsUsed = bundle.getBoolean(chainsused);
    }

    {
        if (Dungeon.isDLC(Conducts.Conduct.BOSSRUSH)) {
            HP =190;
            HT= 190;

        } else {
            HP =140;
            HT= 140;
        }

        EXP = 20;
        defenseSkill = 12;
        spriteClass = SlimeKingMobSprites.class;
        lootChance = 1;
        HUNTING = new Hunting();
        properties.add(Property.BOSS);
        baseSpeed = 0.4f;
    }

    @Override
    public boolean act() {

        if(HP < 70 && !PartCold){
            baseSpeed = 1f;
            SummoningTrap var4 = new  SummoningTrap();
            var4.pos = super.pos;
            var4.activate();
            PartCold = true;
            chainsUsed = true;
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
            if (Random.Int( 2 ) == 0) {
                Buff.prolong( enemy, Blindness.class, Degrade.DURATION );
                Sample.INSTANCE.play( Assets.Sounds.DEBUFF );
            }

            int dmg = Random.NormalIntRange( 2, 4 );
            enemy.damage( dmg, new ColdMagicRat.DarkBolt() );

            if (enemy == Dungeon.hero && !enemy.isAlive()) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(SlimeKing.class, "frost_kill") );
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
                sprite.zap( enemy.pos );
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
            this.sprite.showStatus(0x009999, this.attackCurse[Random.Int(this.attackCurse.length)]);
        }
        int damage2 = super.attackProc(enemy, this.combo + damage);
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

    private boolean chainsUsed = false;
    private boolean chain(int target){
        if (chainsUsed || enemy.properties().contains(Property.IMMOVABLE))
            return false;

        Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

        if (chain.collisionPos != enemy.pos
                || chain.path.size() < 2
                || Dungeon.level.pit[chain.path.get(1)])
            return false;
        else {
            int newPos;
            newPos = -1;
            for (int i : chain.subPath(1, chain.dist)){
                if (!Dungeon.level.solid[i] && Actor.findChar(i) == null){
                    newPos = i;
                    break;
                }
            }

            if (newPos == 0){
                return false;
            } else {
                final int newPosFinal = newPos;
                this.target = newPos;

                if (sprite.visible) {
                    yell(Messages.get(SlimeKing.class, "scorpion"));
                    new Item().throwSound();
                    Sample.INSTANCE.play(Assets.Sounds.CHAINS);
                    sprite.parent.add(new Chains(sprite.center(),
                            enemy.sprite.destinationCenter(),
                            Effects.Type.CHAIN,
                            new Callback() {
                                public void call() {
                                    Actor.add(new Pushing(enemy, enemy.pos, newPosFinal, new Callback() {
                                        public void call() {
                                            pullEnemy(enemy, newPosFinal);
                                        }
                                    }));
                                    next();
                                }
                            }));
                } else {
                    pullEnemy(enemy, newPos);
                }
            }
        }
        chainsUsed = true;
        return true;
    }

    private void pullEnemy( Char enemy, int pullPos ){
        enemy.pos = pullPos;
        enemy.sprite.place(pullPos);
        Dungeon.level.occupyCell(enemy);
        Cripple.prolong(enemy, Cripple.class, 4f);
        if (enemy == Dungeon.hero) {
            Dungeon.hero.interrupt();
            Dungeon.observe();
            GameScene.updateFog();
        }
    }


    public void move( int step ) {
        super.move( step );
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])){
            if (	mob instanceof Swarm ||
                    mob instanceof Crab ||
                    mob instanceof Rat ||
                    mob instanceof Slime) {
                mob.die( cause );
            }
        }
    }

    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {
            enemySeen = enemyInFOV;

            if (!chainsUsed
                    && enemyInFOV
                    && !isCharmedBy( enemy )
                    && !canAttack( enemy )
                    && Dungeon.level.distance( pos, enemy.pos ) < 5


                    && chain(enemy.pos)){
                return !(sprite.visible || enemy.sprite.visible);
            } else {
                return super.act( enemyInFOV, justAlerted );
            }

        }
    }
}
