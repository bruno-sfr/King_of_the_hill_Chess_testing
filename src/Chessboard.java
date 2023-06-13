import java.util.LinkedList;

public class Chessboard {
    //true means castle possible
    //Zobrist zob;
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
    final BitBoard colA = new BitBoard(0x101010101010101L);
    final BitBoard colB = new BitBoard(0x202020202020202L);
    final BitBoard colC = new BitBoard(0x404040404040404L);
    final BitBoard colD = new BitBoard(0x808080808080808L);
    final BitBoard colE = new BitBoard(0x1010101010101010L);
    final BitBoard colF = new BitBoard(0x2020202020202020L);
    final BitBoard colG = new BitBoard(0x4040404040404040L);
    final BitBoard colH = new BitBoard(0x8080808080808080L);


    public Chessboard(){
        //zob = new Zobrist();
        Black = new BitBoard(0xffff000000000000L);
        White = new BitBoard(0x000000000000ffffL);
        Pawn = new BitBoard(0x00ff00000000ff00L);
        Rook = new BitBoard(0x8100000000000081L);
        King = new BitBoard(0x1000000000000010L);
        Queen = new BitBoard(0x0800000000000008L);
        Bishop = new BitBoard(0x2400000000000024L);
        Knight = new BitBoard(0x4200000000000042L);
        BlackLeftCastle = true;
        BlackRightCastle = true;
        WhiteLeftCastle = true;
        WhiteRightCastle = true;
    }

    //copy construtor
    public Chessboard(Chessboard toCopy){
        //zob = new Zobrist();
        this.Black = new BitBoard(toCopy.Black.getBoard());
        this.White = new BitBoard(toCopy.White.getBoard());
        this.Rook = new BitBoard(toCopy.Rook.getBoard());
        this.Queen = new BitBoard(toCopy.Queen.getBoard());
        this.King = new BitBoard(toCopy.King.getBoard());
        this.Bishop = new BitBoard(toCopy.Bishop.getBoard());
        this.Pawn = new BitBoard(toCopy.Pawn.getBoard());
        this.Knight = new BitBoard(toCopy.Knight.getBoard());
        BlackLeftCastle = true;
        BlackRightCastle = true;
        WhiteLeftCastle = true;
        WhiteRightCastle = true;
    }

    public Chessboard(String fen){
        //zob = new Zobrist();
        Black = new BitBoard();
        White = new BitBoard();
        Pawn = new BitBoard();
        Rook = new BitBoard();
        King = new BitBoard();
        Queen = new BitBoard();
        Bishop = new BitBoard();
        Knight = new BitBoard();
        this.BlackLeftCastle = true;
        this.BlackRightCastle = true;
        this.WhiteLeftCastle = true;
        this.WhiteRightCastle = true;
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

    public Chessboard(String fen, boolean _BlackLeftCastle, boolean _BlackRightCastle, boolean _WhiteLeftCastle, boolean _WhiteRightCastle){
        Black = new BitBoard();
        White = new BitBoard();
        Pawn = new BitBoard();
        Rook = new BitBoard();
        King = new BitBoard();
        Queen = new BitBoard();
        Bishop = new BitBoard();
        Knight = new BitBoard();
        this.BlackLeftCastle = _BlackLeftCastle;
        this.BlackRightCastle = _BlackRightCastle;
        this.WhiteLeftCastle = _WhiteLeftCastle;
        this.WhiteRightCastle = _WhiteRightCastle;
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

    public LinkedList<ChessMove>[] allMovesWithPieces(boolean WhiteTurn){
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

    public LinkedList<ChessMove>[] allMoves_withCheck(boolean WhiteTurn){
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
            //targets.printBoard();
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[0].add(new ChessMove(pos, target));
                    }
                }
            }
        }

