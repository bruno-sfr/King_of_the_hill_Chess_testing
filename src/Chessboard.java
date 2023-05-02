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
        boolean figAtFrom;
        BitBoard Attacker;
        BitBoard Defender;
        if(whiteTurn){
            figAtFrom = White.isSquareSet(from);
            Attacker = White;
            Defender = Black;
        }
        else{
            figAtFrom = Black.isSquareSet(from);
            Attacker = Black;
            Defender = White;
        }

        boolean success = false;
        BitBoard figureboard = new BitBoard();   //reference to board of the figure that is to be moved, to change position later
        BitBoard possibleMoves;
        if(figAtFrom){
            if(Pawn.isSquareSet(from)){
                success = PawnMove(whiteTurn, from, to);
                figureboard = Pawn;
            }
            else if(King.isSquareSet(from)){
                success = KingMove(whiteTurn, from, to);
                figureboard = King;
            }
            else if(Queen.isSquareSet(from)){
                possibleMoves = QueenMove(whiteTurn, from, to, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Queen;
            }
            else if(Knight.isSquareSet(from)){
                possibleMoves = KnightMove(whiteTurn, from, to, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Knight;
            }
            else if(Rook.isSquareSet(from)){
                possibleMoves = RookMove(whiteTurn, from, to, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Rook;
            }
            else if(Bishop.isSquareSet(from)){
                possibleMoves = BishopMove(whiteTurn, from, to, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Bishop;
            }
        }
        else {
            return false;
        }

        if(success){
            if(whiteTurn){
                //white moves
                if(Black.isSquareSet(to)){
                    //capture black piece
                    White.clearSquare(from);
                    figureboard.clearSquare(from);
                    White.setSquare(to);
                    BitBoard enemyfig = whichFig(to);
                    enemyfig.clearSquare(to);
                    figureboard.setSquare(to);
                    Black.clearSquare(to);
                }else {
                    //not a capture just move
                    White.clearSquare(from);
                    figureboard.clearSquare(from);
                    White.setSquare(to);
                    figureboard.setSquare(to);
                }
            }else {
                //black moves
                if(White.isSquareSet(to)){
                    //capture black piece
                    Black.clearSquare(from);
                    figureboard.clearSquare(from);
                    Black.setSquare(to);
                    BitBoard enemyfig = whichFig(to);
                    enemyfig.clearSquare(to);
                    figureboard.setSquare(to);
                    White.clearSquare(to);
                }else {
                    //not a capture just move
                    Black.clearSquare(from);
                    figureboard.clearSquare(from);
                    Black.setSquare(to);
                    figureboard.setSquare(to);
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public BitBoard whichFig(int field){
        if(Pawn.isSquareSet(field)){
            return Pawn;
        }
        else if(Knight.isSquareSet(field)){
            return Knight;
        }
        else if(King.isSquareSet(field)){
            return King;
        }
        else if(Queen.isSquareSet(field)){
            return Queen;
        }
        else if(Bishop.isSquareSet(field)){
            return Bishop;
        }
        else if(Rook.isSquareSet(field)){
            return Rook;
        }
        return null;
    }

    public boolean PawnMove(boolean whiteTurn, int from, int to){
        //if pawn moves up or down one row (in respect to color) -> true
        if (((to - from) == 8 && whiteTurn) || (to - from) == -8 && !whiteTurn)
            if (!Black.isSquareSet(to) && !White.isSquareSet(to))
                return true;
        //if white pawn moves right diagonally and beats a piece -> return true
        if (to - from == 9 && Black.isSquareSet(to) && !(from % 8 == 7))
            return true;
        //if white pawn moves left diagonally and beats a piece -> return true
        if (to - from == 7 &&  Black.isSquareSet(to) && !(from % 8 == 0))
            return true;
        //if black pawn moves right diagonally and beats a piece -> return true
        if (to - from == -7 && White.isSquareSet(to) && !(from % 8 == 7))
            return true;
        //if black pawn moves left diagonally and beats a piece -> return true
        if (to - from == -9 &&  White.isSquareSet(to) && !(from % 8 == 0))
            return true;
        //if white pawn moves up by two, nothing is in the way and he is still on base line ->  return true
        if (to - from == 16 && !White.isSquareSet(from + 8) && !Black.isSquareSet(from + 8) && !White.isSquareSet(to) && !Black.isSquareSet(to) && 8 <= from && from <= 15)
            return true;
        //if black pawn moves up by two, nothing is in the way and he is still on base line ->  return true
        if (to - from == -16 && !White.isSquareSet(from - 8) && !Black.isSquareSet(from - 8) && !White.isSquareSet(to) && !Black.isSquareSet(to) && 48 <= from && from <= 55)
            return true;
        return false;
    }

    public boolean KingMove(boolean whiteTurn, int from, int to){
        BitBoard KingSet = new BitBoard();
        KingSet.setSquare(from);
        BitBoard KingAttacks = new BitBoard();
        KingAttacks.setBoard(KingSet.getBoard() << 1 | KingSet.getBoard() >> 1);
        KingSet.setBoard(KingSet.getBoard() | KingAttacks.getBoard());
        KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() << 8 | KingSet.getBoard() >> 8);
        BitBoard possibleMoves = new BitBoard();
        if(whiteTurn){
            possibleMoves.setBoard(KingAttacks.getBoard() & ~White.getBoard());
        }
        else {
            possibleMoves.setBoard(KingAttacks.getBoard() & ~Black.getBoard());
        }
        return possibleMoves.isSquareSet(to);
    }

    public BitBoard QueenMove(boolean whiteTurn, int from, int to, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //west direction so a change of -1
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i)){
                break;
            } else if (Defender.isSquareSet(from-i)) {
                possibleMoves.setSquare(from-i);
                break;
            } else {
                possibleMoves.setSquare(from-i);
            }
        }

        //east direction so a change of +1
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i)){
                break;
            } else if (Defender.isSquareSet(from+i)) {
                possibleMoves.setSquare(from+i);
                break;
            } else {
                possibleMoves.setSquare(from+i);
            }
        }

        //south direction so a change of -8
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*8)){
                break;
            } else if (Defender.isSquareSet(from-i*8)) {
                possibleMoves.setSquare(from-i*8);
                break;
            } else {
                possibleMoves.setSquare(from-i*8);
            }
        }

        //north direction so a change of +8
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*8)){
                break;
            } else if (Defender.isSquareSet(from+i*8)) {
                possibleMoves.setSquare(from+i*8);
                break;
            } else {
                possibleMoves.setSquare(from+i*8);
            }
        }
        //north-west direction so a change of +7
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*7)){
                break;
            } else if (Defender.isSquareSet(from+i*7)) {
                possibleMoves.setSquare(from+i*7);
                break;
            } else {
                possibleMoves.setSquare(from+i*7);
            }
        }

        //north-east direction so a change of +9
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*9)){
                break;
            } else if (Defender.isSquareSet(from+i*9)) {
                possibleMoves.setSquare(from+i*9);
                break;
            } else {
                possibleMoves.setSquare(from+i*9);
            }
        }

        //south-west direction so a change of -9
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*9)){
                break;
            } else if (Defender.isSquareSet(from-i*9)) {
                possibleMoves.setSquare(from-i*9);
                break;
            } else {
                possibleMoves.setSquare(from-i*9);
            }
        }

        //south-east direction so a change of -7
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*7)){
                break;
            } else if (Defender.isSquareSet(from-i*7)) {
                possibleMoves.setSquare(from-i*7);
                break;
            } else {
                possibleMoves.setSquare(from-i*7);
            }
        }
        return possibleMoves;
    }

    public BitBoard KnightMove(boolean whiteTurn, int from, int to, BitBoard Attacker, BitBoard Defender){
        BitBoard nMoves = new BitBoard();
        nMoves.setSquare(from+6);
        nMoves.setSquare(from+10);
        nMoves.setSquare(from+15);
        nMoves.setSquare(from+17);
        nMoves.setSquare(from-6);
        nMoves.setSquare(from-10);
        nMoves.setSquare(from-15);
        nMoves.setSquare(from-17);
        long possibleMoves;
        possibleMoves = nMoves.getBoard() & ~Attacker.getBoard();
        return new BitBoard(possibleMoves);
    }

    public BitBoard BishopMove(boolean whiteTurn, int from, int to, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //north-west direction so a change of +7
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*7)){
                break;
            } else if (Defender.isSquareSet(from+i*7)) {
                possibleMoves.setSquare(from+i*7);
                break;
            } else {
                possibleMoves.setSquare(from+i*7);
            }
        }

        //north-east direction so a change of +9
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*9)){
                break;
            } else if (Defender.isSquareSet(from+i*9)) {
                possibleMoves.setSquare(from+i*9);
                break;
            } else {
                possibleMoves.setSquare(from+i*9);
            }
        }

        //south-west direction so a change of -9
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*9)){
                break;
            } else if (Defender.isSquareSet(from-i*9)) {
                possibleMoves.setSquare(from-i*9);
                break;
            } else {
                possibleMoves.setSquare(from-i*9);
            }
        }

        //south-east direction so a change of -7
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*7)){
                break;
            } else if (Defender.isSquareSet(from-i*7)) {
                possibleMoves.setSquare(from-i*7);
                break;
            } else {
                possibleMoves.setSquare(from-i*7);
            }
        }
        return possibleMoves;
    }

    public BitBoard RookMove(boolean whiteTurn, int from, int to, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //west direction so a change of -1
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i)){
                break;
            } else if (Defender.isSquareSet(from-i)) {
                possibleMoves.setSquare(from-i);
                break;
            } else {
                possibleMoves.setSquare(from-i);
            }
        }

        //east direction so a change of +1
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i)){
                break;
            } else if (Defender.isSquareSet(from+i)) {
                possibleMoves.setSquare(from+i);
                break;
            } else {
                possibleMoves.setSquare(from+i);
            }
        }

        //south direction so a change of -8
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from-i*8)){
                break;
            } else if (Defender.isSquareSet(from-i*8)) {
                possibleMoves.setSquare(from-i*8);
                break;
            } else {
                possibleMoves.setSquare(from-i*8);
            }
        }

        //north direction so a change of +8
        for(int i=1;i<8;i++){
            if(Attacker.isSquareSet(from+i*8)){
                break;
            } else if (Defender.isSquareSet(from+i*8)) {
                possibleMoves.setSquare(from+i*8);
                break;
            } else {
                possibleMoves.setSquare(from+i*8);
            }
        }
        return possibleMoves;
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
            System.out.println("|" + (rank + 1));
        }
        System.out.println("_______________");
        System.out.println("A B C D E F G H");

    }
}
