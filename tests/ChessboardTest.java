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
//import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

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
                //System.out.println("WhiteTurn:"+isWhite);
                Chessboard board = new Chessboard(FEN);
                //board.printBoard();
                LinkedList<ChessMove>[] moves = board.allMoves_withCheck(isWhite);
                for(int i=0; i<6; i++){
                    /*System.out.println(i+ " Moves:" + moves[i].size() + " Expected:" + zugArray.get(i));
                    if((long) moves[i].size() != (long) zugArray.get(i)){
                        for(int i2=0; i2<moves[i].size(); i2++){
                            System.out.println(moves[i].get(i2));
                        }
                    }*/
                    Assertions.assertEquals((long) moves[i].size(), (long) zugArray.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}