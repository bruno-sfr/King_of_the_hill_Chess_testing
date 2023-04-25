import java.lang.reflect.Array;

public class Chessboard {
    BitBoard Black;
    BitBoard White;
    BitBoard Pawn;
    BitBoard Rook;
    BitBoard King;
    BitBoard Queen;
    BitBoard Knight;
    BitBoard Bishop;

    public Chessboard(){
        Black = new BitBoard(0xffff000000000000L);
        White = new BitBoard(0x000000000000ffffL);
        Pawn = new BitBoard(0x00ff00000000ff00L);
        Rook = new BitBoard(0x8100000000000081L);
        King = new BitBoard(0x1000000000000010L);
        Queen = new BitBoard(0x0800000000000008L);
        Bishop = new BitBoard(0x2400000000000024L);
        Knight = new BitBoard(0x4200000000000042L);
    }

    public boolean makeMove(boolean whiteTurn, int from, int to){

        return false;
    }

    public boolean PawnMove(boolean whiteTurn, int from, int to){
        //if pawn moves up one row -> true
        if ((to - from) == 8)
            if (!(Black.isSquareSet(to) || White.isSquareSet(to)))
                return true;
        //if pawn moves right diagonally and beats a piece -> return true
        if (to - from == 9 && whiteTurn ? Black.isSquareSet(to) : White.isSquareSet(to) && !(from % 8 == 7))
            return true;
        //if pawn moves left diagonally and beats a piece -> return true
        if (to - from == 7 && whiteTurn ? Black.isSquareSet(to) : White.isSquareSet(to) && !(from % 8 == 0))
            return true;
        //if pawn moves up by two, nothing is in the way and he is still on base line ->  return true
        if (to - from == 16 && White.isSquareSet(from + 8) && Black.isSquareSet(from + 8) && White.isSquareSet(from) && Black.isSquareSet(from) && 8 <= from && from <= 15)
            return true;
        return false;
    }

    public boolean KingMove(boolean whiteTurn, int from, int to){
        return false;
    }

    public boolean QueenMove(boolean whiteTurn, int from, int to){
        return false;
    }

    public boolean KnightMove(boolean whiteTurn, int from, int to){
        return false;
    }

    public boolean BishopMove(boolean whiteTurn, int from, int to){
        return false;
    }

    public boolean RookMove(boolean whiteTurn, int from, int to){
        return false;
    }

    public void printBoard(){
        /*
        System.out.println("Black");
        Black.printBoard();
        System.out.println("White");
        White.printBoard();
        System.out.println("Pawn");
        Pawn.printBoard();
        System.out.println("Rook");
        Rook.printBoard();
        System.out.println("King");
        King.printBoard();
        System.out.println("Queen");
        Queen.printBoard();
        System.out.println("Knight");
        Knight.printBoard();
        System.out.println("Bishop");
        Bishop.printBoard();
        */

        char[] Humanboard = new char[64];

        //Black
        BitBoard black_pawns = new BitBoard(Black.getBoard() & Pawn.getBoard());
        BitBoard black_king = new BitBoard(Black.getBoard() & King.getBoard());
        BitBoard black_queen = new BitBoard(Black.getBoard() & Queen.getBoard());
        BitBoard black_knights = new BitBoard(Black.getBoard() & Knight.getBoard());
        BitBoard black_bishop = new BitBoard(Black.getBoard() & Bishop.getBoard());
        BitBoard black_rook = new BitBoard(Black.getBoard() & Rook.getBoard());
        String _black_pawns = black_pawns.boardWithChar('p');
        String _black_king = black_king.boardWithChar('k');
        String _black_queen = black_queen.boardWithChar('q');
        String _black_knights = black_knights.boardWithChar('n');
        String _black_bishop = black_bishop.boardWithChar('b');
        String _black_rook = black_rook.boardWithChar('r');
        //White
        BitBoard white_pawns = new BitBoard(White.getBoard() & Pawn.getBoard());
        BitBoard white_king = new BitBoard(White.getBoard() & King.getBoard());
        BitBoard white_queen = new BitBoard(White.getBoard() & Queen.getBoard());
        BitBoard white_knights = new BitBoard(White.getBoard() & Knight.getBoard());
        BitBoard white_bishop = new BitBoard(White.getBoard() & Bishop.getBoard());
        BitBoard white_rook = new BitBoard(White.getBoard() & Rook.getBoard());
        String _white_pawns = white_pawns.boardWithChar('P');
        String _white_king = white_king.boardWithChar('K');
        String _white_queen = white_queen.boardWithChar('Q');
        String _white_knights = white_knights.boardWithChar('N');
        String _white_bishop = white_bishop.boardWithChar('B');
        String _white_rook = white_rook.boardWithChar('R');

        for(int i = 0;i<64;i++){
            if(_black_pawns.charAt(i)!='0'){
                Humanboard[i] = _black_pawns.charAt(i);
            }
            else if(_black_bishop.charAt(i)!='0'){
                Humanboard[i] = _black_bishop.charAt(i);
            }
            else if(_black_king.charAt(i)!='0'){
                Humanboard[i] = _black_king.charAt(i);
            }
            else if(_black_knights.charAt(i)!='0'){
                Humanboard[i] = _black_knights.charAt(i);
            }
            else if(_black_queen.charAt(i)!='0'){
                Humanboard[i] = _black_queen.charAt(i);
            }
            else if(_black_rook.charAt(i)!='0'){
                Humanboard[i] = _black_rook.charAt(i);
            }
            else if(_white_pawns.charAt(i)!='0'){
                Humanboard[i] = _white_pawns.charAt(i);
            }
            else if(_white_bishop.charAt(i)!='0'){
                Humanboard[i] = _white_bishop.charAt(i);
            }
            else if(_white_king.charAt(i)!='0'){
                Humanboard[i] = _white_king.charAt(i);
            }
            else if(_white_knights.charAt(i)!='0'){
                Humanboard[i] = _white_knights.charAt(i);
            }
            else if(_white_queen.charAt(i)!='0'){
                Humanboard[i] = _white_queen.charAt(i);
            }
            else if(_white_rook.charAt(i)!='0'){
                Humanboard[i] = _white_rook.charAt(i);
            }
            else{
                Humanboard[i] = '0';
            }
        }

        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.print(Humanboard[63-square] + " ");
            }
            System.out.println("|" + (char)('A' + rank));
        }
        System.out.println("_______________");
        System.out.println("1 2 3 4 5 6 7 8");

    }
}
