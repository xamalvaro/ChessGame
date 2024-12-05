public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        if (Math.abs(startX - endX) == Math.abs(startY - endY)) { // Diagonal
            int xStep = startX < endX ? 1 : -1;
            int yStep = startY < endY ? 1 : -1;
            int x = startX + xStep, y = startY + yStep;
            while (x != endX && y != endY) {
                if (board[x][y] != null) return false;
                x += xStep;
                y += yStep;
            }
            return true;
        }
        return false;
    }
}
