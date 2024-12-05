public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        if (startX == endX) { // Horizontal
            int step = startY < endY ? 1 : -1;
            for (int y = startY + step; y != endY; y += step) {
                if (board[startX][y] != null) return false;
            }
            return true;
        } else if (startY == endY) { // Vertical
            int step = startX < endX ? 1 : -1;
            for (int x = startX + step; x != endX; x += step) {
                if (board[x][startY] != null) return false;
            }
            return true;
        }
        return false;
    }
}
