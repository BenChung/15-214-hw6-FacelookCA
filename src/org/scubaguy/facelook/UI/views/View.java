package org.scubaguy.facelook.UI.views;

import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a way to draw the board.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public abstract class View extends JPanel {
    protected CellRenderer renderer;
    public View(CellRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Called whenever a board should be drawn
     * @param b the board to draw
     */
    public abstract void draw(EditableBoard b);
}