        //Bishop
        FigPos = AttackBishop.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = BishopMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[1].add(new ChessMove(pos, target));
                    }
                }
            }
        }

        //Knight
        FigPos = AttackKnight.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = KnightMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[2].add(new ChessMove(pos, target));
                    }
                }
            }
        }

        //Rook
        FigPos = AttackRook.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = RookMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[3].add(new ChessMove(pos, target));
                    }
                }
            }
        }

        //Queen
        FigPos = AttackQueen.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = QueenMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                //System.out.println("testing queenmove:" + new ChessMove(pos, target));
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[4].add(new ChessMove(pos, target));
                        //System.out.println("added");
                    }
                }
            }
        }

        //King
        FigPos = AttackKing.allSetSquares();
        for(int pos:FigPos){
            BitBoard targets = KingMove(pos, Attacker, Defender);
            LinkedList<Integer> targets_list = targets.allSetSquares();
            for(int target:targets_list){
                Chessboard test = new Chessboard(this);
                if(test.makeMove(WhiteTurn,pos,target)) {
                    if (!test.isCheck(WhiteTurn)) {
                        Moves[5].add(new ChessMove(pos, target));
                    }
                }
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

        if(new BitBoard((Attacker.getBoard() & King.getBoard())).allSetSquares().size()==0){
            return false;
        } else if(new BitBoard((Defender.getBoard() & King.getBoard())).allSetSquares().size()==0){
            return true;
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
            BitBoard temp = PawnAttack(!whiteTurn, Pos);
            /*if(temp.getBoard()>0){
                temp.printBoard();
            }*/
            allEnemyMoves.setBoard(allEnemyMoves.getBoard() | temp.getBoard());
        }

        //King Moves
        BitBoard AttackerKing = new BitBoard(Attacker.getBoard() & King.getBoard());
        LinkedList<Integer> KingPositions = AttackerKing.allSetSquares();
        for (int Pos: KingPositions) {
            BitBoard temp = KingMove(Pos, Attacker, Defender);
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

        //if any of the kings is missing game state s quite clear
        if(Attacker_king.allSetSquares().size()==0){
            return true;
        } else if(new BitBoard((Defender.getBoard() & King.getBoard())).allSetSquares().size()==0){
            return false;
        }

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
            BitBoard Moves = PawnAttack(whiteTurn,pawn);
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

    public LinkedList<Integer[]> allMovesWithoutPieces(boolean whiteTurn){
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
        LinkedList<Integer[]> possibleMoves = new LinkedList<>();
        long pawnPositions = Attacker.getBoard() & Pawn.getBoard();
        for (int i = 0; i < 8; i++) {
            int from = Long.numberOfTrailingZeros(pawnPositions);             //get index of first piece
            if (from == 64)
                break;
            pawnPositions = pawnPositions & ~Long.lowestOneBit(pawnPositions);     //delete piece from board
            LinkedList<Integer> pawnTos = PawnMove(whiteTurn, from).allSetSquares();

            for (int to : pawnTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        long bishopPositions = Attacker.getBoard() & Bishop.getBoard();
        for (int i = 0; i < 2; i++) {
            int from = Long.numberOfTrailingZeros(bishopPositions);             //get index of first piece
            if (from == 64)
                break;
            bishopPositions = bishopPositions & ~Long.lowestOneBit(bishopPositions);     //delete piece from board
            LinkedList<Integer> bishopTos = BishopMove(from, Attacker, Defender).allSetSquares();

            for (int to : bishopTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        long rookPositions = Attacker.getBoard() & Rook.getBoard();
        for (int i = 0; i < 2; i++) {
            int from = Long.numberOfTrailingZeros(rookPositions);             //get index of first piece
            if (from == 64)
                break;
            rookPositions = rookPositions & ~Long.lowestOneBit(rookPositions);     //delete piece from board
            LinkedList<Integer> rookTos = RookMove(from, Attacker, Defender).allSetSquares();

            for (int to : rookTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        long knightPositions = Attacker.getBoard() & Knight.getBoard();
        for (int i = 0; i < 2; i++) {
            int from = Long.numberOfTrailingZeros(knightPositions);             //get index of first piece
            if (from == 64)
                break;
            knightPositions = knightPositions & ~Long.lowestOneBit(knightPositions);     //delete piece from board
            LinkedList<Integer> knightTos = KnightMove(from, Attacker, Defender).allSetSquares();

            for (int to : knightTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        long QueenPositions = Attacker.getBoard() & Queen.getBoard();
        for (int i = 0; i < 1; i++) {
            int from = Long.numberOfTrailingZeros(QueenPositions);             //get index of first piece
            if (from == 64)
                break;
            QueenPositions = QueenPositions & ~Long.lowestOneBit(QueenPositions);     //delete piece from board
            LinkedList<Integer> QueenTos = QueenMove(from, Attacker, Defender).allSetSquares();

            for (int to : QueenTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        long KingPositions = Attacker.getBoard() & King.getBoard();
        for (int i = 0; i < 1; i++) {
            int from = Long.numberOfTrailingZeros(KingPositions);             //get index of first piece
            if (from == 64)
                break;
            KingPositions = KingPositions & ~Long.lowestOneBit(KingPositions);     //delete piece from board
            LinkedList<Integer> KingTos = KingMove(from, Attacker, Defender).allSetSquares();

            for (int to : KingTos) {                                //save discovered moves in possibleMoves
                Integer[] move = new Integer[]{from, to};
                possibleMoves.add(move);
            }
        }

        return possibleMoves;
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

        //LinkedList<Integer[]> res = allPossibleMoves(whiteTurn, Attacker, Defender);

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
                if(whiteTurn){
                    if(isCheck(true)){
                        WhiteLeftCastle = false;
                        WhiteRightCastle = false;
                    }
                }else {
                    if(isCheck(false)){
                        BlackRightCastle = false;
                        BlackLeftCastle = false;
                    }
                }

                possibleMoves = KingMove(from, Attacker, Defender);
                success = possibleMoves.isSquareSet(to);
                if(success) {
                    //white king moves so set castle flags and check if its a castle
                    if(Attacker == White) {
                        if(from == 4) {
                            if (to == 2 & WhiteLeftCastle) {
                                //move rook according to castle
                                White.clearSquare(0);
                                White.setSquare(3);
                                Rook.clearSquare(0);
                                Rook.setSquare(3);
                            } else if (to == 6 & WhiteRightCastle) {
                                White.clearSquare(7);
                                White.setSquare(5);
                                Rook.clearSquare(7);
                                Rook.setSquare(5);
                            }
                            WhiteLeftCastle = false;
                            WhiteRightCastle = false;
                        }
                    } else {
                        if(from == 60) {
                            if (to == 58 & BlackLeftCastle) {
                                Black.clearSquare(56);
                                Black.setSquare(59);
                                Rook.clearSquare(56);
                                Rook.setSquare(59);
                            } else if (to == 62 & BlackRightCastle) {
                                Black.clearSquare(63);
                                Black.setSquare(61);
                                Rook.clearSquare(63);
                                Rook.setSquare(61);
                            }
                            BlackLeftCastle = false;
                            BlackRightCastle = false;
                        }
                    }
                }
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
                //check for first rook move, disable caslte accordingly
                if(success){
                    if(Attacker == White){
                        if(from == 0){
                            WhiteLeftCastle = false;
                        } else if (from == 7) {
                            WhiteRightCastle = false;
                        }
                    } else {
                        if(from == 56){
                            BlackLeftCastle = false;
                        } else if (from == 63) {
                            BlackRightCastle = false;
                        }
                    }
                }
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
                BitBoard enemyfig = whichFig(to);
                /*if(enemyfig == null){
                    System.out.println("Defender board");
                    Defender.printBoard();
                    Knight.printBoard();
                    King.printBoard();
                    Pawn.printBoard();
                    Bishop.printBoard();
                    Queen.printBoard();
                    Rook.printBoard();
                }*/
                //enemyfig.clearSquare(to);
                try {
                    enemyfig.clearSquare(to);
                } catch (Exception e){
                    System.out.println(e);
                    //System.out.println("From:"+ from +" to:"+ to);
                    //Defender.printBoard();
                    //this.printBoard();
                    return false;
                }
                Attacker.clearSquare(from);
                figureboard.clearSquare(from);
                Attacker.setSquare(to);
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
        //System.out.println("field:" + field);
        //this.printBoard();
        return null;
    }

    public BitBoard PawnMove(boolean whiteTurn, int from){

        BitBoard pawnPos = new BitBoard();
        pawnPos.setSquare(from);

        BitBoard possibleMoves = new BitBoard();
        if (whiteTurn) {
            if ((pawnPos.getBoard() << 8 & (White.getBoard() | Black.getBoard())) == 0) {
                possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() << 8);

                if ((pawnPos.getBoard() & row2.getBoard()) != 0 && (((pawnPos.getBoard() << 8 | pawnPos.getBoard() << 16) & (White.getBoard() | Black.getBoard())) == 0))
                    possibleMoves.setBoard(possibleMoves.getBoard() | pawnPos.getBoard() << 16);
            }
            //if ((pawnPos.getBoard() & colA.getBoard()) == 0) {
            if (!ChessHelper.isWestBorder(from)) {
                int nw = from + 7;
                if (Black.isSquareSet(nw))
                    possibleMoves.setSquare(nw);
            }
            //if ((pawnPos.getBoard() & colH.getBoard()) == 0) {
            if (!ChessHelper.isEastBorder(from)) {
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
            //if ((pawnPos.getBoard() & colA.getBoard()) == 0) {
            if (!ChessHelper.isWestBorder(from)) {
                int sw = from - 9;
                if (White.isSquareSet(sw))
                    possibleMoves.setSquare(sw);
            }
            //if ((pawnPos.getBoard() & colH.getBoard()) == 0) {
            if (!ChessHelper.isEastBorder(from)) {
                int se = from - 7;
                if (White.isSquareSet(se))
                    possibleMoves.setSquare(se);
            }
        }
        return possibleMoves;
    }

    public BitBoard PawnAttack(boolean whiteTurn, int from) {
        BitBoard pawnPos = new BitBoard();
        pawnPos.setSquare(from);
        BitBoard possibleMoves = new BitBoard();
        if (whiteTurn) {
            //if ((pawnPos.getBoard() & colA.getBoard()) == 0) {
            if (!ChessHelper.isWestBorder(from)) {
                int nw = from + 7;
                if (Black.isSquareSet(nw))
                    possibleMoves.setSquare(nw);
            }
            //if ((pawnPos.getBoard() & colH.getBoard()) == 0) {
            if (!ChessHelper.isEastBorder(from)) {
                int ne = from + 9;
                if (Black.isSquareSet(ne))
                    possibleMoves.setSquare(ne);
            }
        } else {
            //if ((pawnPos.getBoard() & colA.getBoard()) == 0) {
            if (!ChessHelper.isWestBorder(from)) {
                int sw = from - 9;
                if (White.isSquareSet(sw))
                    possibleMoves.setSquare(sw);
            }
            //if ((pawnPos.getBoard() & colH.getBoard()) == 0) {
            if (!ChessHelper.isEastBorder(from)) {
                int se = from - 7;
                if (White.isSquareSet(se))
                    possibleMoves.setSquare(se);
            }
        }
        return possibleMoves;
    }


    public BitBoard KingMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleCastle = new BitBoard();
        //white king at starting position, so check for castleing
        if(from == 4 && Attacker == White){
            if(WhiteLeftCastle && (0x1L & Rook.getBoard() & White.getBoard()) != 0) {
                long figbetween = 0x000000000000000eL;
                if((White.getBoard() & figbetween) == 0){
                    possibleCastle.setSquare(2);
                }
            }
            if(WhiteRightCastle && (0x80L & Rook.getBoard() & White.getBoard()) != 0) {
                long figbetween = 0x0000000000000060L;
                if((White.getBoard() & figbetween) == 0){
                    possibleCastle.setSquare(6);
                }
            }
        } else if (from == 60 && Attacker == Black) {
            if(BlackLeftCastle && (0x0100000000000000L & Rook.getBoard() & Black.getBoard()) != 0) {
                long figbetween = 0x0e00000000000000L;
                if((Black.getBoard() & figbetween) == 0){
                    possibleCastle.setSquare(58);
                }
            }
            if(BlackRightCastle && (0x8000000000000000L & Rook.getBoard() & Black.getBoard()) != 0) {
                long figbetween = 0x6000000000000000L;
                if((Black.getBoard() & figbetween) == 0){
                    possibleCastle.setSquare(62);
                }
            }
        }
        BitBoard KingSet = new BitBoard();
        KingSet.setSquare(from);
        BitBoard KingAttacks = new BitBoard();
        if(!ChessHelper.isEastBorder(from)){
            KingAttacks.setBoard(KingSet.getBoard() << 1);
        }
        if(!ChessHelper.isWestBorder(from)){
            KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() >> 1);
        }
        //KingAttacks.setBoard(KingSet.getBoard() << 1 | KingSet.getBoard() >> 1);
        KingSet.setBoard(KingSet.getBoard() | KingAttacks.getBoard());
        if(!ChessHelper.isSouthBorder(from)){
            KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() >>> 8);
        }
        if(!ChessHelper.isNorthBorder(from)){
            KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() << 8);
        }
        //KingAttacks.setBoard(KingAttacks.getBoard() | KingSet.getBoard() << 8 | KingSet.getBoard() >>> 8);
        BitBoard possibleMoves = new BitBoard();
        possibleMoves.setBoard((KingAttacks.getBoard() & ~Attacker.getBoard()) | possibleCastle.getBoard());
        return possibleMoves;
    }

    public BitBoard QueenMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard BishopMoves = BishopMove(from,Attacker,Defender);
        BitBoard RookMoves = RookMove(from,Attacker,Defender);
        return new BitBoard(BishopMoves.getBoard() | RookMoves.getBoard());
    }

    public BitBoard KnightMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard nMoves = new BitBoard();
        if ((from % 8 > 1) & (from < 56))
            nMoves.setSquare(from+6);

        if ((from % 8 < 6) & (from < 56))
            nMoves.setSquare(from + 10);

        if ((from % 8 > 0) & (from < 48))
            nMoves.setSquare(from+15);

        if ((from % 8 < 7) & (from < 48))
            nMoves.setSquare(from + 17);

        if ((from % 8 < 6) & (from > 7))
            nMoves.setSquare(from - 6);

        if ((from % 8 > 1) & (from > 7))
            nMoves.setSquare(from - 10);

        if ((from % 8 < 7) & (from > 15))
            nMoves.setSquare(from - 15);
        
        if ((from % 8 > 0) & (from > 15))
            nMoves.setSquare(from - 17);
        long possibleMoves;
        possibleMoves = nMoves.getBoard() & ~Attacker.getBoard();
        return new BitBoard(possibleMoves);
    }

    public BitBoard BishopMove(int from, BitBoard Attacker, BitBoard Defender){
        BitBoard possibleMoves = new BitBoard();
        //check if Bishop is on the north or west border so movement in north/west dirction is not possible
        if(!(ChessHelper.isNorthBorder(from) | ChessHelper.isWestBorder(from))){
            //north-west direction so a change of +7
            for(int i=1;i<8;i++) {
                //calc next field in north-west direction
                int Pos = from + i * 7;
                //check if figure of same color is in the position, so move and further moves in that direction are not possible
                if (Attacker.isSquareSet(Pos)) {
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
                    if (ChessHelper.isNorthBorder(Pos) | ChessHelper.isWestBorder(Pos)) {
                        break;
                    }
                }
            }
            //same procedure for all bishop direction also rook and queen
        }

        if(!(ChessHelper.isNorthBorder(from) | ChessHelper.isEastBorder(from))) {
            //north-east direction so a change of +9
            for (int i = 1; i < 8; i++) {
                int Pos = from + i * 9;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isNorthBorder(Pos) | ChessHelper.isEastBorder(Pos)) {
                        break;
                    }
                }
            }
        }

        if(!(ChessHelper.isSouthBorder(from) | ChessHelper.isWestBorder(from))) {
            //south-west direction so a change of -9
            for (int i = 1; i < 8; i++) {
                int Pos = from - i * 9;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isSouthBorder(Pos) | ChessHelper.isWestBorder(Pos)) {
                        break;
                    }
                }
            }
        }

        if(!(ChessHelper.isSouthBorder(from) | ChessHelper.isEastBorder(from))) {
            //south-east direction so a change of -7
            for (int i = 1; i < 8; i++) {
                int Pos = from - i * 7;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isSouthBorder(Pos) | ChessHelper.isEastBorder(Pos)) {
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }

    public BitBoard RookMove(int from, BitBoard Attacker, BitBoard Defender) {
        BitBoard possibleMoves = new BitBoard();
        //west direction so a change of -1
        if (!ChessHelper.isWestBorder(from)) {
            for (int i = 1; i < 8; i++) {                                    //TODO: repeat behaviour for all directions
                int Pos = from - i;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isWestBorder(Pos)) {
                        break;
                    }
                }
            }
        }

        //east direction so a change of +1
        if (!ChessHelper.isEastBorder(from)) {
            for (int i = 1; i < 8; i++) {
                int Pos = from + i;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isEastBorder(Pos)) {
                        break;
                    }
                }
            }
        }

        //south direction so a change of -8
        if (!ChessHelper.isSouthBorder(from)) {
            for (int i = 1; i < 8; i++) {
                int Pos = from - i * 8;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isSouthBorder(Pos)) {
                        break;
                    }
                }
            }
        }

        //north direction so a change of +8
        if (!ChessHelper.isNorthBorder(from)) {
            for (int i = 1; i < 8; i++) {
                int Pos = from + i * 8;
                if (Attacker.isSquareSet(Pos)) {
                    break;
                } else if (Defender.isSquareSet(Pos)) {
                    possibleMoves.setSquare(Pos);
                    break;
                } else {
                    possibleMoves.setSquare(Pos);
                    if (ChessHelper.isNorthBorder(Pos)) {
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }

    public boolean isBlackKing(){
        BitBoard black_king = new BitBoard(Black.getBoard() & King.getBoard());
        return black_king.allSetSquares().size()>0;
    }

    public boolean isWhiteKing(){
        BitBoard white_king = new BitBoard(White.getBoard() & King.getBoard());
        return white_king.allSetSquares().size()>0;
    }

    //postive value means white has the advantage and negative means black has the advantage
    //pawn has a value of 1
    //knight and bishop have a value of 3
    //rook has a value of 5
    //queen has a value of 9
    //king has insane high value
    //an available move is worth 0.1
    //and euclidean distance of king to center is valuable
    public double eval_func(){
        double eval = 0.0;
        //Black
        BitBoard black_pawns = new BitBoard(Black.getBoard() & Pawn.getBoard());
        BitBoard black_king = new BitBoard(Black.getBoard() & King.getBoard());
        BitBoard black_queen = new BitBoard(Black.getBoard() & Queen.getBoard());
        BitBoard black_knights = new BitBoard(Black.getBoard() & Knight.getBoard());
        BitBoard black_bishop = new BitBoard(Black.getBoard() & Bishop.getBoard());
        BitBoard black_rook = new BitBoard(Black.getBoard() & Rook.getBoard());
        //White
        BitBoard white_pawns = new BitBoard(White.getBoard() & Pawn.getBoard());
        BitBoard white_king = new BitBoard(White.getBoard() & King.getBoard());
        BitBoard white_queen = new BitBoard(White.getBoard() & Queen.getBoard());
        BitBoard white_knights = new BitBoard(White.getBoard() & Knight.getBoard());
        BitBoard white_bishop = new BitBoard(White.getBoard() & Bishop.getBoard());
        BitBoard white_rook = new BitBoard(White.getBoard() & Rook.getBoard());

        //black value
        eval = eval - black_pawns.allSetSquares().size();
        eval = eval - black_bishop.allSetSquares().size() * 3;
        eval = eval - black_knights.allSetSquares().size() * 3;
        eval = eval - black_rook.allSetSquares().size() * 5;
        eval = eval - black_queen.allSetSquares().size() * 9;
        eval = eval - black_king.allSetSquares().size() * 100000;

        //white value
        eval = eval + white_pawns.allSetSquares().size();
        eval = eval + white_bishop.allSetSquares().size() * 3;
        eval = eval + white_knights.allSetSquares().size() * 3;
        eval = eval + white_rook.allSetSquares().size() * 5;
        eval = eval + white_queen.allSetSquares().size() * 9;
        eval = eval + white_king.allSetSquares().size() * 100000;

        //available moves
        /*LinkedList<ChessMove>[] whiteMoves = this.allMovesWithPieces(true);
        LinkedList<ChessMove>[] blackMoves = this.allMovesWithPieces(false);

        for(int i=0; i<6; i++){
            eval = eval + whiteMoves[i].size() * 0.1;
            eval = eval - blackMoves[i].size() * 0.1;
        }*/
        LinkedList<Integer[]> whiteMoves = this.allMovesWithoutPieces(true);
        LinkedList<Integer[]> blackMoves = this.allMovesWithoutPieces(false);
        eval += whiteMoves.size() * 0.1;
        eval -= blackMoves.size() * 0.1;

        //eval king distance to middle but just subtracting bitboard values (IMPROVE THIS LATER!!!)
        if(white_king.allSetSquares().size()>0){
            LinkedList<Integer> _whiteKing = white_king.allSetSquares();
            int whiteKingPos = _whiteKing.getFirst();
            if(whiteKingPos == 27 | whiteKingPos == 28 | whiteKingPos == 35 | whiteKingPos == 36){
                //white has won via king of the hill
                eval = eval + 100000;
            }
            //TODO: change scaling off distance evaluation to grow exponentially with how close u are to the center
            //int white_distance = (Math.abs(whiteKingPos - 27) + Math.abs(whiteKingPos - 28) + Math.abs(whiteKingPos - 35) + Math.abs(whiteKingPos - 36))/4;
            //System.out.println("white dis:" + ChessHelper.euclidDistanceToMiddle(whiteKingPos));
            eval = eval - ChessHelper.euclidDistanceToMiddle(whiteKingPos);
        }
        if(black_king.allSetSquares().size()>0) {
            LinkedList<Integer> _blackKing = black_king.allSetSquares();
            int blackKingPos = _blackKing.getFirst();
            if(blackKingPos == 27 | blackKingPos == 28 | blackKingPos == 35 | blackKingPos == 36){
                //black has won via king of the hill
                eval = eval -100000;
            }
            //int black_distance = (Math.abs(blackKingPos - 27) + Math.abs(blackKingPos - 28) + Math.abs(blackKingPos - 35) + Math.abs(blackKingPos - 36))/4;
            //System.out.println("black dis:" + ChessHelper.euclidDistanceToMiddle(blackKingPos));
            eval = eval + (ChessHelper.euclidDistanceToMiddle(blackKingPos));
        }
        //pawn structure holes evaluation
        long whitePawnBoard = White.getBoard() & Pawn.getBoard();
        long blackPawnBoard = Black.getBoard() & Pawn.getBoard();
        long colABoard = colA.getBoard();
        long colBBoard = colB.getBoard();
        long colCBoard = colC.getBoard();
        long colDBoard = colD.getBoard();
        long colEBoard = colE.getBoard();
        long colFBoard = colF.getBoard();
        long colGBoard = colG.getBoard();
        long colHBoard = colH.getBoard();
        double deltaEval = 0.0;

        if ((whitePawnBoard & colABoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colBBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colCBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colDBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colEBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colFBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colGBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((whitePawnBoard & colHBoard) == 0L){
            deltaEval -= 0.1;
        }
        if ((blackPawnBoard & colABoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colBBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colCBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colDBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colEBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colFBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colGBoard) == 0L){
            deltaEval += 0.1;
        }
        if ((blackPawnBoard & colHBoard) == 0L){
            deltaEval += 0.1;
        }
        eval += deltaEval;


        /*if(white_king.allSetSquares().size()>0 & black_king.allSetSquares().size()>0) {
            //System.out.println("checking checkmate in eval");
            if(this.isCheck(true)){
                //white in ckeck
                eval = eval - 10;
            }
            if(this.isCheck(false)){
                //black in ckeck
                eval = eval + 10;
            }
            if (this.isCheckmate(true)) {
                //white in ckeckmate so black win
                eval = eval - 100000;
            }
            if (this.isCheckmate(false)) {
                eval = eval + 100000;
            }
        }*/
        return eval;
    }

    public double eval_func_withCheck(){
        double eval = 0.0;
        //Black
        BitBoard black_pawns = new BitBoard(Black.getBoard() & Pawn.getBoard());
        BitBoard black_king = new BitBoard(Black.getBoard() & King.getBoard());
        BitBoard black_queen = new BitBoard(Black.getBoard() & Queen.getBoard());
        BitBoard black_knights = new BitBoard(Black.getBoard() & Knight.getBoard());
        BitBoard black_bishop = new BitBoard(Black.getBoard() & Bishop.getBoard());
        BitBoard black_rook = new BitBoard(Black.getBoard() & Rook.getBoard());
        //White
        BitBoard white_pawns = new BitBoard(White.getBoard() & Pawn.getBoard());
        BitBoard white_king = new BitBoard(White.getBoard() & King.getBoard());
        BitBoard white_queen = new BitBoard(White.getBoard() & Queen.getBoard());
        BitBoard white_knights = new BitBoard(White.getBoard() & Knight.getBoard());
        BitBoard white_bishop = new BitBoard(White.getBoard() & Bishop.getBoard());
        BitBoard white_rook = new BitBoard(White.getBoard() & Rook.getBoard());

        //black value
        eval = eval - black_pawns.allSetSquares().size();
        eval = eval - black_bishop.allSetSquares().size() * 3;
        eval = eval - black_knights.allSetSquares().size() * 3;
        eval = eval - black_rook.allSetSquares().size() * 5;
        eval = eval - black_queen.allSetSquares().size() * 9;
        eval = eval - black_king.allSetSquares().size() * 100000;

        //white value
        eval = eval + white_pawns.allSetSquares().size();
        eval = eval + white_bishop.allSetSquares().size() * 3;
        eval = eval + white_knights.allSetSquares().size() * 3;
        eval = eval + white_rook.allSetSquares().size() * 5;
        eval = eval + white_queen.allSetSquares().size() * 9;
        eval = eval + white_king.allSetSquares().size() * 100000;

        //available moves
        LinkedList<ChessMove>[] whiteMoves = this.allMovesWithPieces(true);
        LinkedList<ChessMove>[] blackMoves = this.allMovesWithPieces(false);

        for(int i=0; i<6; i++){
            eval = eval + whiteMoves[i].size() * 0.1;
            eval = eval - blackMoves[i].size() * 0.1;
        }

        //eval king distance to middle but just subtracting bitboard values (IMPROVE THIS LATER!!!)
        if(white_king.allSetSquares().size()>0){
            LinkedList<Integer> _whiteKing = white_king.allSetSquares();
            int whiteKingPos = _whiteKing.getFirst();
            if(whiteKingPos == 27 | whiteKingPos == 28 | whiteKingPos == 35 | whiteKingPos == 36){
                //white has won via king of the hill, but only when not in check
                if(!this.isCheck(true)) {
                    eval = eval + 100000;
                }
            }
            //TODO: change scaling off distance evaluation to grow exponentially with how close u are to the center
            //int white_distance = (Math.abs(whiteKingPos - 27) + Math.abs(whiteKingPos - 28) + Math.abs(whiteKingPos - 35) + Math.abs(whiteKingPos - 36))/4;
            //System.out.println("white dis:" + ChessHelper.euclidDistanceToMiddle(whiteKingPos));
            eval = eval - ChessHelper.euclidDistanceToMiddle(whiteKingPos);
        }
        if(black_king.allSetSquares().size()>0) {
            LinkedList<Integer> _blackKing = black_king.allSetSquares();
            int blackKingPos = _blackKing.getFirst();
            if(blackKingPos == 27 | blackKingPos == 28 | blackKingPos == 35 | blackKingPos == 36){
                //black has won via king of the hill
                if(!this.isCheck(false)) {
                    eval = eval - 100000;
                }
            }
            //int black_distance = (Math.abs(blackKingPos - 27) + Math.abs(blackKingPos - 28) + Math.abs(blackKingPos - 35) + Math.abs(blackKingPos - 36))/4;
            //System.out.println("black dis:" + ChessHelper.euclidDistanceToMiddle(blackKingPos));
            eval = eval + (ChessHelper.euclidDistanceToMiddle(blackKingPos));
        }

        if(white_king.allSetSquares().size()>0 & black_king.allSetSquares().size()>0) {
            //System.out.println("checking checkmate in eval");
            if(this.isCheck(true)){
                //white in ckeck
                eval = eval - 1;
            }
            if(this.isCheck(false)){
                //black in ckeck
                eval = eval + 1;
            }
            if (this.isCheckmate(true)) {
                //white in ckeckmate so black win
                eval = eval - 100000;
            }
            if (this.isCheckmate(false)) {
                eval = eval + 100000;
            }
        }
        return eval;
    }

    public boolean isGameOver(){
        BitBoard black_king = new BitBoard(Black.getBoard() & King.getBoard());
        BitBoard white_king = new BitBoard(White.getBoard() & King.getBoard());
        if (this.isCheckmate(true)) {
            //white in ckeckmate so black win
            return true;
        }
        if (this.isCheckmate(false)) {
            return true;
        }
        int whiteKingPos = white_king.allSetSquares().getFirst();
        if(whiteKingPos == 27 | whiteKingPos == 28 | whiteKingPos == 35 | whiteKingPos == 36){
            //white has won via king of the hill
            if(!this.isCheck(true)) {
                return true;
            }
        }
        int blackKingPos = black_king.allSetSquares().getFirst();
        if(blackKingPos == 27 | blackKingPos == 28 | blackKingPos == 35 | blackKingPos == 36){
            //white has won via king of the hill
            if(!this.isCheck(false)) {
                return true;
            }
        }
        if(!this.isBlackKing()){
            return true;
        }
        if(!this.isWhiteKing()){
            return true;
        }
        return false;
    }

    /*public long hash_func(Boolean whiteTurn){
        long hash = 0;
        if(whiteTurn){
            //if white then bitwise or
            hash = White.getBoard() ^ Black.getBoard() ^ Knight.getBoard() ^ King.getBoard() ^ Queen.getBoard() ^ Bishop.getBoard() ^ Pawn.getBoard();
        }else {
            //if black then bitwise xor
            hash = ~(White.getBoard() ^ Black.getBoard() ^ Knight.getBoard() ^ King.getBoard() ^ Queen.getBoard() ^ Bishop.getBoard() ^ Pawn.getBoard());
        }
        if(BlackRightCastle){
            hash = hash + 0xf000L;
        }
        if(BlackLeftCastle){
            hash = hash + 0xf00L;
        }
        if(WhiteRightCastle){
            hash = hash + 0xf0L;
        }
        if(WhiteLeftCastle){
            hash = hash + 0xfL;
        }
        String[] bitboardStrings = new String[8];
        bitboardStrings[0] = Long.toBinaryString(White.getBoard());
        bitboardStrings[1] = Long.toBinaryString(Black.getBoard());
        bitboardStrings[2] = Long.toBinaryString(King.getBoard());
        bitboardStrings[3] = Long.toBinaryString(Queen.getBoard());
        bitboardStrings[4] = Long.toBinaryString(Pawn.getBoard());
        bitboardStrings[5] = Long.toBinaryString(Knight.getBoard());
        bitboardStrings[6] = Long.toBinaryString(Rook.getBoard());
        bitboardStrings[7] = Long.toBinaryString(Bishop.getBoard());
        String concatenatedString = String.join("", bitboardStrings);
        long hashKey = Long.parseUnsignedLong(concatenatedString, 2);
        if(whiteTurn){
            hash = hashKey;
        }else {
            hash = ~hashKey;
        }

        return hash;*/
        /*try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            StringBuilder concatenatedString = new StringBuilder();
            concatenatedString.append(Long.toBinaryString(White.getBoard()));
            concatenatedString.append(Long.toBinaryString(Black.getBoard()));
            concatenatedString.append(Long.toBinaryString(King.getBoard()));
            concatenatedString.append(Long.toBinaryString(Queen.getBoard()));
            concatenatedString.append(Long.toBinaryString(Rook.getBoard()));
            concatenatedString.append(Long.toBinaryString(Knight.getBoard()));
            concatenatedString.append(Long.toBinaryString(Bishop.getBoard()));
            concatenatedString.append(Long.toBinaryString(Pawn.getBoard()));


            byte[] hashBytes = digest.digest(concatenatedString.toString().getBytes(StandardCharsets.UTF_8));

            long hashKey = 0;
            for (int i = 0; i < 8; i++) {
                hashKey |= ((long) (hashBytes[i] & 0xFF)) << (8 * i);
            }

            if(whiteTurn){
                return hashKey;
            }else {
                return ~hashKey;
            }

        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return 0; // or throw an exception
        }*/
        /*long hashKey = 0;
        hashKey ^= ChessHelper.deBruijnMultiply(White.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Black.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Knight.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(King.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Queen.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Bishop.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Pawn.getBoard());
        hashKey ^= ChessHelper.deBruijnMultiply(Rook.getBoard());

        if (!whiteTurn) {
            hashKey ^= 0x1L; // Flip the least significant bit if it's Black's turn
        }
        return zob.generateHashKey(this,whiteTurn);
    }*/

    public void printBoard(){

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

        System.out.println("A B C D E F G H");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
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
