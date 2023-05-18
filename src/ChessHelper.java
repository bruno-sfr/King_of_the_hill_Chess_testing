import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

public class ChessHelper {
    public static LinkedList<Integer> calcPos(String field){
        LinkedList<Integer> Positions = new LinkedList<Integer>();
        Pattern pattern = Pattern.compile("([a-h][1-8])");
        Matcher matcher = pattern.matcher(field);
        while (matcher.find()){
            String match = matcher.group();
            //System.out.println(match);
            char letter = match.charAt(0);
            int number = Character.getNumericValue(match.charAt(1));
            int pos = (letter - 'a') + (number-1) * 8;
            Positions.add(pos);
        }
        return Positions;
    }

    public static boolean isNorthBorder(int Pos){
        return Pos>55;
    }

    public static boolean isSouthBorder(int Pos){
        return Pos<8;
    }

    public static boolean isWestBorder(int Pos){
        return Pos%8 == 0;
    }

    public static boolean isEastBorder(int Pos){
        return Pos%8 == 7;
    }

    public static double euclidDistanceToMiddle(int Pos) {
        int x = Pos%8;
        int y = Pos/8;
        return Math.sqrt(Math.pow(x-3.5, 2) + Math.pow(y-3.5, 2))/10;
    }


}
