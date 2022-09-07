package Worms;

import Weapons.Weapon;

public interface IRange extends IShield {

    default void throww(int x, int y, Worm aimWorm, Weapon weapon) {
        if (x == aimWorm.getPosition()[0] && y == aimWorm.getPosition()[1] && !aimWorm.Shield) {
            aimWorm.takeDamage(weapon.getDamage());
            return;
        }
        if (x == aimWorm.getPosition()[0]) {
            int percent = 0;
            switch (Math.abs(y - aimWorm.getPosition()[1])) {
                case 1:
                    percent = 92;
                    break;
                case 2:
                    percent = 84;
                    break;
                case 3:
                    percent = 76;
                    break;
                case 4:
                    percent = 68;
                    break;
                case 5:
                    percent = 60;
                    break;
            }
            aimWorm.move(y > aimWorm.getPosition()[1] ? 'l' : 'r',
                    weapon.getKnockbackRange() * (percent - aimWorm.getResistance()) / 100);
            aimWorm.takeDamage(aimWorm.Shield ? 0 : weapon.getDamage() * percent / 100);
            return;
        }
        if (y == aimWorm.getPosition()[1]) {
            int percent = 0;
            switch (Math.abs(x - aimWorm.getPosition()[0])) {
                case 1:
                    percent = 92;
                    break;
                case 2:
                    percent = 84;
                    break;
                case 3:
                    percent = 76;
                    break;
                case 4:
                    percent = 68;
                    break;
                case 5:
                    percent = 60;
                    break;
            }
            aimWorm.move(x > aimWorm.getPosition()[0] ? 'd' : 'u',
                    weapon.getKnockbackRange() * (percent - aimWorm.getResistance()) / 100);
            if (!aimWorm.Shield)
                aimWorm.takeDamage(aimWorm.Shield ? 0 : weapon.getDamage() * percent / 100);
        }
    }
}
