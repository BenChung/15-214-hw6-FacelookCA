package org.scubaguy.facelook.automata.views;

import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.UI.views.ViewFactory;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A view that uses a grid of swing squares to render the automata
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class SwingView extends View {
    EditableBoard board;

    /**
     * This interface provides a way for CellRenderers to render onto SwingView
     */
    public interface SwingCellRenderer extends CellRenderer<SwingView> {
        /**
         * Called whenever a cell is clicked
         * @param source The board to edit
         * @param cellX The X-Coordinate of the cell
         * @param cellY Y-Coordinate of the cell
         * @return
         */
        public int cellClicked(Board source, int cellX, int cellY);

    }

    /**
     * Gets the ViewFactory associated with this View
     * @return The new ViewFactory
     */
    public static ViewFactory getFactory() {
        return new ViewFactory() {
            @Override
            public boolean canDisplay(CellRenderer renderer) {
                return renderer instanceof SwingCellRenderer;
            }

            @Override
            public View getView(CellRenderer renderer) {
                return new SwingView((SwingCellRenderer)renderer);
            }
        };
    }

    /**
     * Gets the position of a cell on the screen.
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return The location on the screen of the cell
     */
    public Point getPosition(int x, int y) {
        return new Point(getWidth()/board.getWidth() * x, getHeight()/board.getHeight() * y);
    }

    /**
     * Gets the size of a cell
     * @return The cell size
     */
    public Dimension getCellSize() {
        if (board.getWidth() == 0 || board.getHeight() == 0)
            return new Dimension(0,0);
        return new Dimension(getWidth()/board.getWidth(), getHeight()/board.getHeight());
    }

    /**
     * Creates a new SwingView with the given renderer
     * @param renderer The CellRenderer to use
     */
    public SwingView(final SwingCellRenderer renderer) {
        super(renderer);
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellWidth = getWidth()/board.getWidth();
                int cellHeight = getHeight()/board.getHeight();
                int x = e.getX()/cellWidth;
                int y = e.getY()/cellHeight;
                board.setState(renderer.cellClicked(board, x, y), x, y);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void draw(EditableBoard b) {
        board = b;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        currentGraphics = g;
        for (int i = 0; i < board.getWidth(); i++)
            for (int j = 0; j < board.getHeight(); j++) {
                renderer.drawCell(this, board, i, j);
            }
        currentGraphics = null;
    }
    
    private Graphics currentGraphics;

    /**
     * Gets the current Graphics context. Do not use getGraphics, use this instead
     * @return The current Graphics context
     */
    public Graphics getCurrentGraphics() {
        return currentGraphics;
    }


}
