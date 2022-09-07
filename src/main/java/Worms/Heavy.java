package Worms;

public class Heavy extends Worm implements Imelee {
    public Heavy(String name, boolean team, int x, int y) {
        super(name, team, x, y);
        super.wormClass = "Heavy";
        super.moveSpeed = 1;
        super.resistance = 50;
        super.arsenal = new String[] { "Basketball bat" };
    }
}