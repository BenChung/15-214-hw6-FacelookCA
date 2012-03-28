package org.scubaguy.facelook.boardloaders;

import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.exceptions.InvalidFileFormatException;

import java.io.InputStream;

/**
 * Defines functions that allow a file to be read into a board.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface BoardLoader {
    /**
     * Gets the board described in the given stream
     * @param input The stream read from the file
     * @return The board described in the file
     * @throws InvalidFileFormatException If the given stream is in a invalid format
     *
     */
    public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException;

    /**
     * Gets the extensions supported by the plugin
     * @return A array of extensions. The array should not contain the . symbol.
     */
    public String[] getSupportedExtensions();
}
