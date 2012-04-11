package org.scubaguy.facelook.tests;

import org.junit.Test;
import org.scubaguy.facelook.core.CellularAutomata;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.rules.Rule;
import org.scubaguy.facelook.runners.BoardRunner;
import org.scubaguy.facelook.runners.BoardRunnerRepository;
import org.scubaguy.facelook.runners.RunnerFactory;
import org.scubaguy.facelook.runners.RunnerStrictness;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;

/**
 * Tests for the BoardRunnerRepository
 * @author Benjamin Chung
 */
public class BoardRunnerRepositoryTest {
    private final Rule testRule = new Rule() {
        @Override
        public int getState(Board b, int x, int y) {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };
    
    
    @Test
    public void testGetInstance() throws Exception {
        BoardRunnerRepository instance = BoardRunnerRepository.getInstance();
        BoardRunnerRepository instance2 = BoardRunnerRepository.getInstance();
        assertFalse(instance == null);
        assertEquals(instance, instance2);
    }

    @Test
    public void testAddRunnerFactory() throws Exception {
        BoardRunnerRepository brd = BoardRunnerRepository.getInstance();
        final BoardRunner lookingFor = new BoardRunner() {
            @Override
            public void tick() {
                
            }
        };
        
        brd.addRunnerFactory(new RunnerFactory() {

            @Override
            public boolean isSuitable(Rule rule) {
                return rule == testRule;
            }

            @Override
            public RunnerStrictness getStrictness() {
                return RunnerStrictness.HARD;
            }

            @Override
            public BoardRunner buildRunner(CellularAutomata ca, Rule rule) {
                return lookingFor;
            }
        });
    
        assertEquals(brd.getRunnerForRule(new CellularAutomata(0, 0, testRule), testRule), lookingFor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRunnerFactory2() throws Exception {
        BoardRunnerRepository brd = BoardRunnerRepository.getInstance();
        brd.addRunnerFactory(null);
    }

    @Test(expected = RuntimeException.class)
    public void testGetRunnerForRule() throws Exception {
        BoardRunnerRepository brr = BoardRunnerRepository.getInstance();
        final BoardRunner lookingFor = new BoardRunner() {
            @Override
            public void tick() {

            }
        };

        brr.addRunnerFactory(new RunnerFactory() {
            @Override
            public boolean isSuitable(Rule rule) {
                return rule == testRule;
            }

            @Override
            public RunnerStrictness getStrictness() {
                return RunnerStrictness.ANY;
            }

            @Override
            public BoardRunner buildRunner(CellularAutomata ca, Rule rule) {
                return lookingFor;
            }
        });
    
        assertEquals(brr.getRunnerForRule(new CellularAutomata(0, 0, null), null), null);
    }
}
