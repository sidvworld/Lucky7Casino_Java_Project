/**
 * Stores three symbols and checks for matches.
 *
 * @author Siddharth Ghosh
 */
public class SlotMachine {
    private String reel1;
    private String reel2;
    private String reel3;
    private static final String[] SYMBOLS = {"7", "CHERRY", "LEMON", "BELL"};

    /** Creates a slot machine with a question mark on each reel. */
    public SlotMachine() {
        reel1 = "?";
        reel2 = "?";
        reel3 = "?";
    }

    /** Picks a random symbol for each reel. */
    public void spin() {
        reel1 = randomSymbol();
        reel2 = randomSymbol();
        reel3 = randomSymbol();
    }

    /**
     * Picks one of the four symbols at random.
     *
     * @return the randomly chosen symbol
     */
    private String randomSymbol() {
        int index = (int) (Math.random() * SYMBOLS.length);
        return SYMBOLS[index];
    }

    /**
     * Gets the first reel.
     *
     * @return the first symbol
     */
    public String getReel1() {
        return reel1;
    }

    /**
     * Gets the second reel.
     *
     * @return the second symbol
     */
    public String getReel2() {
        return reel2;
    }

    /**
     * Gets the third reel.
     *
     * @return the third symbol
     */
    public String getReel3() {
        return reel3;
    }

    /**
     * Counts the matching reels: 3, 2, or 0.
     *
     * @return 3 for all equal, 2 for one pair, or 0 for all different
     */
    public int countMatches() {
        if (reel1.equals("?") || reel2.equals("?") || reel3.equals("?")) {
            return 0;
        }
        if (reel1.equals(reel2) && reel2.equals(reel3)) {
            return 3;
        }
        if (reel1.equals(reel2) || reel1.equals(reel3) || reel2.equals(reel3)) {
            return 2;
        }
        return 0;
    }

    /**
     * Checks if all three reels show 7.
     *
     * @return true for three sevens
     */
    public boolean isJackpot() {
        return reel1.equals("7") && reel2.equals("7") && reel3.equals("7");
    }
}
