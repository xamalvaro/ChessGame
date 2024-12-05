import java.util.Stack;

public class Board {
    private Piece[][] board;
    private Stack<Piece[][]> undoStack = new Stack<>();
    private Stack<Piece[][]> redoStack = new Stack<>();

    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false); // Black pawns
            board[6][i] = new Pawn(true);  // White pawns
        }
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);

        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);

        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);

        board[0][3] = new Queen(false);
        board[7][3] = new Queen(true);

        board[0][4] = new King(false);
        board[7][4] = new King(true);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = board[startX][startY];
        if (piece == null || !piece.isValidMove(startX, startY, endX, endY, board)) {
            return false; // Invalid move
        }

        saveState(); // Save the current state for undo
        redoStack.clear(); // Clear redo stack after a new move

        board[endX][endY] = piece;
        board[startX][startY] = null;
        return true;
    }

    private void saveState() {
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        }
        undoStack.push(copy);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(copyBoard());
            board = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(copyBoard());
            board = redoStack.pop();
        }
    }

    private Piece[][] copyBoard() {
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        }
        return copy;
    }
}
