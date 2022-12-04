import java.util.Scanner;

public class HumanPlayer extends Player{
    public HumanPlayer(String id, String mode) {
        super(id, mode);
    }

    @Override
    public void makeMove(Game game) {
        System.out.println("Ходит " + id);
        Scanner in = new Scanner(System.in);
        int x, y;
        Point point;
        do {
            try {
                System.out.println("Введите х, у:");
                var str = in.nextLine();
                if (str.split(" ").length != 2) {
                    throw new IllegalArgumentException("Введите 2 числа!");
                }
                x = Integer.parseInt(str.split(" ")[0]);
                y = Integer.parseInt(str.split(" ")[1]);
                if (!(x <= 8 && x >= 1 && y <= 8 && y >= 1)) {
                    throw new IllegalArgumentException("Некорректны введенные данные!");
                }
                point = game.getPoint(x - 1, y - 1);
                if (point.getFlag() != "Possible") {
                    throw new IllegalArgumentException("Нельзя обращаться к данной точке!");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите числа!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        point.changeFlag(id);
    }
}
