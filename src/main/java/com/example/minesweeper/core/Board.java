package com.example.minesweeper.core;

import com.example.minesweeper.util.Preconditions;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class Board {
    private final int size;
    private final Cell[][] cells;
    private int revealedCount = 0;
    private final int totalMines;

    private Board(int size, Cell[][] cells, int totalMines) {
        this.size = size;
        this.cells = cells;
        this.totalMines = totalMines;
    }

    public static Board create(int size, Set<Position> mines) {
        Preconditions.check(size >= 2, "Size must be >= 2");
        Preconditions.check(mines != null, "Mines set must not be null");

        Cell[][] cells = new Cell[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                cells[r][c] = new Cell();
            }
        }

        for (var pos : mines) {
            Preconditions.check(inBounds(size, pos.row(), pos.col()), "Mine out of bounds");
            cells[pos.row()][pos.col()].hasMine = true;
        }

        // compute adjacency
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (!cells[r][c].hasMine) {
                    cells[r][c].adjacent = countAdjacentMines(cells, size, r, c);
                }
            }
        }

        return new Board(size, cells, mines.size());
    }

    public int size() { return size; }

    public boolean isRevealed(int row, int col) {
        checkBounds(row, col);
        return cells[row][col].revealed;
    }

    public int getAdjacentMinesCount(int row, int col) {
        checkBounds(row, col);
        return cells[row][col].adjacent;
    }

    public boolean hasMine(int row, int col) {
        checkBounds(row, col);
        return cells[row][col].hasMine;
    }

    public int nonMineCellCount() {
        return size * size - totalMines;
    }

    public RevealOutcome reveal(int row, int col) {
        checkBounds(row, col);
        Cell selected = cells[row][col];

        if (selected.revealed) {
            // Already revealed: no-op, return current adjacent for message consistency
            return new RevealOutcome(false, selected.adjacent, 0);
        }
        if (selected.hasMine) {
            selected.revealed = true; // optional; doesn't matter for cli
            return new RevealOutcome(true, -1, 1);
        }

        int revealedThisMove = floodReveal(row, col);
        return new RevealOutcome(false, cells[row][col].adjacent, revealedThisMove);
    }

    private int floodReveal(int startR, int startC) {
        int count = 0;
        Queue<Position> q = new ArrayDeque<>();
        q.add(Position.of(startR, startC));

        while (!q.isEmpty()) {
            var p = q.poll();
            int r = p.row(), c = p.col();
            Cell cur = cells[r][c];
            if (cur.revealed) continue;

            cur.revealed = true;
            revealedCount++;
            count++;

            if (cur.adjacent == 0) {
                for (int nr = r - 1; nr <= r + 1; nr++) {
                    for (int nc = c - 1; nc <= c + 1; nc++) {
                        if (nr == r && nc == c) continue;
                        if (inBounds(size, nr, nc)) {
                            Cell neighbor = cells[nr][nc];
                            if (!neighbor.revealed && !neighbor.hasMine) {
                                q.add(Position.of(nr, nc));
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    public boolean allNonMinesRevealed() {
        return revealedCount == nonMineCellCount();
    }

    private void checkBounds(int row, int col) {
        Preconditions.check(inBounds(size, row, col), "Out of bounds");
    }

    private static boolean inBounds(int size, int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    private static int countAdjacentMines(Cell[][] cells, int size, int r, int c) {
        int cnt = 0;
        for (int nr = r - 1; nr <= r + 1; nr++) {
            for (int nc = c - 1; nc <= c + 1; nc++) {
                if (nr == r && nc == c) continue;
                if (nr >= 0 && nr < size && nc >= 0 && nc < size) {
                    if (cells[nr][nc].hasMine) cnt++;
                }
            }
        }
        return cnt;
    }

    public record RevealOutcome(boolean detonated, int adjacentAtSelected, int revealedCells) {}
}
