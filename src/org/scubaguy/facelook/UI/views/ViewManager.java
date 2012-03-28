package org.scubaguy.facelook.UI.views;

import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.automata.views.SwingView;

import java.util.LinkedList;

/**
 * Manages the available types of views to display the cellular automata
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public final class ViewManager {
    private static ViewManager instance;
    public static ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();
        return instance;
    }
    private ViewManager() {
        addView(SwingView.getFactory());

    }
                             
    private LinkedList<ViewFactory> factories = new LinkedList<ViewFactory>();

    public void addView(ViewFactory view) {
        factories.add(view);
    }

    public View getViewForRenderer(CellRenderer renderer) {
        for (ViewFactory factory : factories) {
            if (factory.canDisplay(renderer))
                return factory.getView(renderer);
        }
        throw new RuntimeException("No view avaliable for the renderer");
    }
}
