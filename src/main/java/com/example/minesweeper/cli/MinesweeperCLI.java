package com.example.minesweeper.cli;

import com.example.minesweeper.core.*;

public class MinesweeperCLI {

    private final ConsoleIO io;
    private final Renderer renderer;
    private final MinePlacer minePlacer;
    private final InputParser parser = new InputParser();

    public MinesweeperCLI(ConsoleIO io, Renderer renderer, MinePlacer minePlacer) {
        this.io = io;
        this.renderer = renderer;
        this.minePlacer = minePlacer;
    }

    public void run() {
        io.println("Welcome to Minesweeper!");
        do {
            playSingleGame();
            io.println("Press Enter to play again or type 'q' to quit...");
            String line = io.readLine();
            if (line != null && !line.trim().isEmpty() && line.trim().equalsIgnoreCase("q")) {
                break;
            }
        } while (true);
    }

    private void playSingleGame() {
        int size = promptSize();
        int maxMines = (int)Math.floor(0.35 * size * size);
        int mines = promptMines(maxMines);

        var engine = new MinesweeperEngine(GameConfig.of(size, mines), minePlacer);

        io.println("");
        io.println("Here is your minefield:");
        io.println(renderer.render(engine.getBoard()));
        io.println("");

        while (engine.getStatus() == GameStatus.IN_PROGRESS) {
            io.print("Select a square to reveal (e.g. A1): ");
            String move = io.readLine();
            try {
                var pos = parser.parseMove(move, size);
                var result = engine.reveal(pos.row(), pos.col());
                if (result.status() == GameStatus.LOST) {
                    io.println("Oh no, you detonated a mine! Game over.");
                    return;
                } else if (result.status() == GameStatus.IN_PROGRESS) {
                    io.println("This square contains " + result.adjacentMinesAtSelected() + " adjacent mines.");
                    io.println("");
                    io.println("Here is your updated minefield:");
                    io.println(renderer.render(engine.getBoard()));
                    io.println("");
                } else if (result.status() == GameStatus.WON) {
                    io.println("This square contains " + result.adjacentMinesAtSelected() + " adjacent mines.");
                    io.println("");
                    io.println("Here is your updated minefield:");
                    io.println(renderer.render(engine.getBoard()));
                    io.println("");
                    io.println("Congratulations, you have won the game!");
                    return;
                }
            } catch (IllegalArgumentException ex) {
                io.println("Input error: " + ex.getMessage());
            }
        }
    }

    private int promptSize() {
        while (true) {
            io.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            String line = io.readLine();
            try {
                int size = Integer.parseInt(line.trim());
                if (size < 2 || size > 26) {
                    io.println("Please enter a size between 2 and 26.");
                } else {
                    return size;
                }
            } catch (Exception e) {
                io.println("Please enter a valid integer.");
            }
        }
    }

    private int promptMines(int maxMines) {
        while (true) {
            io.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            String line = io.readLine();
            try {
                int mines = Integer.parseInt(line.trim());
                if (mines < 1) {
                    io.println("Number of mines must be at least 1.");
                } else if (mines > maxMines) {
                    io.println("Too many mines. Maximum allowed: " + maxMines);
                } else {
                    return mines;
                }
            } catch (Exception e) {
                io.println("Please enter a valid integer.");
            }
        }
    }
}
