import java.util.Scanner;

/**
 * Reads player input and prints the game in the console.
 *
 * @author Siddharth G, Siddharth K, and Hope Le
 */
public class ConsoleApp {
    private Scanner scanner;
    private Casino casino;

    /** creates a Scanner to read keyboard input. */
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
                System.out.println("Game over! You have no  money left.");
                break;
            }

            System.out.println();
            System.out.println("Balance: $" + casino.getPlayerBalance());
            System.out.println("Spins: " + casino.getTotalSpins());
            System.out.println("1 - Spin");
            System.out.println("2 - View Stats");
            System.out.println("3 - Show rules");
            System.out.println("4 - Quit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            if (option.equals("1")) {
                playSpin();
            } else if (option.equals("2")) {
                showStats();
            } else if (option.equals("3")) {
                showRules();
            } else if (option.equals("4")) {
                playing = false;
            } else {
                System.out.println("Invalid Option. Please choose 1, 2, 3, or  4.");
            }
        }
        System.out.println("Thanks you for playing " + casino.getPlayerName() + "!");
        showStats();
    }

    /**
     * reads the player's name.
     *
     * @return the entered username
     */
    private String askUsername() {
        while (true) {
            System.out.print("Enter a username (1-20 characters): ");
            String name = scanner.nextLine().trim();
            if (name.length() >= 1 && name.length() <= 20) {
                return name;
            }
            System.out.println("Username must be 1 to 20 characters long.");
        }
    }

    /** Asks for a bet, plays a spin, and prints the results. */
    private void playSpin() {
        while (true) {
            System.out.println("You can bet from $1 to $" + casino.getPlayerBalance());
            System.out.print("Enter your bet or type cancel: ");
            String input = scanner.nextLine().trim();
            if (input.equals("cancel")) {
                return;
            }
            // Check before reading a number so letters do not stop the game.
            Scanner numberInput = new Scanner(input);
            if (!numberInput.hasNextInt()) {
                numberInput.close();
                System.out.println("Please enter a whole number for your bet.");
                continue;
            }
            int bet = numberInput.nextInt();
            boolean extraInput = numberInput.hasNext();
            numberInput.close();
            if (extraInput) {
                System.out.println("Please enter only one whole number.");
                continue;
            }
            if (bet < 1 || bet > casino.getPlayerBalance()) {
                System.out.println("Bet must be between $1 and $" + casino.getPlayerBalance());
                continue;
            }
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
            return;
        }
    }

    /** Asks for a guess and prints the bonus result. */
    private void playBonusRound() {
        System.out.println();
        System.out.println("Bonus round!");
        System.out.println("Guess a number from 1 through 5 for a chance at $100.");
        while (true) {
            System.out.print("Your guess (1-5): ");
            Scanner numberInput = new Scanner(scanner.nextLine().trim());
            if (!numberInput.hasNextInt()) {
                numberInput.close();
                System.out.println("Please enter a whole number from 1 to 5.");
                continue;
            }
            int guess = numberInput.nextInt();
            boolean extraInput = numberInput.hasNext();
            numberInput.close();
            if (extraInput || guess < 1 || guess > 5) {
                System.out.println("Enter one number from 1 to 5.");
                continue;
            }
            int reward = casino.playBonus(guess);
            System.out.println("Winning number: " + casino.getLastBonusWinningNumber());
            if (reward > 0) {
                System.out.println("Correct! You earned $100!");
            } else {
                System.out.println("Wrong. No payout");
            }
            return;
        }
    }

    /** Prints the game rules. */
    private void showRules() {
        System.out.println();
        System.out.println("Rules:");
        System.out.println("Getting three 7s pay 10 times your bet.");
        System.out.println(" However Other triples pay 5 times your bet.");
        System.out.println("A pair pays 2 times your bet.");
        System.out.println("No matches is 0x. Payout includes the original bet.");
        System.out.println("Every 5 spins you get to guess a number 1-5 for  bonus $100.");
        System.out.println("Reach $5,000 to win!!");
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
     * starts the console program.
     *
     * @param args command line arguments, not used in this program
     */
    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}
