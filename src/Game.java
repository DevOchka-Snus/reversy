import java.util.Vector;

public class Game {
    private static int max1;
    private static int max2;
    private Point[][] desk;
    private final String mode;

    public Game(String mode) {
        desk = new Point[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i == 2 && j == 3) || (i == 3 && j == 2) || (i == 4 && j == 5) || (i == 5 && j == 4)) {
                    desk[i][j] = new Point(i, j, "Possible");
                } else if ((i == 3 || i == 4) && (j == 3 || j == 4)) {
                    if (i == j) {
                        desk[i][j] = new Point(i, j, "Player2");
                    } else {
                        desk[i][j] = new Point(i, j, "Player1");
                    }
                } else {
                    desk[i][j] = new Point(i, j, "Free");
                }
            }
        }
        this.mode = mode;
        max1 = 0;
        max2 = 0;
    }

    public Game(Game game) {
        desk = game.desk;
        mode = game.mode;
    }

    private int printPlayerPoints(Point point, String yourName, String enemyName) {
        int count = 0;
        Vector<Point> result = new Vector<>();
        for (int y = -1; y < 2 ; y++) {
            for (int x = -1; x < 2; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Vector<Point> enemyPoints = new Vector<Point>();
                for (int k = 1; k < 8; k++) {
                    var a = point.getX() + y * k;
                    var b = point.getY() + x * k;
                    if (0 <= a && a < 8 && 0 <= b && b < 8) {
                        if (desk[a][b].getFlag().equals(enemyName)) {
                            enemyPoints.add(desk[a][b]);
                        }
                        if (desk[a][b].getFlag().equals("Free")) {
                            break;
                        }
                        if (desk[a][b].getFlag().equals(yourName)) {
                            for (int i = 0; i < enemyPoints.size(); i++) {
                                result.add(enemyPoints.elementAt(i));
                            }
                            count += enemyPoints.size();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < result.size(); i++) {
            result.elementAt(i).changeFlag(yourName);
        }
        return count;
    }

    private void printPossiblePoints(Point point, String yourName, String enemyName) {
        if (point.getFlag().equals(yourName) || point.getFlag().equals(enemyName)) {
            return;
        }
        for (int y = -1; y < 2 ; y++) {
            for (int x = -1; x < 2; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int count = 0;
                for (int k = 1; k < 8; k++) {
                    var a = point.getX() + y * k;
                    var b = point.getY() + x * k;
                    if (0 <= a && a < 8 && 0 <= b && b < 8) {
                        if (desk[a][b].getFlag().equals("Free") || desk[a][b].getFlag().equals("Possible")) {
                            break;
                        }
                        if (desk[a][b].getFlag().equals(enemyName)) {
                            ++count;
                        }
                        if (desk[a][b].getFlag().equals(yourName)) {
                            if (count > 0) {
                                point.changeFlag("Possible");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void helpFixPoints(Player player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (desk[i][j].getFlag().equals(player.toString())) {
                    var count  = printPlayerPoints(desk[i][j], player.toString(), player.getEnemy().toString());
                    player.addScores(count);
                    player.getEnemy().subScores(count);
                }
            }
        }
    }

    public void fixPoints(boolean flag, Player player1, Player player2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (desk[i][j].getFlag().equals("Possible")) {
                    desk[i][j].changeFlag("Free");
                }
            }
        }
        if (flag) {
            helpFixPoints(player1);
            helpFixPoints(player2);
        } else {
            helpFixPoints(player2);
            helpFixPoints(player1);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String p1, p2;
                if (!flag) {
                    p1 = player1.toString();
                    p2 = player2.toString();
                } else {
                    p1 = player2.toString();
                    p2 = player1.toString();
                }
                printPossiblePoints(desk[i][j], p1, p2);
            }
        }
    }

    public boolean over(Player player1, Player player2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (desk[i][j].getFlag().equals("Possible")) {
                    return false;
                }
            }
        }
        return true;
    }

    public Point getPoint(int x, int y) {
        return desk[x][y];
    }

    public Player getWinner(Player player1, Player player2) {
        max1 = Math.max(player1.getScores(), max1);
        if (mode.equals("2Player")) {
            max2 = Math.max(player2.getScores(), max2);
        }
        if (player1.getScores() == player2.getScores()) {
            return null;
        }
        return player1.getScores() < player2.getScores() ? player2 : player1;
    }

    public int getMaxScorePlayer1() {
        return max1;
    }

    public int getMaxScorePlayer2() {
        return max2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < 8; i++) {
            sb.append(" \t" + (i + 1) + "\t|");
        }
        for (int i = 0; i < 8; i++) {
            sb.append("\n");
            for (int j = 0; j < 65; j++) {
                sb.append("—");
            }
            sb.append("\n");
            sb.append((i + 1) + "|");
            for (int j = 0; j < 8; j++) {
                sb.append("\t" + desk[i][j].toString() + "\t|");
            }

        }
        return sb.toString();
    }
}
