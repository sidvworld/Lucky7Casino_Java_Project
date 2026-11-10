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

Input is read directly without validation or exception handling. Enter a username, whole-number bets within your balance, and bonus guesses from 1 to 5.

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
