package com.wordpress.tonytam.chefsmenu.model;

import com.wordpress.tonytam.chefsmenu.R;

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
    public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();

    /**
     * A map of sample (model) items, by ID.
     */
    public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();

    static {
        // TODO: Get this from a JSON data store locally that is refreshed
        // offline
        addItem(new MenuItem("1", "Signature",  R.drawable.nav_food_menu));
        addItem(new MenuItem("2", "Beyond Sashimi",  R.drawable.nav_drink_menu));
        addItem(new MenuItem("3", "Well Done Signature",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("4", "Designer Rolls",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("5", "Well Done Rolls",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("6", "Small Plates",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("7", "Bento Box Lunch",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("8", "Beyond Bento Box",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("9", "Classic Sushi",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("10", "Vegetarian",  R.drawable.nav_dessert_menu));
        addItem(new MenuItem("11", "Gluten Free",  R.drawable.nav_dessert_menu));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A menu item has text and an image
     *
     * toString() - returns the text
     * getImageResource() - returns the resource id of the image
     */
    public static class MenuItem {
        public String id;
        public String content;
        public int imageResource;

        public MenuItem(String id, String content, int imageResource) {
            this.id = id;
            this.content = content;
            this.imageResource = imageResource;
        }

        @Override
        public String toString() {
            return content;
        }

        public int getImageResource() { return imageResource; }
    }
}
