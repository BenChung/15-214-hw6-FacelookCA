package org.scubaguy.facelook.tests;

import org.junit.Test;
import org.scubaguy.facelook.core.CellularAutomata;
import org.scubaguy.facelook.rules.Rule;
import org.scubaguy.facelook.runners.BoardRunner;
import org.scubaguy.facelook.runners.RunnerComparator;
import org.scubaguy.facelook.runners.RunnerFactory;
import org.scubaguy.facelook.runners.RunnerStrictness;

import static junit.framework.Assert.assertTrue;

/**
 * Tests for RunnerComparator
 * @author Benjamin Chung
 */
public class RunnerComparatorTest {
    private class TestCompareFactory implements RunnerFactory {
        private RunnerStrictness strictness;

        public TestCompareFactory(RunnerStrictness strictness) {

            this.strictness = strictness;
        }

        @Override
        public boolean isSuitable(Rule rule) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public RunnerStrictness getStrictness() {
            return strictness;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public BoardRunner buildRunner(CellularAutomata ca, Rule rule) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    @Test
    public void TestCompare() {
        RunnerComparator comparator = new RunnerComparator();
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.HARD), new TestCompareFactory(RunnerStrictness.HARD)) == 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.HARD), new TestCompareFactory(RunnerStrictness.MEDIUM)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.HARD), new TestCompareFactory(RunnerStrictness.SOFT)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.HARD), new TestCompareFactory(RunnerStrictness.ANY)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.MEDIUM), new TestCompareFactory(RunnerStrictness.HARD)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.MEDIUM), new TestCompareFactory(RunnerStrictness.MEDIUM)) == 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.MEDIUM), new TestCompareFactory(RunnerStrictness.SOFT)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.MEDIUM), new TestCompareFactory(RunnerStrictness.ANY)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.SOFT), new TestCompareFactory(RunnerStrictness.HARD)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.SOFT), new TestCompareFactory(RunnerStrictness.MEDIUM)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.SOFT), new TestCompareFactory(RunnerStrictness.SOFT)) == 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.SOFT), new TestCompareFactory(RunnerStrictness.ANY)) < 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.ANY), new TestCompareFactory(RunnerStrictness.HARD)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.ANY), new TestCompareFactory(RunnerStrictness.MEDIUM)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.ANY), new TestCompareFactory(RunnerStrictness.SOFT)) > 0);
        assertTrue(comparator.compare(new TestCompareFactory(RunnerStrictness.ANY), new TestCompareFactory(RunnerStrictness.ANY)) == 0);
    }
}
