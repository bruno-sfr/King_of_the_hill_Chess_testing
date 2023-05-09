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
            Object obj = parser.parse(new FileReader("./tests/unittests.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray stellungenArray = (JSONArray) jsonObject.get("Stellungen");

            Iterator<JSONObject> iterator = stellungenArray.iterator();
            while (iterator.hasNext()) {
                JSONObject stellung = iterator.next();
                String FEN = (String) stellung.get("FEN");
                JSONArray zugArray = (JSONArray) stellung.get("Zug");
                boolean isWhite = (boolean) stellung.get("White");
                Chessboard board = new Chessboard(FEN);
                LinkedList<ChessMove>[] moves = board.allMoves(isWhite);
                for(int i=0; i<6; i++){
                    System.out.println("Moves:" + moves[i].size() + " Expected:" + zugArray.get(i));
                    Assertions.assertEquals((long) moves[i].size(), (long) zugArray.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}