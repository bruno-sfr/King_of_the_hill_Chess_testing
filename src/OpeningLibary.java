import java.util.Arrays;

public class OpeningLibary {

    private LibEntry[] table;

    public OpeningLibary(int size) {
        table = new LibEntry[size];
        Arrays.fill(table, null);
    }

    public void storeEntry(long key, ChessMove move, Boolean pwhiteturn) {
        int index = (int) (key % table.length);
        index = Math.abs(index); //this line feels scuffed
        table[index] = new LibEntry(key, move, pwhiteturn);
    }

    public LibEntry probeEntry(long key) {
        int index = (int) (key % table.length);
        index = Math.abs(index); //this line feels scuffed
        LibEntry result = table[index];
        return result;
    }
}
