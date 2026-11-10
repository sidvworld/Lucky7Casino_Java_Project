import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Swing graphical interface for Lucky 7 Casino.
 * Uses Casino's existing game logic and does not reimplement payout rules.
 *
 * @author ChatGPT
 */
public class CasinoGUI extends JFrame {
    /** Shared game logic used by the graphical interface. */
    private Casino casino;
    /** Label showing the current virtual money. */
    private JLabel balanceLabel;
    /** Label showing the number of completed spins. */
    private JLabel spinsLabel;
    /** Label showing winning and losing spins. */
    private JLabel statsLabel;
    /** Labels showing the three slot-machine symbols. */
    private JLabel[] reelLabels;
    /** Label for the outcome of the most recent action. */
    private JLabel messageLabel;
    /** Text field for the amount wagered. */
    private JTextField betField;
    /** Text field for a bonus number from 1 through 5. */
    private JTextField bonusGuessField;
    /** Button to start a paid round. */
    private JButton spinButton;
    /** Button to claim an unlocked bonus. */
    private JButton bonusButton;
    /** Section shown only when a bonus is available. */
    private JPanel bonusPanel;

    /** Creates and lays out the window and starts a new player. */
    public CasinoGUI() {
        super("Lucky 7 Casino");
        String name = JOptionPane.showInputDialog(null, "Enter your username:",
                "Lucky 7 Casino", JOptionPane.QUESTION_MESSAGE);
        casino = new Casino(name);
        createInterface();
        refreshDisplay();
    }

