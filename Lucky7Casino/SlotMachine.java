
/**
 * stores three symbols and checks for matches
 *
 * @author Siddharth Ghosh
 */
public class SlotMachine {
    private String reel1;
    private String reel2;
    private String reel3;

    /** creates a slot machine with a question mark on each reel. */
    public SlotMachine() {
        reel1 = "?";
        reel2 = "?";
        reel3 = "?";
    }

    /** poicks a random symbol for each reel */
    public void spin() {
        reel1 = randomSymbol();
        reel2 = randomSymbol();
        reel3 = randomSymbol();
    }

    /**
     * picks one of the four symbols at random
     * @return the randomly chosen symbol
     */
    private String randomSymbol() {
        int index = (int) (Math.random() * 4);

        if (index == 0) {
            return "7";
        } else if (index == 1) {
            return "CHERRY";
        } else if (index == 2) {
            return "LEMON";
        } else {
            return "BELL";
        }
    }

    /**
     * gets the first reel.
     * @return the first symbol
     */
    public String getReel1() {
        return reel1;
    }

    /**
     *gets the second reel
     * @return the second symbol
     */
    public String getReel2() {
        return reel2;
    }

    /**
     *Gets the third reel
     * @return the third symbol
     */
    public String getReel3() {
        return reel3;
    }

    /**
     * counts the matching reels: 3, 2, or 0
     *
     * @return 3 for all equal, 2 for one pair, or 0 for all different
     */
    public int countMatches() {
        if (reel1.equals("?") || reel2.equals("?") || reel3.equals("?")) {
            return 0;
        }

        // check a triple before a pair.
        if (reel1.equals(reel2) && reel2.equals(reel3)) {
            return 3;
        }

        if (reel1.equals(reel2) || reel1.equals(reel3) || reel2.equals(reel3)) {
            return 2;
        }

        return 0;
    }

    /**
     * checks if all three reels show 7.
     *
     * @return true for three sevens
     */
    public boolean isJackpot() {
        return reel1.equals("7") && reel2.equals("7") && reel3.equals("7");
    }
}
