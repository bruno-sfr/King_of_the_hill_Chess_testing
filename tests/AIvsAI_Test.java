import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.descriptor.FileSystemSource;

class AIvsAI_Test {
    @Test
    void AB_TT_vs_MTD(){
        int AB_wins = 0;
        int MTD_wins = 0;
        int game_count = 0;
        for(int i = 0; i<10; i++){
            Chessboard board = new Chessboard();
            AI_Board player_AB = new AI_Board(board);
            AI_Board player_MTD = new AI_Board(board);
            boolean whiteturn;
            if(i<5){
                whiteturn = true;
            }else if(i == 5){
                whiteturn = false;
                game_count = 0;
            }else {
                whiteturn = false;
            }
            while (!board.isGameOver()){
                if(!whiteturn){
                    ReturnObject result = player_MTD.iterativeDeepening_MTD(whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("white wanted to play illeagl move");
                        break;
                    }
                }else {
                    ReturnObject result = player_AB.iterativeDeepening_withTT(whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("black wanted to play illeagl move");
                        break;
                    };
                }
                player_AB.setBoard(board);
                player_MTD.setBoard(board);
                whiteturn = !whiteturn;
            }
            board.printBoard();
            game_count++;
            if(board.whiteHasWon()){
                AB_wins++;
            } else if (board.blackHasWon()) {
                MTD_wins++;
            }
            System.out.println("AB:" + AB_wins + " MTD:" + MTD_wins);
        }
        System.out.println("AB has won:"+AB_wins);
        System.out.println("MTD has won:"+MTD_wins);
    }

