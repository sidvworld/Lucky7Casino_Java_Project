/**
 * Uses the other classes to run the casino game.
 *
 * @author Siddharth Ghosh
 */
public class Casino {
    /** The amount of money a new player starts with. */
    public static final int STARTING_BALANCE = 1000;
    /** The amount of money needed to win the game. */
    public static final int GOAL = 5000;

    private Player player;
    private SlotMachine machine;
    private PayoutSystem payoutSystem;
    private BonusGame bonusGame;
    private int totalSpins;
    private int lastBet;
    private int lastPayout;
    private int lastMultiplier;

    /**
     * Sets up a new game for one player.
     *
     * @param username the player's name
     */
    public Casino(String username) {
        player = new Player(username, STARTING_BALANCE);
        machine = new SlotMachine();
        payoutSystem = new PayoutSystem();
        bonusGame = new BonusGame();
        totalSpins = 0;
        lastBet = 0;
        lastPayout = 0;
        lastMultiplier = 0;
    }

    /**
     * Plays one spin and updates the player's money, wins, and losses.
     *
     * @param bet the amount to bet
     * @return the money paid back from the spin
     */
    public int startRound(int bet) {

        player.placeBet(bet); // Deduct the bet only once.
        machine.spin();
        lastMultiplier = payoutSystem.getMultiplier(machine);
        lastPayout = payoutSystem.calculatePayout(bet, lastMultiplier);
        player.addWinnings(lastPayout);
        lastBet = bet;
        totalSpins++;

        if (lastPayout > bet) {
            player.recordWin();
        } else {
            player.recordLoss();
        }
        return lastPayout;
    }

    /**
     * Plays the bonus game and adds any money won.
     *
     * @param guess the player's guess from 1 to 5
     * @return $100 or $0
     */
    public int playBonus(int guess) {
        int reward = bonusGame.playBonus(guess, totalSpins);
        player.addWinnings(reward);
        return reward;
    }

    /**
     * Checks if the bonus game is ready.
     *
     * @return true if the bonus game is ready
     */
    public boolean isBonusAvailable() {
        return bonusGame.isBonusAvailable(totalSpins);
    }

    /**
     * Checks if the player can keep playing.
     *
     * @return true if the goal is not reached and the player has money or a bonus
     */
    public boolean canContinue() {
        return !hasReachedGoal() && (player.getBalance() > 0 || isBonusAvailable());
    }

    /**
     * Checks if the player has reached the winning amount.
     *
     * @return true when balance is at least $5,000
     */
    public boolean hasReachedGoal() {
        return player.getBalance() >= GOAL;
    }

    /**
     * Gets the player's name.
     *
     * @return username
     */
    public String getPlayerName() {
        return player.getUsername();
    }

    /**
     * Gets the player's money.
     *
     * @return balance
     */
    public int getPlayerBalance() {
        return player.getBalance();
    }

    /**
     * Gets the number of spins played.
     *
     * @return number of spins
     */
    public int getTotalSpins() {
        return totalSpins;
    }

    /**
     * Gets the number of wins.
     *
     * @return total wins
     */
    public int getWins() {
        return player.getWins();
    }

    /**
     * Gets the number of losses.
     *
     * @return total losses
     */
    public int getLosses() {
        return player.getLosses();
    }

    /**
     * Gets the first symbol from the last round.
     *
     * @return the first reel
     */
    public String getReel1() {
        return machine.getReel1();
    }

    /**
     * Gets the second symbol from the last round.
     *
     * @return the second reel
     */
    public String getReel2() {
        return machine.getReel2();
    }

    /**
     * Gets the third symbol from the last round.
     *
     * @return the third reel
     */
    public String getReel3() {
        return machine.getReel3();
    }

    /**
     * Gets the multiplier from the last spin.
     *
     * @return 0, 2, 5, or 10
     */
    public int getLastMultiplier() {
        return lastMultiplier;
    }

    /**
     * Gets the bet from the last spin.
     *
     * @return bet amount
     */
    public int getLastBet() {
        return lastBet;
    }

    /**
     * Gets the money paid back from the last spin.
     *
     * @return payout amount
     */
    public int getLastPayout() {
        return lastPayout;
    }

    /**
     * Gets the winning number from the last bonus game.
     *
     * @return the winning number from 1 to 5, or 0 before the first bonus
     */
    public int getLastBonusWinningNumber() {
        return bonusGame.getLastWinningNumber();
    }
}
