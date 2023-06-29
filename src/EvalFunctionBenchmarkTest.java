import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static java.lang.System.out;

public class EvalFunctionBenchmarkTest {
    @Test
    public void EvalFuncTest() throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        long timeBefore;
        double delta = 0;
        try {
            //JSONObject obj = (JSONObject) parser.parse(new FileReader("src\\test\\Stellungen.json"));
            timeBefore = System.nanoTime();
            for (JSONObject stellung : (Iterable<JSONObject>) (JSONArray) ((JSONObject) parser.parse(new FileReader(".\\tests\\data23_wb.json"))).get("Stellungen")) {
                String FEN = (String) stellung.get("fen");
                String[] arr = FEN.split(" ");
                FEN = arr[0];
                //out.println(FEN);
                boolean White = (boolean) stellung.get("white");
                Chessboard board = new Chessboard(FEN);
                //out.println(board);
                double result = White ? board.eval_func() : -board.eval_func();
                out.println("Index: " + stellung.get("id") +"  delta: " + result);
                Object[] obj = ((String) stellung.get("eval")).split("#");
                double eval = Double.parseDouble((String) obj[obj.length - 1]);
                delta += Math.abs(result - eval);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        long timeAfter = System.nanoTime();
        out.println("Time: " +  (timeAfter - timeBefore)/1000000L + "ms");
        out.println("Delta: " + delta);
        out.println("Score (seconds * delta): " + (timeAfter - timeBefore)/1000000000.0 * delta);
    }

}
