package com.wordpress.tonytam.chefsmenu;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class MenuRestClient {
    private static final String BASE_URL = "https://api.locu.com/v2/venue/search";



    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String someData = "{\n" +
                "  \"api_key\" : \"f165c0e560d0700288c2f70cf6b26e0c2de0348f\",\n" +
                "  \"fields\" : [ \"name\", \"location\", \"contact\",\"menus\" ],\n" +
                "  \"venue_queries\" : [\n" +
                "    {\n" +
                "      \"locu_id\" : \"5ea0bbf14816ecd9a155\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        StringEntity entity = null;
        try {
            entity = new StringEntity(someData);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception e) {
//Exception
        }
        client.post(null,getAbsoluteUrl(url),entity,"application/json",responseHandler);


       // client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
