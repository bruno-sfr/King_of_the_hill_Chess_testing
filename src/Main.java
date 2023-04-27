import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static boolean WhiteTurn;
    public static void main(String[] args) throws IOException {
        Chessboard board = new Chessboard();
        board.printBoard();
        WhiteTurn = true;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while(true) {
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