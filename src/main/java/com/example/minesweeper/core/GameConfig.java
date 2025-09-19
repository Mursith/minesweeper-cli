package com.example.minesweeper.core;

import com.example.minesweeper.util.Preconditions;

public record GameConfig(int size, int mines) {
    public static GameConfig of(int size, int mines) {
        Preconditions.check(size >= 2 && size <= 26, "Size must be between 2 and 26.");
        int maxMines = (int)Math.floor(0.35 * size * size);
        Preconditions.check(mines >= 1 && mines <= maxMines,
                "Mines must be between 1 and " + maxMines);
        return new GameConfig(size, mines);
    }
}
