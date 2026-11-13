# Lucky 7 Casino - project review and submission checklist

Reviewed November 10, 2026 against all seven pages of `Java_Creation_Lab_Project_Instructions.pdf` and the Java files currently in this workspace.

## Overall finding

The application now meets the main programming and input-recovery requirements in the supplied PDF. It is **not yet a complete, verified submission**. The remaining blockers are core-class ownership for all five members, the separate saved console version, and the presentation/documentation deliverables not found in this workspace. This is an evidence-based review, not a predicted grade.

The supplied PDF says its AI-use restrictions were omitted. This review cannot certify compliance with omitted teacher instructions or independent authorship. The README also describes an earlier reference implementation. The group needs to reconcile that description with its actual work and disclose assistance accurately; changing author tags alone does not establish authorship.

## Changes made during this review

- Added name checks (1-20 characters) in console and GUI.
- Added whole-number and range checks for console bets and bonus guesses using `Scanner.hasNextInt()` and `if` statements. Invalid entries print a message and repeat the prompt.
- Made the GUI wager menu editable and added equivalent checks with message dialogs. Existing preset wagers and bonus buttons remain available.
- Canceling the GUI name dialog uses `PLAYER 01` instead of failing during startup.
- Added simple backend guards so invalid bets do not change balances or count spins, and invalid or repeated bonus attempts do not consume or award a bonus.
- Added short explanatory regular comments to the non-GUI classes and missing public GUI Javadoc tags.
- Generated current Javadoc in `Lucky7Casino/doc`. The existing `Lucky7Casino/javadoc` is outdated and should not be submitted as the current documentation.
- Updated README instructions and disclosure notes.

No `try`, `catch`, `throw`, or `throws` statements were added to the application's seven Java source files. The core classes still have no Scanner input, menus, or print statements. Console messages belong in ConsoleApp; GUI messages belong in CasinoGUI. Record the backend validation changes in the reflection and keep the final console and GUI copies consistent.

## Rubric review

| # | Requirement | Status and evidence |
|---|---|---|
| 1 | Class design and encapsulation (10) | Five meaningful core classes exist. Four have private instance state and initialized constructors. PayoutSystem is a stateless rules class with an empty constructor; confirm whether your teacher requires state in every individual class. One core class per member is not demonstrated by the author tags. Original authorship cannot be verified from files. |
| 2 | Object collaboration (9) | Met technically: Casino uses Player, SlotMachine, PayoutSystem, and BonusGame through object methods. |
| 3 | Two primitive types (4) | Met: int for balances/counts and boolean for game decisions and the console loop. No double is required. |
| 4 | Conditionals and compound Boolean (5) | Met: payout decisions, match detection, bet checks, and bonus availability use meaningful conditions. |
| 5 | Purposeful loop (5) | Met: ConsoleApp.run and input-retry loops continue until valid input or quitting. Every core class does not need its own loop. |
| 6 | Methods with return values (5) | Met: countMatches, getMultiplier, calculatePayout, startRound, playBonus, and other methods. |
| 7 | Parameters (4) | Met: bet, multiplier, guess, totalSpins, username, and startingBalance are used by methods/constructors. |
| 8 | Two String methods (5) | Met: trim, equals, equalsIgnoreCase, and length affect input or game behavior. |
| 9 | Input validation and recovery (6) | Fixed and tested: invalid names, numeric text, wager ranges, and bonus guesses get messages; the application continues. |
| 10 | Minimal main and code quality (4) | Met technically: main starts the relevant interface; game logic remains in methods. |
| 11 | Console I/O separated (7) | Met: only the interfaces handle user interaction; no Scanner or System.out in the five core classes. |
| 12 | Each member explains a method (10) | Not verifiable yet. Each member must explain a nontrivial method from a class they actually wrote. A constructor, getter, or setter does not count. |
| 13 | GUI functionality (5) | Core-method connections, invalid-input recovery, spins, bonus controls, and continued play tested. Interface does not recalculate payout rules. |
| 14 | Both complete source versions (2) | All seven current files compile. A separately saved/renamed console project is not present in this workspace; historical preservation is unverified. |
| 15 | Regular comments (2) | Short why-comments now appear in each core class and ConsoleApp. |
| 16 | Javadoc comments (3) | Class/author, constructor, parameter, and return documentation present. Added missing GUI tags. Author accuracy remains a group responsibility. |
| 17 | Generated Javadoc (1) | Fresh complete doc folder generated and local links checked. Generated with the JDK tool, not Eclipse. PDF specifically requests Eclipse generation; perform that final step. |
| 18 | Two demonstration videos (5) | Not found in workspace. Must be recorded, narrated, submitted as files, and embedded in slides. |
| 19 | Complete slide deck (4) | Not found in workspace. Full checklist below. |
| 20 | Reflection and disclosure (4) | Full prompt/response record and reflection not found. README notes are not a substitute. |

## Class ownership and individual explanations

These are the current author tags, not independent proof of who wrote each class.

| Class | Current author | Responsibility | Method to prepare to explain |
|---|---|---|---|
| Player | Siddharth Kethini | Player money, wins, and losses | canAffordBet / placeBet: range checks and balance change |
| SlotMachine | Siddharth Ghosh | Reel symbols and matching | countMatches: why triples are checked before pairs |
| PayoutSystem | Hope Le | Multiplier and payout rules | getMultiplier: jackpot, triple, pair, and no match |
| BonusGame | Nithin Sankar | Bonus timing and reward | playBonus: eligibility, random number, and claiming the bonus |
| Casino | Siddharth Ghosh | Coordinates the core objects | startRound: validation, deduction, spin, payout, and stats |
| ConsoleApp | Zuhair Ahmed | Console input and output | Does not count toward the five core classes |
| CasinoGUI | ChatGPT | Graphical interface and animation | Disclose AI assistance; demonstrate spin() calling casino.startRound(bet) |

