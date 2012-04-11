package org.scubaguy.facelook.tests;

import org.junit.Before;
import org.junit.Test;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.exceptions.InvalidCellException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Ben Chung
 * Date: 3/30/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteEditableBoardTest {
    ConcreteEditableBoard board;
    @Before
    public void buildUp() {
        board = new ConcreteEditableBoard(new int[][] {new int[] {1,1,1}, new int[] {1,0,1}, new int[] {1,1,1}});    
    }

    @Test
    public void testIsValid() throws Exception {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertTrue(board.isValid(i,j));
        assertFalse(board.isValid(-1,-1));
        assertFalse(board.isValid(-1,1));
        assertFalse(board.isValid(3,3));
    }

    @Test
    public void testGetState() throws Exception {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (i == 1 && j == 1)
                    assertEquals(board.getState(i,j), 0);
                else
                    assertEquals(board.getState(i,j), 1);
    }

    @Test(expected = InvalidCellException.class)
    public void testGetState2() {
        board.getState(-1, -1);
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(board.getWidth(), 3);
    }

    @Test
    public void testGetHeight() throws Exception {
        assertEquals(board.getHeight(), 3);
    }

    @Test
    public void testSetState() throws Exception {
        board.setState(1,1,1);
        assertEquals(board.getState(1,1), 1);
        board.setState(0, 1, 1);
        assertEquals(board.getState(1,1), 0);
    }

    @Test
    public void testCloneBoard() throws Exception {
        ConcreteEditableBoard board1 = ConcreteEditableBoard.cloneBoard(board);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertEquals(board.getState(i,j), board1.getState(i,j));
    }
}
