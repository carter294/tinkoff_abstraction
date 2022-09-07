package Worms;

import Weapons.Weapon;

import java.util.Objects;

public abstract class Worm {
    protected int resistance;
    protected int moveSpeed;
    protected int hp = 100;
    protected String wormClass;
    protected String name;
    protected String team;
    protected String status = "alive";
    protected int[] position = new int[2];
    protected String[] arsenal;
    protected boolean Shield = false;

    protected Worm(String name, boolean team, int x, int y) {
        this.name = name;
        this.team = team ? "Red" : "Blue";
        this.position[0] = x;
        this.position[1] = y;
    }

    @Override
    public String toString() {
        String color = Objects.equals(this.team, "Red") ? "\u001B[31m" : "\u001B[34m";
        return color + this.name + "\u001B[0m - " + this.wormClass + " - " + this.hp + " hp - (" + this.position[0] +
                "," +  this.position[1] + ") - " + (this.Shield ? "Shield - " : "No shield - ") + this.status;
    }

    public String getTeam() {
        return this.team;
    }

    public String getStatus() {
        return this.status;
    }

    public String getName() {
        String color = Objects.equals(this.team, "Red") ? "\u001B[31m" : "\u001B[34m";
        return color + this.name + "\u001B[0m";
    }

    public String getArsenal() {
        String res = "";
        for (int i = 0; i < this.arsenal.length; i++)
            res += i + " - " + this.arsenal[i] + "\n";
        return res;
    }

    public String getWeapon(int i) {
        return this.arsenal[i];
    }

    public int[] getPosition() {
        return this.position;
    }

    public int getResistance() {
        return this.resistance;
    }

    public boolean outOfMap() {
        return this.position[0] <= 0 || this.position[0] > 10 || this.position[1] <= 0 || this.position[1] > 10;
    }

    public boolean hasHp() {
        return this.hp > 0;
    }

    public void die() {
        if (outOfMap()) {
            this.status = "dead";
            System.out.println("Unfortunately, " + this.getName() + " can't swim.");
            return;
        }
        if (this.hp <= 0) {
            this.status = "dead";
            System.out.println("Unfortunately, " + this.getName() + " is out of hp.");
        }
    }

    protected void takeDamage(int damage) {
        this.hp -= damage * (100 - this.getResistance()) / 100;
        this.Shield = false;
    }

    public void move(char dir, int steps) {
        switch (dir) {
            case 'l':
                this.position[1] -= steps;
                break;
            case 'r':
                this.position[1] += steps;
                break;
            case 'u':
                this.position[0] += steps;
                break;
            case 'd':
                this.position[0] -= steps;
                break;
        }
    }
}