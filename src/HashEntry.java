public class HashEntry {
    private long key;
    private int depth;
    private double score;
    private int flag;

    public HashEntry(long key, int depth, double score, int flag) {
        this.key = key;
        this.depth = depth;
        this.score = score;
        this.flag = flag;
    }

    // Getters and setters for the entry's fields

    public int getDepth() {
        return depth;
    }

    public int getFlag() {
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

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setScore(double score) {
        this.score = score;
    }
}