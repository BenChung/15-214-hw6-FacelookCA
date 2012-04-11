import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.UI.views.ViewFactory;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;

import java.awt.*;

public class BinaryView extends View {
    public interface BinaryRenderer extends CellRenderer<BinaryView>{}
    
    private static class BinaryViewFactory implements ViewFactory {

        @Override
        public boolean canDisplay(CellRenderer renderer) {
            return renderer instanceof BinaryRenderer;
        }

        @Override
        public View getView(CellRenderer renderer) {
            return new BinaryView(renderer);
        }
        
    }

    public static ViewFactory getFactory() {
        return new BinaryViewFactory();
    }

    Board currentBoard;
    Graphics drawing = null;
    public BinaryView(CellRenderer renderer) {
        super(renderer);
    }

    public void drawCell(boolean v, int x, int y) {
        if (drawing == null || currentBoard == null || currentBoard.getWidth() == 0 || currentBoard.getWidth() == 0)
            return;

        if (v)
            drawing.setColor(Color.WHITE);
        else
            drawing.setColor(Color.BLACK);

        drawing.fillRect(getWidth()/currentBoard.getWidth() * x,
                getHeight()/currentBoard.getHeight() * y,
                getWidth()/currentBoard.getWidth(), getHeight()/currentBoard.getHeight());
    }

    @Override
    public void draw(EditableBoard b) {
        currentBoard = b;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawing = g;
        for (int i = 0; i < currentBoard.getWidth();i++)
            for (int j = 0; j < currentBoard.getHeight();j++) {
                renderer.drawCell(this, currentBoard, i, j);
            }
        drawing = null;
    }
}
