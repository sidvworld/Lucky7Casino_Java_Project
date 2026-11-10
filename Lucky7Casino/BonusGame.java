/**
 * Runs a free number-guessing challenge after every five spins.
 * Each earned challenge can be claimed only once.
 *
 * @author Nithin Sankar
 */
public class BonusGame {
    private int lastClaimedSpin;
    private int lastWinningNumber;

    /** Creates a bonus game that has not awarded any prizes. */
    public BonusGame() {
        lastClaimedSpin = 0;
        lastWinningNumber = 0;
    }

    /**
     * Checks whether the next bonus has been earned but not used.
     *
     * @param totalSpins the number of paid spins completed
     * @return true when a new bonus round can be played
     */
    public boolean isBonusAvailable(int totalSpins) {
        return totalSpins > 0 && totalSpins % 5 == 0 && totalSpins != lastClaimedSpin;
    }

    /**
     * Plays an earned bonus exactly once.
     *
     * @param guess player's number from 1 to 5
     * @param totalSpins the number of completed paid spins
     * @return the bonus amount earned, $100 or $0
     */
    public int playBonus(int guess, int totalSpins) {

        lastWinningNumber = (int) (Math.random() * 5) + 1;
        lastClaimedSpin = totalSpins; // Prevents claiming the same bonus twice.
        return getBonusReward(guess == lastWinningNumber);
    }

    /**
     * Gets the random winning number from the latest bonus attempt.
     *
     * @return the winning number, or 0 before any bonus
     */
    public int getLastWinningNumber() {
        return lastWinningNumber;
    }

    /**
     * Converts the result of the guess to a cash reward.
     *
     * @param correct whether the guess matched
     * @return $100 for a correct guess, otherwise $0
     */
    public int getBonusReward(boolean correct) {
        if (correct) {
            return 100;
        }
        return 0;
    }
}
