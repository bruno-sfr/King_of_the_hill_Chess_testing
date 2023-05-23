import java.util.Random;

public class Zobrist {
    private static final int NUM_PIECE_TYPES = 12; // Total number of piece types (6 for each color)
    private static final int NUM_SQUARES = 64; // Total number of squares on the chessboard

    private long[][] pieceSquareKeys; // Hash keys for piece-square combinations
    private long sideToMoveKey; // Hash key for the side to move

    public Zobrist() {
        initializeKeys();
    }

    private void initializeKeys() {
        Random random = new Random();

        // Initialize piece-square keys
        pieceSquareKeys = new long[NUM_PIECE_TYPES][NUM_SQUARES];
        for (int piece = 0; piece < NUM_PIECE_TYPES; piece++) {
            for (int square = 0; square < NUM_SQUARES; square++) {
                pieceSquareKeys[piece][square] = random.nextLong();
            }
        }

        // Initialize side to move key
        sideToMoveKey = random.nextLong();
    }

    public long generateHashKey(Chessboard board, Boolean whiteturn) {
        long hashKey = 0;

        // Hash the piece-square combinations
        /*for (int square = 0; square < NUM_SQUARES; square++) {
            int piece = board.getPiece(square);
            if (piece != Board.EMPTY) {
                hashKey ^= pieceSquareKeys[piece][square];
            }
        }*/
        //
        BitBoard black_pawns = new BitBoard(board.Black.getBoard() & board.Pawn.getBoard());
        BitBoard black_king = new BitBoard(board.Black.getBoard() & board.King.getBoard());
        BitBoard black_queen = new BitBoard(board.Black.getBoard() & board.Queen.getBoard());
        BitBoard black_knights = new BitBoard(board.Black.getBoard() & board.Knight.getBoard());
        BitBoard black_bishop = new BitBoard(board.Black.getBoard() & board.Bishop.getBoard());
        BitBoard black_rook = new BitBoard(board.Black.getBoard() & board.Rook.getBoard());
        //White
        BitBoard white_pawns = new BitBoard(board.White.getBoard() & board.Pawn.getBoard());
        BitBoard white_king = new BitBoard(board.White.getBoard() & board.King.getBoard());
        BitBoard white_queen = new BitBoard(board.White.getBoard() & board.Queen.getBoard());
        BitBoard white_knights = new BitBoard(board.White.getBoard() & board.Knight.getBoard());
        BitBoard white_bishop = new BitBoard(board.White.getBoard() & board.Bishop.getBoard());
        BitBoard white_rook = new BitBoard(board.White.getBoard() & board.Rook.getBoard());

        for(int pos:black_king.allSetSquares()){
            hashKey ^= pieceSquareKeys[0][pos];
        }
        for(int pos:black_bishop.allSetSquares()){
            hashKey ^= pieceSquareKeys[1][pos];
        }
        for(int pos:black_knights.allSetSquares()){
            hashKey ^= pieceSquareKeys[2][pos];
        }
        for(int pos:black_pawns.allSetSquares()){
            hashKey ^= pieceSquareKeys[3][pos];
        }
        for(int pos:black_queen.allSetSquares()){
            hashKey ^= pieceSquareKeys[4][pos];
        }
        for(int pos:black_rook.allSetSquares()){
            hashKey ^= pieceSquareKeys[5][pos];
        }
        for(int pos:white_king.allSetSquares()){
            hashKey ^= pieceSquareKeys[6][pos];
        }
        for(int pos:white_bishop.allSetSquares()){
            hashKey ^= pieceSquareKeys[7][pos];
        }
        for(int pos:white_knights.allSetSquares()){
            hashKey ^= pieceSquareKeys[8][pos];
        }
        for(int pos:white_queen.allSetSquares()){
            hashKey ^= pieceSquareKeys[9][pos];
        }
        for(int pos:white_pawns.allSetSquares()){
            hashKey ^= pieceSquareKeys[10][pos];
        }
        for(int pos:white_rook.allSetSquares()){
            hashKey ^= pieceSquareKeys[11][pos];
        }

        // Hash the side to move
        if (whiteturn) {
            hashKey ^= sideToMoveKey;
        }

        return hashKey;
    }
}
