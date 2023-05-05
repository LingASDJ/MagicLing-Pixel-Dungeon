package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.MagicalFireRoom;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfAnmy extends DamageWand {

    public static class AllyToRestart extends AllyBuff {

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

        @Override
        public void fx(boolean on) {
            if (on) {
                target.sprite.add(CharSprite.State.SHIELDED);
                Statistics.TryUsedAnmy = true;
            }
            else
                target.sprite.remove(CharSprite.State.SHIELDED);
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x66bbcc);
        }

        public int icon() {
            return BuffIndicator.HEX;
        }

    }

    @Override
    protected int initialCharges() {
        return 1;
    }

    @Override
    public void updateLevel() {
        maxCharges = 1;
        curCharges = Math.min( curCharges, maxCharges );
    }

    {
        image = ItemSpriteSheet.WAND_KCX;
    }


    public int min(int lvl){
        return 2+lvl;
    }

    public int max(int lvl){
        return 8+5*lvl;
    }

    @Override
    public void onZap(Ballistica bolt) {

        Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
        if (heap != null) {
            heap.freeze();
        }

        Fire fire = (Fire) Dungeon.level.blobs.get(Fire.class);
        if (fire != null && fire.volume > 0) {
            fire.clear( bolt.collisionPos );
        }

        MagicalFireRoom.EternalFire eternalFire = (MagicalFireRoom.EternalFire)Dungeon.level.blobs.get(MagicalFireRoom.EternalFire.class);
        if (eternalFire != null && eternalFire.volume > 0) {
            eternalFire.clear( bolt.collisionPos );
            //bolt ends 1 tile short of fire, so check next tile too
            if (bolt.path.size() > bolt.dist+1){
                eternalFire.clear( bolt.path.get(bolt.dist+1) );
            }

        }

        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null){

            int damage = 0;

            wandProc(ch, chargesPerCast());
            ch.damage(damage, this);
            Sample.INSTANCE.play( Assets.Sounds.HIT_MAGIC, 1, 1.1f * Random.Float(0.87f, 1.15f) );

            if (ch.isAlive() && !Statistics.TryUsedAnmy && (!ch.properties().contains(Char.Property.BOSS) || !ch.properties().contains(Char.Property.MINIBOSS))){
                Buff.affect(ch, AllyToRestart.class);
            } else {
                GLog.n("不能影响Boss和精英怪，也不能在使用后继续在本层使用,每层仅限一次使用。");
            }
        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }
    }

    @Override
    public void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar(curUser.sprite.parent,
                MagicMissile.FROST,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.Sounds.ZAP);
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
       //
    }

    @Override
    public void staffFx(MagesStaff.StaffParticle particle) {
        particle.color(0x88CCFF);
        particle.am = 0.6f;
        particle.setLifespan(2f);
        float angle = Random.Float(PointF.PI2);
        particle.speed.polar( angle, 2f);
        particle.acc.set( 0f, 1f);
        particle.setSize( 0f, 1.5f);
        particle.radiateXY(Random.Float(1f));
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

}

