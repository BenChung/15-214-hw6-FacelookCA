package org.scubaguy.facelook.automata.rules;

import org.scubaguy.facelook.boards.Board;
import org.scubaguy.facelook.rules.ThreadableRule;
import org.scubaguy.facelook.rules.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A interpreter from MCell notated CA rules to facelook rules. Threadable.
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public class MCell implements ThreadableRule {
    private static Pattern verifer =
            Pattern.compile("B([1-9]*)/S([1-9]*)");

    private final int[] birth;
    private final int[] survives;

    /**
     * Creates a new rule from MCell notation, in the form B[numbers]/S[numbers]. Look it up!
     * @param desc The MCell rule
     * @author Benjamin Chung, Hank Zwally, Cory Williams
     */
    public MCell(String desc) {
        Matcher matcher = verifer.matcher(desc);
        if (!matcher.matches())
            throw new RuntimeException(desc);
        String bs = matcher.group(1);
        birth = new int[bs.length()];
        for (int i = 0; i < bs.length(); i++)
            birth[i] = Integer.parseInt(""+bs.charAt(i));
        String ss = matcher.group(2);
        survives = new int[ss.length()];
        for (int i = 0; i < ss.length(); i++)
            survives[i] = Integer.parseInt("" + ss.charAt(i));
    }
    
    @Override
    public int getState(Board b, int x, int y) {
        int n = Util.getNeighborsWithState(b, 1, x, y);
        
        for (int i = 0; i < birth.length; i++) {
            if (birth[i] == n)
                return 1;
        }
        for (int i = 0; i < survives.length; i++)
            if (b.getState(x,y) == 1 && survives[i] == n)
                return 1;

        return 0;
    }

}
