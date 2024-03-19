package com.hardik.volleysample;

import android.app.Application;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hardik.volleysample.databinding.ActivityMainBinding;
import com.hardik.volleysample.models.DataResponseItem;
import com.hardik.volleysample.utils.ApplicationInstance;
import com.hardik.volleysample.volley.MyRepository;
import com.hardik.volleysample.volley.VolleyViewModel;
import com.hardik.volleysample.volley.VolleyViewModelProviderFactory;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public VolleyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VolleyViewModelProviderFactory viewModelProviderFactory = new VolleyViewModelProviderFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(VolleyViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

//        MyRepository.getInstance(this).getData();
//        VolleyViewModel viewModel = new ViewModelProvider(this,new VolleyViewModelProviderFactory(getApplication())).get(VolleyViewModel.class);
//        viewModel.getPostsLiveData().observe(this, dataResponseItems -> {
//            dataResponseItems.iterator().forEachRemaining(dataResponseItem ->{
//                Log.e("Api", "onChanged: "+dataResponseItem.getTitle());
//            });
//        });

        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL+"\\posts";//"https://www.google.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseJSONArray = new JSONArray(response);
                            for (int i = 0; i < responseJSONArray.length(); i++) {
                                JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);
                                Log.e("Api", "onResponse: "+responseJSONObject.toString() );
                                String title = responseJSONObject.getString("title");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> {
                    Log.e("Api", "onErrorResponse: " + error.getLocalizedMessage());
                });

        queue.add(stringRequest);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
/*
In Volley library, there are several types of requests you can make to communicate with a web server. Some of the common types of requests in Volley are:

StringRequest: Used for making a request for a string response from the server. Typically used for fetching textual data from a web service.

JsonArrayRequest: Used for making a request for a JSON array response from the server. Suitable for retrieving an array of JSON objects from a web service.

JsonObjectRequest: Used for making a request for a JSON object response from the server. Suitable for retrieving a single JSON object from a web service.

ImageRequest: Used for making a request for an image from the server. Useful for loading images into an ImageView directly from a URL.

GsonRequest: Used for making a request for a response that will be parsed into Java objects using Gson library. Suitable when the response from the server is in JSON format and needs to be converted into Java objects.

XmlRequest: Used for making a request for an XML response from the server. Suitable when the response from the server is in XML format.

ByteArrayRequest: Used for making a request for a raw byte array response from the server. Suitable for cases where the response is not in text or JSON format.

Each type of request has its own use case depending on the type of response expected from the server. For example:

Use StringRequest when you expect a plain text response.
Use JsonObjectRequest or JsonArrayRequest when you expect a JSON response.
Use ImageRequest when you need to load an image from a URL.
Use GsonRequest when you want to parse the JSON response into Java objects using Gson library.*/