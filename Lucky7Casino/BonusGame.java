/**
 * Runs the number guessing bonus game.
 *
 * @author Nithin Sankar
 */
public class BonusGame {
    private int lastClaimedSpin;
    private int lastWinningNumber;

    /** Creates a new bonus game. */
    public BonusGame() {
        lastClaimedSpin = 0;
        lastWinningNumber = 0;
    }

    /**
     * Checks if five spins have passed and the bonus has not been used.
     *
     * @param totalSpins the number of spins played
     * @return true when a new bonus round can be played
     */
    public boolean isBonusAvailable(int totalSpins) {
        return totalSpins > 0 && totalSpins % 5 == 0 && totalSpins != lastClaimedSpin;
    }

    /**
     * Picks a winning number and checks the player's guess.
     *
     * @param guess player's number from 1 to 5
     * @param totalSpins the number of spins played
     * @return 100 for a correct guess, or 0 for a wrong guess
     */
    public int playBonus(int guess, int totalSpins) {

        lastWinningNumber = (int) (Math.random() * 5) + 1;
        lastClaimedSpin = totalSpins; // Prevents claiming the same bonus twice.
        return getBonusReward(guess == lastWinningNumber);
    }

    /**
     * Gets the winning number from the last bonus game.
     *
     * @return the winning number, or 0 before any bonus
     */
    public int getLastWinningNumber() {
        return lastWinningNumber;
    }

    /**
     * Gets the prize for the player's guess.
     *
     * @param correct true if the guess matches the winning number
     * @return 100 if correct is true, or 0 if it is false
     */
    public int getBonusReward(boolean correct) {
        if (correct) {
            return 100;
        }
        return 0;
    }
}
