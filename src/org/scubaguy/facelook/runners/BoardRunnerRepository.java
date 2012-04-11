package org.scubaguy.facelook.runners;

import org.scubaguy.facelook.core.CellularAutomata;
import org.scubaguy.facelook.automata.runners.MTBoardRunner;
import org.scubaguy.facelook.automata.runners.SimpleRunner;
import org.scubaguy.facelook.rules.Rule;

import java.util.PriorityQueue;

/**
 * Stores factories for all BoardRunners, and creates new BoardRunners on demand.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class BoardRunnerRepository {


    private static BoardRunnerRepository instance;
    public static BoardRunnerRepository getInstance() {
        if (instance == null)
            instance = new BoardRunnerRepository();
        return instance;
    }
    
    public PriorityQueue<RunnerFactory> runners = new PriorityQueue<RunnerFactory>(10, new RunnerComparator());

    private BoardRunnerRepository() {
        runners.add(SimpleRunner.getFactory());
        runners.add(MTBoardRunner.getFactory());
    }

    /**
     * Add a runner factory to create runners to execute automata. The potential runners will be tested in the order they are passed in, and the first runner that is successful is the one that will be used.
     *
     * @param runner The runner factory to add
     */
    public void addRunnerFactory(RunnerFactory runner) {
        if (runner == null)
            throw new IllegalArgumentException("Runner null");
        runners.add(runner);
    }

    /**
     * Creates a new runner for the given rule. Returns null if no runner is found.
     * @param ca The cellular automaton to bind
     * @param rule The rule to create a Runner for
     * @return THe created Runner.
     */
    public BoardRunner getRunnerForRule(CellularAutomata ca, Rule rule) {
        for (RunnerFactory factory : runners) {
            if (factory.isSuitable(rule))
                return factory.buildRunner(ca, rule);
        }
        return null;
    }
}