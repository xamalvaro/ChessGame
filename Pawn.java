public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int direction = isWhite ? -1 : 1;

        if (endY == startY && board[endX][endY] == null) { // Move forward
            if (endX == startX + direction) return true;
            if (firstMove && endX == startX + 2 * direction && board[startX + direction][startY] == null) return true;
        } else if (Math.abs(endY - startY) == 1 && endX == startX + direction) { // Capture
            if (board[endX][endY] != null && board[endX][endY].isWhite() != isWhite) return true;
        }
        return false;
    }
}
