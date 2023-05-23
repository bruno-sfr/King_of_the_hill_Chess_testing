import java.util.LinkedList;

public class HashEntry {
    private long key;
    private int depth;
    private double score;
    private ChessMove flag;

    public HashEntry(long key, int depth, double score, ChessMove flag) {
        this.key = key;
        this.depth = depth;
        this.score = score;
        this.flag = flag;
    }

    // Getters and setters for the entry's fields

    public int getDepth() {
        return depth;
    }

    public ChessMove getFlag() {
        return flag;
    }

    public double getScore() {
        return score;
    }

    public long getKey() {
        return key;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setFlag(ChessMove flag) {
        this.flag = flag;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setScore(double score) {
        this.score = score;
    }
}