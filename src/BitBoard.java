import java.util.LinkedList;

public class BitBoard {
    private long board;

    public BitBoard() {
        board = 0L;
    }

    public BitBoard(long pboard) {
        board = pboard;
    }

    public void setSquare(int square) {
        if(square > 63 | square < 0){
            return;
        }
        board |= (1L << square);
    }

    public void clearSquare(int square) {
        if(square > 63 | square < 0){
            return;
        }
        board &= ~(1L << square);
    }

    public boolean isSquareSet(int square) {
        if(square > 63 | square < 0){
            return false;
        }
        return ((board & (1L << square)) != 0);
    }

    public long getBoard() {
        return board;
    }

    public LinkedList<Integer> allSetSquares(){
        LinkedList<Integer> list = new LinkedList<>();
        long _board = board;
        for (int i = 0; i < 64; i++) {
            int index = Long.numberOfTrailingZeros(_board);             //get index of first piece
            if (index == 64)         //that seems unnecessary? someone delete, mom Im scared
                break;
            _board = _board & ~Long.lowestOneBit(_board);     //delete piece from board
            list.add(index);
        }
        return list;
    }

    public void setBoard(long board) {
        this.board = board;
    }

    public String boardWithChar(char c){
        String binaryString = Long.toBinaryString(board);
        String _formattedString = String.format("%64s", binaryString).replace('1', c);
        return String.format("%64s", _formattedString).replace(' ', '0');
    }

    public void printBoard() {
        String binaryString = Long.toBinaryString(board);
        String formattedString = String.format("%64s", binaryString).replace(' ', '0');
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.print(formattedString.charAt(Math.abs(63-square)) + " ");
            }
            System.out.println("|" + (rank + 1));
        }
        System.out.println("_______________");
        System.out.println("A B C D E F G H");
    }
}
