import java.util.Vector;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String id, String mode) {
        super(id, mode);
    }

    @Override
    public void makeMove(Game game) {
        System.out.println("Ходит компьютер");
        easyMove(game);
    }

    private Vector<Point> getUnprintedPoints(Point point, String yourName, Game game) {
        var unprintedPoints = new Vector<Point>();
        for (int y = -1; y < 2 ; y++) {
            for (int x = -1; x < 2; x++) {
                for (int k = 1; k < 8; k++) {
                    var a = point.getX() + y * k;
                    var b = point.getY() + x * k;
                    if (0 <= a && a < 8 && 0 <= b && b < 8) {
                        var tmp = game.getPoint(a, b);
                        if (tmp.getFlag().equals("Free") || tmp.getFlag().equals("Possible")) {
                            continue;
                        } else if (tmp.getFlag().equals(yourName)) {
                            break;
                        } else {
                            unprintedPoints.add(tmp);
                        }
                    }
                }
            }
        }
        return unprintedPoints;
    }

    private void easyMove(Game game) {
        Vector<Point> possiblePoints = new Vector<Point>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPoint(i, j).getFlag().equals("Possible")) {
                    possiblePoints.add(game.getPoint(i, j));
                }
            }
        }
        double[] arr = new double[possiblePoints.size()];
        for (int i = 0; i < possiblePoints.size(); i++) {
            var unprintedPoints = getUnprintedPoints(possiblePoints.elementAt(i), id, game);
            arr[i] = 0;
            for (int j = 0; j < unprintedPoints.size(); j++) {
                var point = unprintedPoints.elementAt(j);
                if (point.getX() == 0 || point.getY() == 0 || point.getX() == 7 || point.getY() == 7) {
                    arr[i] += 2;
                    if (point.getX() == 0 || point.getX() == 7) {
                        if (point.getY() == 0 || point.getY() == 7) {
                            arr[i] += 0.8;
                        } else {
                            arr[i] += 0.4;
                        }
                    } else {
                        arr[i] += 0.4;
                    }
                } else {
                    arr[i] += 1;
                }
            }
        }
        double max = 0;
        Point answer = possiblePoints.firstElement();
        for (int i = 0; i < possiblePoints.size(); i++) {
            if (max < arr[i]) {
                max = arr[i];
                answer = possiblePoints.elementAt(i);
            }
        }
        answer.changeFlag(id);
    }
}
