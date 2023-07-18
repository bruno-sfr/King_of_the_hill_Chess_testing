import java.util.LinkedList;

public class LibEntry {
    public Chessboard chessboard;
    public ChessMove move;
    private long key;
    private boolean WhiteTurn;

    public LibEntry(Chessboard board, ChessMove pmove, Boolean pWhiteTurn){
        chessboard = board;
        move = pmove;
        WhiteTurn = pWhiteTurn;
    }

    public LibEntry(long pkey, ChessMove pmove, Boolean pWhiteTurn){
        key = pkey;
        move = pmove;
        WhiteTurn = pWhiteTurn;
    }

    public ChessMove getMove() {
        return move;
    }

    public Boolean getWhiteTurn() {
        return WhiteTurn;
    }

    public LinkedList<String> listentry(){
        LinkedList<String> list = new LinkedList<>();
        list.add(Long.toString(chessboard.White.getBoard()));
        list.add(Long.toString(chessboard.Black.getBoard()));
        list.add(Long.toString(chessboard.Pawn.getBoard()));
        list.add(Long.toString(chessboard.Bishop.getBoard()));
        list.add(Long.toString(chessboard.Knight.getBoard()));
        list.add(Long.toString(chessboard.Rook.getBoard()));
        list.add(Long.toString(chessboard.King.getBoard()));
        list.add(Long.toString(chessboard.Queen.getBoard()));
        list.add(Long.toString(move.getFrom()));
        list.add(Long.toString(move.getTo()));
        list.add(Boolean.toString(WhiteTurn));
        return list;
    }
}
