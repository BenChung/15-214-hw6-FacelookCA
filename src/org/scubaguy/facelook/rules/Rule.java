package org.scubaguy.facelook.rules;

import org.scubaguy.facelook.boards.Board;

/**
 * This interface defines the methods for a standard Rule.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface Rule {
    /**
     * Get the new state of a cell.
     * @param b The board that the cell is in
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The new state of the cell
     */
    public int getState(Board b, int x, int y);

}
