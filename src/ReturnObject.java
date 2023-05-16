import java.util.LinkedList;

public class ReturnObject {
    public double eval;
    public LinkedList<ChessMove> moves;
    public ReturnObject(double _eval, LinkedList<ChessMove> _moves) {
        this.eval=_eval;
        this.moves=_moves;
    }

    public ReturnObject(LinkedList<ChessMove> _moves){
        this.moves=_moves;
        this.eval=0;

    }
    public ReturnObject(double _eval){
        this.eval=_eval;
        this.moves=new LinkedList<>();
    }
}
