package com.example.minesweeper.core;

import java.util.Set;

public class MinesweeperEngine {

    private final GameConfig config;
    private final Board board;
    private GameStatus status = GameStatus.IN_PROGRESS;

    public MinesweeperEngine(GameConfig config, MinePlacer placer) {
        this.config = config;
        Set<Position> mines = placer.placeMines(config.size(), config.mines());
        this.board = Board.create(config.size(), mines);
    }

    public Board getBoard() {
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }

    public RevealResult reveal(int row, int col) {
        var outcome = board.reveal(row, col);
        if (outcome.detonated()) {
            status = GameStatus.LOST;
            return new RevealResult(GameStatus.LOST, -1);
        }
        if (board.allNonMinesRevealed()) {
            status = GameStatus.WON;
            return new RevealResult(GameStatus.WON, outcome.adjacentAtSelected());
        }
        return new RevealResult(GameStatus.IN_PROGRESS, outcome.adjacentAtSelected());
    }

    public record RevealResult(GameStatus status, int adjacentMinesAtSelected) {}
}
