package com.example.minesweeper.core;

final class Cell {
    boolean hasMine;
    boolean revealed;
    int adjacent; // cached after board setup
}
