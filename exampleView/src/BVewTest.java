import org.scubaguy.facelook.UI.CADialog;
import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.ViewManager;
import org.scubaguy.facelook.automata.rules.GOL;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.core.CellularAutomata;

public class BVewTest {
    public static void main(String[] args) {
        ViewManager.getInstance().addView(BinaryView.getFactory());

        CellularAutomata ca = new CellularAutomata(40,40, new GOL());
        CADialog cd = new CADialog(ca, new BinaryView.BinaryRenderer(){
            public void drawCell(BinaryView view, Board board, int x, int y) {
                view.drawCell(board.getState(x,y)==1, x, y);
            }
        });
        cd.show();
    }
}
