package org.scubaguy.facelook.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scubaguy.facelook.boardloaders.BoardLoader;
import org.scubaguy.facelook.boardloaders.BoardLoaderRepository;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.exceptions.InvalidFileFormatException;

import java.io.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Ben Chung
 * Date: 4/1/12
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardLoaderRepositoryTest {
    BoardLoaderRepository bl;
    String testFile;
    String testFile2;
    @Before
    public void setup() {
        bl = BoardLoaderRepository.getInstance();
        try {
            File tempfile = File.createTempFile("hw6Tests",".test");
            testFile = tempfile.getAbsolutePath();
            tempfile = File.createTempFile("hw6Tests",".test2");
            testFile2 = tempfile.getAbsolutePath();
            FileOutputStream fop = new FileOutputStream(tempfile);
            fop.write("3,3 111|111|111".getBytes());
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    @After
    public void cleanup() {
        new File(testFile).delete();
        new File(testFile2).delete();
    }
    
    @Test
    public void testGetInstance() throws Exception {
        assertEquals(bl, BoardLoaderRepository.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddBoardLoader() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        bl.addBoardLoader(null);
    }

    @Test
    public void testAddBoardLoader2() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        final EditableBoard testBoard = new ConcreteEditableBoard(3,3);
        bl.addBoardLoader(new BoardLoader() {
            @Override
            public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException {
                return testBoard;
            }

            @Override
            public String[] getSupportedExtensions() {
                return new String[] {"test"};
            }
        });
        assertEquals(bl.getBoardFromFile(testFile), testBoard);
    }

    @Test
    public void testGetBoardFromFile() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        final EditableBoard testBoard = new ConcreteEditableBoard(3,3);
        assertEquals(bl.getBoardFromFile(testFile, new BoardLoader() {
            @Override
            public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException {
                return testBoard;
            }

            @Override
            public String[] getSupportedExtensions() {
                return new String[] {"test"};
            }
        }), testBoard);
    }


    @Test(expected = RuntimeException.class)
    public void testGetBoardFromFile2() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        final EditableBoard testBoard = new ConcreteEditableBoard(3,3);
        bl.getBoardFromFile("nonexistant.test");
    }

    @Test(expected = RuntimeException.class)
    public void testGetBoardFromFile3() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        final EditableBoard testBoard = new ConcreteEditableBoard(3,3);
        bl.getBoardFromFile(testFile2);
    }

    @Test(expected = RuntimeException.class)
    public void testGetBoardFromFile4() throws Exception {
        BoardLoaderRepository bl = BoardLoaderRepository.getInstance();
        final EditableBoard testBoard = new ConcreteEditableBoard(3,3);
        bl.getBoardFromFile(testFile, new BoardLoader() {
            @Override
            public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException {
                throw new InvalidFileFormatException();
            }

            @Override
            public String[] getSupportedExtensions() {
                return new String[] {".test"};  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
