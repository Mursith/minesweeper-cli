package com.example.minesweeper.core;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class RandomMinePlacer implements MinePlacer {
    private final SecureRandom random = new SecureRandom();

    @Override
    public Set<Position> placeMines(int size, int mines) {
        Set<Position> set = new HashSet<>();
        while (set.size() < mines) {
            int r = random.nextInt(size);
            int c = random.nextInt(size);
            set.add(Position.of(r, c));
        }
        return set;
    }
}
