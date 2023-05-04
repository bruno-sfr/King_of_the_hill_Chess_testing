import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean WhiteTurn;
    public static void main(String[] args) throws IOException {
        Chessboard board = new Chessboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        board.printBoard();
        WhiteTurn = true;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while(true) {
            long Starttime = System.nanoTime();
            LinkedList<ChessMove>[] test = board.allMoves(WhiteTurn);
            long endtime = System.nanoTime();
            System.out.println("Duration_Bruno:" + (endtime-Starttime));
            Starttime = System.nanoTime();
            LinkedList<Integer[]> test2 = board.allPossibleMoves(WhiteTurn);
            endtime = System.nanoTime();
            System.out.println("Duration_Niko:" + (endtime-Starttime));
            System.out.println("Whiteturn? " + board.isCheck(WhiteTurn));
            System.out.println("Is check? " + board.isCheck(WhiteTurn));
            System.out.println("Is checkmate? " + board.isCheckmate(WhiteTurn));
            String input = reader.readLine();
            if (input.equals("exit"))
                   break;
            LinkedList<Integer> move = ChessHelper.calcPos(input);
            if (board.makeMove(WhiteTurn, move.get(0), move.get(1))) {
                board.printBoard();
                WhiteTurn = !WhiteTurn;
            }
            else {
                if (WhiteTurn) {
                    System.out.println("Invalid move provided. It's white's turn.");
                } else {
                    System.out.println("Invalid move provided. It's black's turn.");
                }
                board.printBoard();
            }
        }
    }
}