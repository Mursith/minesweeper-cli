package com.example.minesweeper.cli;

import com.example.minesweeper.core.MinePlacer;
import com.example.minesweeper.core.Position;
import com.example.minesweeper.core.TestMinePlacer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test (3x3 board) with a single deterministic mine at C3 (2,2).
 * Move sequence:
 *   - A2 -> zero-adjacent => flood fill reveals all non-mine cells -> WIN
 *   - q  -> quit at the replay prompt
 */
public class MinesweeperCLIE2ETest {

    @Test
    void e2e_win_small_board_3x3_one_move_then_quit() throws Exception {
        String userInput = String.join(System.lineSeparator(),
                "3",    // size
                "1",    // mines
                "A2",   // first reveal -> flood-fill -> WIN
                "q"     // quit at the replay prompt
        ) + System.lineSeparator();

        try (ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(outBuffer, true, StandardCharsets.UTF_8)) {

            ConsoleIO io = new ConsoleIO(in, out);
            Renderer renderer = new Renderer();

            // Deterministic mine placement: single mine at C3 (row=2, col=2)
            MinePlacer placer = new TestMinePlacer(Set.of(Position.of(2, 2)));

            MinesweeperCLI cli = new MinesweeperCLI(io, renderer, placer);
            cli.run();

            String output = outBuffer.toString(StandardCharsets.UTF_8);

            // Sanity: app started and initial board was shown
            assertTrue(output.contains("Welcome to Minesweeper!"));
            assertTrue(output.contains("Here is your minefield:"));

            // After revealing A2, we expect both the reveal message and a win
            assertTrue(output.contains("This square contains"));         // reveal feedback
            assertTrue(output.contains("Congratulations, you have won")); // flood-fill -> WIN
        }
    }

    @Test
    void e2e_lose_small_board_3x3_one_move_then_quit() throws Exception {
        // Inputs:
        //  - 3 (size)
        //  - 1 (mine count)
        //  - B2 (hit the mine, lose immediately)
        //  - q (quit at replay prompt to avoid starting a second game)
        String userInput = String.join(System.lineSeparator(),
                "3",   // size
                "1",   // mines
                "B2",  // first reveal -> mine -> LOSS
                "q"    // quit at the replay prompt
        ) + System.lineSeparator();

        try (ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(outBuffer, true, StandardCharsets.UTF_8)) {

            ConsoleIO io = new ConsoleIO(in, out);
            Renderer renderer = new Renderer();

            // Deterministic mine placement: single mine at B2 (row=1, col=1)
            MinePlacer placer = new TestMinePlacer(Set.of(Position.of(1, 1)));

            MinesweeperCLI cli = new MinesweeperCLI(io, renderer, placer);
            cli.run();

            String output = outBuffer.toString(StandardCharsets.UTF_8);

            // Sanity: app started and initial board was shown
            assertTrue(output.contains("Welcome to Minesweeper!"));
            assertTrue(output.contains("Here is your minefield:"));

            // We expect a clear loss message
            assertTrue(output.contains("Oh no, you detonated a mine! Game over."));

            // Ensure we did not incorrectly report a win
            assertTrue(!output.contains("Congratulations, you have won"));
        }
    }
}