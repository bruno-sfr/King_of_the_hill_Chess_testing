public class ChessMove {
    private int from;
    private int to;

    public ChessMove(int _from, int _to){
        if(64>_from & _from>0 & 64>_to & _to>0){
            this.from = _from;
            this.to = _to;
        }else {
            throw new RuntimeException("Chess Position not in 63 to 0 Bounds");
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setFrom(int _from) {
        if(64>_from & _from>0){
            this.from = _from;
        }else {
            throw new RuntimeException("Chess Position not in 63 to 0 Bounds");
        }
    }

    public void setTo(int _to) {
        if(64>_to & _to>0){
            this.to = _to;
        }else {
            throw new RuntimeException("Chess Position not in 63 to 0 Bounds");
        }
    }
}
