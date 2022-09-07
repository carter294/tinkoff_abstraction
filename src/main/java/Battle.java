import Weapons.BasketballBat;
import Weapons.Grenade;
import Worms.*;

import java.util.Arrays;
import java.util.Scanner;

public class Battle {

    private void teamBuild(Worm[] team, boolean teamColor) {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < team.length; i++) {
            System.out.print("Pick class for a worm:\n" +
                    "1 - Soldier: move speed 2, resist 25%, melee + range\n" +
                    "2 - Heavy: move speed 1, resist 50%, melee\n" +
                    "3 - Scout: move speed 3, resist 0%, range\n");
            int wormClass = scan.nextInt();
            System.out.println("Enter name:");
            scan.nextLine();
            String name = scan.nextLine();
            switch (wormClass) {
                case 1:
                    team[i] = new Soldier(name, teamColor, teamColor ? 1 : 10, 1 + i * 3);
                    break;
                case 2:
                    team[i] = new Heavy(name, teamColor, teamColor ? 1 : 10, 1 + i * 3);
                    break;
                case 3:
                    team[i] = new Scout(name, teamColor, teamColor ? 1 : 10, 1 + i * 3);
                    break;
            }
            System.out.println(team[i].toString());
        }
    }

    public void printTeam(Worm[] team) {
        System.out.println("Team " + team[0].getTeam() + ":");
        Arrays.stream(team).forEach(it -> System.out.println(it.toString()));
    }

    public void cheers(int redAlive) {
        System.out.println("Congrats team " + (redAlive != 0 ? "\u001B[31mRed\u001B[0m!" : "\u001B[34mBlue!\u001B[0m"));
    }

    public void start() {
        System.out.println("Red's turn to build the team:");
        Worm[] teamRed = new Worm[1];
        teamBuild(teamRed, true);
        printTeam(teamRed);
        System.out.println("Blue's turn to build the team:");
        Worm[] teamBlue = new Worm[1];
        teamBuild(teamBlue, false);
        printTeam(teamBlue);
        int redAlive = teamRed.length;
        int blueAlive = teamBlue.length;
        int redWormsIndex = 0;
        int blueWormsIndex = 0;
        boolean redsTurn = true;
        printTeam(teamRed);
        printTeam(teamBlue);
        Scanner scan = new Scanner(System.in);
        while (blueAlive != 0 && redAlive != 0) {
            boolean shooted = false;
            Worm[] currentTeam = redsTurn ? teamRed : teamBlue;
            Worm[] oppositeTeam = !redsTurn ? teamRed : teamBlue;
            int currentIndex = redsTurn ? redWormsIndex : blueWormsIndex;
            Worm currentWorm = currentTeam[currentIndex % currentTeam.length].getStatus().equals("alive") ? currentTeam[currentIndex % currentTeam.length] :
                    currentTeam[(currentIndex + 1) % currentTeam.length].getStatus().equals("alive") ? currentTeam[(currentIndex + 1) % currentTeam.length] :
                            currentTeam[(currentIndex + 2) % currentTeam.length].getStatus().equals("alive") ? currentTeam[(currentIndex + 2) % currentTeam.length] :
                                    currentTeam[(currentIndex + 3) % 4];
            System.out.println(currentWorm.getName() + "'s turn:");
            System.out.println("To move in specific direction write <\u001B[33mmove l/r/u/d 1/.../move speed of current" +
                    " worm\u001B[0m>\nTo shoot write <\u001B[33marsenal\u001B[0m>\nTo use shield write<\u001B[33mshield index of the worm\u001B[0m>");
            for (int i = 0; i < 3 && currentWorm.getStatus().equals("alive") && redAlive != 0 && blueAlive != 0; i++) {
                String ch = scan.nextLine();
                if (ch.matches("move [lrdu] \\d")) {
                    char dir = ch.split(" ")[1].charAt(0);
                    int steps = Integer.parseInt(ch.split(" ")[2]);
                    currentWorm.move(dir, steps);
                    if (currentWorm.outOfMap()) {
                        currentWorm.die();
                        if (redsTurn)
                            redAlive--;
                        else
                            blueAlive--;
                    }
                    printTeam(teamRed);
                    printTeam(teamBlue);
                    continue;
                }
                if (ch.equals("arsenal") && !shooted) {
                    System.out.println("Choose weapon:");
                    System.out.print(currentWorm.getArsenal());
                    int n = Integer.parseInt(scan.nextLine());
                    System.out.println("Write coordinates to hit/shoot in:");
                    int[] coords = Arrays.stream(scan.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    switch (currentWorm.getWeapon(n)) {
                        case "Grenade":
                            Grenade g = new Grenade(coords[0], coords[1]);
                            for (Worm worm : oppositeTeam) {
                                if (g.inRange(worm)) {
                                    if (currentWorm instanceof Scout)
                                        ((Scout) currentWorm).throww(coords[0], coords[1], worm, g);
                                    if (currentWorm instanceof Soldier)
                                        ((Soldier) currentWorm).throww(coords[0], coords[1], worm, g);
                                }
                                if (worm.outOfMap() || !worm.hasHp()) {
                                    worm.die();
                                    if (!redsTurn)
                                        redAlive--;
                                    else
                                        blueAlive--;
                                }
                            }
                            for (Worm worm : currentTeam) {
                                if (g.inRange(worm)) {
                                    if (currentWorm instanceof Scout)
                                        ((Scout) currentWorm).throww(coords[0], coords[1], worm, g);
                                    if (currentWorm instanceof Soldier)
                                        ((Soldier) currentWorm).throww(coords[0], coords[1], worm, g);
                                }
                                if (worm.outOfMap() || !worm.hasHp()) {
                                    worm.die();
                                    if (redsTurn)
                                        redAlive--;
                                    else
                                        blueAlive--;
                                }
                            }
                            break;
                        case "Basketball bat":
                            for (Worm worm : oppositeTeam) {
                                if (worm.getPosition()[0] == coords[0] && worm.getPosition()[1] == coords[1]) {
                                    if (currentWorm instanceof Soldier)
                                        ((Soldier) currentWorm).hit(currentWorm.getPosition()[0], currentWorm.getPosition()[1], worm, new BasketballBat(coords[0], coords[1]));
                                    if (currentWorm instanceof Heavy)
                                        ((Heavy) currentWorm).hit(currentWorm.getPosition()[0], currentWorm.getPosition()[1], worm, new BasketballBat(coords[0], coords[1]));
                                    if (worm.outOfMap() || !worm.hasHp()) {
                                        worm.die();
                                        if (!redsTurn)
                                            redAlive--;
                                        else
                                            blueAlive--;
                                    }
                                }
                            }
                            break;
                    }
                    printTeam(teamRed);
                    printTeam(teamBlue);
                    shooted = true;
                    continue;
                }
                if (ch.matches("shield \\d")) {
                    if (currentWorm instanceof Soldier)
                        ((Soldier) currentWorm).useShield(currentTeam[Integer.parseInt(ch.split(" ")[1]) - 1]);
                    if (currentWorm instanceof Scout)
                        ((Scout) currentWorm).useShield(currentTeam[Integer.parseInt(ch.split(" ")[1]) - 1]);
                    if (currentWorm instanceof Heavy)
                        ((Heavy) currentWorm).useShield(currentTeam[Integer.parseInt(ch.split(" ")[1]) - 1]);
                    printTeam(teamRed);
                    printTeam(teamBlue);
                    shooted = true;
                }
            }
            if (redsTurn)
                redWormsIndex++;
            else
                blueWormsIndex++;
            redsTurn = !redsTurn;
        }
        if (redAlive == 0 && blueAlive == 0)
            System.out.println("Friendship won!");
        else
            cheers(redAlive);
    }
}