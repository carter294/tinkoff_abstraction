package Weapons;

public class BasketballBat extends Weapon {
    public BasketballBat(int x, int y) {
        super(x, y);
        super.name = "Basketball bat";
        super.range = 1;
        super.damage = 40;
        super.knockbackRange = 6;
    }
}
