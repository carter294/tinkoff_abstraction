package Worms;

public class Scout extends Worm implements IRange {
    public Scout(String name, boolean team, int x, int y) {
        super(name, team, x, y);
        super.wormClass = "Scout";
        super.moveSpeed = 3;
        super.resistance = 0;
        super.arsenal = new String[] { "Grenade" };
    }
}