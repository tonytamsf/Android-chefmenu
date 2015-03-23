package com.wordpress.tonytam.chefsmenu.model;

import java.util.ArrayList;

/**
 * A list of menu items or menu sections
 *
 * Created by yahoo on 3/2/15.
 */
public class MenuSection {
    static public MenuSection topMenu;
    public enum MENU_TYPE {
        MENU_TYPE_ITEM,
        MENU_TYPE_SECTION
    }

    public MENU_TYPE type;
    public ArrayList<MenuItem> menuItems;
    public ArrayList<MenuSection> menuSections;

    public String name;

    public  MenuSection () {
        name = "no name";
    }

    public ArrayList<MenuItem> getAllItems() {
        return getAllItems(menuSections);
    }

    public ArrayList<MenuItem> getAllItems(ArrayList<MenuSection> secs) {
        ArrayList<MenuItem> all = new ArrayList<MenuItem>();

        for (int i = 0; i < secs.size(); i++) {
            if (secs.get(i).menuItems != null) {
                all.addAll( secs.get(i).menuItems );
            } else {
                 all.addAll(getAllItems(secs.get(i).menuSections));
            }
        }
        return all;
    }

    /**
     *
     * @return ArrayList<String> list of submenus
     */
    public ArrayList<String> getSubmenus() {
        ArrayList<MenuSection> secs = this.menuSections;
        ArrayList<String> subMenus = new ArrayList<String>();

        for (int i = 0; i < secs.size(); i++) {
            if (secs.get(i).menuSections != null) {
                subMenus.add( secs.get(i).name );
            }
        }
        return subMenus;
    }

    @Override
    public String toString() {
        return name;
    }
    /**
     * Get the first level menus
     * @return ArrayList<String> of menu titles
     */
    public ArrayList<String> getTopLevelMenus() {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < topMenu.menuSections.size(); i++) {
            result.add(topMenu.menuSections.get(i).toString());
        }
        return result;
    }
}
