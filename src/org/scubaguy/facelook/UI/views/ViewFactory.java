package org.scubaguy.facelook.UI.views;

import org.scubaguy.facelook.UI.CellRenderer;

/**
 * Determines if a view is suitable for the given renderer
 * @author Benjamin Chung, Hank Zwally, Cory Williams
 */
public interface ViewFactory {
    /**
     * Tests to see if the renderer is suitable for the rule
     * @param renderer The renderer to test
     * @return is the rule suitable
     */
    public boolean canDisplay(CellRenderer renderer);

    /**
     * Gets the view created by the factory
     * @return The created view
     */
    public View getView(CellRenderer renderer);
}
