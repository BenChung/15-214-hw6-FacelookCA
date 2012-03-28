import org.scubaguy.facelook.CellularAutomata;
import org.scubaguy.facelook.UI.CADialog;
import org.scubaguy.facelook.automata.views.SwingView;
import org.scubaguy.facelook.boardloaders.BoardLoader;
import org.scubaguy.facelook.boardloaders.BoardLoaderRepository;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.exceptions.InvalidFileFormatException;
import org.scubaguy.facelook.rules.ThreadableRule;
import org.scubaguy.facelook.rules.Util;

import java.awt.*;
import java.io.*;

public class Wireworld {
    private static final int EMPTY = 0;
    private static final int CONDUCTOR = 1;
    private static final int ELECTRON_HEAD = 2;
    private static final int ELECTRON_TAIL = 3;

    private static class WireRule implements ThreadableRule {

        @Override
        public int getState(Board board, int x, int y) {
            int state = board.getState(x, y);
            if (state == EMPTY)
                return 0;
            else if  (state == CONDUCTOR) {
                int n = Util.getNeighborsWithState(board, ELECTRON_HEAD, x, y);
                if (n == 1 || n == 2)
                    return ELECTRON_HEAD;
                else
                    return CONDUCTOR;
            }
            else if (state == ELECTRON_HEAD) {
                return ELECTRON_TAIL;
            }
            else if (state == ELECTRON_TAIL) {
                return CONDUCTOR;
            }
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

    }
    private static class WireRenderer
        implements SwingView.SwingCellRenderer {

            @Override
            public void drawCell(SwingView target, Board source, int x, int y) {
                int state = source.getState(x, y);
                Graphics graphics = target.getCurrentGraphics();
                if (state == EMPTY)
                    graphics.setColor(Color.BLACK);
                else if (state == CONDUCTOR)
                    graphics.setColor(Color.YELLOW);
                else if (state == ELECTRON_HEAD)
                    graphics.setColor(Color.BLUE);
                else if (state == ELECTRON_TAIL)
                    graphics.setColor(Color.RED);

                Point start = target.getPosition(x, y);
                Dimension size = target.getCellSize();

                graphics.fillRect(start.x, start.y, size.width, size.height);
            }
        @Override
        public int cellClicked(Board board, int cellX, int cellY) {
            return (board.getState(cellX, cellY)+1)%4;
        }
    }
    public static class WireLoader implements BoardLoader {
        @Override
        public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            int x;
            int y;
            try {
                String dimensions = reader.readLine();
                String[] dims = dimensions.split(" ");
                x = Integer.parseInt(dims[0]);
                y = Integer.parseInt(dims[1]);

                EditableBoard board = new ConcreteEditableBoard(x, y);
                String line = "";
                int lineNum = 0;
                while ((line = reader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        if (i > x)
                            throw new InvalidFileFormatException();
                        switch (line.charAt(i)) {
                            case ' ': board.setState(EMPTY, i, lineNum); break;
                            case '#': board.setState(CONDUCTOR, i, lineNum); break;
                            case '~': board.setState(ELECTRON_TAIL, i, lineNum); break;
                            case '@': board.setState(ELECTRON_HEAD, i, lineNum); break;
                            default:
                                throw new InvalidFileFormatException();
                        }
                    }
                    if (lineNum > y)
                        throw new InvalidFileFormatException();
                    lineNum++;
                }
                return board;
            } catch (IOException e) {
                throw new InvalidFileFormatException();
            }
        }

        @Override
        public String[] getSupportedExtensions() {
            return new String[] {"wi"};
        }
    }

    public static void main(String[] args) {
        BoardLoaderRepository.getInstance().addBoardLoader(new WireLoader());
        CellularAutomata ca = new CellularAutomata(BoardLoaderRepository.getInstance().getBoardFromFile("primes.wi"), new WireRule());
        CADialog cap = new CADialog(ca, new WireRenderer());
        cap.show();
    }
}
