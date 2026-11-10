import java.util.Scanner;

/**
 * Reads player input and prints the game in the console.
 *
 * @author Zuhair Ahmed
 */
public class ConsoleApp {
    private Scanner scanner;
    private Casino casino;

    /** Creates a Scanner to read keyboard input. */
    public ConsoleApp() {
        scanner = new Scanner(System.in);
    }

    /** Starts the game and shows the menu. */
    public void run() {
        System.out.println("Lucky 7 Casino");
        System.out.println("Virtual money only.");
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

            System.out.println();
            System.out.println("Balance: $" + casino.getPlayerBalance());
            System.out.println("Spins: " + casino.getTotalSpins());
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

    /** Asks for a bet, plays a spin, and prints the results. */
    private void playSpin() {
        System.out.println("You can bet from $1 to $" + casino.getPlayerBalance());
        System.out.print("Enter your bet or type cancel: ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("cancel")) {
            return;
        }
        int bet = Integer.parseInt(input);
        int payout = casino.startRound(bet);
        System.out.println();
        System.out.println("Reel 1: " + casino.getReel1());
        System.out.println("Reel 2: " + casino.getReel2());
        System.out.println("Reel 3: " + casino.getReel3());
        System.out.println("Multiplier: " + casino.getLastMultiplier() + "x");
        System.out.println("Payout: $" + payout);
        int change = payout - bet;
        System.out.println("Balance change: " + change + " dollars");
        System.out.println("New balance: $" + casino.getPlayerBalance());
    }

    /** Asks for a guess and prints the bonus result. */
    private void playBonusRound() {
        System.out.println();
        System.out.println("Bonus round!");
        System.out.println("Guess a number from 1 through 5 for a chance at $100.");
        System.out.print("Your guess (1-5): ");
        String input = scanner.nextLine().trim();
        int guess = Integer.parseInt(input);
        int reward = casino.playBonus(guess);
        System.out.println("Winning number: " + casino.getLastBonusWinningNumber());
        if (reward > 0) {
            System.out.println("Correct! You earned $100!");
        } else {
            System.out.println("Not this time. Bonus payout: $0.");
        }
    }

    /** Prints the game rules. */
    private void showRules() {
        System.out.println();
        System.out.println("Rules:");
        System.out.println("Three 7s pay 10 times your bet.");
        System.out.println("Other triples pay 5 times your bet.");
        System.out.println("A pair pays 2 times your bet.");
        System.out.println("No matches: 0x. Payout includes the original bet.");
        System.out.println("Every 5 spins: free number-guessing bonus for $100.");
        System.out.println("Reach $5,000 to win. No real-money gambling.");
    }

    /** Prints the player's money, spins, wins, and losses. */
    private void showStats() {
        System.out.println();
        System.out.println("Stats for " + casino.getPlayerName());
        System.out.println("Balance: $" + casino.getPlayerBalance());
        System.out.println("Spins: " + casino.getTotalSpins());
        System.out.println("Winning spins: " + casino.getWins());
        System.out.println("Losing spins: " + casino.getLosses());
    }

    /**
     * Starts the console program.
     *
     * @param args command line arguments, not used in this program
     */
    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}
