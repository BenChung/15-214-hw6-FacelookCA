package org.scubaguy.facelook.UI;

import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.boards.Board;

import java.awt.*;

/**
 * This interface exposes the methods to render a cell and recide GUI input on a board into a cellular automata.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface CellRenderer<T extends View> {

    /**
     * Draws a cell onto the target
     * @param target The view to draw the cell onto
     * @param source The board to get the cell from
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     */
    public void drawCell(T target, Board source, int x, int y);
}
