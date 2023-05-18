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
                System.out.println(result.eval);
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
                if(result.moves.size()>=2){
                    System.out.println(result.moves.getFirst());
                    System.out.println(result.moves.get(1));
                }
                int expected = ChessHelper.calcPos((String) zugArray.get(0)).getFirst();
                Assertions.assertEquals(expected, actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}