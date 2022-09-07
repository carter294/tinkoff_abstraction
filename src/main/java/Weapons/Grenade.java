package Weapons;

public class Grenade extends Weapon {
    public Grenade(int x, int y) {
        super(x, y);
        super.name = "Grenade";
        super.range = 5;
        super.damage = 60;
        super.knockbackRange = 4;
    }
}
