package Worms;

import Weapons.Weapon;

public interface Imelee extends IShield{

    default void hit(int x, int y, Worm aimWorm, Weapon weapon) {
        aimWorm.takeDamage(aimWorm.Shield ? 0 : weapon.getDamage());
        if (x == aimWorm.getPosition()[0] && y == aimWorm.getPosition()[1])
            return;
        if (x == aimWorm.getPosition()[0]) {
            aimWorm.move(y - aimWorm.getPosition()[1] == 1 ? 'l' : 'r',
                    weapon.getKnockbackRange() * (100 - aimWorm.getResistance()) / 100);
        }
        if (y == aimWorm.getPosition()[1]) {
            aimWorm.move(x - aimWorm.getPosition()[0] == 1 ? 'd' : 'u',
                    weapon.getKnockbackRange() * (100 - aimWorm.getResistance()) / 100);
        }
    }
}
