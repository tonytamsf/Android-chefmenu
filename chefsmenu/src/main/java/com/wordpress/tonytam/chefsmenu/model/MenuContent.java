package com.wordpress.tonytam.chefsmenu.model;

import com.wordpress.tonytam.chefsmenu.R;

import com.wordpress.tonytam.chefsmenu.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MenuContent {

    /**
     * An array of sample (model) items.
     */
    public static ArrayList<MenuItem> ITEMS = new ArrayList<MenuItem>();

    /**
     * A map of sample (model) items, by ID.
     */
    public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }
}
