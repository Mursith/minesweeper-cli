package com.example.minesweeper.core;

import java.util.Set;

public interface MinePlacer {
    /**
     * Returns a set of mine positions (size = mines) for a board of size NxN.
     */
    Set<Position> placeMines(int size, int mines);
}
