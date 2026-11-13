import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * AI-created arcade interface. All game rules and results come from Casino.
 * Pixel art is drawn in Java, so no image files or extra libraries are needed.
 * @author ChatGPT
 */
public class CasinoGUI extends JFrame {
    private static final Color INK = new Color(17, 23, 23);
    private static final Color PANEL = new Color(29, 39, 37);
    private static final Color GOLD = new Color(233, 187, 91);
    private static final Color CREAM = new Color(242, 226, 182);
    private static final Color MUTED = new Color(155, 171, 151);
    private static final Color GREEN = new Color(154, 213, 145);
    private static final Color RED = new Color(193, 78, 65);
    private Casino casino;
    private JLabel balanceLabel, statsLabel, messageLabel, bonusLabel;
    private JComboBox<Integer> betBox;
    private JButton spinButton;
    private JButton[] guessButtons = new JButton[5];
    private JProgressBar goalBar;
    private MachineScreen screen;
    private Timer spinTimer;
    private Timer bonusTimer;
    private Timer winTimer = new Timer(40, event -> animateWin());
    private boolean celebrating;
    private int winFrame, winAmount, winDuration;
    private String winTitle;
    private int[] confettiX = new int[70];
    private int[] confettiY = new int[70];
    private boolean bonusLit;
    private boolean spinning;
    private int animationFrame;
    private int lastBet, lastPayout;
    private String[] displayed = {"7", "7", "7"};
    private final String[] symbols = {"7", "CHERRY", "LEMON", "BELL"};

    /** Opens the arcade and asks for a player name. */
    public CasinoGUI() {
        this(askPlayerName());
    }

