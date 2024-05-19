import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/*<applet code="NumberGame.class" height=500 width=500 ></applet>*/

public class NumberGame extends Applet {

    private int randomNumber;
    private int attempts;
    private int maxAttempts = 5;
    private Label guessLabel;
    private TextField guessField;
    private Button guessButton;
    private TextArea resultArea;
    private Button playAgainButton;
    private Label attemptsLabel;
    private Label maxAttemptsLabel;

    public void init() {
        setBackground(new Color(173, 216, 230)); // Light Blue background
        setSize(500, 500);
        setLayout(new BorderLayout());

        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        Label titleLabel = new Label("Gowtham's Number Guessing Game");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        Panel centerPanel = new Panel(new BorderLayout());
        Panel leftPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        guessLabel = new Label("Enter your guess (1-100): ");
        guessLabel.setFont(new Font("Verdana", Font.PLAIN, 18)); 
        guessField = new TextField(10);
        guessField.setFont(new Font("Verdana", Font.PLAIN, 18)); 
        guessButton = new Button("Guess");
        guessButton.setFont(new Font("Verdana", Font.BOLD, 18));
        leftPanel.add(guessLabel);
        leftPanel.add(guessField);
        leftPanel.add(guessButton);
        centerPanel.add(leftPanel, BorderLayout.WEST);

        resultArea = new TextArea(15, 30); 
        resultArea.setEditable(false); 
        resultArea.setFont(new Font("Verdana", Font.BOLD, 20)); 
        centerPanel.add(resultArea, BorderLayout.CENTER);

        Panel bottomPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        attemptsLabel = new Label("Total attempts: ");
        attemptsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        maxAttemptsLabel = new Label("Max attempts: " + maxAttempts);
        maxAttemptsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        playAgainButton = new Button("Play Again");
        playAgainButton.setFont(new Font("Verdana", Font.BOLD, 18));
        bottomPanel.add(attemptsLabel);
        bottomPanel.add(maxAttemptsLabel);
        bottomPanel.add(playAgainButton);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        generateRandomNumber();
        resetGame();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomNumber();
                resetGame();
            }
        });
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    private void resetGame() {
        attempts = 0;
        resultArea.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        guessField.setText("");
        updateAttemptsLabel();
    }

    private void checkGuess() {
        int guess = Integer.parseInt(guessField.getText());
        attempts++;

        int difference = Math.abs(randomNumber - guess);
        String hint = "";

        if (guess == randomNumber) {
            resultArea.append("Congratulations! You guessed the number " + randomNumber + " in " + attempts + " attempts.\n");
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
            hint = "";
        } else if (attempts >= maxAttempts) {
            resultArea.append("Sorry, you've run out of attempts. The correct number was " + randomNumber + ".\n");
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
            hint = "";
        } else if (guess < randomNumber) {
            hint = "Too low! ";
        } else {
            hint = "Too high! ";
        }

        if (!hint.isEmpty()) {
            if (difference <= 10) {
                hint += "You're getting close!";
            } else if (guess < randomNumber) {
                hint += "Try higher.";
            } else {
                hint += "Try lower.";
            }

            resultArea.append(hint + "\n");
        }

        updateAttemptsLabel();
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Total attempts: " + attempts);
    }
}