    @Test
    void AB_TT_vs_AB_MI(){
        int AB_wins = 0;
        int AB_MI_wins = 0;
        int game_count = 0;
        for(int i = 0; i<10; i++){
            Chessboard board = new Chessboard();
            AI_Board player_AB = new AI_Board(board);
            AI_Board player_AB_MI = new AI_Board(board);
            boolean whiteturn;
            if(i<5){
                whiteturn = true;
            }else if(i == 5){
                whiteturn = false;
                game_count = 0;
            }else {
                whiteturn = false;
            }
            while (!board.isGameOver()){
                if(!whiteturn){
                    ReturnObject result = player_AB.iterativeDeepening_withTT(whiteturn,game_count * 1000 + 1000);
                    //System.out.println(result.moves.getFirst());
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("White wanted to play illeagl move");
                        break;
                    };
                }else {
                    ReturnObject result = player_AB_MI.iterativeDeepening_withTTwithLearndEval(whiteturn,game_count * 1000 + 1000);
                    //System.out.println(result.moves.getFirst());
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("Black wanted to play illeagl move");
                        break;
                    }
                }
                player_AB.setBoard(board);
                player_AB_MI.setBoard(board);
                whiteturn = !whiteturn;
            }
            board.printBoard();
            //game_count++;
            if(board.whiteHasWon()){
                AB_wins++;
            } else if (board.blackHasWon()) {
                AB_MI_wins++;
            }
            System.out.println("AB:" + AB_wins + " AB_MI_wins:" + AB_MI_wins);
        }
        System.out.println("AB has won:"+AB_wins);
        System.out.println("AB_MI_wins has won:"+AB_MI_wins);
    }

    @Test
    void AB_TT_vs_AB_Libary(){
        int AB_wins = 0;
        int AB_MI_wins = 0;
        int game_count = 0;
        for(int i = 0; i<10; i++){
            Chessboard board = new Chessboard();
            AI_Board player_AB = new AI_Board(board);
            AI_Board player_AB_MI = new AI_Board(board,true);
            boolean whiteturn;
            if(i<5){
                whiteturn = true;
            }else if(i == 5){
                whiteturn = false;
                game_count = 0;
            }else {
                whiteturn = false;
            }
            while (!board.isGameOver()){
                if(!whiteturn){
                    ReturnObject result = player_AB.iterativeDeepening_withTT(whiteturn,game_count * 1000 + 1000);
                    //System.out.println(result.moves.getFirst());
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("White wanted to play illeagl move");
                        break;
                    };
                }else {
                    ReturnObject result = player_AB_MI.iterativeDeepening_withTTandLibary(whiteturn,game_count * 1000 + 1000);
                    //System.out.println(result.moves.getFirst());
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("Black wanted to play illeagl move");
                        break;
                    }
                }
                player_AB.setBoard(board);
                player_AB_MI.setBoard(board);
                whiteturn = !whiteturn;
            }
            board.printBoard();
            //game_count++;
            if(board.whiteHasWon()){
                AB_wins++;
            } else if (board.blackHasWon()) {
                AB_MI_wins++;
            }
            System.out.println("AB:" + AB_wins + " AB_Libary_wins:" + AB_MI_wins);
        }
        System.out.println("AB has won:"+AB_wins);
        System.out.println("AB_Libary_wins has won:"+AB_MI_wins);
    }

    @Test
    void MCTS_vs_MTD(){
        int MCTS_wins = 0;
        int MTD_wins = 0;
        int game_count = 0;
        for(int i = 0; i<20; i++){
            Chessboard board = new Chessboard();
            MCTS player_MCTS = new MCTS();
            AI_Board player_MTD = new AI_Board(board);
            boolean whiteturn;
            if(i<10){
                whiteturn = true;
            }else if(i == 10){
                whiteturn = false;
                game_count = 0;
            }else {
                whiteturn = false;
            }
            while (!board.isGameOver()){
                if(!whiteturn){
                    ChessMove result = player_MCTS.iterativeDeepening_MCTS(board ,whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result)){
                        System.out.println("White wanted to play illeagl move");
                        break;
                    };
                }else {
                    ReturnObject result = player_MTD.iterativeDeepening_MTD(whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("Black wanted to play illeagl move");
                        break;
                    }
                }
                player_MTD.setBoard(board);
                whiteturn = !whiteturn;
            }
            board.printBoard();
            game_count++;
            if(board.whiteHasWon()){
                MCTS_wins++;
            } else if (board.blackHasWon()) {
                MTD_wins++;
            }
            System.out.println("MCTS:" + MCTS_wins + " MTD:" + MTD_wins);
        }
        System.out.println("MCTS has won:"+MCTS_wins);
        System.out.println("MTD has won:"+MTD_wins);
    }

    @Test
     void AB_vs_MCTS(){
        int MCTS_wins = 0;
        int AB_wins = 0;
        int game_count = 0;
        for(int i = 0; i<20; i++){
            Chessboard board = new Chessboard();
            MCTS player_MCTS = new MCTS();
            AI_Board player_AB = new AI_Board(board);
            boolean whiteturn;
            if(i<10){
                whiteturn = true;
            }else if(i == 10){
                whiteturn = false;
                game_count = 0;
            }else {
                whiteturn = false;
            }
            while (!board.isGameOver()){
                if(!whiteturn){
                    ChessMove result = player_MCTS.iterativeDeepening_MCTS(board ,whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result)){
                        System.out.println("White wanted to play illeagl move");
                        break;
                    };
                }else {
                    ReturnObject result = player_AB.iterativeDeepening_withTT(whiteturn,game_count * 1000 + 1000);
                    if(!board.makeMove(whiteturn, result.moves.getFirst())){
                        System.out.println("Black wanted to play illeagl move");
                        break;
                    }
                }
                player_AB.setBoard(board);
                whiteturn = !whiteturn;
            }
            board.printBoard();
            game_count++;
            if(board.whiteHasWon()){
                MCTS_wins++;
            } else if (board.blackHasWon()) {
                AB_wins++;
            }
            System.out.println("MCTS:" + MCTS_wins + " AB:" + AB_wins);
        }
        System.out.println("MCTS has won:"+MCTS_wins);
        System.out.println("AB has won:"+AB_wins);
    }
}
