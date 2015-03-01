package com.wordpress.tonytam.chefsmenu;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wordpress.tonytam.chefsmenu.model.MenuContent;
import com.wordpress.tonytam.chefsmenu.model.MenuItem;

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

    MenuRestClientUse(Fragment a) {
        this.a = a;
    }

    public interface dataReady {
        public void onDataReady(ArrayList<MenuItem> items);
    }
        
    public void fetchMenuItems (final dataReady d) {

        if (MenuContent.ITEMS != null && MenuContent.ITEMS.size() > 0) {
            // already got data
            d.onDataReady(MenuContent.ITEMS);

            return;
        }
        MenuRestClient.post("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                System.err.println("onSuccess " + response.toString());

                    try {
                        JSONArray menus = response.getJSONArray("venues").getJSONObject(0)
                                .getJSONArray("menus");
                                
                        JSONObject section;
                        for (int i = 0; i < menus.length(); i++) {
                            section = menus.getJSONObject(i);
                            JSONArray sections = section.getJSONArray("sections");
                            for (int s = 0; s < sections.length(); s++) {
                                JSONArray subsections = sections.getJSONObject(s).getJSONArray("subsections");
                                for (int ss = 0; ss < subsections.length(); ss++)  {
                                    JSONArray contents = subsections.getJSONObject(ss).getJSONArray("contents");
                                    MenuContent.ITEMS.addAll(MenuItem.fromJson(contents));

                                }
                            }
                        }

                        d.onDataReady(MenuContent.ITEMS);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(a.getActivity().getBaseContext(), "FAIL: "+responseString, Toast.LENGTH_LONG).show();
                System.err.println("onFailure" + responseString);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
        
    }
}
