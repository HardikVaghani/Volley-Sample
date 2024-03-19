package com.hardik.volleysample.volley;

import static com.hardik.volleysample.utils.Constants.BASE_URL;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyRepository {
    private static volatile MyRepository instance;
    private final RequestQueue requestQueue;

    private MyRepository(Context context) {
        Context context1 = context.getApplicationContext();
        this.requestQueue = Volley.newRequestQueue(context1);
    }

    public static MyRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (MyRepository.class) {
                if (instance == null) {
                    instance = new MyRepository(context);
                }
            }
        }
        return instance;
    }

    String url = BASE_URL + "/posts";

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseJSONArray = new JSONArray(response);
                            for (int i = 0; i < responseJSONArray.length(); i++) {
                                JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);
                                Log.e("Api", "onResponse: " + responseJSONObject.toString());
                                String title = responseJSONObject.getString("title");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> {
            Log.e("Api", "onErrorResponse: " + error.getLocalizedMessage());
        });
        requestQueue.add(stringRequest);
    }

}
