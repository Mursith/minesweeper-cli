package com.example.minesweeper.util;

public final class Preconditions {
    private Preconditions() {}

    public static void check(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }
}
