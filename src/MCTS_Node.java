import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class MCTS_Node {
    public int visits;
    public double score;
    public List<MCTS_Node> children;
    // Other variables to store game state or move information
    public MCTS_Node parent;
    public Chessboard state;
    public ChessMove move;
    public boolean whiteTurn;

    public MCTS_Node() {
        visits = 0;
        score = 0.0;
        children = new ArrayList<>();
        state = new Chessboard();
        whiteTurn = true;
    }

    public MCTS_Node(Chessboard pstate, boolean pwhiteTurn, ChessMove pmove) {
        visits = 0;
        score = 0.0;
        children = new ArrayList<>();
        state = new Chessboard(pstate);
        whiteTurn = pwhiteTurn;
        move = pmove;
    }

    public void setBoard(Chessboard pstate){
        state = new Chessboard(pstate);
    }

    public Chessboard getState() {
        return state;
    }

    @Override
    public String toString() {
        state.printBoard();
        return "visits:" + visits + " score" + score;
    }

    public void printTree(){
        if(children.size() == 0){
            return;
        }else{
            System.out.println("children:" + children.size());
            for(MCTS_Node child : children){
                child.printTree();
            }
        }
    }
}