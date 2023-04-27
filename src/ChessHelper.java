import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
