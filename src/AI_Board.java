import java.util.LinkedList;
import java.util.concurrent.*;

public class AI_Board {

    Chessboard board;
    private ReturnObject_withStats lastResult_MTD;
    //private ReturnObject_withStats _lastResult;
    private int Depth;
    private TranspositionTable table;

    Zobrist zob;

    public AI_Board(Chessboard board) {
        zob = new Zobrist();
        this.board=board;
        this.table=new TranspositionTable(1 << 28);
    }

    public void setBoard(Chessboard board) {
        this.board = board;
    }

    //current player and duration in ms
    public ReturnObject_withStats iterativeDeepening_withNumpos(boolean whiteTurn, long DURATION) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        ReturnObject_withStats _lastResult = new ReturnObject_withStats(0);
        Depth = 1;

        while (System.currentTimeMillis() < endTime) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ReturnObject_withStats> future = executor.submit(new Callable<ReturnObject_withStats>() {
                @Override
                public ReturnObject_withStats call() throws Exception {
                    //return randomMove(board,!board.lastPlayer);
                    return alphabeta_withNumPostions(-9999999,9999999,Depth,whiteTurn,board, new ReturnObject(0));
                }
            });
            try {
                //wait for result but with only the time that is left as a limit: endtime - current time
                _lastResult = future.get(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                Depth++;
            } catch (TimeoutException f) {
                future.cancel(true);
                //throw new RuntimeException("Timeout", f);
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                executor.shutdownNow();
            }
        }

