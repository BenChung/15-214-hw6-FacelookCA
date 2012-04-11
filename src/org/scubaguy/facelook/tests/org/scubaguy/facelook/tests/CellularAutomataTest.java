package org.scubaguy.facelook.tests;

import org.junit.Before;
import org.junit.Test;
import org.scubaguy.facelook.core.CellularAutomata;
import org.scubaguy.facelook.automata.runners.SimpleRunner;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.rules.Rule;
import org.scubaguy.facelook.runners.BoardRunnerRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Ben Chung
 * Date: 4/1/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CellularAutomataTest {

    private CellularAutomata ca;

    @Before
    public void setup() {
        BoardRunnerRepository.getInstance().addRunnerFactory(SimpleRunner.getFactory());
        ca = new CellularAutomata(10,10, new Rule() {
            @Override
            public int getState(Board b, int x, int y) {
                return 1;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    @Test
    public void testCons1() throws Exception {
        CellularAutomata test = new CellularAutomata(new int[][]{{1,1},{0,0}}, new Rule() {
            @Override
            public int getState(Board b, int x, int y) {
                return 0;
            }
        });
        for (int i = 0; i < 2; i++) {
            assertEquals(test.getTop().getState(0,i), 1);
            assertEquals(test.getTop().getState(1,i), 0);
        }
    }

    @Test
    public void testCons2() throws Exception {
        EditableBoard testTop = new ConcreteEditableBoard(3,3);
        CellularAutomata test = new CellularAutomata(testTop, new Rule() {
            @Override
            public int getState(Board b, int x, int y) {
                return 0;
            }
        });
        assertEquals(testTop, test.getTop());
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(ca.getWidth(), 10);
    }

    @Test
    public void testGetHeight() throws Exception {
        assertEquals(ca.getHeight(), 10);
    }

    @Test
    public void testGetTop() throws Exception {
        assertNotNull(ca.getTop());
    }

    @Test
    public void testGetBottom() throws Exception {
        assertNotNull(ca.getBottom());
    }

    @Test
    public void testTick() throws Exception {
        EditableBoard top = ca.getTop();
        EditableBoard bottom = ca.getBottom();

        ca.tick();
        
        assertEquals(top, ca.getBottom());
        assertEquals(bottom,ca.getTop());
        
        for (int i = 0; i < ca.getWidth(); i++)
            for (int j = 0; j<ca.getHeight();j++)
            {
                assertEquals(ca.getTop().getState(i,j), 1);
            }
    }
}
