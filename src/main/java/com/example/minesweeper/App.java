package com.example.minesweeper;

import com.example.minesweeper.cli.ConsoleIO;
import com.example.minesweeper.cli.MinesweeperCLI;
import com.example.minesweeper.cli.Renderer;
import com.example.minesweeper.core.RandomMinePlacer;

public class App {
    public static void main(String[] args) {
        var io = new ConsoleIO(System.in, System.out);
        var renderer = new Renderer();
        var minePlacer = new RandomMinePlacer();
        var cli = new MinesweeperCLI(io, renderer, minePlacer);
        cli.run();
    }
}
