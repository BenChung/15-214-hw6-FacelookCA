package org.scubaguy.facelook.automata.renderers;

import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.automata.views.SwingView;
import org.scubaguy.facelook.boards.Board;

import java.awt.*;

/**
 * A simple renderer for two-state cells.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class BinaryRenderer implements SwingView.SwingCellRenderer {
    @Override
    public void drawCell(SwingView target, Board source, int x, int y) {
        Graphics g = target.getCurrentGraphics();
        if (source.getState(x,y) == 0)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.BLACK);

        Point start = target.getPosition(x, y);
        Dimension size = target.getCellSize();
        
        g.fillRect(start.x, start.y, size.width, size.height);
    }

    @Override
    public int cellClicked(Board source, int cellX, int cellY) {
        return (source.getState(cellX, cellY)+1)%2;
    }
}
