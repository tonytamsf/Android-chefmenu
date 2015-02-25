package com.wordpress.tonytam.chefsmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.tonytam.chefsmenu.R;
import com.wordpress.tonytam.chefsmenu.dummy.MenuContent;

import java.util.List;

/**
 * Created by yahoo on 2/22/15.
 */
public class MenuItemArrayAdaptor extends ArrayAdapter<MenuContent.MenuItem> {
    private final Context context;
    private final List<MenuContent.MenuItem> values;

    public MenuItemArrayAdaptor(Context context, List<MenuContent.MenuItem> values) {
        super(context, R.layout.main_menu_navigation, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_menu_navigation, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.menu_id);
        textView.setText(values.get(position).toString());

        //
        String s = values.get(position).toString();

        ImageView imageView = (ImageView) rowView.findViewById(R.id.menu_image);
        imageView.setImageResource(values.get(position).getImageResource());

        return rowView;
    }
}
