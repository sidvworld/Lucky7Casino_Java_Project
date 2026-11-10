import java.util.Scanner;

/**
 * Console interface for Lucky 7 Casino. Only this class reads console input.
 *
 * @author Zuhair Ahmed
 */
public class ConsoleApp {
    private Scanner scanner;
    private Casino casino;

    /** Sets up the console input reader. */
    public ConsoleApp() {
        scanner = new Scanner(System.in);
    }

    /** Starts the console game and displays the main menu. */
    public void run() {
        System.out.println("====================================");
        System.out.println("        LUCKY 7 CASINO");
        System.out.println("       Virtual money only!");
        System.out.println("====================================");
        String username = askUsername();
        casino = new Casino(username);
        System.out.println("Welcome, " + casino.getPlayerName() + "!");
        System.out.println("You start with $1,000. Try to reach $5,000!");
        showRules();

        boolean playing = true;
        while (playing) {
            if (casino.hasReachedGoal()) {
                System.out.println("YOU WIN! You reached $5,000!");
                break;
            }
            if (casino.isBonusAvailable()) {
                playBonusRound();
                continue;
            }
            if (!casino.canContinue()) {
                System.out.println("Game over! You have no virtual money left.");
                break;
            }

            System.out.println("\nBalance: $" + casino.getPlayerBalance()
                    + " | Spins: " + casino.getTotalSpins());
            System.out.println("1 - Spin");
            System.out.println("2 - View stats");
            System.out.println("3 - Show rules");
            System.out.println("4 - Quit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            if (option.equals("1") || option.equalsIgnoreCase("spin")) {
                playSpin();
            } else if (option.equals("2") || option.equalsIgnoreCase("stats")) {
                showStats();
            } else if (option.equals("3") || option.equalsIgnoreCase("rules")) {
                showRules();
            } else if (option.equals("4") || option.equalsIgnoreCase("quit")) {
                playing = false;
            } else {
                System.out.println("Invalid menu option. Please choose 1, 2, 3, or 4.");
            }
        }
        System.out.println("Thanks for playing, " + casino.getPlayerName() + "!");
        showStats();
    }

    /**
     * Reads the player's name.
     *
     * @return the entered username
     */
    private String askUsername() {
        System.out.print("Enter a username: ");
        return scanner.nextLine().trim();
    }

    /** Prompts for a bet, spins the reels, and reports the results. */
    private void playSpin() {
        System.out.print("Enter a bet ($1-$" + casino.getPlayerBalance()
                + ") or type cancel: ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("cancel")) {
            return;
        }
        int bet = Integer.parseInt(input);
        int payout = casino.startRound(bet);
        System.out.println("\n[ " + casino.getReel1() + " | "
                + casino.getReel2() + " | " + casino.getReel3() + " ]");
        System.out.println("Multiplier: " + casino.getLastMultiplier() + "x");
        System.out.println("Payout: $" + payout);
        System.out.println("Net change: "
                + formatSigned(payout - bet));
        System.out.println("New balance: $" + casino.getPlayerBalance());
    }

    /** Plays the free guessing game when it is available. */
    private void playBonusRound() {
        System.out.println("\n*** BONUS ROUND UNLOCKED! ***");
        System.out.println("Guess a number from 1 through 5 for a chance at $100.");
        System.out.print("Your guess (1-5): ");
        String input = scanner.nextLine().trim();
        int guess = Integer.parseInt(input);
        int reward = casino.playBonus(guess);
        System.out.println("Winning number: "
                + casino.getLastBonusWinningNumber());
        if (reward > 0) {
            System.out.println("Correct! You earned $100!");
        } else {
            System.out.println("Not this time. Bonus payout: $0.");
        }
    }

    /** Displays the official rules used by this game. */
    private void showRules() {
        System.out.println("\n--- RULES ---");
        System.out.println("Three 7s: 10x | Other triples: 5x | A pair: 2x");
        System.out.println("No matches: 0x. Payout includes the original bet.");
        System.out.println("Every 5 spins: free number-guessing bonus for $100.");
        System.out.println("Reach $5,000 to win. No real-money gambling.");
    }

    /** Displays the player's current game statistics. */
    private void showStats() {
        System.out.println("\n--- STATS for " + casino.getPlayerName() + " ---");
        System.out.println("Balance: $" + casino.getPlayerBalance());
        System.out.println("Spins: " + casino.getTotalSpins());
        System.out.println("Winning spins: " + casino.getWins());
        System.out.println("Losing spins: " + casino.getLosses());
    }

    /**
     * Formats a net result as positive or negative currency.
     *
     * @param amount payout minus bet
     * @return a signed dollar amount
     */
    private String formatSigned(int amount) {
        if (amount >= 0) {
            return "+$" + amount;
        }
        return "-$" + (-amount);
    }

    /**
     * Launches the console interface.
     *
     * @param args optional command line arguments (unused)
     */
    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}
