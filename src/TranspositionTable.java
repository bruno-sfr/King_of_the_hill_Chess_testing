import java.util.Arrays;
import java.util.LinkedList;

public class TranspositionTable {
    private static final int DEFAULT_SIZE = 1 << 20; // Default hash table size (2^20)
    private HashEntry[] table;

    public TranspositionTable() {
        this(DEFAULT_SIZE);
    }

    public TranspositionTable(int size) {
        table = new HashEntry[size];
        Arrays.fill(table, null);
    }

    public void storeEntry(long key, int depth, double score, ChessMove flag) {
        int index = (int) (key % table.length);
        index = Math.abs(index); //this line feels scuffed
        table[index] = new HashEntry(key, depth, score, flag);
    }

    public HashEntry probeEntry(long key) {
        int index = (int) (key % table.length);
        index = Math.abs(index); //this line feels scuffed
        HashEntry result = table[index];
        return result;
    }
}