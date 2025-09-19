package com.example.minesweeper.cli;

import com.example.minesweeper.core.Board;

public class Renderer {

    public String render(Board board) {
        StringBuilder sb = new StringBuilder();
        int n = board.size();

        // Header
        sb.append("  ");
        for (int c = 1; c <= n; c++) {
            sb.append(c);
            if (c < n) sb.append(" ");
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < n; r++) {
            sb.append((char)('A' + r)).append(" ");
            for (int c = 0; c < n; c++) {
                if (!board.isRevealed(r, c)) {
                    sb.append("_");
                } else {
                    int count = board.getAdjacentMinesCount(r, c);
                    sb.append(count);
                }
                if (c < n - 1) sb.append(" ");
            }
            if (r < n - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
