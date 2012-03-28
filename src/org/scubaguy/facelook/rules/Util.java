package org.scubaguy.facelook.rules;

import org.scubaguy.facelook.boards.Board;

/**
 * This class provides some commonly used methods.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class Util {
    /**
     * Looks for neighboring cells with the given state.
     * @param board The board to look in
     * @param state The state to look for
     * @param x The x-position of the "home" cell
     * @param y The y-position of the "home" cell
     * @return The number of cells with the given state
     */
    public static int getNeighborsWithState(Board board, int state, int x, int y) {
        int n = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                if (board.isValid(x+i, y+j) && board.getState(x+i, y+j) == state)
                    n++;
            }
        }
        return n;
    }    
}
