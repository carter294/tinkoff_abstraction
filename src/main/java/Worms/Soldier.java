package Worms;

public class Soldier extends Worm implements IRange, Imelee {
    public Soldier(String name, boolean team, int x, int y) {
        super(name, team, x, y);
        super.wormClass = "Soldier";
        super.moveSpeed = 2;
        super.resistance = 25;
        super.arsenal = new String[] { "Grenade", "Basketball bat" };
    }
}