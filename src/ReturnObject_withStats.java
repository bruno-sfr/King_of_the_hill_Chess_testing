import java.util.LinkedList;

public class ReturnObject_withStats {
    public double eval;
    public LinkedList<ChessMove> moves;
    public int NumPositons;
    public int NumHashs;

    public ReturnObject_withStats(double _eval, int _NumPositons, int _NumHashs, LinkedList<ChessMove> _moves) {
        this.eval=_eval;
        this.moves=_moves;
        this.NumPositons = _NumPositons;
        this.NumHashs = _NumHashs;
    }

    public ReturnObject_withStats(double _eval, int _NumPositons, LinkedList<ChessMove> _moves) {
        this.eval=_eval;
        this.moves=_moves;
        this.NumPositons = _NumPositons;
        this.NumHashs = 0;
    }

    public ReturnObject_withStats(LinkedList<ChessMove> _moves){
        this.moves=_moves;
        this.eval=0;
        this.NumPositons=0;
        this.NumHashs = 0;
    }
    public ReturnObject_withStats(double _eval){
        this.eval=_eval;
        this.NumPositons=0;
        this.moves=new LinkedList<>();
        this.NumHashs = 0;
    }
}
