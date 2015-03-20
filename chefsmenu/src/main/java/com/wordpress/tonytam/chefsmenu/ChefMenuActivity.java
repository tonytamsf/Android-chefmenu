package com.wordpress.tonytam.chefsmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class ChefMenuActivity extends ActionBarActivity
    implements MenuListFragment.Callbacks,
        SubMenuFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<SubMenuFragment> subMenuFragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            subMenuFragments = new ArrayList<SubMenuFragment>(3);
        }

        @Override
        public Fragment getItem(int position) {
            if (false) {
                // getItem is called to instantiate the fragment for the given page.
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SubMenuFragment fragment = SubMenuFragment.newInstance(position + 1);
                fragmentTransaction.replace(R.layout.fragment_submenu, fragment);
                fragmentTransaction.commit();
                return fragment;
            } else {
                try {
                    if (subMenuFragments.get(position) != null) {
                        return subMenuFragments.get(position);
                    } else {
                        // save this, do not create new new fragments
                        subMenuFragments.add(position, SubMenuFragment.newInstance(position + 1));
                        return subMenuFragments.get(position);
                    }
                } catch (IndexOutOfBoundsException e) {
                    // save this, do not create new new fragments
                    subMenuFragments.add(position, SubMenuFragment.newInstance(position + 1));
                    return subMenuFragments.get(position);
                }
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onItemSelected (String id) {
        Toast.makeText(this.getBaseContext(), "onItemSelected", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
