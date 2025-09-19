package com.example.minesweeper.cli;

import com.example.minesweeper.core.Position;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {

    private static final Pattern MOVE_PATTERN = Pattern.compile("^\\s*([A-Za-z])\\s*(\\d+)\\s*$");

    public Position parseMove(String input, int size) throws IllegalArgumentException {
        Matcher m = MOVE_PATTERN.matcher(input);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid format. Example: A1");
        }
        char rowChar = m.group(1).toUpperCase(Locale.ROOT).charAt(0);
        int col = Integer.parseInt(m.group(2));

        int row = rowChar - 'A' + 1; // 1-based
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("Row must be between A and " + (char)('A' + size - 1));
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("Column must be between 1 and " + size);
        }
        return Position.of(row - 1, col - 1); // 0-based
    }
}
