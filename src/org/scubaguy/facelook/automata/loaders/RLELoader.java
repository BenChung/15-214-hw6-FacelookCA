package org.scubaguy.facelook.automata.loaders;

import org.scubaguy.facelook.boardloaders.BoardLoader;
import org.scubaguy.facelook.boards.ConcreteEditableBoard;
import org.scubaguy.facelook.boards.EditableBoard;
import org.scubaguy.facelook.exceptions.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Loads the standard RLE format. Does not support custom rules.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class RLELoader implements BoardLoader{
    @Override
    public EditableBoard getBoard(InputStream input) throws InvalidFileFormatException {
        BufferedReader inReader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        try {
            int x = 0;
            int y = 0;
            while((line = inReader.readLine()) != null) {
                if (line.length() <= 0)
                    continue;
                
                else if (line.charAt(0) == '#')
                    continue;
                
                else if (line.charAt(0) == 'x') {
                    String[] commands = line.split(",");
                    for (int i = 0; i < commands.length; i++) {
                        String[] command = commands[i].trim().split("=");
                        if (command.length == 0)
                            continue;
                        if (command[0].trim().equals("x"))
                            x = Integer.parseInt(command[1].trim());
                        else if (command[0].trim().equals("y"))
                            y = Integer.parseInt(command[1].trim());
                    }
                }
                else 
                    break;
            }


            EditableBoard outBoard = new ConcreteEditableBoard(x, y);
            int insertLine = 0;
            int n = 0;
            int repeat = 1;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (Character.isDigit(ch)) {
                    repeat = Character.digit(ch, 10);
                } else if (Character.isLetter(ch)) {
                    int state = (ch == 'b')?0:1;
                    for (;repeat>0;repeat--) {
                        outBoard.setState(state, n, insertLine);
                        n++;
                    }
                    repeat = 1;
                } else if (ch == '$') {
                    n = 0;
                    insertLine++;
                } else if (ch == '!') {
                    break;
                }
            }

            return outBoard;
        } catch (IOException e) {
            throw new InvalidFileFormatException();
        }
    }

    @Override
    public String[] getSupportedExtensions() {
        return new String[] {"rle"};
    }
}
