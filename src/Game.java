public class Game {
    public static void main(String[] args) {
        Chessboard board = new Chessboard();
        AI_Board Player = new AI_Board(board);
        boolean whiteturn = true;
        while (!board.isGameOver()){
            if(whiteturn){
                ReturnObject result = Player.runFunction(true,10000);
                board.makeMove(true, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo());
                System.out.println("Depth:"+result.moves.size());
            }else {
                ReturnObject_MiniMax result = Player.runFunction_withNumpos(false,10000);
                board.makeMove(false, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo());
                System.out.println("Depth:"+result.moves.size());
            }
            System.out.println("White Turn:"+whiteturn);
            board.printBoard();
            whiteturn = !whiteturn;
        }
    }
}
