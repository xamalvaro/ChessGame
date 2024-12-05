import java.awt.*;
import javax.swing.*;
/* Group Members:
 * David Alvarado
 * Alvaro Miranda
 * Cliffton Williams
 * 
 * Optional Feature(s):
 * Redo button
 * Surrender
 * Reset <- should be core feature
 * Scoreboard
 * 
 * Main Problem(s):
 * When board is reset, the board starts hidden
 * white side has multiple kings (fixed)
 * black side has multiple queens (fixed)
 */

public class ChessGame extends JFrame {
    private JPanel chessBoardPanel;
    private JLabel turnLabel;

    public ChessGame() {

        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Create UI components
        createChessBoard();
        createControlPanel();

        setVisible(true);
    }

    private void createChessBoard() {
        chessBoardPanel = new JPanel(new GridLayout(8, 8));
        boolean white = true;

        //Set up grid layout with alternating colors
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                square.setBackground(white ? Color.PINK : Color.WHITE);
                chessBoardPanel.add(square);

                // Alternate colors
                white = !white;

                // Add piece labels
                if (row == 0 || row == 7) {
                    square.setText(getPieceName(col, row == 0 ? "black" : "white"));
                } else if (row == 1 || row == 6) {
                    square.setText("pawn");
                }
            }
            white = !white; // Alternate start color for next row
        }

        add(chessBoardPanel, BorderLayout.CENTER);
    }

    private String getPieceName(int col, String color) {
        switch (col) {
            case 0:
                return "rook";
            case 1:
                return "horse";
            case 2:
                return "bishop";
            case 3:
                return color.equals("white") ? "queen" : "king";
            case 4:
                return color.equals("white") ? "king" : "queen";
            case 5:
                return "bishop";
            case 6:
                return "horse"; // Knight, renamed for consistency
            case 7:
                return "rook";
            default:
                return "";
        }
    }

    private void createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Status Label
        turnLabel = new JLabel("Your turn!");
        controlPanel.add(turnLabel);

        // Surrender Button
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.addActionListener(ae -> showCheckmateScreen());
        controlPanel.add(surrenderButton);

        // Redo Button
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(ae -> JOptionPane.showMessageDialog(this, "Redo not implemented yet."));
        controlPanel.add(redoButton);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(ae -> resetGame());
        controlPanel.add(resetButton);

        add(controlPanel, BorderLayout.NORTH);
    }

    //Core feature still buggy
    private void resetGame() {
        chessBoardPanel.removeAll();
        createChessBoard();
        turnLabel.setText("Your turn!");

        //remakes all buttons and labels for board
        revalidate();
        //remakes square colors
        repaint();
    }

    //Optional feature still buggy
    private void showCheckmateScreen() {
        JFrame checkmateFrame = new JFrame("Game Over");
        checkmateFrame.setSize(300, 200);
        checkmateFrame.setLayout(new BorderLayout());

        JLabel checkmateLabel = new JLabel("Check-Mate", SwingConstants.CENTER);
        checkmateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        checkmateFrame.add(checkmateLabel, BorderLayout.CENTER);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(ae -> {
            checkmateFrame.dispose();
            resetGame();
        });
        checkmateFrame.add(startGameButton, BorderLayout.SOUTH);

        checkmateFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ChessGame();
    }
}