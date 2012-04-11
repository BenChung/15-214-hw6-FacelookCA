package org.scubaguy.facelook.tests;

import org.junit.Test;
import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.rules.Util;

import static junit.framework.Assert.assertTrue;

/**
 * Tests for the Runner framework
 * @author Benjamin Chung
 */
public class RuleUtilTests {
    @Test
    public void TestNeighbors() {
        Board testBoard = new ConcreteEditableBoard(new int[][] {new int[] {1,1,1}, new int[] {1,0,1}, new int[] {1,1,1}});
        assertTrue(Util.getNeighborsWithState(testBoard, 1,1, 1) == 8);
    }
}
