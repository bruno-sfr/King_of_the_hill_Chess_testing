public class Game {
    public static void main(String[] args) {
        Chessboard board = new Chessboard();
        AI_Board Player_1 = new AI_Board(board);
        AI_Board Player_2 = new AI_Board(board);
        MCTS mcts = new MCTS();
        boolean whiteturn = true;
        while (!board.isGameOver()){
            if(!whiteturn){
                System.out.println("MTD(f):");
                //ReturnObject_withStats result = Player.iterativeDeepening_MTD_stats(true,20000);
                ReturnObject result = Player_1.iterativeDeepening_MTD(whiteturn,5000);
                System.out.println(result.moves.getFirst());
                if(!board.makeMove(whiteturn, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo())){
                    System.out.println("White wanted to play illeagl move");
                    break;
                };
                System.out.println("Depth:"+result.moves.size());
            }else {
                System.out.println("MCTS:");
                ChessMove move = mcts.iterativeDeepening_MCTS(board, whiteturn, 5000);
                if(!board.makeMove(whiteturn, move.getFrom(), move.getTo())){
                    System.out.println("Black wanted to play illeagl move");
                    break;
                }
                /*System.out.println("MTD(f) simple:");
                //ReturnObject result = Player_2.iterativeDeepening_MTD_simple(false,10000);
                ReturnObject_withStats result = Player_2.iterativeDeepening_MTD_stats(false,5000);
                System.out.println(result.moves.getFirst());
                if(!board.makeMove(false, result.moves.getFirst().getFrom(), result.moves.getFirst().getTo())){
                    System.out.println("Black wanted to play illeagl move");
                    break;
                };;
                System.out.println("Depth:"+result.moves.size());*/
            }
            System.out.println("White Turn:"+whiteturn);
            Player_1.setBoard(board);
            Player_2.setBoard(board);
            board.printBoard();
            whiteturn = !whiteturn;
        }
    }
}
