import java.lang.reflect.Array;
import java.util.LinkedList;

public class Chessboard {
    //true means castle possible
    Boolean BlackLeftCastle;
    Boolean BlackRightCastle;
    Boolean WhiteLeftCastle;
    Boolean WhiteRightCastle;
    BitBoard Black, White, Pawn, Rook, King, Queen, Knight, Bishop;
    final BitBoard row1 = new BitBoard(0xffL);
    final BitBoard row2 = new BitBoard(0xff00L);
    final BitBoard row3 = new BitBoard(0xff0000L);
    final BitBoard row4 = new BitBoard(0xff000000L);
    final BitBoard row5 = new BitBoard(0xff00000000L);
    final BitBoard row6 = new BitBoard(0xff0000000000L);
    final BitBoard row7 = new BitBoard(0xff000000000000L);
    final BitBoard row8 = new BitBoard(0xff00000000000000L);
    final BitBoard colA = new BitBoard(0x8080808080808080L);
    final BitBoard colB = new BitBoard(0x4040404040404040L);
    final BitBoard colC = new BitBoard(0x2020202020202020L);
    final BitBoard colD = new BitBoard(0x1010101010101010L);
    final BitBoard colE = new BitBoard(0x88888888L);
    final BitBoard colF = new BitBoard(0x44444444L);
    final BitBoard colG = new BitBoard(0x22222222L);
    final BitBoard colH = new BitBoard(0x11111111L);


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

    //copy construtor
    public Chessboard(Chessboard toCopy){
        this.Black = new BitBoard(toCopy.Black.getBoard());
        this.White = new BitBoard(toCopy.White.getBoard());
        this.Rook = new BitBoard(toCopy.Rook.getBoard());
        this.Queen = new BitBoard(toCopy.Queen.getBoard());
        this.King = new BitBoard(toCopy.King.getBoard());
        this.Bishop = new BitBoard(toCopy.Bishop.getBoard());
        this.Pawn = new BitBoard(toCopy.Pawn.getBoard());
        this.Knight = new BitBoard(toCopy.Knight.getBoard());
    }

