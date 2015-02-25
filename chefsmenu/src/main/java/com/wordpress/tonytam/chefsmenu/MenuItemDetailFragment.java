package com.wordpress.tonytam.chefsmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wordpress.tonytam.chefsmenu.model.MenuContent;

/**
 * A fragment representing a single MenuItem detail screen.
 * This fragment is either contained in a {@link MenuItemListActivity}
 * in two-pane mode (on tablets) or a {@link MenuItemDetailActivity}
 * on handsets.
 */
public class MenuItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The model content this fragment is presenting.
     */
    private MenuContent.MenuItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * *
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the model content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MenuContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menuitem_detail, container, false);

        // Show the model content as text in a TextView.
        if (mItem != null) {
            // TODO: ((TextView) rootView.findViewById(R.id.menuitem_detail)).setText(mItem.content);
        }

        return rootView;
    }
}

/*


<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/menuitem_detail"
    style="?android:attr/textAppearanceLarge" android:layout_width="match_parent"
    android:layout_height="match_parent" android:padding="16dp" android:textIsSelectable="true"
    tools:context=".MenuItemDetailFragment" />


 */
