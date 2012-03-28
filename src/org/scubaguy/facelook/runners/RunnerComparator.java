package org.scubaguy.facelook.runners;

import java.util.Comparator;

/**
 * Compares two different RunnerFactories based on difficulty of selection.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class RunnerComparator implements Comparator<RunnerFactory>{
    @Override
    public int compare(RunnerFactory o1, RunnerFactory o2) {
        return o2.getStrictness().getInt() - o1.getStrictness().getInt();
    }
}
