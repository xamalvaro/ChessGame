import java.awt.*;
import javax.swing.*;

public class ChessGameUI extends JFrame {
    private Board board;
    private JButton[][] boardButtons;
    private JLabel turnLabel; // Label to display the current turn
    private int selectedX = -1, selectedY = -1;
    private boolean isWhiteTurn = true;

    public ChessGameUI() {
        board = new Board();
        boardButtons = new JButton[8][8];
        setLayout(new BorderLayout());

        // Create and add the chessboard
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        // Add control buttons
        addControlButtons();

        setTitle("Chess Game");
        setSize(800, 850); // Increased size to fit controls
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = new JButton();
                button.setBackground((x + y) % 2 == 0 ? Color.PINK : Color.WHITE);
                final int finalX = x;
                final int finalY = y;
                button.addActionListener(e -> handleSquareClick(finalX, finalY));
                boardButtons[x][y] = button;
                boardPanel.add(button);
                updateButton(x, y);
            }
        }
    }

    private void addControlButtons() {
        JPanel controls = new JPanel(new BorderLayout());

        // Left panel for turn display
        JPanel leftPanel = new JPanel();
        turnLabel = new JLabel("White's Turn");
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        leftPanel.add(turnLabel);
        controls.add(leftPanel, BorderLayout.WEST);

        // Right panel for control buttons
        JPanel rightPanel = new JPanel(new FlowLayout());

        // Undo Button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            board.undo();
            refreshBoard();
        });
        rightPanel.add(undoButton);

        // Redo Button
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            board.redo();
            refreshBoard();
        });
        rightPanel.add(redoButton);

        // Surrender Button
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.addActionListener(e -> handleSurrender());
        rightPanel.add(surrenderButton);

        // End Game Button
        JButton endGameButton = new JButton("End Game");
        endGameButton.addActionListener(e -> handleEndGame());
        rightPanel.add(endGameButton);

        controls.add(rightPanel, BorderLayout.EAST);

        add(controls, BorderLayout.SOUTH);
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
                updateTurnLabel(); // Update the turn label
            }
            selectedX = -1;
            selectedY = -1;
            refreshBoard();
        }
    }

    private void handleSurrender() {
        String winner = isWhiteTurn ? "Black" : "White";
        JOptionPane.showMessageDialog(this, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void handleEndGame() {
        dispose(); // Close the ChessGameUI window
        new MenuScreen(); // Return to the menu screen
    }

    private void resetGame() {
        board = new Board();
        isWhiteTurn = true;
        refreshBoard();
        updateTurnLabel(); // Reset the turn label
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
        JButton button = boardButtons[x][y];

        if (piece == null) {
            button.setIcon(null); // No piece on this square
        } else {
            String pieceName = piece.getClass().getSimpleName().toLowerCase(); // e.g., "pawn", "rook"
            String color = piece.isWhite() ? "white" : "black"; // e.g., "white", "black"
            String imagePath = "resources/" + color + "_" + pieceName + ".png"; // Path to image

            // Load and set the image as an icon
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Scale to fit button
                button.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagePath);
            }
        }
        button.setBackground((x + y) % 2 == 0 ? Color.PINK : Color.WHITE);
    }

    private void updateTurnLabel() {
        turnLabel.setText(isWhiteTurn ? "White's Turn" : "Black's Turn");
    }

    public static void main(String[] args) {
        new ChessGameUI();
    }
}
