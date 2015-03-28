package com.wordpress.tonytam.chefsmenu;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;

import java.util.List;

/**
 * Created by tonytam on 2/28/15.
 */
public class MenuItemArrayAdaptor extends ArrayAdapter <MenuItem> {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView image;
        TextView description;

    }
    
    public Picasso _builder;
    
    public MenuItemArrayAdaptor(Context context, int resource, int textViewResourceId, List<MenuItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        MenuItem item = getItem(position);
        if (convertView == null ) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_menu_detail,
                    parent,
                    false);
            
            viewHolder.name = (TextView) convertView.findViewById(R.id.menu_detail_name);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.menu_image);
            viewHolder.description = (TextView) convertView.findViewById(R.id.menu_detail_description);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.name);
            viewHolder.description.setText(item.description);

            getPicasso()
                    .with(getContext())
                    .load(Uri.parse(item.primary_image_url))
                    .noFade()
                    .placeholder(R.drawable.placeholder)
                    .priority(Picasso.Priority.HIGH)
                    .fit()
                    .centerInside()
                    .into(viewHolder.image);

        return convertView;
    }
    
    public Picasso getPicasso() {
        if (_builder == null) {
            _builder = new Picasso.Builder(getContext()).memoryCache(new LruCache(200*1024*1024)).build();
        }
        return _builder;
    }
}
