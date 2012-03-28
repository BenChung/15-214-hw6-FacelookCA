package org.scubaguy.facelook.boardloaders;

import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.exceptions.InvalidFileFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This class keeps track of the avaliable file format parsers and calls the appropriate one for the format, based on extension or explict definition
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public final class BoardLoaderRepository {
    private HashMap<String, BoardLoader> loaders = new HashMap<String, BoardLoader>();
    private static BoardLoaderRepository instance;
    public static BoardLoaderRepository getInstance() {
        if (instance == null)
            instance = new BoardLoaderRepository();
        return instance;
    }

    private BoardLoaderRepository() {}
    

    public void addBoardLoader(BoardLoader loader) {
        for (String extension : loader.getSupportedExtensions()) {
            if (loaders.containsKey(extension))
                throw new RuntimeException("Invalid extension: "+extension);

            loaders.put(extension, loader);
        }
    }

    public EditableBoard getBoardFromFile(String filename)  {
        File file = new File(filename);

        if (!file.exists())
            throw new RuntimeException(new FileNotFoundException());

        String name = file.getName();
        String ext = name.substring(name.lastIndexOf('.')+1, name.length());

        if (!loaders.containsKey(ext))
            throw new RuntimeException(new InvalidFileFormatException());

        try {
            return loaders.get(ext).getBoard(new FileInputStream(file));
        } catch (InvalidFileFormatException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public EditableBoard getBoardFromFile(String filename, BoardLoader loader) throws FileNotFoundException {
        File file = new File(filename);

        if (!file.exists())
            throw new RuntimeException(new FileNotFoundException());

        try {
            return loader.getBoard(new FileInputStream(file));
        } catch (InvalidFileFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
