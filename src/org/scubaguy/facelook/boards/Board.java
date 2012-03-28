package org.scubaguy.facelook.boards;

/**
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 *
 * This interface exposes the common methods for a read-only Board structre.
 */
public interface Board {
    /**
     * Checks if the given coordinate is valid
     *
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     * @return The validity of the cell
     */
    public boolean isValid(int x, int y);

    /**
     * Gets the state of the cell.
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     * @return The cells state
     * @throws org.scubaguy.facelook.exceptions.InvalidCellException If the cell is invalid
     */
    public int getState(int x, int y);

    /**
     * Gets the width of the board
     * @return The width of the board
     */
    public int getWidth();

    /**
     * Gets the height of the board
     * @return The height of the board
     */
    public int getHeight();
}