**Ownership blocker:** the five core classes list only four distinct students. Zuhair is listed only on ConsoleApp, which the PDF explicitly excludes from the core-class count. Confirm the actual authors. If the tags are accurate, Zuhair still needs to write a meaningful core class or genuinely implement one of the core responsibilities. Do not simply replace another student's author tag.

**PayoutSystem note:** its methods have a real role and interact with SlotMachine, so it is not an empty placeholder. It has no private instance variables because it stores no state. The rubric's wording about private instance variables and initialized state could be interpreted per class; ask the teacher whether this stateless design is acceptable. Do not add unused variables merely to fill a requirement.

## Verification performed

- Compiled all seven current sources with JDK 11 using `--release 8`.
- Tested all 64 reel combinations: match counts, multipliers, payout arithmetic, and winning-spin checks.
- Core checks passed 277 assertions covering normal arithmetic, invalid bets, round updates, bonus eligibility, repeated claims, stats, the $5,000 goal, and zero-balance behavior with/without an earned bonus.
- Console run rejected empty/long names, an invalid menu choice, letters, negative/too-large wagers, an oversized integer, extra numeric tokens, decimals, a blank wager, and invalid bonus guesses. It then completed five valid spins, a bonus, stats/rules, and quitting.
- GUI run rejected empty/long names and seven invalid wager cases (letters, negative, too large, oversized integer, decimal, blank, extra tokens). It displayed error dialogs, preserved game state for rejected bets, and continued with valid spins. Bonus blinking, claiming, and subsequent play passed.
- Generated Javadoc without documentation warnings. All seven class pages exist; no missing local file targets were found in the generated top-level HTML files.
- Scanned application Java sources: no try/catch/throw/throws; no user-facing input/output in core classes.

These automated checks support the review; they do not replace the required narrated videos or each student's method explanation. The videos and slides must show the final version, not earlier screenshots of input handling that was removed.

## Everything to turn in

Submit in Canvas's **Unit 3 Project folder** by **Sunday, November 15, 2026, 11:59 pm**, according to the supplied PDF. Presentations begin Monday, November 16.

1. **Saved console source version:** Player.java, SlotMachine.java, PayoutSystem.java, BonusGame.java, Casino.java, and ConsoleApp.java. Save the entire console project separately with a clear name, such as Lucky7Casino_Console. No separate saved console copy was found here. Do not claim a newly created copy was preserved before GUI development.
2. **Final GUI source version:** all seven current Java files, including CasinoGUI.java and the console class whose comments are graded. Keep these in a separate GUI project folder, such as Lucky7Casino_GUI. Include any additional core class genuinely written to resolve ownership.
3. **Class diagram:** class responsibilities, key fields and methods, and arrows showing which classes use which. Include the console/GUI connections to Casino. No diagram found here.
4. **Complete generated Javadoc folder:** currently `Lucky7Casino/doc`, including supporting files. Regenerate from the final source using Eclipse's Project > Generate Javadoc, open index.html, and confirm class pages. Do not submit only index.html or screenshots; avoid the stale `javadoc` folder.
5. **Console video file:** about 2-4 minutes, running console program, main features, object interaction, at least two invalid inputs with messages and recovery. The PDF's timeline asks for the console recording by Thursday, November 12.
6. **GUI video file:** about 2-4 minutes, same main features, invalid inputs with recovery, and an explanation of one GUI action calling an original Java method.
7. **Slide deck:** include all items below and embed both videos directly. Links alone do not satisfy the video requirement.
8. **Project documentation:** prompts AND responses used, plus a reflection covering each member's contribution, changes, and learning. Include AI help with GUI, comments, formatting, and validation/backend guards, not just the artwork.

Both videos must show the running application rather than source code, have narration, and collectively include every member speaking. Submit the video files separately even though they are embedded in the deck.

The diagram, slide deck, videos, full prompt/response record, reflection, and saved console copy were **not found in this workspace**. They may exist elsewhere; this review does not certify those unseen files.

## Full slide checklist

- Project title and all five member names.
- Link to the project folder.
- Project description and purpose.
- Class diagram.
- Responsibility and actual member author for each class.
- How the objects interact.
- Short code screenshots for primitive types; conditionals and compound Boolean; loops; return values; parameters; String methods; private fields and constructors; minimal main.
- Console I/O class and why it is separate.
- Console screenshots and GUI screenshots.
- How a GUI action calls a core Java method.
- Testing evidence, including invalid inputs and successful recovery.
- Regular comment screenshot.
- Javadoc comment screenshot.
- Generated documentation folder and index.html screenshots.
- Project prompts and contributions.
- Reflection.
- Embedded console video and embedded GUI video.

## Suggested shared demo tests

For both interfaces, enter `abc` as a wager, show the error, then enter `1` and complete a spin. Next enter `-1`, show the range error, then enter `1` and complete another spin. The GUI wager control now accepts typed text for these tests. You can additionally show an empty player name being rejected. Use $1 wagers to reach five spins without risking an early game over, then show the bonus.

## Priority before submitting

1. Resolve the one-core-class-per-member issue truthfully and verify the teacher's expectations for the stateless payout class.
2. Review and understand the new simple validation checks; record the changes and assistance in the reflection.
3. Save separate final console and GUI copies with matching core logic; rerun the shared demo tests after any source changes.
4. Generate the final Javadoc through Eclipse.
5. Finish the class diagram, narrated videos, complete slides, prompt/response record, and reflection.
6. Have all five members practice a nontrivial method explanation.
