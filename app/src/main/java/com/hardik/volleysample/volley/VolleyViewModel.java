package com.hardik.volleysample.volley;

import static com.hardik.volleysample.utils.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hardik.volleysample.R;
import com.hardik.volleysample.models.DataResponse;
import com.hardik.volleysample.models.DataResponseItem;
import com.hardik.volleysample.utils.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VolleyViewModel extends AndroidViewModel {
    private MutableLiveData<List<DataResponseItem>> postsLiveData;
    private final RequestQueue requestQueue;

    private MutableLiveData<Resource<DataResponse>> posts = new MutableLiveData<>();
    public LiveData<Resource<DataResponse>> getPosts;
    private DataResponse dataResponse = null;


    public VolleyViewModel(@NonNull Application application) {
        super(application);
        postsLiveData = new MutableLiveData<>();
        this.requestQueue = Volley.newRequestQueue(application.getApplicationContext());
    }

    public void getData(){
        posts.postValue(safeDataCallPosts());
        getPosts = posts;
    }
    private Resource<DataResponse> safeDataCallPosts() {
        posts.postValue(new Resource.Loading<>());
            if (hasInternetConnection()) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + "/posts",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    List<DataResponseItem> dataResponseItems = new ArrayList<DataResponseItem>();
                                    JSONArray responseJSONArray = new JSONArray(response);
                                    for (int i = 0; i < responseJSONArray.length(); i++) {
                                        JSONObject responseJsonObject = responseJSONArray.getJSONObject(i);

                                        // Convert JSONObject to your model class using Gson
                                        Gson gson = new Gson();
                                        DataResponseItem dataResponseItem = gson.fromJson(responseJsonObject.toString(), DataResponseItem.class);
                                        dataResponseItems.add(dataResponseItem);
                                    }
                                    if (!dataResponseItems.isEmpty()) {
                                        if (dataResponse == null) {
                                            dataResponse = new DataResponse();
                                        }
                                        dataResponse.addAll(dataResponseItems);
                                        posts.postValue(new Resource.Success<>(dataResponse));
                                    }
//                                    posts.postValue(new Resource.Success<>(dataResponse));

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, error -> {
                    posts.postValue(new Resource.Error<>(error.getLocalizedMessage()));
                    Log.e("Api", "onErrorResponse: " + error.getLocalizedMessage());
                });
                requestQueue.add(stringRequest);

            } else {
                posts.postValue(new Resource.Error<>("NoInternetConnection"));
            }

        return new Resource.Success<>(dataResponse);
    }

    public LiveData<List<DataResponseItem>> getPostsLiveData() {
        safeDataCall();
        return postsLiveData;
    }

    private void safeDataCall() {
        if (hasInternetConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + "/posts",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<DataResponseItem> dataResponseItems = new ArrayList<DataResponseItem>();
                                JSONArray responseJSONArray = new JSONArray(response);
                                for (int i = 0; i < responseJSONArray.length(); i++) {
                                    JSONObject responseJsonObject = responseJSONArray.getJSONObject(i);

                                    // Convert JSONObject to your model class using Gson
                                    Gson gson = new Gson();
                                    DataResponseItem dataResponseItem = gson.fromJson(responseJsonObject.toString(), DataResponseItem.class);
                                    dataResponseItems.add(dataResponseItem);

//                                    Log.e("Api", "onResponse: " + responseJsonObject.toString());
//                                    String title = responseJsonObject.getString("title");
                                }
                                postsLiveData.postValue(dataResponseItems);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, error -> {
                postsLiveData.postValue(null);
                Log.e("Api", "onErrorResponse: " + error.getLocalizedMessage());
            });
            requestQueue.add(stringRequest);
        }
    }

    /// check internet connection
    @SuppressLint("ObsoleteSdkInt")
    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        } else {
            assert connectivityManager.getActiveNetworkInfo() != null;
            return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI
                    || connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE
                    || connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_ETHERNET;
        }
    }
}