    public Chessboard(String fen){
        Black = new BitBoard();
        White = new BitBoard();
        Pawn = new BitBoard();
        Rook = new BitBoard();
        King = new BitBoard();
        Queen = new BitBoard();
        Bishop = new BitBoard();
        Knight = new BitBoard();
        String[] lines = fen.split("/");
        //go through all ranks, note that the last array entry in lines is the bottom 0 rank
        for (int r = 0; r<8; r++) {
            int file = 0; //place in line
            for(int i =0; i<lines[7-r].length();i++) {
                char c = lines[7-r].charAt(i);
                if(Character.isDigit(c)){
                    file += Character.getNumericValue(c);
                }else {
                    switch (c) {
                        case 'P' -> {
                            Pawn.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'p' -> {
                            Pawn.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                        case 'N' -> {
                            Knight.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'n' -> {
                            Knight.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                        case 'B' -> {
                            Bishop.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'b' -> {
                            Bishop.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                        case 'R' -> {
                            Rook.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'r' -> {
                            Rook.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                        case 'Q' -> {
                            Queen.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'q' -> {
                            Queen.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                        case 'K' -> {
                            King.setSquare(r * 8 + file);
                            White.setSquare(r * 8 + file);
                        }
                        case 'k' -> {
                            King.setSquare(r * 8 + file);
                            Black.setSquare(r * 8 + file);
                        }
                    }
                    file++;
                }
            }
        }
    }

    public LinkedList<ChessMove>[] allMoves(boolean WhiteTurn){
        //Moves in Order Pawn / bishop / knight / rook / queen / king
        LinkedList<ChessMove>[] Moves = new LinkedList[6];
        for(int i = 0; i<Moves.length; i++){
            Moves[i]=new LinkedList<>();
        }
        BitBoard Attacker;
        BitBoard Defender;
        if(WhiteTurn){
            Attacker = White;
            Defender = Black;
        }
        else{
            Attacker = Black;
            Defender = White;
        }
        BitBoard AttackPawn = new BitBoard(Attacker.getBoard() & Pawn.getBoard());
        BitBoard AttackRook = new BitBoard(Attacker.getBoard() & Rook.getBoard());
        BitBoard AttackKnight = new BitBoard(Attacker.getBoard() & Knight.getBoard());
        BitBoard AttackKing = new BitBoard(Attacker.getBoard() & King.getBoard());
        BitBoard AttackQueen = new BitBoard(Attacker.getBoard() & Queen.getBoard());
        BitBoard AttackBishop = new BitBoard(Attacker.getBoard() & Bishop.getBoard());
        LinkedList<Integer> FigPos;

        //Pawn
        FigPos = AttackPawn.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = PawnMove(WhiteTurn,pos);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[0].add(new ChessMove(pos,target));
            }
        }

        //Bishop
        FigPos = AttackBishop.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = BishopMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[1].add(new ChessMove(pos,target));
            }
        }

        //Knight
        FigPos = AttackKnight.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = KnightMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[2].add(new ChessMove(pos,target));
            }
        }

        //Rook
        FigPos = AttackRook.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = RookMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[3].add(new ChessMove(pos,target));
            }
        }

        //Queen
        FigPos = AttackQueen.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = QueenMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[4].add(new ChessMove(pos,target));
            }
        }

        //King
        FigPos = AttackKing.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = KingMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Moves[5].add(new ChessMove(pos,target));
            }
        }

        return Moves;
    }

    //checks if player whose current turn it is, is in a check. The Person who is in check is considerd to be the defender
    public boolean isCheck(boolean whiteTurn){
        BitBoard Attacker;
        BitBoard Defender;
        int KingPos;
        if(whiteTurn){
            Attacker = Black;
            Defender = White;
        }
        else{
            Attacker = White;
            Defender = Black;
        }
        BitBoard DefenderKing = new BitBoard(Defender.getBoard() & King.getBoard());
        LinkedList<Integer> KingPoses = DefenderKing.allSetSquares();
        KingPos = KingPoses.get(0);
        BitBoard allEnemyMoves = new BitBoard();

        //Bishop Moves
        BitBoard AttackerBishop = new BitBoard(Attacker.getBoard() & Bishop.getBoard());
        LinkedList<Integer> BishopPositions = AttackerBishop.allSetSquares();
        for (int Pos: BishopPositions) {
            BitBoard temp = BishopMove(Pos, Attacker, Defender);
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //Queen Moves
        BitBoard AttackerQueen = new BitBoard(Attacker.getBoard() & Queen.getBoard());
        LinkedList<Integer> QueenPositions = AttackerQueen.allSetSquares();
        for (int Pos: QueenPositions) {
            BitBoard temp = QueenMove(Pos, Attacker, Defender);
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //Rook Moves
        BitBoard AttackerRook = new BitBoard(Attacker.getBoard() & Rook.getBoard());
        LinkedList<Integer> RookPositions = AttackerRook.allSetSquares();
        for (int Pos: RookPositions) {
            BitBoard temp = RookMove(Pos,Attacker, Defender);
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //Knight Moves
        BitBoard AttackerKnight = new BitBoard(Attacker.getBoard() & Knight.getBoard());
        LinkedList<Integer> KnightPositions = AttackerKnight.allSetSquares();
        for (int Pos: KnightPositions) {
            BitBoard temp = KnightMove(Pos, Attacker, Defender);
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //Pawn Moves
        BitBoard AttackerPawn = new BitBoard(Attacker.getBoard() & Pawn.getBoard());
        LinkedList<Integer> PawnPositions = AttackerPawn.allSetSquares();
        for (int Pos: PawnPositions) {
            BitBoard temp = PawnMove(!whiteTurn, Pos);
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //allEnemyMoves.printBoard();
        return allEnemyMoves.isSquareSet(KingPos);
    }

    //check if the player whose turn it is, is in check mate
    public boolean isCheckmate(boolean whiteTurn){
        BitBoard Attacker;
        BitBoard Defender;
        if(whiteTurn){
            Attacker = White;
            Defender = Black;
        }
        else{
            Attacker = Black;
            Defender = White;
        }
        BitBoard Attacker_pawns = new BitBoard(Attacker.getBoard() & Pawn.getBoard());
        BitBoard Attacker_king = new BitBoard(Attacker.getBoard() & King.getBoard());
        BitBoard Attacker_queen = new BitBoard(Attacker.getBoard() & Queen.getBoard());
        BitBoard Attacker_knights = new BitBoard(Attacker.getBoard() & Knight.getBoard());
        BitBoard Attacker_bishop = new BitBoard(Attacker.getBoard() & Bishop.getBoard());
        BitBoard Attacker_rook = new BitBoard(Attacker.getBoard() & Rook.getBoard());

        //king
        LinkedList<Integer> kingPos = Attacker_king.allSetSquares();
        for(int king:kingPos){
            BitBoard Moves = KingMove(king,Attacker,Defender);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,king,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        //Pawn
        LinkedList<Integer> PawnPos = Attacker_pawns.allSetSquares();
        for(int pawn:PawnPos){
            BitBoard Moves = PawnMove(whiteTurn,pawn);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,pawn,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        //queen
        LinkedList<Integer> queenPos = Attacker_queen.allSetSquares();
        for(int queen:queenPos){
            BitBoard Moves = QueenMove(queen,Attacker,Defender);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,queen,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        //knight
        LinkedList<Integer> knightPos = Attacker_knights.allSetSquares();
        for(int knight:knightPos){
            BitBoard Moves = KnightMove(knight,Attacker,Defender);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,knight,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        //bishop
        LinkedList<Integer> bishopPos = Attacker_bishop.allSetSquares();
        for(int bishop:bishopPos){
            BitBoard Moves = BishopMove(bishop,Attacker,Defender);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,bishop,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        //rook
        LinkedList<Integer> rookPos = Attacker_rook.allSetSquares();
        for(int rook:rookPos){
            BitBoard Moves = RookMove(rook,Attacker,Defender);
            LinkedList<Integer> _moves = Moves.allSetSquares();
            for(int move:_moves){
                Chessboard State = new Chessboard(this);
                State.makeMove(whiteTurn,rook,move);
                if(!State.isCheck(whiteTurn)){
                    return false;
                }
            }
        }

        return true;
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
                possibleMoves = PawnMove(whiteTurn, from);
                success = possibleMoves.isSquareSet(to);
                figureboard = Pawn;
            }
            else if(King.isSquareSet(from)){
                possibleMoves = KingMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = King;
            }
            else if(Queen.isSquareSet(from)){
                possibleMoves = QueenMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Queen;
            }
            else if(Knight.isSquareSet(from)){
                possibleMoves = KnightMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Knight;
            }
            else if(Rook.isSquareSet(from)){
                possibleMoves = RookMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Rook;
            }
            else if(Bishop.isSquareSet(from)){
                possibleMoves = BishopMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                figureboard = Bishop;
            }
        }
        else {
            return false;
        }

        if(success){
            if(Defender.isSquareSet(to)){
                //capture black piece
                Attacker.clearSquare(from);
                figureboard.clearSquare(from);
                Attacker.setSquare(to);
                BitBoard enemyfig = whichFig(to);
                enemyfig.clearSquare(to);
                figureboard.setSquare(to);
                Defender.clearSquare(to);
            }else {
                //not a capture just move
                Attacker.clearSquare(from);
                figureboard.clearSquare(from);
                Attacker.setSquare(to);
                figureboard.setSquare(to);
            }
            return true;
        }else{
            return false;
        }
        
        /*if(success){
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
        }*/
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

    public BitBoard PawnMove(boolean whiteTurn, int from){

        BitBoard pawnPos = new BitBoard();
        pawnPos.setSquare(from);
        /*if (!whiteTurn) {
            pawnPos.setBoard(Long.reverse(pawnPos.getBoard()));     TRYING TO REVERSE BOARD WHEN BLACKS TURN TO REUSE WHITE CODE
        }*/
        BitBoard possibleMoves = new BitBoard();
        if (whiteTurn) {
            if ((pawnPos.getBoard() << 8 & (White.getBoard() | Black.getBoard())) == 0) {
                possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() << 8);

                if ((pawnPos.getBoard() & row2.getBoard()) != 0 && (((pawnPos.getBoard() << 8 | pawnPos.getBoard() << 16) & (White.getBoard() | Black.getBoard())) == 0))
                    possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() << 16);

                int nw = from + 7;
                if (Black.isSquareSet(nw))
                    possibleMoves.setSquare(nw);

                int ne = from + 9;
                if (Black.isSquareSet(ne))
                    possibleMoves.setSquare(ne);
            }
        } else {
            if ((pawnPos.getBoard() >> 8 & (White.getBoard() | Black.getBoard())) == 0) {
                possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() >> 8);

                if ((pawnPos.getBoard() & row7.getBoard()) != 0 & (((pawnPos.getBoard() >> 8 | pawnPos.getBoard() >> 16) & (White.getBoard() | Black.getBoard())) == 0))
                    possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() >> 16);
            }
                int sw = from - 9;
                if (White.isSquareSet(sw))
                    possibleMoves.setSquare(sw);

                int se = from - 7;
                if (White.isSquareSet(se))
                    possibleMoves.setSquare(se);
        }
        return possibleMoves;
    }

    public BitBoard KingMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard KingSet = new BitBoard();
        KingSet.setSquare(from);
        BitBoard KingAttacks = new BitBoard();
        KingAttacks.setBoard(KingSet.getBoard() << 1 | KingSet.getBoard() >> 1);
        KingSet.setBoard(KingSet.getBoard() | KingAttacks.getBoard());
        KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() << 8 | KingSet.getBoard() >> 8);
        BitBoard possibleMoves = new BitBoard();
        possibleMoves.setBoard(KingAttacks.getBoard() & ~Attacker.getBoard());
        return possibleMoves;
    }

    public BitBoard QueenMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //west direction so a change of -1
        for(int i=1;i<8;i++){
            //calc next field in north-west direction
            int Pos = from+i*7;
            //check if Bishop is on the north or west border so movement in north/west dirction is not possible
            if(ChessHelper.isNorthBorder(from) | ChessHelper.isWestBorder(from)){
                break;
            }
            //check if figure of same color is in the position, so move and further moves in that direction are not possible
            else if(Attacker.isSquareSet(Pos)){
                break;
            }
            //check if figure of other color is in the position, capture possible but not more moves in that direction
            else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            }
            //by elimination of case should a free field, so move is possible. check if field is on border so no further move are possible
            else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos) | ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
            //same procedure for all bishop direction also rook and queen
        }

        //north-east direction so a change of +9
        for(int i=1;i<8;i++){
            int Pos = from+i*9;
            if(ChessHelper.isNorthBorder(from) | ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos) | ChessHelper.isEastBorder(Pos)){
                    break;
                }
            }
        }

        //south-west direction so a change of -9
        for(int i=1;i<8;i++){
            int Pos = from-i*9;
            if(ChessHelper.isSouthBorder(from) | ChessHelper.isWestBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos) | ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
        }

        //south-east direction so a change of -7
        for(int i=1;i<8;i++){
            int Pos = from-i*7;
            if(ChessHelper.isSouthBorder(from) | ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos) | ChessHelper.isEastBorder(Pos)) {
                    break;
                }
            }
        }

        for(int i=1;i<8;i++){
            int Pos = from-i;
            if(ChessHelper.isWestBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
        }

        //east direction so a change of +1
        for(int i=1;i<8;i++){
            int Pos = from+i;
            if(ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isEastBorder(Pos)){
                    break;
                }
            }
        }

        //south direction so a change of -8
        for(int i=1;i<8;i++){
            int Pos = from-i*8;
            if(ChessHelper.isSouthBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos)){
                    break;
                }
            }
        }

        //north direction so a change of +8
        for(int i=1;i<8;i++){
            int Pos = from+i*8;
            if(ChessHelper.isNorthBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos)){
                    break;
                }
            }
        }
        return possibleMoves;

        /*for(int i=1;i<8;i++){
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
        }*/
    }

    public BitBoard KnightMove(int from, BitBoard Attacker, BitBoard Defender){
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

    public BitBoard BishopMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //north-west direction so a change of +7
        for(int i=1;i<8;i++){
            //calc next field in north-west direction
            int Pos = from+i*7;
            //check if Bishop is on the north or west border so movement in north/west dirction is not possible
            if(ChessHelper.isNorthBorder(from) | ChessHelper.isWestBorder(from)){
                break;
            }
            //check if figure of same color is in the position, so move and further moves in that direction are not possible
            else if(Attacker.isSquareSet(Pos)){
                break;
            }
            //check if figure of other color is in the position, capture possible but not more moves in that direction
            else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            }
            //by elimination of case should a free field, so move is possible. check if field is on border so no further move are possible
            else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos) | ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
            //same procedure for all bishop direction also rook and queen
        }

        //north-east direction so a change of +9
        for(int i=1;i<8;i++){
            int Pos = from+i*9;
            if(ChessHelper.isNorthBorder(from) | ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos) | ChessHelper.isEastBorder(Pos)){
                    break;
                }
            }
        }

        //south-west direction so a change of -9
        for(int i=1;i<8;i++){
            int Pos = from-i*9;
            if(ChessHelper.isSouthBorder(from) | ChessHelper.isWestBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos) | ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
        }

        //south-east direction so a change of -7
        for(int i=1;i<8;i++){
            int Pos = from-i*7;
            if(ChessHelper.isSouthBorder(from) | ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos) | ChessHelper.isEastBorder(Pos)) {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    public BitBoard RookMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //west direction so a change of -1
        for(int i=1;i<8;i++){
            int Pos = from-i;
            if(ChessHelper.isWestBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isWestBorder(Pos)){
                    break;
                }
            }
        }

        //east direction so a change of +1
        for(int i=1;i<8;i++){
            int Pos = from+i;
            if(ChessHelper.isEastBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isEastBorder(Pos)){
                    break;
                }
            }
        }

        //south direction so a change of -8
        for(int i=1;i<8;i++){
            int Pos = from-i*8;
            if(ChessHelper.isSouthBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isSouthBorder(Pos)){
                    break;
                }
            }
        }

        //north direction so a change of +8
        for(int i=1;i<8;i++){
            int Pos = from+i*8;
            if(ChessHelper.isNorthBorder(from)){
                break;
            }else if(Attacker.isSquareSet(Pos)){
                break;
            } else if (Defender.isSquareSet(Pos)) {
                possibleMoves.setSquare(Pos);
                break;
            } else {
                possibleMoves.setSquare(Pos);
                if(ChessHelper.isNorthBorder(Pos)){
                    break;
                }
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
