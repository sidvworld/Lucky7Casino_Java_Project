# Lucky 7 Casino (Java / AP CSA)

A slot machine simulation using **virtual money only**.

## Run it

Requires JDK 8 or newer. Keep the Java files in the same directory.

```bash
javac *.java
java ConsoleApp
```

To run the Swing graphical version instead:

```bash
java CasinoGUI
```

In Eclipse: create a Java project, add the seven `.java` files to `src`, then run `ConsoleApp` or `CasinoGUI` as a Java Application.

Both interfaces check input before using it. Names must be 1-20 characters, bets must be whole numbers within your balance, and bonus guesses must be 1-5. The console prints an error and asks again; the GUI shows an error message. These checks use Scanner.hasNextInt() and if statements, with no try/catch or throw statements. The GUI wager menu also accepts typed bets.

## Rules

- Start with $1,000. Win by reaching $5,000.
- Four equally likely symbols: `7`, `CHERRY`, `LEMON`, `BELL`.
- Triple 7 = 10x; any other triple = 5x; exactly one pair = 2x; no matches = 0x.
- A payout is the **total money returned**, so a $50 bet that hits 2x produces $100 back (net +$50).
- Every five paid spins, a free bonus challenge is offered: guess 1-5 to possibly earn $100. Each bonus opportunity is claimable only once.
- Game ends when you quit, reach $5,000, or have no money and no pending bonus.

## Files

- `Player.java` — tracks player balance and spin stats
- `SlotMachine.java` — spins three reels and counts matches
- `PayoutSystem.java` — computes multipliers and total payout
- `BonusGame.java` — awards one bonus per five spins
- `Casino.java` — coordinates the five core classes
- `ConsoleApp.java` — reads console input, displays results; contains a minimal `main()`
- `CasinoGUI.java` — optional Swing version using the SAME core classes

## Assignment note

The shared project plan states your teacher requires each student to write their own core class without AI-generated logic. The five core classes here are a **reference implementation**: do not submit them as student-written work if your teacher has prohibited that. Review, test, and implement the allowed portions yourself. Replace the placeholder `@author` tags with the real contributors only on code they authored.

## Retro arcade GUI

The AI-built Swing interface lives entirely in `CasinoGUI.java`. It includes pixel-drawn symbols and lettering, CRT scanlines, staggered reel animation, a bankroll target meter, and an always-visible paytable. No external images, fonts, or libraries are required; Consolas is used when available, with Java's font fallback otherwise.

Choose a wager and press **SPIN** (or Enter). Every five spins, choose a numbered bonus button. **RESET** starts a fresh game for the same player, and **EXIT** closes the window. The five backend classes still calculate every spin, payout, and bonus; the timer only reveals the result visually. No try/catch blocks or explicit exception handling were added.

## Current documentation and review

The updated generated documentation is in `doc/index.html`. Submit the entire `doc` folder, including its supporting files. The older `javadoc` folder is outdated. The current folder was generated with the JDK Javadoc tool; the assignment specifically asks for Eclipse generation, so regenerate it using Eclipse's Project > Generate Javadoc before the final submission.

See `../PROJECT_REVIEW.md` for the requirement-by-requirement review, remaining group responsibilities, and submission checklist. Source changes to validation and comments were AI-assisted and should be included in the project disclosure and reflection.
