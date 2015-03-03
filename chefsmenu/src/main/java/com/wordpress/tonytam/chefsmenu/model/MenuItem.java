package com.wordpress.tonytam.chefsmenu.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonytam on 2/28/15.
 */
public class MenuItem {
    public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();

    public String name;
    public TYPE section_type;
    public String description;
    public String primary_image_url;

    public enum TYPE {
        MENU_ITEM,
        SECTION_TEXT,
        SECTION,
        SUBSECTION
    }

    ArrayList<String> photos;
    ArrayList<String> options;
    
    public static MenuItem fromJson(JSONObject jsonObject) {
        MenuItem m = new MenuItem();
        try {
            String type = jsonObject.getString("type");
            if (type.equals("ITEM")) {
                m.name = jsonObject.getString("name");
                m.description = jsonObject.getString("description");
                m.section_type = TYPE.MENU_ITEM;


                JSONArray o;
                o = jsonObject.optJSONArray("photos");
                if (o != null) {
                    m.primary_image_url = o.get(0).toString();
                } else {
                    m.primary_image_url = "https://static1.squarespace.com/static/528ef1eee4b02543d3a4caab/t/54f2a72ee4b09389aa38dd8e/1425188654968/placeholder.jpeg";
                }
            } else {
                m.description = jsonObject.getString("text");
                m.name = "";
                m.primary_image_url = "https://static1.squarespace.com/static/528ef1eee4b02543d3a4caab/t/54f2a72ee4b09389aa38dd8e/1425188654968/placeholder.jpeg";
                m.section_type = TYPE.SECTION_TEXT;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return m;
    }
    
    public static ArrayList<MenuItem> fromJson(JSONArray jsonArray) {
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(jsonArray.length());
        
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject menuJson = null;
            try {
                menuJson = jsonArray.getJSONObject(i);
                System.err.println("Menu item: " + menuJson.toString());
                
            } catch(JSONException e) {
                e.printStackTrace();
                continue;
            }
            
            MenuItem m = MenuItem.fromJson(menuJson);
            if (m != null) {
                menuItems.add(m);
            }

        }
        return menuItems;
    }
}
