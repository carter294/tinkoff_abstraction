package Weapons;

import Worms.Worm;

public abstract class Weapon {
    protected int range;
    protected int knockbackRange;
    protected String name;
    protected int damage;
    protected int[] position;

    protected Weapon(int x, int y) {
        this.position = new int[] { x, y };
    }

    public int getDamage() {
        return this.damage;
    }

    public int getKnockbackRange() {
        return this.knockbackRange;
    }

    public boolean inRange(Worm worm) {
        return (worm.getPosition()[0] >= this.position[0] - this.range) &&
                (worm.getPosition()[0] <= this.position[0] + this.range) &&
                (worm.getPosition()[1] == this.position[1]) ||
                (worm.getPosition()[1] >= this.position[1] - this.range) &&
                        (worm.getPosition()[1] <= this.position[1] + this.range) &&
                        (worm.getPosition()[0] == this.position[0]);
    }
}
