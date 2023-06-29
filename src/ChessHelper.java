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

    public static String indexToHuman(int index){
        int row = index/8;
        int column = index%8;
        char letter = (char) (column + 'a');
        row += 1;
        return letter + "" + row;
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
        /*float x = Pos%8;
        float y = (float)Pos/8;
        return Math.sqrt(Math.pow(x-3.5, 2) + Math.pow(y-3.5, 2));*/
        float x = Pos % 8;
        int y = Pos / 8;
        double xdistance = Math.abs(x-3.5);
        double ydistance = Math.abs(y-3.5);
        return Math.sqrt(Math.pow(xdistance, 2) + Math.pow(ydistance, 2));
    }

    public static ChessMove randomMove(Chessboard chessboard, Boolean whiteTurn){
        int MAX_ITERATIONS = 10;
        LinkedList<ChessMove>[] list = chessboard.allMovesWithPieces(whiteTurn);
        int random = ChessHelper.getRandomNumber(0, 5);
        int i = 0;
        while(list[random].size() == 0) {
            //System.out.println("list size:" + list[random].size() + " random:" + random);
            random = ChessHelper.getRandomNumber(0, 5);
            i++;
            if(i == MAX_ITERATIONS){
                return null;
            }
        }
        int random_2 = ChessHelper.getRandomNumber(0, list[random].size()-1);
        //System.out.println("random2:" + random_2);
        //System.out.println("list[random].size():" + list[random].size());
        return list[random].get(random_2);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}
