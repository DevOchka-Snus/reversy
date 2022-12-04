public class Point {
    private final int x;
    private final int y;
    private String flag;

    public Point(int x, int y, String flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String getFlag() {
        return flag;
    }

    public void changeFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        switch (flag) {
            case "Free":
                return " ";
            case "Player1":
                return "○";
            case "Player2":
                return "●";
            default:
                return "*";
        }
    }
}
