package org.scubaguy.facelook.runners;

public enum RunnerStrictness {
    HARD (3),
    MEDIUM (2),
    SOFT (1),
    ANY (0);
    
    private final int difficulty;
    RunnerStrictness(int n) {
        difficulty = n;
    }

    int getInt() {
        return difficulty;
    }

}
