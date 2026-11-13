/**
 * calculates how much money a spin pays.
 *
 * @author Hope Le
 */
public class PayoutSystem {

    /** creates a the object. */
    public PayoutSystem() {
    }

    /**
     * gets the multiplier for the symbols on the reels
     *
     * @param machine the slot machine after a spin
     * @return 10 for triple sevens, 5 for another triple, 2 for a pair, or 0
     */
    public int getMultiplier(SlotMachine machine) {
        // check three 7s first because they also count as a triple.
        if (machine.isJackpot()) {
            return 10;
        } else if (machine.countMatches() == 3) {
            return 5;
        } else if (machine.countMatches() == 2) {
            return 2;
        }
        return 0;
    }

    /**
     * Multiplies the bet by the multiplier to find the payout
     * @param bet the original bet
     * @param multiplier the number to multiply the bet by
     * @return the total payout
     */
    public int calculatePayout(int bet, int multiplier) {
        return bet * multiplier;
    }

    /**
     * checks if the spin wins money.
     * @param machine the slot machine after a spin
     * @return true if two or more reels match
     */
    public boolean isWinningSpin(SlotMachine machine) {
        return getMultiplier(machine) > 0;
    }
}
