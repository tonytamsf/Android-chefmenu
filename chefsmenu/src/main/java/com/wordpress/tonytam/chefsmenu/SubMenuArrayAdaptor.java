package com.wordpress.tonytam.chefsmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wordpress.tonytam.chefsmenu.model.MenuSection;

import java.util.List;

/**
 * Created by yahoo on 3/24/15.
 */
public class SubMenuArrayAdaptor extends ArrayAdapter<MenuSection> {

    public SubMenuArrayAdaptor(Context context, int resource, int textViewResourceId, List<MenuSection> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
