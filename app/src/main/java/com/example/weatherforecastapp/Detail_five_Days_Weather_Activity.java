package com.example.weatherforecastapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Detail_five_Days_Weather_Activity extends AppCompatActivity {
    String country_code;
    String zip_code;
    ProgressDialog pd;
    RecyclerView recyclerView;
    String date_check_glob="";
    String other_date="";
    long timeInMilliseconds;
    List<Weather_record> list_five_day_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_five__days__weather_);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_five_days);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(Detail_five_Days_Weather_Activity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        pd=new ProgressDialog(Detail_five_Days_Weather_Activity.this);
        country_code=getIntent().getStringExtra("country_code");
        zip_code=getIntent().getStringExtra("zip_code");
        Log.i("ddd",""+zip_code+"cc"+country_code);
        new requestData().execute(zip_code,country_code);
    }
    class requestData extends AsyncTask<String, Void, List<Weather_record>> {

        @Override
        protected void onPreExecute() {
            pd.setTitle("GETTING WEATHER OF FIVE DAYS");
            pd.setMessage("Please Wait..");
            pd.show();
            pd.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected List<Weather_record> doInBackground(String... params) {
            String zipcode=params[0];
            String country_code=params[1];
            //http://api.openweathermap.org/data/2.5/forecast?zip=25000,PK&appid=6181a2306e49fb2b2df664832ff2a3e1  //data_fivedays
            String url = "http://api.openweathermap.org/data/2.5/forecast?zip=" + zipcode + "," + country_code + "&appid=6181a2306e49fb2b2df664832ff2a3e1";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            list_five_day_weather=new ArrayList<>();

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

                Log.i("fffff", buffer.toString());
                list_five_day_weather.clear();
                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray list_json_array=jsonObject.getJSONArray("list");
                //////////////////////////////////////////////////////////////////////////////////////////////

                //////////////////////////////////////////////////////////////////////////////


                for(int s=0;s<list_json_array.length();s++){
                    JSONObject jsonobject_index=list_json_array.getJSONObject(s);

                    String date_time=jsonobject_index.getString("dt_txt");


                    /////////////////////////////////////////////////////////////////////
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try
                    {
                        Date mDate = sdf.parse(date_time);
                        timeInMilliseconds = mDate.getTime();
                        System.out.println("Date in milli :: " + timeInMilliseconds);
                    }
                    catch (ParseException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    other_date = formatter.format(new Date(timeInMilliseconds));

                    /////////////////////////////////////////////////////////////////////
                    if(date_check_glob.equals(other_date)){



                    }
                    else{
                        date_check_glob=other_date;

                        JSONObject jsonobject_main=jsonobject_index.getJSONObject("main");
                        String temperature=jsonobject_main.getString("temp_max");
                        String humidity=jsonobject_main.getString("humidity");
                        String pressure=jsonobject_main.getString("pressure");
                        String minumun_temperature=jsonobject_main.getString("temp_min");
                        JSONArray jsonArray_weather=jsonobject_index.getJSONArray("weather");
                        JSONObject jsonObject_weather_index=jsonArray_weather.getJSONObject(0);
                        String weather_condition=jsonObject_weather_index.getString("main");
                        String weather_condition_few_c=jsonObject_weather_index.getString("description");

                        Weather_record weather_record=new Weather_record();
                        //////////////////////////////////////////////////////
                        Double temp=Double.parseDouble(temperature);
                        Double centigrade_temperature=temp-273.15;
                        long ln_temp = Math.round(centigrade_temperature);
                        //////////////////////////////////////////////////////
                        Double tempmin=Double.parseDouble(minumun_temperature);
                        Double centigrade_temperature_min=tempmin-273.15;
                        long ln_temp_min = Math.round(centigrade_temperature_min);

                        /////////////////////////////////////////////////////


                        String temperature_data_string=""+ln_temp;//max temperature
                        String temperature_min=""+ln_temp_min;//min temperature
                        weather_record.setTemperature(temperature_data_string);
                        weather_record.setWeather_condition(weather_condition);
                        weather_record.setDate_time(date_time);
                        weather_record.setPressure(pressure);
                        weather_record.setHumidity(humidity);
                        weather_record.setDate_milliseconds(timeInMilliseconds);
                        weather_record.setMinimum_temperature(""+temperature_min);


                        list_five_day_weather.add(weather_record);


                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list_five_day_weather;
        }

        @Override
        protected void onPostExecute(List<Weather_record> list_five_days) {

            pd.dismiss();
            List<Weather_record> list_days=list_five_days;
            LocationFiveDaysAdapter locationFiveDaysAdapter=new LocationFiveDaysAdapter(Detail_five_Days_Weather_Activity.this,list_days);
            recyclerView.setAdapter(locationFiveDaysAdapter);


            super.onPostExecute(list_five_days);
        }

    }

}
