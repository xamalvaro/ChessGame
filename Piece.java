public abstract class Piece {
    protected boolean isWhite;
    protected boolean firstMove = true;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board);

    public void setMoved() {
        firstMove = false;
    }
}
