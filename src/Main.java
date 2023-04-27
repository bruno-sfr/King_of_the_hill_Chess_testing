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
        Positions = ChessHelper.clacPos("e2,e3");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("f2,f4");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("f7,d6");
        System.out.println(board.makeMove(true,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("e7,d6");
        System.out.println(board.makeMove(false,Positions.get(0),Positions.get(1)));
        board.printBoard();
        Positions = ChessHelper.clacPos("d6,e5");
        System.out.println(board.makeMove(false,Positions.get(0),Positions.get(1)));
        board.printBoard();
    }
}