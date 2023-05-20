import java.util.LinkedList;

public class ReturnObject_MiniMax {
    public double eval;
    public LinkedList<ChessMove> moves;
    public int NumPositons;
    public ReturnObject_MiniMax(double _eval,int _NumPositons, LinkedList<ChessMove> _moves) {
        this.eval=_eval;
        this.moves=_moves;
        this.NumPositons = _NumPositons;
    }

    public ReturnObject_MiniMax(LinkedList<ChessMove> _moves){
        this.moves=_moves;
        this.eval=0;
        this.NumPositons=0;
    }
    public ReturnObject_MiniMax(double _eval){
        this.eval=_eval;
        this.NumPositons=0;
        this.moves=new LinkedList<>();
    }
}
