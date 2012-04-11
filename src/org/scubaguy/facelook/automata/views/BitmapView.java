package org.scubaguy.facelook.automata.views;

import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.UI.views.ViewFactory;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A view that uses a grid of swing squares to render the automata
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class BitmapView extends View {
    EditableBoard board;

    /**
     * This interface provides a way for CellRenderers to render onto the image
     */
    public interface BitmapCellRenderer extends CellRenderer<BitmapView> {
    }

    /**
     * Gets the ViewFactory associated with this View
     * @return The new ViewFactory
     */
    public static ViewFactory getFactory() {
        return new ViewFactory() {
            @Override
            public boolean canDisplay(CellRenderer renderer) {
                return renderer instanceof BitmapCellRenderer;
            }

            @Override
            public View getView(CellRenderer renderer) {
                return new BitmapView((BitmapCellRenderer)renderer);
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
    public BitmapView(final BitmapCellRenderer renderer) {
        super(renderer);
        

    }

    @Override
    public void draw(EditableBoard b) {
        board = b;
        repaint();
    }

    private BufferedImage bufferedImage;
    private int[] pixels;
    private boolean isWriting = false;
    
    @Override
    public void paintComponent(Graphics g) {
        isWriting = true;
        if (pixels == null)
            pixels = new int[board.getWidth() * board.getHeight()];
        bufferedImage = new BufferedImage(board.getWidth(), board.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < board.getWidth(); i++)
            for (int j = 0; j < board.getHeight(); j++) {
                renderer.drawCell(this, board, i, j);
            }
        isWriting = false;
        bufferedImage.setRGB(0,0,board.getWidth(), board.getHeight(), pixels, 0, board.getWidth());
        g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
    
    public void drawPixel(int rgb, int x, int y) {
        if (!isWriting || bufferedImage == null)
            throw new RuntimeException();
        pixels[x + y*board.getWidth()] = rgb;
    }


}
