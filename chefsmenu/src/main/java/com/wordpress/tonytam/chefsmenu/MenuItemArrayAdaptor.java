package com.wordpress.tonytam.chefsmenu;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonytam on 2/28/15.
 */
public class MenuItemArrayAdaptor extends ArrayAdapter <MenuItem> {
    public MenuItemArrayAdaptor(Context context, int resource, int textViewResourceId, List<MenuItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem item = getItem(position);
        if (convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_menu_detail,
                    parent,
                    false
            );
        }
        
        TextView name = (TextView) convertView.findViewById(R.id.menu_detail_name);
        ImageView image =  (ImageView) convertView.findViewById(R.id.menu_image);
        TextView description = (TextView) convertView.findViewById(R.id.menu_detail_description);
        
        name.setText(item.name);
        description.setText(item.description);
        
        Picasso.with(getContext()).load(Uri.parse(item.primary_image_url)).into(image);

        return convertView;
        
    }
}
