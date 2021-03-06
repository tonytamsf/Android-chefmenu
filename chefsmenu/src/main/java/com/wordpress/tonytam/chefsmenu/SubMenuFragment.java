package com.wordpress.tonytam.chefsmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wordpress.tonytam.chefsmenu.dummy.DummyContent;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;
import com.wordpress.tonytam.chefsmenu.model.MenuSection;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SubMenuFragment extends Fragment implements AbsListView.OnItemClickListener {

    String menuName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String STATE_ACTIVATED_MENU = "activated_menu";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static SubMenuFragment newInstance(String param1, String param2) {
        SubMenuFragment fragment = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }


        if (savedInstanceState == null) {
            final View view;
            //view = container.findViewById(R.id.menu_item_list);
            // TODO: learn NestedFragments http://developer.android.com/about/versions/android-4.2.html#NestedFragments

                try {
                    view = inflater.inflate(R.layout.fragment_submenu, container, false);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    MenuListFragment fragment =  MenuListFragment.newInstance(this.menuName);
                    fragmentTransaction.add(R.id.menu_placeholder_menu_detail, fragment);
                    fragmentTransaction.commit();

                    mListView = (AbsListView) view.findViewById(android.R.id.list);

                    // Network, fetch data
                    MenuRestClientUse fetcher = new MenuRestClientUse(this.getActivity());
                    fetcher.fetchMenuItems(new MenuRestClientUse.dataReady() {
                        @Override
                        public void onDataReady(MenuSection menu) {
                            MenuSection firstSection = menu;
                            // filter based on menuName
                            ArrayList<MenuItem> items = firstSection.menuSections.get(0).getAllItems();

                            mAdapter = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    android.R.id.text1,
                                    firstSection.getSubmenus());
                            /*
                            mAdapter = new SubMenuArrayAdaptor(
                                    getActivity(),
                                    R.layout.fragment_submenu_section,
                                    R.id.submenu_text,
                                    sections);
                                    */
                            // Set the adapter
                            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);


                        }
                    });

                    // Set OnItemClickListener so we can be notified on item clicks
                    mListView.setOnItemClickListener(this);
                    return view;
                } catch (InflateException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.menu_placeholder_menu_detail);
        if (f != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(f);
            // workaround: http://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-h
            fragmentTransaction.commitAllowingStateLoss();

        }
    }

    public static SubMenuFragment newInstance(String menuName) {
        SubMenuFragment fragment = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putString(STATE_ACTIVATED_MENU, menuName);
        fragment.setArguments(args);
        fragment.menuName = menuName;
        return fragment;
    }
}
