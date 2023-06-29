import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class EvalFunctionBenchmarkTest {
    @Test
    public void EvalFuncTest() throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        try {
            //JSONObject obj = (JSONObject) parser.parse(new FileReader("src\\test\\Stellungen.json"));
            for (JSONObject stellung : (Iterable<JSONObject>) (JSONArray) ((JSONObject) parser.parse(new FileReader(".\\tests\\Stellungen.json"))).get("Stellungen")) {
                String FEN = (String) stellung.get("FEN");
                System.out.println(FEN);
                System.out.println(stellung.get("comment"));
                boolean isWhite = (boolean) stellung.get("White");
                Chessboard board = new Chessboard(FEN);
                System.out.println(board);
                long timeBefore = System.currentTimeMillis();
                System.out.println(board.eval_func_simple());
                long timeAfter = System.currentTimeMillis();
                System.out.println("Time: " + (timeAfter - timeBefore));

            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