        return _lastResult;
    }

    public ReturnObject_withStats iterativeDeepening_withTT(boolean whiteTurn, long DURATION) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        ReturnObject_withStats _lastResult = new ReturnObject_withStats(0);
        Depth = 1;

        while (System.currentTimeMillis() < endTime) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ReturnObject_withStats> future = executor.submit(new Callable<ReturnObject_withStats>() {
                @Override
                public ReturnObject_withStats call() throws Exception {
                    //return randomMove(board,!board.lastPlayer);
                    return alphabeta_withStatsAndTT(-9999999,9999999,Depth,whiteTurn,board, new ReturnObject(0));
                }
            });
            try {
                //wait for result but with only the time that is left as a limit: endtime - current time
                _lastResult = future.get(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                System.out.println("Depth is " + Depth);
                Depth++;
            } catch (TimeoutException f) {
                future.cancel(true);
                //throw new RuntimeException("Timeout", f);
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                executor.shutdownNow();
            }
        }

        return _lastResult;
    }

    //current player and duration in ms
    public ReturnObject iterativeDeepening(boolean whiteTurn, long DURATION) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        ReturnObject lastResult = new ReturnObject(0, new LinkedList<ChessMove>());
        Depth = 1;

        while (System.currentTimeMillis() < endTime) {
            ReturnObject object = new ReturnObject(0, new LinkedList<ChessMove>());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ReturnObject> future = executor.submit(new Callable<ReturnObject>() {
                @Override
                public ReturnObject call() throws Exception {
                    //return randomMove(board,!board.lastPlayer);
                    return alphabeta(-9999999,9999999,Depth,whiteTurn,board, object);
                }
            });
            try {
                //wait for result but with only the time that is left as a limit: endtime - current time
                lastResult = future.get(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                Depth++;
            } catch (TimeoutException f) {
                future.cancel(true);
                //throw new RuntimeException("Timeout", f);
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                executor.shutdownNow();
            }
        }

        return lastResult;
    }

    //current player and duration in ms
    public ReturnObject_withStats iterativeDeepening_MTD(boolean whiteTurn, long DURATION) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        lastResult_MTD = new ReturnObject_withStats(0);
        Depth = 1;

        while (System.currentTimeMillis() < endTime) {
            ReturnObject object = new ReturnObject(0, new LinkedList<ChessMove>());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ReturnObject_withStats> future = executor.submit(new Callable<ReturnObject_withStats>() {
                @Override
                public ReturnObject_withStats call() throws Exception {
                    //return randomMove(board,!board.lastPlayer);
                    return MTDF(Depth,whiteTurn,board, lastResult_MTD.eval);
                }
            });
            try {
                //wait for result but with only the time that is left as a limit: endtime - current time
                lastResult_MTD = future.get(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                System.out.println("Depth is " + Depth);
                Depth++;
            } catch (TimeoutException f) {
                future.cancel(true);
                //throw new RuntimeException("Timeout", f);
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                executor.shutdownNow();
            }
        }

        return lastResult_MTD;
    }

    public static void main(String[] args) {
        AI_Board runner = new AI_Board(new Chessboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
        long startTime = System.currentTimeMillis();
        ReturnObject result = runner.iterativeDeepening(true, 10000);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken in ms:"+(endTime-startTime));
        System.out.println("Result: " + result);
        runner.board.setWhiteNext();
        for(ChessMove move: result.moves){
            runner.board.makeMove(move.getFrom(), move.getTo());
            runner.board.setWhiteNext(!runner.board.WhiteTurn);
        }
        runner.board.printBoard();
    }

    public ReturnObject alphabeta(double alpha, double beta, int depthleft, boolean player, Chessboard board, ReturnObject object){
        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            return new ReturnObject(board.eval_func_withCheck(),object.moves);
        }/*else if(board.isCheckmate(player)){
            if(player){
                return new ReturnObject(-100000,object.moves);
            }else {
                return new ReturnObject(100000,object.moves);
            }
        }*/
        if(!board.isWhiteKing()){
            //no white king so black has won
            return new ReturnObject(-100000,object.moves);
        }
        if(!board.isBlackKing()){
            //no black king so white has won
            return new ReturnObject(100000,object.moves);
        }
        /*if(board.isCheckmate(true)){
            return new ReturnObject(-100000,object.moves);
        }else if(board.isCheckmate(false)){
            return new ReturnObject(100000,object.moves);
        }*/

        if(player){
            ReturnObject maxEval = new ReturnObject(-9999999,new LinkedList<ChessMove>());

            boolean tmp = board.WhiteTurn;
            board.setWhiteNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(tmp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(true);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setWhiteNext();
                    child.makeMove(move.getFrom(),move.getTo());
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

            ReturnObject minEval = new ReturnObject(9999999,new LinkedList<ChessMove>());

            boolean tmp = board.WhiteTurn;
            board.setBlackNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(tmp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(false);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setBlackNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=(LinkedList<ChessMove>) object.moves.clone();
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

    public ReturnObject_withStats minimax(int depthleft, boolean player, Chessboard board, ReturnObject object){
        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            return new ReturnObject_withStats(board.eval_func_withCheck(),1,object.moves);
        }
        boolean tmp = board.WhiteTurn;
        board.setWhiteNext();
        if(board.isCheckmate()){
            return new ReturnObject_withStats(-100000,1,object.moves);
        }else{
            board.setBlackNext();
            if(board.isCheckmate()) {
                return new ReturnObject_withStats(100000, 1, object.moves);
            }
        }
        board.setWhiteNext(tmp);

        if(player){
            ReturnObject_withStats maxEval = new ReturnObject_withStats(-9999999,1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setWhiteNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);

            for(LinkedList<ChessMove> moves:allMoves){
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setWhiteNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist= (LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=minimax(depthleft-1,false,child,new ReturnObject(object.eval,Movelist));
                    maxEval.NumPositons = maxEval.NumPositons + eval.NumPositons;
                    if(eval.eval==maxEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = maxEval.NumPositons;
                            maxEval = eval;
                            maxEval.NumPositons = tempNum;
                        }
                    }else if(eval.eval>maxEval.eval){//maxEval=max(eval,maxEval);
                        int tempNum = maxEval.NumPositons;
                        maxEval = eval;
                        maxEval.NumPositons = tempNum;
                    }


                }
            }
            return maxEval;

        }else{
            ReturnObject_withStats minEval = new ReturnObject_withStats(9999999,1,new LinkedList<ChessMove>());
            boolean temp = board.WhiteTurn;
            board.setBlackNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            for(LinkedList<ChessMove> moves:allMoves){
                for(ChessMove move:moves){
                    Chessboard child = new Chessboard(board);
                    child.setBlackNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=(LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);
                    ReturnObject_withStats eval=minimax(depthleft-1,true,child,new ReturnObject(object.eval,Movelist));
                    minEval.NumPositons = minEval.NumPositons + eval.NumPositons;

                    if(eval.eval== minEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = minEval.NumPositons;
                            minEval = eval;
                            minEval.NumPositons = tempNum;
                        }
                    }else if(eval.eval< minEval.eval){//minEval=max(eval,minEval);
                        int tempNum = minEval.NumPositons;
                        minEval = eval;
                        minEval.NumPositons = tempNum;
                    }


                }
            }
            return minEval;



        }
        //return object;

    }

    public ReturnObject_withStats alphabeta_withNumPostions(double alpha, double beta, int depthleft, boolean player, Chessboard board, ReturnObject object){
        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            return new ReturnObject_withStats(board.eval_func_withCheck(),1,object.moves);
        }/*else if(board.isCheckmate(player)){
            if(player){
                return new ReturnObject(-100000,object.moves);
            }else {
                return new ReturnObject(100000,object.moves);
            }
        }*/

        boolean tmp = board.WhiteTurn;
        board.setWhiteNext();
        if(board.isCheckmate()){
            return new ReturnObject_withStats(-100000,1,object.moves);
        }else{
            board.setBlackNext();
            if(board.isCheckmate()) {
                return new ReturnObject_withStats(100000, 1, object.moves);
            }
        }
        board.setWhiteNext(tmp);

        if(player){
            ReturnObject_withStats maxEval = new ReturnObject_withStats(-9999999, 1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setWhiteNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(true);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setWhiteNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist= (LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withNumPostions(alpha,beta,depthleft-1,false,child,new ReturnObject(object.eval,Movelist));
                    maxEval.NumPositons = maxEval.NumPositons + eval.NumPositons;

                    if(eval.eval==maxEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = maxEval.NumPositons;
                            maxEval = eval;
                            maxEval.NumPositons = tempNum;
                        }
                    }else if(eval.eval>maxEval.eval){//maxEval=max(eval,maxEval);
                        int tempNum = maxEval.NumPositons;
                        maxEval = eval;
                        maxEval.NumPositons = tempNum;
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

            ReturnObject_withStats minEval = new ReturnObject_withStats(9999999,1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setBlackNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(false);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setBlackNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=(LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withNumPostions(alpha,beta,depthleft-1,true,child,new ReturnObject(object.eval,Movelist));
                    minEval.NumPositons = minEval.NumPositons + eval.NumPositons;

                    if(eval.eval== minEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = minEval.NumPositons;
                            minEval = eval;
                            minEval.NumPositons = tempNum;
                        }
                    }else if(eval.eval< minEval.eval){//minEval=max(eval,minEval);
                        int tempNum = minEval.NumPositons;
                        minEval = eval;
                        minEval.NumPositons = tempNum;
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

    public ReturnObject_withStats alphabeta_withStatsAndTT(double alpha, double beta, int depthleft, boolean player, Chessboard board, ReturnObject object){
        long boardhash = zob.generateHashKey(board,player);
        HashEntry hash = table.probeEntry(boardhash);
        if(hash != null){
            if(hash.getDepth()>=depthleft) {
                LinkedList<ChessMove> list = new LinkedList<>();
                list.add(hash.getFlag());
                return new ReturnObject_withStats(hash.getScore(), 1, 1, list);
            }
        }
        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            table.storeEntry(boardhash,depthleft,board.eval_func_withCheck(),object.moves.getFirst());
            return new ReturnObject_withStats(board.eval_func_withCheck(),1,0,object.moves);
        }/*else if(board.isCheckmate(player)){
            if(player){
                return new ReturnObject(-100000,object.moves);
            }else {
                return new ReturnObject(100000,object.moves);
            }
        }*/

        boolean tmp = board.WhiteTurn;
        board.setWhiteNext();
        if(board.isCheckmate()){
            table.storeEntry(boardhash,depthleft,-100000,object.moves.getFirst());
            return new ReturnObject_withStats(-100000,1,0,object.moves);
        }else{
            board.setBlackNext();
            if(board.isCheckmate()){
                table.storeEntry(boardhash,depthleft,100000,object.moves.getFirst());
                return new ReturnObject_withStats(100000,1,0,object.moves);
            }
        }
        board.setWhiteNext(tmp);

        if(player){
            ReturnObject_withStats maxEval = new ReturnObject_withStats(-9999999, 1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setWhiteNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(true);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setWhiteNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist= (LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withStatsAndTT(alpha,beta,depthleft-1,false,child,new ReturnObject(object.eval,Movelist));
                    maxEval.NumPositons = maxEval.NumPositons + eval.NumPositons;
                    maxEval.NumHashs = maxEval.NumHashs + eval.NumHashs;

                    if(eval.eval==maxEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = maxEval.NumPositons;
                            int tempNumh = maxEval.NumHashs;
                            maxEval = eval;
                            maxEval.NumPositons = tempNum;
                            maxEval.NumHashs = tempNumh;
                        }
                    }else if(eval.eval>maxEval.eval){//maxEval=max(eval,maxEval);
                        int tempNum = maxEval.NumPositons;
                        int tempNumh = maxEval.NumHashs;
                        maxEval = eval;
                        maxEval.NumPositons = tempNum;
                        maxEval.NumHashs = tempNumh;
                    }

                    alpha=Math.max(alpha,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            if(object.moves.size()!=0) {
                table.storeEntry(boardhash, depthleft, maxEval.eval, object.moves.getFirst());
            }
            return maxEval;

        }else{

            ReturnObject_withStats minEval = new ReturnObject_withStats(9999999,1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setBlackNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(false);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setBlackNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=(LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withStatsAndTT(alpha,beta,depthleft-1,true,child,new ReturnObject(object.eval,Movelist));
                    minEval.NumPositons = minEval.NumPositons + eval.NumPositons;
                    minEval.NumHashs = minEval.NumHashs + eval.NumHashs;

                    if(eval.eval== minEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = minEval.NumPositons;
                            int tempNumh = minEval.NumHashs;
                            minEval = eval;
                            minEval.NumPositons = tempNum;
                            minEval.NumHashs = tempNumh;
                        }
                    }else if(eval.eval< minEval.eval){//minEval=max(eval,minEval);
                        int tempNum = minEval.NumPositons;
                        int tempNumh = minEval.NumHashs;
                        minEval = eval;
                        minEval.NumPositons = tempNum;
                        minEval.NumHashs = tempNumh;
                    }

                    beta=Math.min(beta,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            if(object.moves.size()!=0) {
                table.storeEntry(boardhash, depthleft, minEval.eval, object.moves.getFirst());
            }
            return minEval;
        }
    }


    public ReturnObject_withStats alphabeta_withStatsAndTTforMTD(double alpha, double beta, double lowerbound, double upperbound, int depthleft, boolean player, Chessboard board, ReturnObject object){
        long boardhash = zob.generateHashKey(board,player);
        HashEntry hash = table.probeEntry(boardhash);
        if(hash != null){
            if(hash.getDepth()>=depthleft) {
                LinkedList<ChessMove> list = new LinkedList<>();
                list.add(hash.getFlag());
                return new ReturnObject_withStats(hash.getScore(), 1, 1, list);
            }
        }

        /*if n.lowerbound >= beta then return n.lowerbound;
        if n.upperbound <= alpha then return n.upperbound;
        alpha := max(alpha, n.lowerbound);
        beta := min(beta, n.upperbound);*/

        if(lowerbound>=beta){return new ReturnObject_withStats(lowerbound,1,0,object.moves);}

        if(upperbound<=alpha){return new ReturnObject_withStats(upperbound,1,0,object.moves);}

        alpha = Math.max(alpha,lowerbound);
        beta = Math.min(beta,upperbound);

        if(depthleft==0){
            //object.eval=board.eval_func();
            //System.out.println("depth==0: returning: "+returnvalue[0]+","+returnvalue[1]+","+returnvalue[2]);
            table.storeEntry(boardhash,depthleft,board.eval_func_withCheck(),object.moves.getFirst());
            return new ReturnObject_withStats(board.eval_func_withCheck(),1,0,object.moves);
        }/*else if(board.isCheckmate(player)){
            if(player){
                return new ReturnObject(-100000,object.moves);
            }else {
                return new ReturnObject(100000,object.moves);
            }
        }*/

        boolean tmp = board.WhiteTurn;
        board.setWhiteNext();
        if(board.isCheckmate()){
            table.storeEntry(boardhash,depthleft,-100000,object.moves.getFirst());
            return new ReturnObject_withStats(-100000,1,0,object.moves);
        }else {
            board.setBlackNext();
            if(board.isCheckmate()){
                table.storeEntry(boardhash,depthleft,100000,object.moves.getFirst());
                return new ReturnObject_withStats(100000,1,0,object.moves);
            }
        }
        board.setWhiteNext(tmp);

        if(player){
            ReturnObject_withStats maxEval = new ReturnObject_withStats(-9999999, 1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setWhiteNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(true);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    child.setWhiteNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist= (LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withStatsAndTTforMTD(alpha,beta, lowerbound, upperbound, depthleft-1,false,child,new ReturnObject(object.eval,Movelist));
                    maxEval.NumPositons = maxEval.NumPositons + eval.NumPositons;
                    maxEval.NumHashs = maxEval.NumHashs + eval.NumHashs;

                    if(eval.eval==maxEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = maxEval.NumPositons;
                            int tempNumh = maxEval.NumHashs;
                            maxEval = eval;
                            maxEval.NumPositons = tempNum;
                            maxEval.NumHashs = tempNumh;
                        }
                    }else if(eval.eval>maxEval.eval){//maxEval=max(eval,maxEval);
                        int tempNum = maxEval.NumPositons;
                        int tempNumh = maxEval.NumHashs;
                        maxEval = eval;
                        maxEval.NumPositons = tempNum;
                        maxEval.NumHashs = tempNumh;
                    }

                    alpha=Math.max(alpha,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            if(object.moves.size()!=0) {
                table.storeEntry(boardhash, depthleft, maxEval.eval, object.moves.getFirst());
            }
            return maxEval;

        }else{

            ReturnObject_withStats minEval = new ReturnObject_withStats(9999999,1,new LinkedList<ChessMove>());

            boolean temp = board.WhiteTurn;
            board.setBlackNext();
            LinkedList<ChessMove>[] allMoves = board.allMovesWithPieces();
            board.setWhiteNext(temp);
            //LinkedList<ChessMove>[] allMoves = board.allMoves_withCheck(false);

            for(LinkedList<ChessMove> moves:allMoves){
                boolean breakstate=false;
                for(ChessMove move:moves){

                    Chessboard child = new Chessboard(board);
                    board.setBlackNext();
                    child.makeMove(move.getFrom(),move.getTo());
                    LinkedList<ChessMove> Movelist=(LinkedList<ChessMove>) object.moves.clone();
                    Movelist.add(move);

                    ReturnObject_withStats eval=alphabeta_withStatsAndTTforMTD(alpha,beta, lowerbound, upperbound,depthleft-1,true,child,new ReturnObject(object.eval,Movelist));
                    minEval.NumPositons = minEval.NumPositons + eval.NumPositons;
                    minEval.NumHashs = minEval.NumHashs + eval.NumHashs;

                    if(eval.eval== minEval.eval){
                        int max = 2;
                        int min = 1;
                        int range = max - min + 1;
                        int rand = (int)(Math.random() * range) + min;
                        if(rand != 1) {
                            int tempNum = minEval.NumPositons;
                            int tempNumh = minEval.NumHashs;
                            minEval = eval;
                            minEval.NumPositons = tempNum;
                            minEval.NumHashs = tempNumh;
                        }
                    }else if(eval.eval< minEval.eval){//minEval=max(eval,minEval);
                        int tempNum = minEval.NumPositons;
                        int tempNumh = minEval.NumHashs;
                        minEval = eval;
                        minEval.NumPositons = tempNum;
                        minEval.NumHashs = tempNumh;
                    }

                    beta=Math.min(beta,eval.eval);
                    if(beta<=alpha){
                        breakstate=true;
                        break;
                    }
                }
                if(breakstate)break;
            }
            if(object.moves.size()!=0) {
                table.storeEntry(boardhash, depthleft, minEval.eval, object.moves.getFirst());
            }
            return minEval;
        }
    }

    public ReturnObject_withStats MTDF(int depthleft, boolean player, Chessboard board, double f){
        double g = f;
        ReturnObject_withStats result;
        ReturnObject_withStats MTD_result = new ReturnObject_withStats(0);
        double upperbound = Double.POSITIVE_INFINITY;
        double lowerbound = Double.NEGATIVE_INFINITY;
        double beta;
        double increment = 10;
        double tolerance = 1e-15;
        do{
            //g == lowerbound
            if(Math.abs(g - lowerbound) <= tolerance){//Math.ulp(g)){
                beta = g+increment;
            }else {
                beta = g;
            }
            //System.out.println("beta"+beta);
            result = alphabeta_withStatsAndTTforMTD(beta-increment, beta, lowerbound, upperbound, depthleft,player,board,new ReturnObject(0));
            //result = alphabeta_withStatsAndTT(beta-increment, beta, depthleft,player,board,new ReturnObject(0));

            //increment = increment/10;
            g = result.eval;
            MTD_result.NumPositons = MTD_result.NumPositons + result.NumPositons;
            MTD_result.NumHashs = MTD_result.NumHashs + result.NumHashs;
            /*System.out.println("transpos:"+result.NumHashs);
            System.out.println("pos:"+result.NumPositons);
            System.out.println("eval"+result.eval);*/

            //g<beta
            //if((beta - g) > tolerance){
            if(g<beta){
                upperbound = g;
            }else {
                lowerbound = g;
            }
        //upperbound>lowerbound
            //System.out.println("lowerbound:"+lowerbound);
            //System.out.println("upperbound:"+upperbound);
        //} while ((upperbound - lowerbound) > tolerance || Math.abs(upperbound - lowerbound) <= tolerance);
        }while ((upperbound - lowerbound) > tolerance);//Math.ulp(upperbound));//Double.longBitsToDouble(971l << 52));
        MTD_result.eval = g;
        MTD_result.moves = result.moves;
        return MTD_result;
    }
}
