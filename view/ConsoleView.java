package view;

import java.util.Scanner;

import model.Player;
import model.Stadium;

public class ConsoleView {
    private Stadium stadium;

    public ConsoleView(Stadium stadium) {
        this.stadium = stadium;
    }

    public void display() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Player player = stadium.whatsInTheBox(i, j);

                if (player == null) {
                        System.out.print(".");
                } else if (player.getTeam().equals("Snowkids")) {
                        System.out.print(player.getBallPossession() ? "N" : "n");
                } else if (player.getTeam().equals("Shadows")) {
                        System.out.print(player.getBallPossession() ? "H" : "h");
                }
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Stadium stadium = new Stadium();
        ConsoleView consoleView = new ConsoleView(stadium);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Utilisation :");
        System.out.println();
        System.out.println("deplacement = d [position] [direction]");
        System.out.println("passe = p [depuis] [vers]");
        System.out.println();
        System.out.println("position, depuis, vers = coordonnées d'échequier");
        System.out.println("a7 b7 c7 d7 e7 f7 g7");
        System.out.println("a6 b6 c6 d6 e6 f6 g6");
        System.out.println("a5 b5 c5 d5 e5 f5 g5");
        System.out.println("a4 b4 c4 d4 e4 f4 g4");
        System.out.println("a3 b3 c3 d3 e3 f3 g3");
        System.out.println("a2 b2 c2 d2 e2 f2 g2");
        System.out.println("a1 b1 c1 d1 e1 f1 g1");
        System.out.println();
        System.out.println("direction = u | d | l | r");
        System.out.println();

        while (true) {
            consoleView.display();
            System.out.println();

            String command = scanner.next();

            if (command.equals("d")) {
                String position = scanner.next();
                int i = 6 - ((int)position.charAt(1) - (int)'1');
                int j = (int)position.charAt(0) - (int)'a';

                char direction = scanner.next().toUpperCase().charAt(0);

                Player player = stadium.whatsInTheBox(i, j);
                stadium.move(player, direction);
            } else if (command.equals("p")) {
                String from = scanner.next();
                int fromI =  6 - ((int)from.charAt(1) - (int)'1');
                int fromJ = (int)from.charAt(0) - (int)'a';

                String to = scanner.next();
                int toI = 6 - ((int)to.charAt(1) - (int)'1');
                int toJ = (int)to.charAt(0) - (int)'a';

                Player fromPlayer = stadium.whatsInTheBox(fromI, fromJ);
                Player toPlayer = stadium.whatsInTheBox(toI, toJ);
                stadium.pass(fromPlayer, toPlayer);
            }
        }
    }
}