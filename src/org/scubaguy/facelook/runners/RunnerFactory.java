package org.scubaguy.facelook.runners;

import org.scubaguy.facelook.CellularAutomata;
import org.scubaguy.facelook.rules.Rule;

/**
 * Determines if a runner is suitable tor a rule and creates the runner.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface RunnerFactory {
    /**
     * Checks to see if a rule is viable
     * @param rule The rule to check.
     * @return Is the runner suitable for the rule
     */
    public boolean isSuitable(Rule rule);

    /**
     * Gets how strict the runner is, in terms of rules. Difficulty should be an indicator of how small a category of
     * rules will be accepted. ANY will take whatever rule it is given, HARD takes almost none, and the other two are obvious.
     * @return The strictness of the rule.
     */
    public RunnerStrictness getStrictness();

    /**
     * Gets the runner that the factory can create
     * @param ca The CellularAutomata to bind to.
     * @param rule The Rule to use.
     * @return The new runner.
     */
    public BoardRunner buildRunner(CellularAutomata ca, Rule rule);
}


