import org.scubaguy.facelook.CellularAutomata;
import org.scubaguy.facelook.UI.CADialog;
import org.scubaguy.facelook.automata.renderers.BinaryRenderer;
import org.scubaguy.facelook.automata.rules.GOL;

public class GameOfLife {
    public static void main(String[] args) {

        CellularAutomata ca = new CellularAutomata(50,50, new GOL());
        CADialog cap = new CADialog(ca, new BinaryRenderer());
        cap.show();
    }
}
