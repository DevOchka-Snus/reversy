import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void menu() {
        System.out.println("Условные обозначения:");
        System.out.println("○ - клетка, где находится фишка 1-го игрока");
        System.out.println("● - клетка, где находится фишка 2-го игрока");
        System.out.println("* - свободная клетка, куда можно сходить");
        System.out.println("Сначала вводите координаты по вертикали, а потом через пробел по горизонтали!!!");
    }
    public static String chooseMode(Scanner in) {
        System.out.println("Выберете режим игры:");
        System.out.println("Easy - легкий реджим");
        System.out.println("2Player - режим с 2-мя игроками");
        String mode;
        try {
            mode = in.next();
            if (!mode.equals("Easy") && !mode.equals("Hard") && !mode.equals("2Player")) {
                throw new IllegalArgumentException("Неверно введеное значение!");
            }
            return mode;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return chooseMode(in);
        }
    }

    public static void main(String[] args) {
        menu();
        boolean flag = true;
        boolean exit;
        Scanner in = new Scanner(System.in);
        do {
            var mode = chooseMode(in);
            Game game = new Game(mode);
            Player player1 = new HumanPlayer("Player1", mode);
            Player player2;
            if (mode.equals("2Player")) {
                player2 = new HumanPlayer("Player2", mode);
            } else {
                player2 = new ComputerPlayer("Player2", mode);
            }
            player1.setEnemy(player2);
            player2.setEnemy(player1);
            do {
                try {
                    if (flag || mode.equals("2Player")) {
                        System.out.println(game);
                    }
                    if (flag) {
                        player1.makeMove(game);
                        player1.addScores(1);
                    } else {
                        player2.makeMove(game);
                        player2.addScores(1);
                    }
                    game.fixPoints(flag, player1, player2);
                    flag = !flag;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            } while (!game.over(player1, player2));
            System.out.println(game.toString());
            Player winner = game.getWinner(player1, player2);
            if (winner == null) {
                System.out.println("Ничья");
            } else {
                System.out.println("Победил " + winner.toString());
                System.out.println("Количество очков: " + winner.getScores());
                if (mode.equals("2Player")) {
                    System.out.println("Рекорд 1-го игрока: " + game.getMaxScorePlayer1());
                    System.out.println("Рекорд 2-го игрока: " + game.getMaxScorePlayer2());
                } else {
                    System.out.println("Рекорд игрока: " + game.getMaxScorePlayer1());
                }
            }
            System.out.println("Если хотите начать заново, введите y, иначе любую другую клавишу");
            var answer = in.next();
            exit = !Objects.equals(answer, "y");
        }while (!exit);
    }
}