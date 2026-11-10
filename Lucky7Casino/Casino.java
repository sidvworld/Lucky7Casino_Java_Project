/**
 * Coordinates the player, slot machine, payouts, and bonus rounds.
 * All game rules live here or in the other core classes, not in the UI.
 *
 * @author Siddharth Ghosh
 */
public class Casino {
    /** Starting virtual money for every new player. */
    public static final int STARTING_BALANCE = 1000;
    /** Virtual balance needed to win the game. */
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
     * Plays one paid round and updates the player's balance and statistics.
     *
     * @param bet money to wager
     * @return the total payout earned for that round
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
     * Claims an earned bonus and credits the reward.
     *
     * @param guess an integer from 1 through 5
     * @return $100 or $0
     */
    public int playBonus(int guess) {
        int reward = bonusGame.playBonus(guess, totalSpins);
        player.addWinnings(reward);
        return reward;
    }

    /**
     * Checks whether a bonus can be claimed right now.
     *
     * @return true when a free challenge is available
     */
    public boolean isBonusAvailable() {
        return bonusGame.isBonusAvailable(totalSpins);
    }

    /**
     * Checks whether there is more game to play.
     *
     * @return true unless the goal is reached or no moves remain
     */
    public boolean canContinue() {
        return !hasReachedGoal() && (player.getBalance() > 0 || isBonusAvailable());
    }

    /**
     * Checks the virtual-dollar target.
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
     * Gets the player's remaining virtual money.
     *
     * @return balance
     */
    public int getPlayerBalance() {
        return player.getBalance();
    }

    /**
     * Gets the total number of paid spins.
     *
     * @return number of spins
     */
    public int getTotalSpins() {
        return totalSpins;
    }

    /**
     * Gets the count of winning spins.
     *
     * @return total wins
     */
    public int getWins() {
        return player.getWins();
    }

    /**
     * Gets the count of losing spins.
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
     * Gets the latest multiplier.
     *
     * @return 0, 2, 5, or 10
     */
    public int getLastMultiplier() {
        return lastMultiplier;
    }

    /**
     * Gets the previous bet.
     *
     * @return bet amount
     */
    public int getLastBet() {
        return lastBet;
    }

    /**
     * Gets the latest round's total payout.
     *
     * @return payout amount
     */
    public int getLastPayout() {
        return lastPayout;
    }

    /**
     * Gets the most recent bonus's winning number.
     *
     * @return the random number, 1 through 5 (or 0 if not played)
     */
    public int getLastBonusWinningNumber() {
        return bonusGame.getLastWinningNumber();
    }
}
