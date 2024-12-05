import java.awt.*;
import javax.swing.*;

public class ChessGameUI extends JFrame {
    private Board board;
    private JButton[][] boardButtons;
    private int selectedX = -1, selectedY = -1;
    private boolean isWhiteTurn = true;

    public ChessGameUI() {
        board = new Board();
        boardButtons = new JButton[8][8];
        setLayout(new GridLayout(8, 8));
        initializeBoard();
        setTitle("Chess Game");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = new JButton();
                button.setBackground((x + y) % 2 == 0 ? Color.PINK : Color.WHITE);
                final int finalX = x; // Fix lambda scope issue
                final int finalY = y;
                button.addActionListener(e -> handleSquareClick(finalX, finalY));
                boardButtons[x][y] = button;
                add(button);
                updateButton(x, y);
            }
        }
    }

    private void handleSquareClick(int x, int y) {
        if (selectedX == -1) { // First click
            if (board.getBoard()[x][y] != null && board.getBoard()[x][y].isWhite() == isWhiteTurn) {
                selectedX = x;
                selectedY = y;
                boardButtons[x][y].setBackground(Color.YELLOW); // Highlight selected square
            }
        } else { // Second click
            if (board.movePiece(selectedX, selectedY, x, y)) {
                isWhiteTurn = !isWhiteTurn; // Switch turn
            }
            selectedX = -1;
            selectedY = -1;
            refreshBoard();
        }
    }

    private void refreshBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                updateButton(x, y);
            }
        }
    }

    private void updateButton(int x, int y) {
        Piece piece = board.getBoard()[x][y];
        boardButtons[x][y].setText(piece == null ? "" : piece.getClass().getSimpleName());
        boardButtons[x][y].setBackground((x + y) % 2 == 0 ? Color.PINK : Color.WHITE);
    }

    public static void main(String[] args) {
        new ChessGameUI();
    }
}
