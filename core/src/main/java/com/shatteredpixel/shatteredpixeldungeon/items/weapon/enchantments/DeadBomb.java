package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class DeadBomb extends Weapon.Enchantment {

    private static ItemSprite.Glowing ORANGERED = new ItemSprite.Glowing(0xFF3300);

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        // lvl 0 - 33%
        // lvl 1 - 50%
        // lvl 2 - 60%
        int level = Math.max(0, weapon.level());

        if (Random.Int(level + 3) >= 2) {

           //

        }

        if (attacker instanceof Hero) {
            if (((Hero) attacker).belongings.armor.glyph != null) {
                comboProc(this, ((Hero) attacker).belongings.armor.glyph, attacker, defender, damage);
            }
        }

        return damage;

    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xDC143C, 1f);
    }
}
