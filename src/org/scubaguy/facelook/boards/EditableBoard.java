package org.scubaguy.facelook.boards;

/**
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 *
 * This interface exposes a method to edit a board, as well as the methods for a read-only board
 */
public interface EditableBoard extends Board {
    /**
     * Set the state of a cell
     * @param v The new state of the cell
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     * @throws org.scubaguy.facelook.exceptions.InvalidCellException If the cell given is invalid
     */
    public void setState(int v, int x, int y);
}