    /**
     * Builds a cabinet for the named player.
     * @param name the player's name
     */
    public CasinoGUI(String name) {
        super("LUCKY 7 / Midnight Arcade");
        casino = new Casino(name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 760));
        setSize(1100, 820);
        setLocationRelativeTo(null);
        buildInterface();
        bonusTimer = new Timer(500, event -> {
            bonusLit = !bonusLit;
            paintBonusButtons();
        });
        refreshDisplay();
    }

    private static String askPlayerName() {
        while (true) {
            String name = JOptionPane.showInputDialog(null, "ENTER YOUR PLAYER NAME (1-20 CHARACTERS)", "PLAYER 01");
            if (name == null) {
                return "PLAYER 01";
            }
            name = name.trim();
            if (name.length() >= 1 && name.length() <= 20) {
                return name;
            }
            JOptionPane.showMessageDialog(null, "Username must be 1 to 20 characters long.",
                    "Invalid Name", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Font font(int size) {
        return new Font("Consolas", Font.BOLD, size);
    }

    private JLabel label(String text, int size, Color color) {
        JLabel result = new JLabel(text);
        result.setFont(font(size));
        result.setForeground(color);
        Dimension textSize = result.getPreferredSize();
        result.setPreferredSize(new Dimension(textSize.width + 40, size + 10));
        return result;
    }

    private JPanel panel(LayoutManager layout, Color color) {
        JPanel result = new JPanel(layout);
        result.setBackground(color);
        return result;
    }

    private Border frame(Color color) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(16, 18, 16, 18));
    }

    private JButton button(String text, Color background, Color foreground) {
        JButton result = new JButton(text);
        result.setFont(font(15));
        result.setBackground(background);
        result.setForeground(foreground);
        result.setFocusPainted(false);
        result.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 5, 2, background.brighter()),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return result;
    }

    private void buildInterface() {
        JPanel root = panel(new BorderLayout(22, 20), INK);
        root.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));
        setContentPane(root);

        JPanel header = panel(new BorderLayout(), INK);
        JPanel branding = panel(new GridLayout(2, 1, 0, 4), INK);
        branding.add(new PixelTitle());
        branding.add(label("M I D N I G H T   A R C A D E", 12, MUTED));
        header.add(branding, BorderLayout.WEST);
        JLabel player = label("PLAYER / " + casino.getPlayerName().toUpperCase(), 14, GREEN);
        header.add(player, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel center = panel(new BorderLayout(22, 0), INK);
        root.add(center, BorderLayout.CENTER);
        JPanel cabinet = panel(new BorderLayout(0, 16), PANEL);
        cabinet.setBorder(frame(new Color(100, 106, 77)));
        center.add(cabinet, BorderLayout.CENTER);
        JPanel machineHeader = panel(new BorderLayout(), PANEL);
        machineHeader.add(label("[ 01 ]  THE LUCKY MACHINE", 15, CREAM), BorderLayout.WEST);
        machineHeader.add(label("VIRTUAL $", 12, MUTED), BorderLayout.EAST);
        cabinet.add(machineHeader, BorderLayout.NORTH);
        screen = new MachineScreen();
        screen.setPreferredSize(new Dimension(620, 290));
        cabinet.add(screen, BorderLayout.CENTER);

        JPanel console = panel(new BorderLayout(0, 15), PANEL);
        cabinet.add(console, BorderLayout.SOUTH);
        messageLabel = label("YOUR LUCK STARTS HERE.", 15, GOLD);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setPreferredSize(new Dimension(0, 28));
        console.add(messageLabel, BorderLayout.NORTH);
        JPanel controls = panel(new BorderLayout(16, 0), PANEL);
        JPanel wager = panel(new BorderLayout(0, 6), PANEL);
        wager.add(label("WAGER / $", 12, MUTED), BorderLayout.NORTH);
        betBox = new JComboBox<Integer>();
        betBox.setEditable(true);
        betBox.setFont(font(22));
        betBox.setBackground(INK);
        betBox.setForeground(CREAM);
        betBox.setPreferredSize(new Dimension(150, 48));
        wager.add(betBox, BorderLayout.CENTER);
        controls.add(wager, BorderLayout.WEST);
        spinButton = button("SPIN  >>>", RED, CREAM);
        spinButton.setFont(font(25));
        spinButton.addActionListener(event -> spin());
        controls.add(spinButton, BorderLayout.CENTER);
        console.add(controls, BorderLayout.CENTER);
        JPanel bonus = panel(new BorderLayout(0, 8), PANEL);
        bonusLabel = label("BONUS LOCKED / 5 SPINS TO GO", 12, MUTED);
        bonus.add(bonusLabel, BorderLayout.NORTH);
        JPanel choices = panel(new GridLayout(1, 5, 8, 0), PANEL);
        for (int i = 0; i < guessButtons.length; i++) {
            final int guess = i + 1;
            guessButtons[i] = button("" + guess, new Color(57, 77, 61), CREAM);
            guessButtons[i].addActionListener(event -> playBonus(guess));
            choices.add(guessButtons[i]);
        }
        bonus.add(choices, BorderLayout.CENTER);
        console.add(bonus, BorderLayout.SOUTH);

        JPanel sidebar = panel(new BorderLayout(0, 16), INK);
        sidebar.setPreferredSize(new Dimension(260, 0));
        center.add(sidebar, BorderLayout.EAST);
        JPanel wallet = panel(new GridLayout(5, 1, 0, 8), PANEL);
        wallet.setBorder(frame(new Color(65, 89, 72)));
        wallet.setPreferredSize(new Dimension(260, 205));
        wallet.add(label("YOUR BANKROLL", 12, MUTED));
        balanceLabel = label("", 24, GREEN);
        wallet.add(balanceLabel);
        wallet.add(label("TARGET / $5,000", 13, CREAM));
        goalBar = new JProgressBar(0, Casino.GOAL);
        goalBar.setForeground(GREEN);
        goalBar.setBackground(INK);
        goalBar.setBorder(BorderFactory.createLineBorder(new Color(65, 89, 72), 2));
        wallet.add(goalBar);
        statsLabel = label("", 11, MUTED);
        wallet.add(statsLabel);
        sidebar.add(wallet, BorderLayout.NORTH);

        JPanel payouts = panel(new BorderLayout(0, 18), PANEL);
        payouts.setBorder(frame(new Color(65, 89, 72)));
        payouts.add(label("THE PAYTABLE", 17, GOLD), BorderLayout.NORTH);
        JPanel rows = panel(new GridLayout(5, 1, 0, 10), PANEL);
        rows.add(label("7 7 7          10x", 15, GOLD));
        rows.add(label("ANY TRIPLE      5x", 15, CREAM));
        rows.add(label("ANY PAIR        2x", 15, CREAM));
        rows.add(label("NO MATCH        0x", 15, MUTED));
        rows.add(label("5 SPINS = BONUS", 13, GREEN));
        payouts.add(rows, BorderLayout.CENTER);
        payouts.add(label("PAYOUT INCLUDES BET", 11, MUTED), BorderLayout.SOUTH);
        sidebar.add(payouts, BorderLayout.CENTER);
        JPanel utility = panel(new GridLayout(1, 2, 10, 0), INK);
        JButton restart = button("RESET", PANEL, CREAM);
        restart.addActionListener(event -> resetGame());
        JButton quit = button("EXIT", PANEL, CREAM);
        quit.addActionListener(event -> dispose());
        utility.add(restart);
        utility.add(quit);
        sidebar.add(utility, BorderLayout.SOUTH);

        JPanel footer = panel(new BorderLayout(), INK);
        footer.add(label("VIRTUAL MONEY / REAL LUCK", 12, MUTED), BorderLayout.WEST);
        footer.add(label("MIDNIGHT ARCADE / 01", 12, GOLD), BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(spinButton);
    }

    /** Casino decides the result once; the timer only animates its reveal. */
    private void spin() {
        String input = betBox.getEditor().getItem().toString().trim();
        Scanner numberInput = new Scanner(input);
        if (!numberInput.hasNextInt()) {
            numberInput.close();
            JOptionPane.showMessageDialog(this, "Please enter a whole number for your bet.",
                    "Invalid Bet", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int bet = numberInput.nextInt();
        boolean extraInput = numberInput.hasNext();
        numberInput.close();
        if (extraInput || bet < 1 || bet > casino.getPlayerBalance()) {
            JOptionPane.showMessageDialog(this, "Enter one bet between $1 and $" + casino.getPlayerBalance(),
                    "Invalid Bet", JOptionPane.ERROR_MESSAGE);
            return;
        }
        stopWinAnimation();
        lastBet = bet;
        betBox.setSelectedItem(bet);
        lastPayout = casino.startRound(lastBet);
        spinning = true;
        animationFrame = 0;
        messageLabel.setText("THE REELS ARE TURNING...");
        messageLabel.setForeground(GOLD);
        spinButton.setEnabled(false);
        betBox.setEnabled(false);
        for (JButton choice : guessButtons) {
            choice.setEnabled(false);
        }
        spinTimer = new Timer(80, event -> animateReels());
        spinTimer.start();
    }

    private void animateReels() {
        animationFrame++;
        String[] result = {casino.getReel1(), casino.getReel2(), casino.getReel3()};
        for (int i = 0; i < 3; i++) {
            if (animationFrame < 10 + i * 5) {
                displayed[i] = symbols[(animationFrame + i) % symbols.length];
            } else {
                displayed[i] = result[i];
            }
        }
        screen.repaint();
        if (animationFrame >= 20) {
            spinTimer.stop();
            spinning = false;
            int net = lastPayout - lastBet;
            messageLabel.setText(casino.getLastMultiplier() + "x / PAID $" + lastPayout
                    + " / NET " + (net >= 0 ? "+$" + net : "-$" + (-net)));
            messageLabel.setForeground(net > 0 ? GREEN : GOLD);
            refreshDisplay();
            if (net > 0) {
                startWinAnimation(lastPayout, casino.getLastMultiplier() == 10 ? "JACKPOT!" : "WINNER!");
            }
        }
    }

    private void playBonus(int guess) {
        stopWinAnimation();
        int reward = casino.playBonus(guess);
        messageLabel.setText("BONUS / NUMBER " + casino.getLastBonusWinningNumber()
                + " / YOU EARNED $" + reward);
        messageLabel.setForeground(GREEN);
        refreshDisplay();
        if (reward > 0) {
            startWinAnimation(reward, "BONUS WIN!");
        }
    }

    /** Celebrates an existing payout without changing the backend result. */
    private void startWinAnimation(int amount, String title) {
        winAmount = amount;
        winTitle = casino.hasReachedGoal() ? "YOU BEAT THE MACHINE!" : title;
        winDuration = casino.hasReachedGoal() ? 150 : 75;
        winFrame = 0;
        celebrating = true;
        for (int i = 0; i < confettiX.length; i++) {
            confettiX[i] = (int) (Math.random() * 1000);
            confettiY[i] = -(int) (Math.random() * 500);
        }
        winTimer.restart();
        screen.repaint();
    }

    private void animateWin() {
        winFrame++;
        for (int i = 0; i < confettiY.length; i++) {
            confettiY[i] += 6 + i % 5;
            if (confettiY[i] > 1000) {
                confettiY[i] = -20;
            }
        }
        if (winFrame >= winDuration) {
            stopWinAnimation();
        }
        screen.repaint();
    }

    private void stopWinAnimation() {
        winTimer.stop();
        celebrating = false;
        screen.repaint();
    }

    private void refreshDisplay() {
        balanceLabel.setText("$" + String.format("%,d", casino.getPlayerBalance()));
        statsLabel.setText("SPINS " + casino.getTotalSpins() + " / W " + casino.getWins()
                + " / L " + casino.getLosses());
        goalBar.setValue(casino.getPlayerBalance());
        Object selected = betBox.getSelectedItem();
        betBox.removeAllItems();
        int[] bets = {1, 5, 10, 25, 50, 100, 250, 500, 1000};
        for (int bet : bets) {
            if (bet <= casino.getPlayerBalance()) {
                betBox.addItem(bet);
            }
        }
        if (selected == null) {
            selected = 50;
        }
        for (int i = 0; i < betBox.getItemCount(); i++) {
            if (betBox.getItemAt(i).equals(selected)) {
                betBox.setSelectedIndex(i);
            }
        }
        boolean bonus = casino.isBonusAvailable() && !casino.hasReachedGoal();
        for (JButton choice : guessButtons) {
            choice.setEnabled(bonus);
        }
        bonusLit = bonus;
        paintBonusButtons();
        if (bonus) {
            bonusTimer.start();
        } else {
            bonusTimer.stop();
        }
        bonusLabel.setText(bonus ? "BONUS READY / PICK 1-5 / WIN $100"
                : "BONUS LOCKED / " + (5 - casino.getTotalSpins() % 5) + " SPINS TO GO");
        bonusLabel.setForeground(bonus ? GREEN : MUTED);
        boolean playable = casino.canContinue() && !bonus;
        spinButton.setEnabled(playable);
        betBox.setEnabled(playable);
        spinButton.setText(bonus ? "PICK BELOW" : "SPIN  >>>");
        if (casino.hasReachedGoal()) {
            messageLabel.setText("$5,000 REACHED. YOU BEAT THE MACHINE!");
            messageLabel.setForeground(GREEN);
            spinButton.setText("YOU WIN!");
        } else if (!casino.canContinue()) {
            messageLabel.setText("BANKROLL EMPTY. RESET TO PLAY AGAIN.");
            spinButton.setText("GAME OVER");
        }
        screen.repaint();
    }

    /** Alternates the bonus numbers between the normal and glowing colors. */
    private void paintBonusButtons() {
        for (JButton choice : guessButtons) {
            choice.setBackground(bonusLit ? GOLD : new Color(57, 77, 61));
            choice.setForeground(bonusLit ? INK : CREAM);
        }
    }

    private void resetGame() {
        stopWinAnimation();
        if (spinning) {
            spinTimer.stop();
        }
        spinning = false;
        casino = new Casino(casino.getPlayerName());
        displayed = new String[] {"7", "7", "7"};
        messageLabel.setText("FRESH COINS. FRESH LUCK.");
        messageLabel.setForeground(GOLD);
        refreshDisplay();
    }

    /** Stops the animation when the window closes. */
    @Override
    public void dispose() {
        stopWinAnimation();
        bonusTimer.stop();
        if (spinning) {
            spinTimer.stop();
        }
        super.dispose();
    }

    /** A small bitmap marquee, drawn without a downloaded font. */
    private class PixelTitle extends JPanel {
        private final String[] letters = {
            "10000100001000010000100001000011111",
            "10001100011000110001100011000101110",
            "01111100001000010000100001000001111",
            "10001100101010011000101001001010001",
            "10001100010101000100001000010000100",
            "00000000000000000000000000000000000",
            "11111000010001000100010000100001000"
        };
        PixelTitle() {
            setBackground(INK);
            setPreferredSize(new Dimension(240, 34));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int letter = 0; letter < letters.length; letter++) {
                for (int pixel = 0; pixel < 35; pixel++) {
                    if (letters[letter].charAt(pixel) == '1') {
                        int x = letter * 28 + pixel % 5 * 4;
                        int y = pixel / 5 * 4;
                        g.setColor(new Color(93, 65, 35));
                        g.fillRect(x + 2, y + 3, 4, 4);
                        g.setColor(GOLD);
                        g.fillRect(x, y, 4, 4);
                    }
                }
            }
        }
    }

    /** Custom artwork belongs to the GUI, not the five backend classes. */
    private class MachineScreen extends JPanel {
        private final String[][] art = {
            {"111111111", "111111111", "000000110", "000001100", "000011000", "000110000", "001100000", "001100000", "001100000"},
            {"000022000", "000220000", "002002000", "002000200", "011100000", "111110110", "131111111", "111113111", "011100110"},
            {"000000020", "000111100", "001111110", "011311110", "113111110", "111111100", "011111000", "001110000", "010000000"},
            {"000110000", "001111000", "011111100", "011311100", "011311100", "011111100", "111111110", "000000000", "000110000"}
        };

        MachineScreen() {
            setBackground(INK);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            int w = getWidth();
            int h = getHeight();
            g.setColor(new Color(7, 17, 17));
            g.fillRect(0, 0, w, h);
            g.setColor(new Color(83, 109, 82));
            g.drawRect(1, 1, w - 3, h - 3);
            // Marquee bulbs and a recessed cabinet face.
            for (int x = 16; x < w - 12; x += 24) {
                g.setColor((x / 24 + animationFrame + winFrame / 6) % 2 == 0 ? GOLD : new Color(99, 81, 42));
                g.fillRect(x, 13, 6, 6);
                g.fillRect(x, h - 20, 6, 6);
            }
            g.setFont(font(12));
            g.setColor(MUTED);
            centered(g, "*  F O R T U N E   F A V O R S   T H E   B O L D  *", w / 2, 44);
            int gap = 12;
            int reelW = (w - 64) / 3;
            int reelY = 66;
            int reelH = h - 125;
            for (int i = 0; i < 3; i++) {
                int x = 20 + i * (reelW + gap);
                g.setColor(celebrating ? (winFrame / 12 % 2 == 0 ? GOLD : GREEN)
                        : new Color(57, 75, 58));
                g.fillRect(x - 3, reelY - 3, reelW + 6, reelH + 6);
                g.setColor(new Color(213, 202, 158));
                g.fillRect(x, reelY, reelW, reelH);
                g.setColor(new Color(166, 159, 122));
                g.fillRect(x, reelY, reelW, 9);
                g.fillRect(x, reelY + reelH - 9, reelW, 9);
                int index = 0;
                for (int j = 0; j < symbols.length; j++) {
                    if (symbols[j].equals(displayed[i])) {
                        index = j;
                    }
                }
                int scale = Math.max(3, Math.min(reelW / 13, (reelH - 35) / 9));
                drawSymbol(g, index, x + (reelW - 9 * scale) / 2,
                        reelY + (reelH - 9 * scale) / 2 - 8, scale);
                g.setFont(font(11));
                g.setColor(new Color(61, 61, 40));
                centered(g, displayed[i], x + reelW / 2, reelY + reelH - 17);
            }
            g.setColor(GOLD);
            g.fillPolygon(new int[] {5, 15, 5}, new int[] {h / 2 - 7, h / 2, h / 2 + 7}, 3);
            g.fillPolygon(new int[] {w - 5, w - 15, w - 5}, new int[] {h / 2 - 7, h / 2, h / 2 + 7}, 3);
            g.setFont(font(12));
            centered(g, spinning ? "[ REELS IN MOTION ]" : "- - -  MAKE YOUR OWN LUCK  - - -", w / 2, h - 36);
            if (celebrating) {
                drawWinCelebration(g, w, h);
            }
            // Subtle CRT scanlines, painted in whole pixels.
            g.setColor(new Color(0, 0, 0, 16));
            for (int y = 0; y < h; y += 4) {
                g.drawLine(0, y, w, y);
            }
            g.dispose();
        }

        private void drawWinCelebration(Graphics2D g, int w, int h) {
            Color[] colors = {GOLD, GREEN, CREAM, RED};
            for (int i = 0; i < confettiX.length; i++) {
                int x = confettiX[i] * w / 1000 + (winFrame + i * 3) % 20 - 10;
                int y = confettiY[i] * h / 1000;
                g.setColor(colors[i % colors.length]);
                g.fillRect(x, y, 4 + i % 4, 4 + (i + winFrame / 4) % 5);
            }
            int bannerW = Math.min(w - 40, 480);
            int x = (w - bannerW) / 2;
            int y = h / 2 - 43;
            // The banner pops into place over its first few frames.
            y -= Math.max(0, 8 - winFrame) * 2;
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(x + 6, y + 6, bannerW, 90);
            g.setColor(INK);
            g.fillRect(x, y, bannerW, 90);
            g.setColor(winFrame / 12 % 2 == 0 ? GOLD : GREEN);
            g.fillRect(x, y, bannerW, 3);
            g.fillRect(x, y + 87, bannerW, 3);
            g.fillRect(x, y, 3, 90);
            g.fillRect(x + bannerW - 3, y, 3, 90);
            g.setFont(font(casino.hasReachedGoal() ? 25 : 30));
            centered(g, winTitle, w / 2, y + 35);
            int countedPayout = winAmount * Math.min(winFrame, 30) / 30;
            g.setColor(CREAM);
            g.setFont(font(23));
            centered(g, "PAID $" + String.format("%,d", countedPayout), w / 2, y + 67);
        }

        private void centered(Graphics2D g, String text, int x, int y) {
            g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y);
        }

        private void drawSymbol(Graphics2D g, int index, int x, int y, int scale) {
            Color[] colors = {RED, RED, new Color(195, 145, 26), new Color(173, 117, 30)};
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    char pixel = art[index][row].charAt(col);
                    if (pixel != '0') {
                        g.setColor(new Color(74, 66, 40));
                        g.fillRect(x + col * scale + 3, y + row * scale + 4, scale, scale);
                    }
                }
            }
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    char pixel = art[index][row].charAt(col);
                    if (pixel != '0') {
                        g.setColor(pixel == '2' ? new Color(56, 104, 59)
                                : pixel == '3' ? CREAM : colors[index]);
                        g.fillRect(x + col * scale, y + row * scale, scale, scale);
                    }
                }
            }
        }
    }

    /**
     * Starts the graphical game.
     * @param args command line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CasinoGUI().setVisible(true));
    }
}

