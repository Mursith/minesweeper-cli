package com.example.minesweeper.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestMinePlacer implements MinePlacer {
    private final Set<Position> mines;

    public TestMinePlacer(Set<Position> mines) {
        this.mines = new HashSet<>(mines);
    }

    @Override
    public Set<Position> placeMines(int size, int minesCount) {
        if (mines.size() != minesCount) {
            throw new IllegalArgumentException("TestMinePlacer mines count mismatch");
        }
        return Collections.unmodifiableSet(mines);
    }
}
