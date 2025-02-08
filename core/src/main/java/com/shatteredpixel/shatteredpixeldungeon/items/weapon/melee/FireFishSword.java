package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class FireFishSword extends MeleeWeapon{

    {
        image = ItemSpriteSheet.FIREFISHSWORD;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1f;
        tier = 6;
        ACC = 2.90f; //20% boost to accuracy
        DLY = 1.6f; //2x speed
        cursed = true;
        enchant(Enchantment.randomCurse());
    }

    public static Weapon resetling(IceFishSword ingredient ) {
        FireFishSword result = new FireFishSword();
        result.quantity = ingredient.quantity();
        result.identify();
        result.level = ingredient.level;
        if(ingredient.customName != null){
            result.customName = ingredient.customName;
        }
        GLog.n(Messages.get( FireFishSword.class, "resetling"),result.name());
        return result;
    }

    @Override
    public int min(int lvl) {
        return 2 + lvl * 3;
    }

    @Override
    public int max(int lvl) {
        return 6 + lvl * 7;
    }


    public void bolt(Integer target, final Mob mob){
        if (target != null) {

            final Ballistica shot = new Ballistica( Dungeon.hero.pos, target, Ballistica.PROJECTILE);

            fx(shot, new Callback() {
                public void call() {
                    onHit(shot, mob);
                }
            });
        }
    }

    protected void onHit(Ballistica bolt, Mob mob) {

        //presses all tiles in the AOE first

        if (mob != null){

            if (mob.isAlive() && bolt.path.size() > bolt.dist+1) {
                Buff.prolong( mob, Hex.class, Hex.DURATION );
                Buff.affect(mob, Burning.class).reignite(mob, 8f);
            }
        }

    }

    @Override
    public String info() {

        String info = desc();

        if (levelKnown && Dungeon.hero != null) {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
            if (STRReq() > Dungeon.hero.STR()) {
                info += " " + Messages.get(Weapon.class, "too_heavy");
            } else if (Dungeon.hero.STR() > STRReq()){
                info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
            }
        } else {
            info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));

            if(Dungeon.hero !=null){
                if (STRReq(0) > Dungeon.hero.STR()) {
                    info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
                }
            }
        }

        switch (augment) {
            case SPEED:
                info += " " + Messages.get(Weapon.class, "faster");
                break;
            case DAMAGE:
                info += " " + Messages.get(Weapon.class, "stronger");
                break;
            case NONE:
        }

        if (enchantment != null && (cursedKnown || !enchantment.curse())){
            info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
            info += " " + Messages.get(enchantment, "desc");
        }

        if(Dungeon.hero != null){
            if (cursed && isEquipped( Dungeon.hero ) ) {
                info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
            } else if (cursedKnown && cursed) {
                info += "\n\n" + Messages.get(Weapon.class, "cursed");
            } else if (!isIdentified() && cursedKnown){
                info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
            }
        }

        return info;
    }

    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar( Dungeon.hero.sprite.parent, MagicMissile.FORCE, Dungeon.hero.sprite, bolt.collisionPos,
                callback);
    }

    public int proc(Char attacker, Char defender, int damage) {
        if(attacker instanceof Hero && Random.Int(10)==3){
            for(Mob mob : ((Hero) attacker).visibleEnemiesList()){
                bolt(mob.pos, mob);
            }
        }

        return super.proc(attacker, defender, damage);
    }
}

