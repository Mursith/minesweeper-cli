package com.example.minesweeper.cli;

import com.example.minesweeper.core.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputParserTest {

    @Test
    void parsesValidInputs() {
        var parser = new InputParser();
        var p = parser.parseMove("A1", 4);
        assertEquals(0, p.row());
        assertEquals(0, p.col());

        var p2 = parser.parseMove("d4", 4);
        assertEquals(3, p2.row());
        assertEquals(3, p2.col());
    }

    @Test
    void rejectsInvalidRow() {
        var parser = new InputParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parseMove("Z1", 4));
    }

    @Test
    void rejectsInvalidCol() {
        var parser = new InputParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parseMove("A0", 4));
        assertThrows(IllegalArgumentException.class, () -> parser.parseMove("A5", 4));
    }

    @Test
    void rejectsWrongFormat() {
        var parser = new InputParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parseMove("11A", 4));
        assertThrows(IllegalArgumentException.class, () -> parser.parseMove("", 4));
    }
}
