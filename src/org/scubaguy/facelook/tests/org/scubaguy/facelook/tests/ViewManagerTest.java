package org.scubaguy.facelook.tests;

import org.junit.Test;
import org.scubaguy.facelook.UI.CellRenderer;
import org.scubaguy.facelook.UI.views.View;
import org.scubaguy.facelook.UI.views.ViewFactory;
import org.scubaguy.facelook.UI.views.ViewManager;
import org.scubaguy.facelook.boards.EditableBoard;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Tests for View Manager
 */
public class ViewManagerTest {
    @Test
    public void testGetInstance() throws Exception {
        ViewManager instance = ViewManager.getInstance();
        ViewManager instance2 = ViewManager.getInstance();
        assertFalse(instance == null);
        assertEquals(instance, instance2);
    }

    @Test
     public void testAddView() throws Exception {
        ViewManager instance = ViewManager.getInstance();

        instance.addView(new ViewFactory() {
            @Override
            public boolean canDisplay(CellRenderer renderer) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public View getView(CellRenderer renderer) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddView2() throws Exception {
        ViewManager instance = ViewManager.getInstance();
        instance.addView(null);
    }

    @Test
    public void testGetViewForRenderer() throws Exception {
        ViewManager instance = ViewManager.getInstance();
        final View lookingFor = new View(null) {
            @Override
            public void draw(EditableBoard b) {
                
            }
        };
        
        instance.addView(new ViewFactory() {
            @Override
            public boolean canDisplay(CellRenderer renderer) {
                return true;
            }

            @Override
            public View getView(CellRenderer renderer) {
                return lookingFor;
            }
        });    

        assertEquals(instance.getViewForRenderer(null), lookingFor);
    }
}
