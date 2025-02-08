package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.bosses.hollow;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.BGMPlayer;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Boss;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Stone;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DeadDogCerberusSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class DeadDogCerberus extends Boss {

    /**
     * 恶狗扑食技能参数
     */
    private int lastEnemyPos = -1;
    private int leapPos = -1;
    private float leapCooldown = 0;

    /**
     * 狩猎准备技能参数
     */
    public int HunterReady = 0;
    public boolean Hunter;

    /**
     * 连环撕咬技能参数
     */
    public int ComboAttackCooldown;
    public boolean ComboAttackThis = true;

    {
        initProperty();
        initBaseStatus(20, 60, 20, 18, 1000, 0, 0);
        initStatus(100);

        spriteClass = DeadDogCerberusSprite.class;

        //baseSpeed = 1.2f;

        HUNTING = new Hunting();

        properties.add(Property.BOSS);
        properties.add(Property.DEMONIC);
        properties.add(Property.ACIDIC);
    }

    @Override
    public void notice() {
        //super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
            GameScene.flash(0x33ff0000);
            BGMPlayer.playBoss();
            GameScene.bossReady();
            for (Char ch : Actor.chars()){
                if (ch instanceof DriedRose.GhostHero){
                    ((DriedRose.GhostHero) ch).sayBoss();
                }
            }
        }
    }

    public void activate(){
        ((DeadDogCerberusSprite) sprite).activateAttack();
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );

        if (damage > 0 && ComboAttackThis) {
            int dmg = damageRoll();
            dmg = Math.round(dmg * AscensionChallenge.statModifier(DeadDogCerberus.this));
            dmg = defenseProc(enemy,dmg);
            dmg -= enemy.drRoll();
            for (int i = 0; i < 4; i++) {
                enemy.damage(dmg, new Stone());
            }
            ComboAttackThis = false;
            activate();
        }

        return damage;
    }


    @Override
    protected boolean act() {
        if (state == WANDERING){
            leapPos = -1;
        }

        AiState lastState = state;
        boolean result = super.act();
        if (paralysed <= 0) {
            leapCooldown --;
            if(HunterReady <= 0){
                HunterReady = 15;
                Hunter = false;
                Buff.affect(this, CerberusBless.class, CerberusBless.DURATION);
            } else if(HunterReady == 5){
                Hunter = true;
                HunterReady--;
                GLog.n(Messages.get(this,"dog_skills_one"));
            } else {
                HunterReady--;
            }
        }


        //连环撕咬
        if(ComboAttackCooldown == 0){
            ComboAttackCooldown = 15;
        } else {
            ComboAttackCooldown--;
            ComboAttackThis = false;
        }

        //if state changed from wandering to hunting, we haven't acted yet, don't update.
        if (!(lastState == WANDERING && state == HUNTING)) {
            if (enemy != null) {
                lastEnemyPos = enemy.pos;
            } else {
                lastEnemyPos = Dungeon.hero.pos;
            }
        }

        return result;
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        if (Hunter) {
            //TODO 未知问题，暂时动画不生效
            ((DeadDogCerberusSprite) sprite).setTooteh(enemy.pos);
            return true;
        } else {
            return super.doAttack(enemy);
        }
    }

    private static final String LAST_ENEMY_POS = "last_enemy_pos";
    private static final String LEAP_POS = "leap_pos";
    private static final String LEAP_CD = "leap_cd";

    private static final String HUNTER_COOLDOWN  = "hunter_cooldown";
    private static final String HUNTER_BOOLEAN   = "hunter_boolean";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LAST_ENEMY_POS, lastEnemyPos);
        bundle.put(LEAP_POS, leapPos);
        bundle.put(LEAP_CD, leapCooldown);

        bundle.put(HUNTER_COOLDOWN,HunterReady);
        bundle.put(HUNTER_BOOLEAN, Hunter);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        lastEnemyPos = bundle.getInt(LAST_ENEMY_POS);
        leapPos = bundle.getInt(LEAP_POS);
        leapCooldown = bundle.getFloat(LEAP_CD);

        HunterReady = bundle.getInt(HUNTER_COOLDOWN);
        Hunter = bundle.getBoolean(HUNTER_BOOLEAN);

        if (state != SLEEPING) BossHealthBar.assignBoss(this);
    }

    @Override
    public int drRoll() {

        FireSuperDr fireSuperDr = buff(FireSuperDr.class);
        int NormalDr = Random.NormalIntRange(0, 15);
        if(fireSuperDr != null){
            NormalDr *= 3;
        }

        return NormalDr;
    }

    /**
     * 恶狗扑食技能需要重写Hunting
     */
    public class Hunting extends Mob.Hunting {

        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {

            if (leapPos != -1){

                leapCooldown = 15;

                if (rooted){
                    leapPos = -1;
                    return true;
                }

                Ballistica b = new Ballistica(pos, leapPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                leapPos = b.collisionPos;

                final Char leapVictim = Actor.findChar(leapPos);
                final int endPos;

                //ensure there is somewhere to land after leaping
                if (leapVictim != null){
                    int bouncepos = -1;
                    //attempt to bounce in free passable space
                    for (int i : PathFinder.NEIGHBOURS8){
                        if ((bouncepos == -1 || Dungeon.level.trueDistance(pos, leapPos+i) < Dungeon.level.trueDistance(pos, bouncepos))
                                && Actor.findChar(leapPos+i) == null && Dungeon.level.passable[leapPos+i]){
                            bouncepos = leapPos+i;
                        }
                    }
                    //try again, allowing a bounce into any non-solid terrain
                    if (bouncepos == -1){
                        for (int i : PathFinder.NEIGHBOURS8){
                            if ((bouncepos == -1 || Dungeon.level.trueDistance(pos, leapPos+i) < Dungeon.level.trueDistance(pos, bouncepos))
                                    && Actor.findChar(leapPos+i) == null && !Dungeon.level.solid[leapPos+i]){
                                bouncepos = leapPos+i;
                            }
                        }
                    }
                    //if no valid position, cancel the leap
                    if (bouncepos == -1) {
                        leapPos = -1;
                        return true;
                    } else {
                        endPos = bouncepos;
                    }
                } else {
                    endPos = leapPos;
                }

                //do leap
                sprite.visible = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[leapPos] || Dungeon.level.heroFOV[endPos];
                sprite.jump(pos, leapPos, new Callback() {
                    @Override
                    public void call() {

                        if (leapVictim != null && alignment != leapVictim.alignment){
                            if (hit( DeadDogCerberus.this, leapVictim, Char.INFINITE_ACCURACY, false)) {
                                int dmg = damageRoll();
                                dmg *= Random.NormalIntRange(2,3);
                                dmg = Math.round(dmg * AscensionChallenge.statModifier(DeadDogCerberus.this));
                                dmg = defenseProc(enemy,dmg);
                                dmg -= enemy.drRoll();
                                enemy.damage(dmg, new Stone());
                                Buff.affect(leapVictim, Bleeding.class).set(0.75f * damageRoll());
                                leapVictim.sprite.flash();
                                Sample.INSTANCE.play(Assets.Sounds.HIT);

                                int targetingPos = enemy.pos;
                                for (int i : PathFinder.NEIGHBOURS8) {
                                    if (!Dungeon.level.solid[targetingPos + i]) {
                                        CellEmitter.get(targetingPos + i).burst(ElmoParticle.FACTORY, 5);
                                        GameScene.add(Blob.seed(targetingPos + i, 12, DeadHaloFire.class));
                                    }
                                }
                            } else {
                                leapVictim.sprite.showStatus( CharSprite.NEUTRAL, leapVictim.defenseVerb() );
                                Sample.INSTANCE.play(Assets.Sounds.MISS);
                            }
                        }

                        if (endPos != leapPos){
                            Actor.add(new Pushing( DeadDogCerberus.this, leapPos, endPos));
                        }

                        pos = endPos;
                        leapPos = -1;
                        sprite.idle();
                        Dungeon.level.occupyCell( DeadDogCerberus.this);
                        next();
                    }
                });
                return false;
            }

            enemySeen = enemyInFOV;
            if (enemyInFOV && !isCharmedBy( enemy ) && canAttack( enemy )) {

                return doAttack( enemy );

            } else {

                if (enemyInFOV) {
                    target = enemy.pos;
                } else if (enemy == null) {
                    state = WANDERING;
                    target = Dungeon.level.randomDestination(  DeadDogCerberus.this );
                    return true;
                }

                if (leapCooldown <= 0 && enemyInFOV && !rooted
                        && Dungeon.level.distance(pos, enemy.pos) >= 3) {

                    int targetPos = enemy.pos;
                    if (lastEnemyPos != enemy.pos){
                        int closestIdx = 0;
                        for (int i = 1; i < PathFinder.CIRCLE8.length; i++){
                            if (Dungeon.level.trueDistance(lastEnemyPos, enemy.pos+PathFinder.CIRCLE8[i])
                                    < Dungeon.level.trueDistance(lastEnemyPos, enemy.pos+PathFinder.CIRCLE8[closestIdx])){
                                closestIdx = i;
                            }
                        }
                        targetPos = enemy.pos + PathFinder.CIRCLE8[(closestIdx+4)%8];
                    }

                    Ballistica b = new Ballistica(pos, targetPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                    //try aiming directly at hero if aiming near them doesn't work
                    if (b.collisionPos != targetPos && targetPos != enemy.pos){
                        targetPos = enemy.pos;
                        b = new Ballistica(pos, targetPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                    }
                    if (b.collisionPos == targetPos){
                        //get ready to leap
                        leapPos = targetPos;
                        //don't want to overly punish players with slow move or attack speed
                        spend(GameMath.gate(attackDelay(), (int)Math.ceil(enemy.cooldown()), 3*attackDelay()));
                        if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[leapPos]){
                            GLog.w(Messages.get( DeadDogCerberus.this, "leap"));
                            sprite.parent.addToBack(new TargetedCell(leapPos, 0xFF0000));
                            ((DeadDogCerberusSprite)sprite).FlyAttack( leapPos );
                            Dungeon.hero.interrupt();
                        }
                        return true;
                    }
                }

                int oldPos = pos;
                if (target != -1 && getCloser( target )) {

                    spend( 1 / speed() );
                    return moveSprite( oldPos,  pos );

                } else {
                    spend( TICK );
                    if (!enemyInFOV) {
                        sprite.showLost();
                        state = WANDERING;
                        target = Dungeon.level.randomDestination(  DeadDogCerberus.this );
                    }
                    return true;
                }
            }
        }

    }

    /**
     * 狗子在这里面获得3倍防御加成
     */
    public static class DeadHaloFire extends Blob {

        @Override
        protected void evolve() {

            boolean[] flamable = Dungeon.level.flamable;
            int cell;
            int fire;

            Freezing freeze = (Freezing)Dungeon.level.blobs.get( Freezing.class );

            boolean observe = false;

            for (int i = area.left-1; i <= area.right; i++) {
                for (int j = area.top-1; j <= area.bottom; j++) {
                    cell = i + j*Dungeon.level.width();
                    if (cur[cell] > 0) {

                        if (freeze != null && freeze.volume > 0 && freeze.cur[cell] > 0){
                            freeze.clear(cell);
                            off[cell] = cur[cell] = 0;
                            continue;
                        }

                        burn( cell );

                        fire = cur[cell] - 1;
                        if (fire <= 0 && flamable[cell]) {

                            Dungeon.level.destroy( cell );

                            observe = true;
                            GameScene.updateMap( cell );

                        }

                    } else if (freeze == null || freeze.volume <= 0 || freeze.cur[cell] <= 0) {

                        if (flamable[cell]
                                && (cur[cell-1] > 0
                                || cur[cell+1] > 0
                                || cur[cell-Dungeon.level.width()] > 0
                                || cur[cell+Dungeon.level.width()] > 0)) {
                            fire = 4;
                            burn( cell );
                            area.union(i, j);
                        } else {
                            fire = 0;
                        }

                    } else {
                        fire = 0;
                    }

                    volume += (off[cell] = fire);
                }
            }

            if (observe) {
                Dungeon.observe();
            }
        }

        public static void burn( int pos ) {
            Char ch = Actor.findChar( pos );
            if (ch != null && !ch.isImmune(com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire.class)) {

                if(ch instanceof DeadDogCerberus){
                    Buff.affect( ch, FireSuperDr.class).set((2), 1);
                } else {
                    Buff.affect( ch, Burning.class ).reignite( ch );
                }

            }

            Heap heap = Dungeon.level.heaps.get( pos );
            if (heap != null) {
                heap.burn();
            }

            Plant plant = Dungeon.level.plants.get( pos );
            if (plant != null){
                plant.wither();
            }
        }

        @Override
        public void use( BlobEmitter emitter ) {
            super.use( emitter );
            emitter.pour( FlameParticle.FACTORY, 0.03f );
        }

        @Override
        public String tileDesc() {
            return Messages.get(this, "desc");
        }
    }

    /**
     * 近战攻击获得50%穿甲与50%精准
     */
    public static class CerberusBless extends FlavourBuff {

        public static final float DURATION	= 10f;

        {
            type = buffType.POSITIVE;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.BLESS;
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

    }

}
