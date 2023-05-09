public class Position {
    String comment;
    String FEN;
    int[] moves;
    boolean whiteTurn;

    public Position(String _comment, String _FEN, int[] _moves, boolean _whiteTurn){
        this.comment = _comment;
        this.FEN =_FEN;
        this.moves = _moves;
        this.whiteTurn = _whiteTurn;
    }
}
