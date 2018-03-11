package com.example.weatherforecastapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Viswa Chikkala March 10 2018
 */
public class Fragment_Insert_Record extends Fragment {
    Button btn_add_data;
    Spinner sp_coutries;
    EditText ed_enter_data;
    DataBaseHelper dataBaseHelper;
    String[] arr_country_code;
    String[] arr_country_name;
    String country_code="";
    String zip_code="";
    ProgressDialog pd;
    String response="";
    String country_name="";

    List<Weather_record> listWeatherRecord;
    public Fragment_Insert_Record() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insert_record, container, false);
        dataBaseHelper=new DataBaseHelper(getActivity());
        pd=new ProgressDialog(getActivity());
        btn_add_data=(Button)view.findViewById(R.id.btn_add);
        sp_coutries=(Spinner)view.findViewById(R.id.sp_country);
        ed_enter_data=(EditText)view.findViewById(R.id.ed_zip_code);
        arr_country_code = getResources().getStringArray(R.array.array_codes);
        arr_country_name=getResources().getStringArray(R.array.array_countries);
        sp_coutries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country_code=arr_country_code[i];
                country_name=arr_country_name[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipCode=""+ed_enter_data.getText();
                if(zipCode.equals("")){
                    Toast.makeText(getActivity(), "Please Enter The Zip CODE", Toast.LENGTH_SHORT).show();
                }
                Log.i("info",""+zipCode+" cc"+country_code);
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
//                Weather_record weather_record=new Weather_record();
//                weather_record.setCountry_code(country_code);
//                weather_record.setZip_code(zipCode);

                weather(zipCode,country_code);
//                dataBaseHelper.InsertData(weather_record);
            }
        });
        return view;
    }
    public void weather(final String zipc, final String cCode) {
        class requestData extends AsyncTask<String, Void, List<Weather_record>> {

            @Override
            protected void onPreExecute() {
                pd.setTitle("GETTING WEATHER UPDATES");
                pd.setMessage("Please Wait..");
                pd.show();
                pd.setCancelable(false);
                super.onPreExecute();
            }

            @Override
            protected List<Weather_record> doInBackground(String... params) {
                String country_cod = cCode;
                String zip_cod = zipc;
                String url = "https://api.openweathermap.org/data/2.5/weather?zip=" + zip_cod + "," + country_cod + "&appid=6181a2306e49fb2b2df664832ff2a3e1";
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                listWeatherRecord=new ArrayList<>();

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

                    Log.i("myResponse", buffer.toString());


                    JSONObject jsonObject = new JSONObject(buffer.toString());
                    JSONObject json_temp=jsonObject.getJSONObject("main");
                    String temperature=json_temp.getString("temp");
                    String city_name=jsonObject.getString("name");

                    Weather_record weather_recordd = new Weather_record();
                    weather_recordd.setCountry_code(cCode);
                    weather_recordd.setZip_code(zipc);
                    weather_recordd.setTemperature(temperature);
                    weather_recordd.setCity_name(city_name);
                    weather_recordd.setCountry_name(country_name);
                    listWeatherRecord.add(weather_recordd);





                    Log.i("iii",""+temperature);
                    //Toast.makeText(getActivity(), ""+temperature, Toast.LENGTH_SHORT).show();

                    response=buffer.toString();



                    Log.i("json",""+jsonObject);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return listWeatherRecord;
            }

            @Override
            protected void onPostExecute(List<Weather_record> list_data_weather) {
                Log.i("jjj",""+zipc);
                pd.dismiss();
                if(response.equals("")){
                    Toast.makeText(getActivity(), "PLEASE ENTER VALID ZIP CODE", Toast.LENGTH_SHORT).show();
                }
                else{

                    String zip_c=dataBaseHelper.showSingle(zipc);

                    Log.i("jjj","s:"+zip_c);
                    if(zipc.equals(zip_c)){
                           Toast.makeText(getActivity(), "Data Already Exist", Toast.LENGTH_SHORT).show();
                        response="";
                    }
                    else {
                        Weather_record weather_record = list_data_weather.get(0);
//                        weather_record.setCountry_code(cCode);
//                        weather_record.setZip_code(zipc);
                        Log.i("data",""+cCode+" : zipc"+zipc);
                        dataBaseHelper.InsertData(weather_record);
                        list_data_weather.clear();
                        listWeatherRecord.clear();
                        response="";
                    }
                }


                //Log.i("data",""+s);

                super.onPostExecute(list_data_weather);
            }

        }
        new requestData().execute();

    }
}


