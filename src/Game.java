public class Game {
    public static void main(String[] args) {
        Chessboard board = new Chessboard();
        AI_Board Player = new AI_Board(board);
        boolean whiteturn = true;
        while (!board.isGameOver()){
            if(whiteturn){
                System.out.println("MTD(f):");
                ReturnObject_withStats result = Player.iterativeDeepening_MTD(true,20000);
                System.out.println(result.moves.getFirst());
                board.makeMove(true, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo());
                System.out.println("Depth:"+result.moves.size());
            }else {
                System.out.println("Alpha-Beta TT:");
                ReturnObject_withStats result = Player.iterativeDeepening_withTT(false,20000);
                System.out.println(result.moves.getFirst());
                board.makeMove(false, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo());
                System.out.println("Depth:"+result.moves.size());
            }
            System.out.println("White Turn:"+whiteturn);
            Player.setBoard(board);
            board.printBoard();
            whiteturn = !whiteturn;
        }
    }
}
