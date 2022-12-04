
public abstract class Player {
    protected Player enemy;
    protected String id;
    protected int scores;
    protected String mode;

    public Player(String id, String mode) {
        this.id = id;
        this.mode = mode;
        scores = 2;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public void addScores(int value) {
        scores += value;
    }

    public void subScores(int value) {
        scores -= value;
    }

    public int getScores() {
        return scores;
    }

    public abstract void makeMove(Game game);

    @Override
    public String toString() {
        return id;
    }
}
