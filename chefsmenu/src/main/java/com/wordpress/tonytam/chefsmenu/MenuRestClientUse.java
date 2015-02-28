package com.wordpress.tonytam.chefsmenu;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yahoo on 2/27/15.
 */

class MenuRestClientUse {
    public void getMenItems () {
        MenuRestClient.post("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.err.println("onSuccess " + response.toString());

                    // JSONObject firstEvent = (JSONObject) timeline.get(0);
                    // String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    // System.out.println(tweetText);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.err.println("onFailure" + responseString);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }
}
