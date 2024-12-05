import java.util.Stack;

public class Board {
    private Piece[][] board;
    private Stack<Piece[][]> history;

    public Board() {
        board = new Piece[8][8];
        history = new Stack<>();
        setupBoard();
    }

    private void setupBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false);
            board[6][i] = new Pawn(true);
        }
        board[0][0] = board[0][7] = new Rook(false);
        board[7][0] = board[7][7] = new Rook(true);
        board[0][1] = board[0][6] = new Knight(false);
        board[7][1] = board[7][6] = new Knight(true);
        board[0][2] = board[0][5] = new Bishop(false);
        board[7][2] = board[7][5] = new Bishop(true);
        board[0][3] = new Queen(false);
        board[0][4] = new King(false);
        board[7][3] = new Queen(true);
        board[7][4] = new King(true);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void saveState() {
        Piece[][] copy = new Piece[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                copy[x][y] = board[x][y];
            }
        }
        history.push(copy);
    }

    public void undo() {
        if (!history.isEmpty()) {
            board = history.pop();
        }
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = board[startX][startY];
        if (piece == null || !piece.isValidMove(startX, startY, endX, endY, board)) {
            return false;
        }
        saveState();
        if (piece instanceof Pawn && (endX == 0 || endX == 7)) {
            board[endX][endY] = new Queen(piece.isWhite());
        } else {
            board[endX][endY] = piece;
        }
        board[startX][startY] = null;
        piece.setMoved();
        return true;
    }
}
