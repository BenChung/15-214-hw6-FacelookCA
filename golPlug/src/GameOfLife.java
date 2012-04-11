import org.scubaguy.facelook.core.CellularAutomata;
import org.scubaguy.facelook.UI.CADialog;
import org.scubaguy.facelook.automata.renderers.BinaryRenderer;
import org.scubaguy.facelook.automata.rules.GOL;
import org.scubaguy.facelook.automata.runners.MTBoardRunner;
import org.scubaguy.facelook.runners.BoardRunnerRepository;

public class GameOfLife {
    public static void main(String[] args) {
        BoardRunnerRepository.getInstance().addRunnerFactory(MTBoardRunner.getFactory());
        CellularAutomata ca = new CellularAutomata(50,50, new GOL());
        CADialog cap = new CADialog(ca, new BinaryRenderer());
        cap.show();
    }
}
