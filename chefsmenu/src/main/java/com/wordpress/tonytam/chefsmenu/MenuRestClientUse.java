package com.wordpress.tonytam.chefsmenu;

import android.support.v4.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;
import com.wordpress.tonytam.chefsmenu.model.MenuSection;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yahoo on 2/27/15.
 */

class MenuRestClientUse {
    Fragment a;

    MenuRestClientUse(Fragment a ) {
        this.a = a;
    }
    MenuRestClientUse( ) {
        this.a = null;
    }

    public interface dataReady {
        public void onDataReady(ArrayList<MenuSection> items);
    }
        
    public void fetchMenuItems (final dataReady d) {
        final MenuRestClientUse that = this;

        if (MenuSection.topMenu != null) {
            // already got data
            d.onDataReady(MenuSection.topMenu.menuSections);

            return;
        }
        MenuRestClient.post("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                System.err.println("onSuccess " + response.toString());


                MenuSection menus = new MenuSection();
                MenuSection.topMenu = menus;

                menus.menuSections = new ArrayList<MenuSection>();

                    try {
                        JSONArray menusJson = response.getJSONArray("venues").getJSONObject(0)
                                .getJSONArray("menus");

                        JSONObject sectionJson;
                        for (int i = 0; i < menusJson.length(); i++) {
                            MenuSection menu = new MenuSection();
                            menus.menuSections.add(menu);

                            menu.menuSections = new ArrayList<MenuSection>();
                            menus.menuSections.add(menu);

                            sectionJson = menusJson.getJSONObject(i);
                            menu.name = sectionJson.getString("menu_name");

                            JSONArray sectionsJson = sectionJson.getJSONArray("sections");

                            for (int s = 0; s < sectionsJson.length(); s++) {
                                MenuSection section = new MenuSection();
                                section.menuSections = new ArrayList<MenuSection>();
                                menu.menuSections.add(section);

                                JSONArray subsectionsJson = sectionsJson.getJSONObject(s).getJSONArray("subsections");
                                section.name = sectionsJson.getJSONObject(s).getString("section_name");

                                for (int ss = 0; ss < subsectionsJson.length(); ss++)  {
                                    MenuSection subsection = new MenuSection();
                                    section.menuSections.add(subsection);

                                    subsection.menuItems = new ArrayList<MenuItem>();

                                    JSONArray contents = subsectionsJson.getJSONObject(ss).getJSONArray("contents");
                                    //subsection.name = subsectionsJson.getJSONObject(ss).getString("section_name");
                                    System.err.println(contents.toString());
                                    subsection.menuItems.addAll(MenuItem.fromJson(contents));
                                }
                            }
                        }

                        d.onDataReady(menus.menuSections);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //Toast.makeText(a.getActivity().getBaseContext(), "FAIL: "+responseString, Toast.LENGTH_LONG).show();
                System.err.println("onFailure" + responseString);
                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + responseString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.err.println("onFailure" + errorResponse);
                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + errorResponse, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.err.println("onFailure: " + errorResponse);
                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + errorResponse + statusCode + headers, Toast.LENGTH_LONG).show();

                try {
                    if (errorResponse != null &&
                            errorResponse.get("http_status") == 429) {
                     that.fetchMenuItems(d);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
        
    }
}
