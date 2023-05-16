import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Game_Vs_AI {

    Chessboard board;
    private static final long DURATION = 3000; // Duration in milliseconds
    private static final long DELAY = 1000; // Delay before starting the function in milliseconds
    private static final long INTERVAL = 100; // Interval between function calls in milliseconds

    private ReturnObject lastResult;
    private int Depth;

    public Game_Vs_AI(Chessboard board) {
        this.board=board;
    }

    public ReturnObject runFunction(boolean whiteTurn) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        lastResult = new ReturnObject(0, new LinkedList<ChessMove>());
        Depth = 1;

        while (System.currentTimeMillis() < endTime) {
            lastResult = alphabeta(-99999,99999, Depth, whiteTurn, board, new ReturnObject(0, new LinkedList<ChessMove>())); // Call your alphabeta function here
            Depth++;
            /*try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        return lastResult;
        /*if (isRunning) {
            return lastResult;
        }

        startTime = System.currentTimeMillis();
        isRunning = true;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                lastResult = alphabeta(-99999,99999, Depth, whiteTurn, board, new ReturnObject(0, new LinkedList<ChessMove>())); // Call your alphabeta function here
                Depth++;
            }
        };

        timer.schedule(task, INTERVAL, INTERVAL);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isRunning = false;
                task.cancel();
                timer.cancel();
                timer.purge();
            }
        }, DURATION);

        return lastResult;*/
        /*if (isRunning) {
            return lastResult;
        }

        startTime = System.currentTimeMillis();
        isRunning = true;
        lastResult = alphabeta(-99999,99999, Depth, whiteTurn, board, new ReturnObject(0, new LinkedList<ChessMove>())); // Call your alphabeta function here
        Depth++;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isRunning = false;
            }
        }, DURATION + DELAY);

        return lastResult;*/
    }

    public static void main(String[] args) {
        Game_Vs_AI runner = new Game_Vs_AI(new Chessboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
        long startTime = System.currentTimeMillis();
        ReturnObject result = runner.runFunction(true);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken in ms:"+(endTime-startTime));
        System.out.println("Result: " + result);
    }


    /*public ReturnObject iterative_Deepening(boolean player, Chessboard board, int seconds){
        ReturnObject object = new ReturnObject(0, new LinkedList<ChessMove>());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<int[]> future = executor.submit(new Callable<int[]>() {
            @Override
            public int[] call() throws Exception {
                //return randomMove(board,!board.lastPlayer);
                return alphabeta(-99999,99999,3,false,board, object);
            }
        });
        try {
            AI_move = future.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException f) {
            future.cancel(true);
            throw new RuntimeException("Timeout", f);
        } catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            executor.shutdownNow();
        }
    }*/

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
                    LinkedList<ChessMove> Movelist= (LinkedList<ChessMove>) object.moves.clone();
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
