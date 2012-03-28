package org.scubaguy.facelook.automata.runners;

import org.scubaguy.facelook.CellularAutomata;
import org.scubaguy.facelook.rules.Rule;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.runners.BoardRunner;
import org.scubaguy.facelook.runners.RunnerFactory;
import org.scubaguy.facelook.runners.RunnerStrictness;

/**
 * A basic single-threaded implementation of BoardRunner
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class SimpleRunner implements BoardRunner {

    private CellularAutomata ca;
    private Rule rule;

    private static class RFactory implements RunnerFactory {
        @Override
        public boolean isSuitable(Rule rule) {
            return true;
        }

        @Override
        public RunnerStrictness getStrictness() {
            return RunnerStrictness.ANY;
        }

        @Override
        public BoardRunner buildRunner(CellularAutomata ca, Rule rule) {
            return new SimpleRunner(ca, rule);
        }
    }

    public static RunnerFactory getFactory() {
        return new RFactory();
    }

    public SimpleRunner(CellularAutomata ca, Rule rule) {
        this.ca = ca;
        this.rule = rule;
    }

    @Override
    public void tick() {
        Board from = ca.getTop();
        EditableBoard to = ca.getBottom();
        for (int i = 0; i < from.getWidth(); i++)
            for (int j = 0; j < from.getHeight(); j++)
                to.setState(rule.getState(from, i, j), i, j);
    }
}
