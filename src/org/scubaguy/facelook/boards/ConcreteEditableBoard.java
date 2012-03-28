package org.scubaguy.facelook.boards;

import org.scubaguy.facelook.exceptions.InvalidCellException;

/**
 * The fundamental board
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class ConcreteEditableBoard implements EditableBoard {
    private int[][] cells;
    public ConcreteEditableBoard(int w, int h) {
        cells = new int[w][h];
    }
    public ConcreteEditableBoard(int[][] cells) {
        this.cells = cells;
    }

    public boolean isValid(int x, int y) {
        return x < cells.length && x >= 0 && y < cells[x].length && y >= 0;
    }
    public int getState(int x, int y) {
        if (!isValid(x, y))
            throw new InvalidCellException();
        return cells[x][y];
    }

    @Override
    public int getWidth() {
        return cells.length;
    }

    @Override
    public int getHeight() {
        return cells[0].length;
    }

    public void setState(int v, int x, int y) {
        if (!isValid(x, y))
            throw new InvalidCellException();
        cells[x][y] = v;
    }
    
    public static ConcreteEditableBoard cloneBoard(Board source) {
        ConcreteEditableBoard output = new ConcreteEditableBoard(source.getWidth(), source.getHeight());
        for (int i = 0; i < source.getWidth(); i++) {
            for (int j = 0; j < source.getHeight(); j++)
                output.setState(source.getState(i,j),i,j);
        }
        return output;
    }
}