    /** Builds the labels, reel display, betting controls, and bonus controls. */
    private void createInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 540);
        setMinimumSize(new Dimension(620, 480));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(24, 28, 39));
        setLayout(new BorderLayout(12, 12));

        Color white = new Color(246, 246, 250);
        JPanel header = new JPanel(new GridLayout(4, 1, 0, 4));
        header.setBackground(new Color(24, 28, 39));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 6, 20));
        JLabel title = createLabel("LUCKY 7 CASINO", 27, new Color(253, 203, 77));
        JLabel nameLabel = createLabel("Player: " + casino.getPlayerName(), 15, white);
        balanceLabel = createLabel("", 19, white);
        spinsLabel = createLabel("", 14, white);
        header.add(title);
        header.add(nameLabel);
        header.add(balanceLabel);
        header.add(spinsLabel);
        add(header, BorderLayout.NORTH);

        JPanel middle = new JPanel(new GridLayout(3, 1, 12, 12));
        middle.setBackground(new Color(24, 28, 39));
        middle.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));

        JPanel reels = new JPanel(new GridLayout(1, 3, 12, 12));
        reels.setBackground(new Color(24, 28, 39));
        reelLabels = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            reelLabels[i] = createLabel("?", 22, new Color(253, 203, 77));
            reelLabels[i].setOpaque(true);
            reelLabels[i].setBackground(new Color(43, 50, 68));
            reelLabels[i].setBorder(BorderFactory.createLineBorder(new Color(253, 203, 77), 2));
            reels.add(reelLabels[i]);
        }
        middle.add(reels);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        controls.setBackground(new Color(24, 28, 39));
        JLabel betLabel = createLabel("Bet ($):", 16, white);
        betField = new JTextField("50", 7);
        betField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        spinButton.setMargin(new Insets(6, 18, 6, 18));
        spinButton.addActionListener(event -> spin());
        JButton rulesButton = new JButton("Rules");
        rulesButton.addActionListener(event -> showRules());
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(event -> dispose());
        controls.add(betLabel);
        controls.add(betField);
        controls.add(spinButton);
        controls.add(rulesButton);
        controls.add(quitButton);
        middle.add(controls);

        bonusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bonusPanel.setBackground(new Color(50, 45, 32));
        JLabel bonusLabel = createLabel("BONUS! Guess 1-5:", 15, new Color(253, 203, 77));
        bonusGuessField = new JTextField(4);
        bonusButton = new JButton("Claim Bonus");
        bonusButton.addActionListener(event -> playBonus());
        bonusPanel.add(bonusLabel);
        bonusPanel.add(bonusGuessField);
        bonusPanel.add(bonusButton);
        middle.add(bonusPanel);
        add(middle, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(2, 1, 0, 4));
        footer.setBackground(new Color(24, 28, 39));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        messageLabel = createLabel("Place a bet and click SPIN!", 15, white);
        statsLabel = createLabel("", 13, white);
        footer.add(messageLabel);
        footer.add(statsLabel);
        add(footer, BorderLayout.SOUTH);
    }

    /**
     * Creates a centered label with the requested style.
     *
     * @param text displayed text
     * @param fontSize font point size
     * @param color text color
     * @return styled label
     */
    private JLabel createLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        return label;
    }

    /** Reads a bet and asks the Casino object to play the round. */
    private void spin() {
        String input = betField.getText().trim();
        int bet = Integer.parseInt(input);
        int payout = casino.startRound(bet);
        int net = payout - bet;
        String signed = net >= 0 ? "+$" + net : "-$" + (-net);
        messageLabel.setText("Result: " + casino.getLastMultiplier() + "x | Payout: $"
                + payout + " | Net: " + signed);
        refreshDisplay();
        if (casino.hasReachedGoal()) {
            JOptionPane.showMessageDialog(this,
                    "Congratulations! You reached $5,000!", "You Win!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (casino.isBonusAvailable()) {
            JOptionPane.showMessageDialog(this,
                    "Five spins completed! Use the bonus controls to guess 1-5.",
                    "Bonus Unlocked", JOptionPane.INFORMATION_MESSAGE);
        } else if (!casino.canContinue()) {
            JOptionPane.showMessageDialog(this,
                    "You have no virtual money left. Game over!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Reads a bonus guess and asks Casino to credit any reward. */
    private void playBonus() {
        String input = bonusGuessField.getText().trim();
        int guess = Integer.parseInt(input);
        int reward = casino.playBonus(guess);
        int winningNumber = casino.getLastBonusWinningNumber();
        messageLabel.setText("Bonus winning number: " + winningNumber
                + " | You earned: $" + reward);
        bonusGuessField.setText("");
        refreshDisplay();
        if (casino.hasReachedGoal()) {
            JOptionPane.showMessageDialog(this, "You reached the $5,000 goal!",
                    "You Win!", JOptionPane.INFORMATION_MESSAGE);
        } else if (!casino.canContinue()) {
            JOptionPane.showMessageDialog(this, "You ran out of virtual money.",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Updates labels and buttons after every spin or bonus. */
    private void refreshDisplay() {
        reelLabels[0].setText(casino.getReel1());
        reelLabels[1].setText(casino.getReel2());
        reelLabels[2].setText(casino.getReel3());
        balanceLabel.setText("Balance: $" + casino.getPlayerBalance() + "    Goal: $5,000");
        spinsLabel.setText("Total spins: " + casino.getTotalSpins());
        statsLabel.setText("Winning spins: " + casino.getWins()
                + "     Losing spins: " + casino.getLosses());

        boolean bonusAvailable = casino.isBonusAvailable();
        bonusPanel.setVisible(bonusAvailable);
        bonusButton.setEnabled(bonusAvailable);
        spinButton.setEnabled(casino.canContinue() && !bonusAvailable);
        revalidate();
        repaint();
    }

    /** Opens the payout and goal information. */
    private void showRules() {
        JOptionPane.showMessageDialog(this,
                "Three 7s: 10x\nOther triples: 5x\nExactly one pair: 2x\n"
                + "No matching symbols: 0x\n\n"
                + "Every 5 spins, guess a number 1-5 for a free chance at $100.\n"
                + "Start with $1,000 and reach $5,000 to win.\n"
                + "Payout multipliers include your original bet.\n"
                + "This game uses virtual money only.",
                "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Starts the graphical version.
     *
     * @param args optional command line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CasinoGUI().setVisible(true));
    }
}
