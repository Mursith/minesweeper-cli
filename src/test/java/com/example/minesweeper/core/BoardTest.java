package com.example.minesweeper.core;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void revealsZeroAreaWithFloodFill() {
        int size = 4;
        // Place mines at (0,3) and (3,0)
        var mines = Set.of(Position.of(0,3), Position.of(3,0));
        var board = Board.create(size, mines);

        // Reveal a cell far from mines that likely has 0 adjacency
        var outcome = board.reveal(1,1);
        assertFalse(outcome.detonated());
        // It should reveal multiple cells via flood fill
        assertTrue(outcome.revealedCells() > 1);
    }

    @Test
    void detonatesOnMine() {
        int size = 3;
        var mines = Set.of(Position.of(1,1));
        var board = Board.create(size, mines);

        var outcome = board.reveal(1,1);
        assertTrue(outcome.detonated());
    }

    @Test
    void winConditionWhenAllNonMinesRevealed() {
        int size = 2;
        var mines = Set.of(Position.of(0,0));
        var board = Board.create(size, mines);

        var o1 = board.reveal(0,1);
        var o2 = board.reveal(1,0);
        var o3 = board.reveal(1,1);
        assertTrue(board.allNonMinesRevealed());
    }

    @Test
    void adjacentCountsAreCorrect() {
        int size = 3;
        var mines = Set.of(Position.of(0,0), Position.of(1,1));
        var board = Board.create(size, mines);


        assertEquals(2, board.getAdjacentMinesCount(0,1));
        assertEquals(2, board.getAdjacentMinesCount(1,0));
        assertEquals(1, board.getAdjacentMinesCount(0,2));
        assertEquals(1, board.getAdjacentMinesCount(2,2));
        assertEquals(1, board.getAdjacentMinesCount(1,2));
        assertEquals(1, board.getAdjacentMinesCount(2,0));
        assertEquals(1, board.getAdjacentMinesCount(2,1));


    }
}
