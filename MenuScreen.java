import java.awt.*;
import javax.swing.*;

public class MenuScreen extends JFrame {
    public MenuScreen() {
        setTitle("Chess Game Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to Chess Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Start Game Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(e -> startGame());
        add(startButton, BorderLayout.CENTER);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startGame() {
        dispose(); // Close the menu screen
        new ChessGameUI(); // Launch the chess game
    }

    public static void main(String[] args) {
        new MenuScreen(); // Start with the menu screen
    }
}
