import java.util.LinkedList;

public class Game_Vs_AI {

    Chessboard board;
    public Game_Vs_AI(Chessboard board) {
        this.board=board;
    }



    public ReturnObject alphabeta(double alpha, double beta, int depthleft, boolean player, Chessboard board, ReturnObject object){
        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            return new ReturnObject(board.eval_func(),object.moves);
        }

        if(player){
            ReturnObject maxEval = new ReturnObject(-99999,new LinkedList<ChessMove>());

            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces(true);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.makeMove(true,move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=object.moves;
                    Movelist.add(move);

                    ReturnObject eval=alphabeta(alpha,beta,depthleft-1,false,child,new ReturnObject(object.eval,Movelist));

                    if(eval.eval==maxEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1)
                            maxEval=eval;
                    }else if(eval.eval>maxEval.eval){//maxEval=max(eval,maxEval);
                        maxEval=eval;
                    }

                    alpha=Math.max(alpha,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            return maxEval;

        }else{

            ReturnObject minEval = new ReturnObject(99999,new LinkedList<ChessMove>());

            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces(false);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.makeMove(false,move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=object.moves;
                    Movelist.add(move);

                    ReturnObject eval=alphabeta(alpha,beta,depthleft-1,true,child,new ReturnObject(object.eval,Movelist));

                    if(eval.eval== minEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1)
                            minEval =eval;
                    }else if(eval.eval< minEval.eval){//minEval=max(eval,minEval);
                        minEval =eval;
                    }

                    beta=Math.min(beta,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            return minEval;



        }
        //return object;

    }
}
