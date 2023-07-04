import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean WhiteTurn;
    public static void main(String[] args) throws IOException {
        Chessboard board = new Chessboard("kb5Q/p7/Pp6/1P6/4p3/4R3/4P1p1/6K1");
        System.out.println(board.isCheckmate(false));
        board.printBoard();
        WhiteTurn = false;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while(true) {
            /*Game_Vs_AI game = new Game_Vs_AI(board);
            ReturnObject object = new ReturnObject(0, new LinkedList<ChessMove>());
            ReturnObject result = game.alphabeta(-999999, 999999, 6, true, board, object);
            System.out.println(result);*/
            long Starttime = System.nanoTime();
            LinkedList<ChessMove>[] test = board.allMovesWithPieces(WhiteTurn);
            long endtime = System.nanoTime();
            System.out.println("Duration_Bruno:" + (endtime-Starttime));
            Starttime = System.nanoTime();
            LinkedList<Integer> test2 = board.allMovesWithoutPieces(WhiteTurn);
            endtime = System.nanoTime();
            System.out.println("Duration_Nico:" + (endtime-Starttime));
            System.out.println("Whiteturn? " + board.isCheck(WhiteTurn));
            System.out.println("Is check? " + board.isCheck(WhiteTurn));
            System.out.println("Is checkmate? " + board.isCheckmate(WhiteTurn));
            System.out.println("Eval:" + board.evalFuncCaller());
            String input = reader.readLine();
            if (input.equals("exit"))
                   break;
            LinkedList<Integer> foo = ChessHelper.calcPos(input);
            ChessMove move = new ChessMove(foo.get(0), foo.get(1));
            try {
                if (board.makeMove(WhiteTurn, move)) {
                    board.printBoard();
                    WhiteTurn = !WhiteTurn;
                } else {
                    if (WhiteTurn) {
                        System.out.println("Invalid move provided. It's white's turn.");
                    } else {
                        System.out.println("Invalid move provided. It's black's turn.");
                    }
                    board.printBoard();
                }
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Invalid move provided. Please use small letters in the following format: a2,a3 OR a2a3.");
                board.printBoard();
            }
        }
    }
}