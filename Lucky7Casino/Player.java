/**
 * Stores the player's name, money, wins, and losses.
 *
 * @author Siddharth Kethini
 */
public class Player {
    private String username;
    private int balance;
    private int wins;
    private int losses;

    /**
     * Creates a player with a name and starting amount of money.
     *
     * @param username the player's name
     * @param startingBalance the starting amount of money
     */
    public Player(String username, int startingBalance) {
        this.username = username.trim();
        balance = startingBalance;
        wins = 0;
        losses = 0;
    }

    /**
     * Gets the player's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the current balance.
     *
     * @return the amount of money the player has
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Checks if the player has enough money for the bet.
     *
     * @param bet the amount to bet
     * @return true if the bet is greater than 0 and no more than the balance
     */
    public boolean canAffordBet(int bet) {
        return bet > 0 && bet <= balance;
    }

    /**
     * Subtracts the bet from the player's money.
     *
     * @param bet the amount to subtract
     */
    public void placeBet(int bet) {
        balance -= bet;
    }

    /**
     * Adds winnings to the player's money.
     *
     * @param amount the amount of money won
     */
    public void addWinnings(int amount) {
        balance += amount;
    }

    /** Adds 1 to the number of wins. */
    public void recordWin() {
        wins++;
    }

    /** Adds 1 to the number of losses. */
    public void recordLoss() {
        losses++;
    }

    /**
     * Gets the number of winning spins.
     *
     * @return number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Gets the number of losing spins.
     *
     * @return number of losses
     */
    public int getLosses() {
        return losses;
    }
}
