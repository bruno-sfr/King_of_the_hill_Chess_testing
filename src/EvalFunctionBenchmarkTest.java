import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.out;

public class EvalFunctionBenchmarkTest {
    @Test
    public void OGEvalFuncTest() {
        JSONParser parser = new JSONParser();
        long timeBefore;
        double delta = 0;
        try {
            //JSONObject obj = (JSONObject) parser.parse(new FileReader("src\\test\\Stellungen.json"));
            timeBefore = System.nanoTime();
            for (JSONObject stellung : (Iterable<JSONObject>) ((JSONObject) parser.parse(new FileReader("./tests/data23_wb.json"))).get("Stellungen")) {
                String FEN = (String) stellung.get("fen");
                String[] arr = FEN.split(" ");
                FEN = arr[0];
                //out.println(FEN);
                boolean White = (boolean) stellung.get("white");
                Chessboard board = new Chessboard(FEN);
                //out.println(board);
                double result = board.evalFuncCaller();
                delta = adjustDelta(delta, stellung, White, result);
                out.println("Index: " + stellung.get("id") +"  delta: " + result);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        long timeAfter = System.nanoTime();
        out.println("Time: " +  (timeAfter - timeBefore)/1000000L + "ms");
        out.println("Delta: " + delta);
        out.println("Score (seconds * delta): " + (timeAfter - timeBefore)/1000000000.0 * delta);
    }

    public double EvalFuncScore(double DoubledOrMissingPawnValue, double AvailableMoveValue,double RookCoveredValue, double BishopCoveredValue, double KnightCoveredValue, double QueenCoveredValue, double PawnCoveredValue) {
        JSONParser parser = new JSONParser();
        long timeBefore;
        double delta = 0;
        try {
            //JSONObject obj = (JSONObject) parser.parse(new FileReader("src\\test\\Stellungen.json"));
            timeBefore = System.nanoTime();
            for (JSONObject stellung : (Iterable<JSONObject>) ((JSONObject) parser.parse(new FileReader("./tests/data23_wb.json"))).get("Stellungen")) {
                String FEN = (String) stellung.get("fen");
                String[] arr = FEN.split(" ");
                FEN = arr[0];
                //out.println(FEN);
                boolean White = (boolean) stellung.get("white");
                Chessboard board = new Chessboard(FEN);
                //out.println(board);
                double result = board.eval_func(DoubledOrMissingPawnValue, AvailableMoveValue, RookCoveredValue, BishopCoveredValue, KnightCoveredValue, QueenCoveredValue, PawnCoveredValue);
                delta = adjustDelta(delta, stellung, White, result);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        long timeAfter = System.nanoTime();
        out.println("Time: " +  (timeAfter - timeBefore)/1000000L + "ms");
        out.println("Delta: " + delta);
        double score = (timeAfter - timeBefore) / 1000000000.0 * delta;
        out.println("Score (seconds * delta): " + score);
        return score;
    }

    public double EvalFuncSimpleScore(double AvailableMoveValue) {
        JSONParser parser = new JSONParser();
        long timeBefore;
        double delta = 0;
        try {
            //JSONObject obj = (JSONObject) parser.parse(new FileReader("src\\test\\Stellungen.json"));
            timeBefore = System.nanoTime();
            for (JSONObject stellung : (Iterable<JSONObject>) ((JSONObject) parser.parse(new FileReader("./tests/data23_wb.json"))).get("Stellungen")) {
                String FEN = (String) stellung.get("fen");
                String[] arr = FEN.split(" ");
                FEN = arr[0];
                boolean White = (boolean) stellung.get("white");
                Chessboard board = new Chessboard(FEN);
                double result = board.eval_func_simple(AvailableMoveValue);
                delta = adjustDelta(delta, stellung, White, result);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        long timeAfter = System.nanoTime();
        out.println("Time: " +  (timeAfter - timeBefore)/1000000L + "ms");
        out.println("Delta: " + delta);
        double score = (timeAfter - timeBefore) / 1000000000.0 * delta;
        out.println("Score (seconds * delta): " + score);
        return score;
    }

    private double adjustDelta(double delta, JSONObject stellung, boolean white, double result) {
        if(!white) result = -result;
        Object[] obj = ((String) stellung.get("eval")).split("#");
        double eval = Double.parseDouble((String) obj[obj.length - 1]);
        delta += Math.abs(result - eval);
        return delta;
    }

}
