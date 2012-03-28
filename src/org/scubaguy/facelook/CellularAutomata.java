package org.scubaguy.facelook;

import org.scubaguy.facelook.runners.BoardRunner;
import org.scubaguy.facelook.runners.BoardRunnerRepository;
import org.scubaguy.facelook.runners.RunnerFactory;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.rules.Rule;

import java.util.PriorityQueue;

/**
 * This class is a Cellular Automata. Provided with a Rule and dimensions, it can execute the rule across a board.
 * 
 */
public class CellularAutomata {

    private Rule rule;
    private BoardRunner runner;

    private EditableBoard top;
    private EditableBoard bottom;

    /**
     * Gets the width of the automata
     * @return The width
     */
    public int getWidth() {
        return top.getWidth();
    }

    /**
     * Gets the height of the automata
     * @return The height
     */
    public int getHeight() {
        return top.getHeight();
    }

    private void SetupRunner() {
        runner = BoardRunnerRepository.getInstance().getRunnerForRule(this, rule);
        if (runner == null)
            throw new RuntimeException("No valid runner found!");
    }
    /**
     * Creates a new Cellular automata with specified width and height. The created cells will be in the 0 state.
     * @param width The width of the new board
     * @param height The height of the board
     * @param rule The Automata rule
     */
    public CellularAutomata(int width, int height, Rule rule){
        this.rule = rule;

        top = new ConcreteEditableBoard(width, height);
        bottom = new ConcreteEditableBoard(width, height);
        SetupRunner();
    }

    /**
     * Creates a new Cellular automata with specified states
     * @param states A array of beginning states
     * @param rule The Automata rule
     */
    public CellularAutomata(int[][] states, Rule rule){

        this.rule = rule;

        top = new ConcreteEditableBoard(states);
        bottom = new ConcreteEditableBoard(states);
        SetupRunner();
    }

    /**
     * Creates a new Cellular Automata from an existing board.
     * @param board The board to use
     * @param rule  The rule
     */
    public CellularAutomata(EditableBoard board,Rule rule) {
        this.rule = rule;
        top = board;
        bottom = ConcreteEditableBoard.cloneBoard(board);
        SetupRunner();
    }
    

    /**
     * Gets the top board
     * @return The top board
     */
    public EditableBoard getTop() {
        return top;
    }

    /**
     * Gets the bottom board
     * @return the bottom board
     */
    public EditableBoard getBottom() {
        return bottom;
    }

    /**
     * Run the simulation one frame.
     */
    public void tick() {
        runner.tick();
        EditableBoard temp = top;
        top = bottom;
        bottom = temp;
    }
}
