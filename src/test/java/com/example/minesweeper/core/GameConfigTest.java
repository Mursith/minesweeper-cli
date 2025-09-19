package com.example.minesweeper.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @Test
    void sizeMustBeBetween2And26() {
        assertThrows(IllegalArgumentException.class, () -> GameConfig.of(1, 1));
        assertDoesNotThrow(() -> GameConfig.of(2, 1));
        assertDoesNotThrow(() -> GameConfig.of(26, 1));
        assertThrows(IllegalArgumentException.class, () -> GameConfig.of(27, 1));
    }

    @Test
    void minesMustNotExceed35Percent() {
        int n = 10; // 100 cells
        int max = (int)Math.floor(0.35 * n * n); // 35
        assertDoesNotThrow(() -> GameConfig.of(n, 1));
        assertDoesNotThrow(() -> GameConfig.of(n, max));
        assertThrows(IllegalArgumentException.class, () -> GameConfig.of(n, max + 1));
        assertThrows(IllegalArgumentException.class, () -> GameConfig.of(n, 0));
    }
}