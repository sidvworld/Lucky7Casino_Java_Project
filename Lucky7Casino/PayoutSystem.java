/**
 * Applies slot-machine payout rules, without generating new symbols.
 *
 * @author Hope Le
 */
public class PayoutSystem {

    /** Creates the payout calculator. */
    public PayoutSystem() {
    }

    /**
     * Finds a multiplier using the result of an existing spin.
     *
     * @param machine the machine that already spun
     * @return 10 for triple sevens, 5 for another triple, 2 for a pair, or 0
     */
    public int getMultiplier(SlotMachine machine) {
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
     * Computes the total prize, including the original bet.
     *
     * @param bet the original bet
     * @param multiplier the outcome multiplier
     * @return the total payout
     */
    public int calculatePayout(int bet, int multiplier) {
        return bet * multiplier;
    }

    /**
     * Checks whether a spin will return a prize.
     *
     * @param machine the machine that already spun
     * @return true for any matching combination
     */
    public boolean isWinningSpin(SlotMachine machine) {
        return getMultiplier(machine) > 0;
    }
}
