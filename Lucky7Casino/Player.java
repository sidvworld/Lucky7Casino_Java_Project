/**
 * Stores a player's name, virtual balance, and spin statistics.
 *
 * @author Siddharth Kethini
 */
public class Player {
    private String username;
    private int balance;
    private int wins;
    private int losses;

    /**
     * Creates a player with the supplied starting balance.
     *
     * @param username the player's name
     * @param startingBalance the initial virtual money amount
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
     * @return the virtual dollar balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Checks whether a bet is positive and affordable.
     *
     * @param bet the proposed bet
     * @return true when the bet is valid
     */
    public boolean canAffordBet(int bet) {
        return bet > 0 && bet <= balance;
    }

    /**
     * Deducts a valid bet before the reels spin.
     *
     * @param bet the amount to deduct
     */
    public void placeBet(int bet) {
        balance -= bet;
    }

    /**
     * Adds earned virtual money to the balance.
     *
     * @param amount nonnegative money earned
     */
    public void addWinnings(int amount) {
        balance += amount;
    }

    /** Increases the number of profitable spins. */
    public void recordWin() {
        wins++;
    }

    /** Increases the number of losing spins. */
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
