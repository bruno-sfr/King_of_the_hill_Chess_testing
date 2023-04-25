import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Chessboard board = new Chessboard();
        board.printBoard();
        LinkedList<Integer> Positions = ChessHelper.clacPos("g1,f3");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("f3,e3");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("f3,g5,f7");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        System.out.println(board.makeMove(true,Positions.get(1),Positions.get(2)));
        board.printBoard();
    }
}