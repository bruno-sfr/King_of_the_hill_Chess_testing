//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

class ChessboardTest {
    @Test
    void PostionsTest() throws IOException{
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./tests/Stellungen.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray stellungenArray = (JSONArray) jsonObject.get("Stellungen");

            Iterator<JSONObject> iterator = stellungenArray.iterator();
            while (iterator.hasNext()) {
                JSONObject stellung = iterator.next();
                String FEN = (String) stellung.get("FEN");
                System.out.println(FEN);
                //System.out.println("0 Pawn / 1 bishop / 2 knight / 3 rook / 4 queen / 5 king");
                System.out.println((String) stellung.get("comment"));
                JSONArray zugArray = (JSONArray) stellung.get("Zug");
                boolean isWhite = (boolean) stellung.get("White");
                Chessboard board = new Chessboard(FEN);
                LinkedList<ChessMove>[] moves = board.allMoves_withCheck(isWhite);
                for(int i=0; i<6; i++){
                    Assertions.assertEquals((long) moves[i].size(), (long) zugArray.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void Alpha_Beta_Checkmate_Test() throws IOException{
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./tests/StellungenSuchtiefe.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray stellungenArray = (JSONArray) jsonObject.get("Stellungen");

            Iterator<JSONObject> iterator = stellungenArray.iterator();
            while (iterator.hasNext()) {
                JSONObject stellung = iterator.next();
                String FEN = (String) stellung.get("FEN");
                JSONArray zugArray = (JSONArray) stellung.get("Zug");
                boolean isWhite = (boolean) stellung.get("White");
                long Suchtiefe = (long) stellung.get("Suchtiefe");
                System.out.println(FEN);
                System.out.println((String) stellung.get("comment"));
                Chessboard chessboard = new Chessboard(FEN);
                chessboard.printBoard();
                Game_Vs_AI game = new Game_Vs_AI(chessboard);
                ReturnObject result = game.alphabeta(-9999999,9999999,(int) (Suchtiefe) ,isWhite,chessboard,new ReturnObject(0, new LinkedList<ChessMove>()));
                System.out.println("Evaluation:"+result.eval);
                /*if(result.moves.size()>=2){
                    System.out.println(result.moves.getFirst());
                    System.out.println(result.moves.get(1));
                }*/
                if(isWhite){
                    //50000 seems big enough to be bigger than normal eval but smaller than 100000 if black king is slayn
                    Assertions.assertTrue(result.eval>50000);
                }else {
                    Assertions.assertTrue(result.eval<-50000);
                }
                /*int actual = result.moves.getFirst().getTo();
                int expected = ChessHelper.calcPos((String) zugArray.get(0)).getFirst();
                Assertions.assertEquals(expected, actual);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void Alpha_Beta_Best_move_Test() throws IOException{
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./tests/StellungenBM.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray stellungenArray = (JSONArray) jsonObject.get("Stellungen");

            Iterator<JSONObject> iterator = stellungenArray.iterator();
            while (iterator.hasNext()) {
                JSONObject stellung = iterator.next();
                String FEN = (String) stellung.get("FEN");
                JSONArray zugArray = (JSONArray) stellung.get("Zug");
                boolean isWhite = (boolean) stellung.get("White");
                System.out.println(FEN);
                System.out.println((String) stellung.get("comment"));
                Chessboard chessboard = new Chessboard(FEN);
                chessboard.printBoard();
                Game_Vs_AI game = new Game_Vs_AI(chessboard);
                ReturnObject result = game.alphabeta(-9999999,9999999,5 ,isWhite,chessboard,new ReturnObject(0, new LinkedList<ChessMove>()));
                System.out.println(result.eval);
                int actual = result.moves.getFirst().getTo();
                for(ChessMove move : result.moves){
                    System.out.println(move);
                }
                /*if(result.moves.size()>=2){
                    System.out.println(result.moves.getFirst());
                    System.out.println(result.moves.get(1));
                }*/
                int expected = ChessHelper.calcPos((String) zugArray.get(0)).getFirst();
                Assertions.assertEquals(expected, actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void Mini_Max_Checkmate_Test() throws IOException{
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./tests/StellungenSuchtiefe.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray stellungenArray = (JSONArray) jsonObject.get("Stellungen");

            Iterator<JSONObject> iterator = stellungenArray.iterator();
            while (iterator.hasNext()) {
                JSONObject stellung = iterator.next();
                String FEN = (String) stellung.get("FEN");
                JSONArray zugArray = (JSONArray) stellung.get("Zug");
                boolean isWhite = (boolean) stellung.get("White");
                long Suchtiefe = (long) stellung.get("Suchtiefe");
                System.out.println(FEN);
                System.out.println((String) stellung.get("comment"));
                Chessboard chessboard = new Chessboard(FEN);
                //chessboard.printBoard();
                new Chessboard("2k5/6q1/3P1P2/4N3/8/1K6/8/8").printBoard();
                /*LinkedList<ChessMove>[] moves = new Chessboard("2k5/6q1/3P1P2/4N3/8/1K6/8/8").allMovesWithPieces(true);
                for(int i = 0; i<6; i++){
                    System.out.println(i);
                    LinkedList<ChessMove> piecemove = moves[i];
                    for(ChessMove move : piecemove){
                        System.out.println(move);
                    }
                }*/
                Game_Vs_AI game = new Game_Vs_AI(chessboard);
                ReturnObject_MiniMax result = game.minimax(2 ,true, new Chessboard("2k5/6q1/3P1P2/4N3/8/1K6/8/8"),new ReturnObject(0, new LinkedList<ChessMove>()));
                System.out.println("Evaluation:"+result.eval);
                System.out.println("Num Pos:" + result.NumPositons);
                System.out.println(result.moves.getFirst());
                /*if(result.moves.size()>=2){
                    System.out.println(result.moves.getFirst());
                    System.out.println(result.moves.get(1));
                }*/
                /*if(isWhite){
                    //50000 seems big enough to be bigger than normal eval but smaller than 100000 if black king is slayn
                    Assertions.assertTrue(result.eval>50000);
                }else {
                    Assertions.assertTrue(result.eval<-50000);
                }*/
                /*int actual = result.moves.getFirst().getTo();
                int expected = ChessHelper.calcPos((String) zugArray.get(0)).getFirst();
                Assertions.assertEquals(expected, actual);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void Mini_Max_Benchmarks(){
        long starttime;
        long endtime;
        Chessboard Chessboard1 = new Chessboard("r1b2rk1/4qpp1/4p2R/p2pP3/2pP2QP/4P1P1/PqB4K/8");
        Game_Vs_AI game1 = new Game_Vs_AI(Chessboard1);
        System.out.println("Board 1:");
        Chessboard1.printBoard();
        Chessboard Chessboard2 = new Chessboard("6k1/p2qpp2/2p2PpQ/1p4N1/2n5/2N5/PPP2P2/2K5");
        Game_Vs_AI game2 = new Game_Vs_AI(Chessboard2);
        System.out.println("Board w:");
        Chessboard2.printBoard();
        for(int i = 1; i<6; i++){
            System.out.println("Depth:" + i);
            System.out.println("----------------------------------------");
            System.out.println("Board 1:");
            System.out.println("Minimax:");
            starttime = System.nanoTime();
            ReturnObject_MiniMax result_minimax_1 = game1.minimax(i,true,Chessboard1,new ReturnObject(0));
            endtime = System.nanoTime();
            System.out.println("Time taken in ms:" + (float)(endtime-starttime)/1000000);
            System.out.println("Postions evaluated:" + result_minimax_1.NumPositons);
            System.out.println("Evaluation:" + result_minimax_1.eval);
            System.out.println("Best Move" + result_minimax_1.moves.getFirst());
            System.out.println();
            System.out.println("Alpha-Beta:");
            starttime = System.nanoTime();
            ReturnObject_MiniMax result_ab_1 = game1.alphabeta_withNumPostions(-9999999,9999999,i,true,Chessboard1,new ReturnObject(0));
            endtime = System.nanoTime();
            System.out.println("Time taken in ms:" + (float)(endtime-starttime)/1000000);
            System.out.println("Postions evaluated:" + result_ab_1.NumPositons);
            System.out.println("Evaluation:" + result_ab_1.eval);
            System.out.println("Best Move" + result_ab_1.moves.getFirst());
            System.out.println("----------------------------------------");
            System.out.println("Board 2:");
            System.out.println("Minimax:");
            starttime = System.nanoTime();
            ReturnObject_MiniMax result_minimax_2 = game2.minimax(i,true,Chessboard2,new ReturnObject(0));
            endtime = System.nanoTime();
            System.out.println("Time taken in ms:" + (float)(endtime-starttime)/1000000);
            System.out.println("Postions evaluated:" + result_minimax_2.NumPositons);
            System.out.println("Evaluation:" + result_minimax_2.eval);
            System.out.println("Best Move" + result_minimax_2.moves.getFirst());
            System.out.println();
            System.out.println("Alpha-Beta:");
            starttime = System.nanoTime();
            ReturnObject_MiniMax result_ab_2 = game2.alphabeta_withNumPostions(-9999999,9999999,i,false,Chessboard2,new ReturnObject(0));
            endtime = System.nanoTime();
            System.out.println("Time taken in ms:" + (float)(endtime-starttime)/1000000);
            System.out.println("Postions evaluated:" + result_ab_2.NumPositons);
            System.out.println("Evaluation:" + result_ab_2.eval);
            System.out.println("Best Move" + result_ab_2.moves.getFirst());
            System.out.println("----------------------------------------");
            System.out.println();
        }
    }
}