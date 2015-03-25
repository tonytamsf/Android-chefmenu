package com.wordpress.tonytam.chefsmenu;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;
import com.wordpress.tonytam.chefsmenu.model.MenuSection;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by yahoo on 2/27/15.
 */

class MenuRestClientUse {
    public interface dataReady {
        public void onDataReady(ArrayList<MenuSection> items);
    }

    Activity a;

    MenuRestClientUse(Activity a ) {
        this.a = a;
    }
    MenuRestClientUse( ) {
        this.a = null;
    }
        
    public void fetchMenuItems (final dataReady d) {
        final MenuRestClientUse that = this;

        if (MenuSection.topMenu != null) {
            // already got data
            d.onDataReady(MenuSection.topMenu.menuSections);

            return;
        }
        if (a != null) {
            ConnectivityManager cm = (ConnectivityManager) a
                    .getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected()) {
                    readOffineData(d);
                    return;
                }
            }
        }
        MenuRestClient.post("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                System.err.println("onSuccess " + response.toString());
                MenuSection menus = parseMenuData(response);
                if (menus != null) {
                    d.onDataReady(menus.menuSections);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(a.getActivity().getBaseContext(), "FAIL: "+responseString, Toast.LENGTH_LONG).show();
                System.err.println("onFailure" + responseString);
                readOffineData(d);

                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + responseString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.err.println("onFailure" + errorResponse);
                readOffineData(d);

                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + errorResponse, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.err.println("onFailure: int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse " + errorResponse);
                //Toast.makeText(that.a.getActivity().getBaseContext(), "onFailure" + errorResponse + statusCode + headers, Toast.LENGTH_LONG).show();

                readOffineData(d);

                try {
                    if (errorResponse != null &&
                            errorResponse.get("http_status") == 429) {
                        Log.d("Nework","locu.com blocking us on too many requests: "+errorResponse.toString() );
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

    private MenuSection parseMenuData (JSONObject response) {
        MenuSection menus = new MenuSection();

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
                // data is ready
                MenuSection.topMenu = menus;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return menus;
    }

    public void readOffineData( final dataReady d ){
        JSONObject obj = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();

        // TODO: REALLY?  When is a null?
        if (a == null) {
            return;
        }

        AssetManager assetManager = a.getAssets();
        String OFFLINE_URL = "assets";
        String[] files = null;

        try {
            files = assetManager.list("menu");
        } catch (IOException e) {
            Log.e("MenuRestClientUse", e.getMessage());
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(assetManager.open("menu/menu.json")));
            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();

            while (mLine != null) {
                //process line
                stringBuffer.append(mLine);
                mLine = reader.readLine();
            }

        } catch (IOException e) {
            //log the exception
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        //d.onDataReady(MenuSection.topMenu.menuSections);
        try {
            obj = new JSONObject(stringBuffer.toString());
        } catch(JSONException e) {
            e.printStackTrace();
            return;
        }
        MenuSection menus = parseMenuData(obj);
        if (menus != null) {
            d.onDataReady(menus.menuSections);
        }
    }
}
