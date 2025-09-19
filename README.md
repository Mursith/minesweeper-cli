
# Minesweeper CLI (Java 17)

A clean, testable implementation of Minesweeper with a console UI.
- Built with Maven, tested with JUnit 5
- Zero-adjacent reveal uses BFS flood fill
- Max mines limited to 35% of total squares
- Input format: `A1`, `B10` (case-insensitive)

## Requirements
- Java 17+
- Maven 3.8+

## Build & Run

```bash
mvn clean package
java -jar target/minesweeper-cli-1.0.0.jar
```

## Tests
```bash
mvn test
```

## Design
- `core/`: Pure domain logic: `Board`, `MinesweeperEngine`, `Position`, `GameConfig`, `MinePlacer`, etc.
- `cli/`: Console interface (**I/O decoupled** for testability), `InputParser`, `Renderer`.
- **Dependency Inversion**: `MinePlacer` interface for deterministic tests and random in production.

## Gameplay
- On start, enter grid size (2–26) and number of mines (≤ 35% of n²).
- Reveal squares using coordinates like `A1`.
- If you hit a mine, you lose.
- If you reveal all non-mine squares, you win.
- Board updates after each valid move.

## Assumptions
- First move can hit a mine (as per requirement's failure example).
- Zeros are displayed as `0` to match the example output.

## Platforms
- Works on Windows, macOS, and Linux (console-based).
