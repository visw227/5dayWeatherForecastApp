package com.example.weatherforecastapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
Viswa CHIKKALA- March 2018
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog pd;
    DataBaseHelper dataBaseHelper;
    List<Weather_record> list_weather_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd=new ProgressDialog(getApplicationContext());
        list_weather_record=new ArrayList<>();
        dataBaseHelper=new DataBaseHelper(getApplication());
        list_weather_record=dataBaseHelper.ShowAll();
        if(list_weather_record.size()>0){

        }
        else{

            Weather_record weather_record=new Weather_record();
            weather_record.setCountry_code("US");
            weather_record.setCountry_name("UNITED STATES");
            weather_record.setZip_code("68164");
            weather_record.setTemperature("261.45");
            weather_record.setCity_name("Omaha");
            dataBaseHelper.InsertData(weather_record);


            Weather_record weather_recordsec=new Weather_record();
            weather_recordsec.setCountry_code("IN");
            weather_recordsec.setCountry_name("India");
            weather_recordsec.setZip_code("533003");
            weather_recordsec.setTemperature("291.45");
            weather_recordsec.setCity_name("Kakinada");
            dataBaseHelper.InsertData(weather_recordsec);

            Weather_record weather_recordthir=new Weather_record();
            weather_recordthir.setCountry_code("GB");
            weather_recordthir.setCountry_name("UNITED KINGDOM");
            weather_recordthir.setZip_code("EC2M");
            weather_recordthir.setTemperature("285.42");
            weather_recordthir.setCity_name("London");
            dataBaseHelper.InsertData(weather_recordthir);
        }

        Fragment_Show_Locations insert_frag = new Fragment_Show_Locations();
        getSupportFragmentManager().beginTransaction().add(R.id.rl_main_layout, insert_frag).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




//        new requestData().execute("","");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_location) {
            Fragment_Insert_Record insert_frag = new Fragment_Insert_Record();
            getSupportFragmentManager().beginTransaction().add(R.id.rl_main_layout, insert_frag).commit();

        } else if (id == R.id.show_location) {
            Fragment_Show_Locations insert_frag = new Fragment_Show_Locations();
            getSupportFragmentManager().beginTransaction().add(R.id.rl_main_layout, insert_frag).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
        class requestData extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                pd.setTitle("GETTING WEATHER UPDATES");
                pd.setMessage("Please Wait..");
                pd.show();
                pd.setCancelable(false);
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String country_name=params[0];
                String zip_code=params[1];
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=islamabad&key=AIzaSyC1K9py0hD9-q_yty4mNAdy21johpH-l5Q";
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;


                try {

                    URL url1 = new URL(url);

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url1.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {

                        buffer.append(line).append("\n");
                    }

                    Log.d("myResponse", buffer.toString());


                    JSONObject jsonObject = new JSONObject(buffer.toString());


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {

//                pd.dismiss();


                super.onPostExecute(s);
            }
        }



}
