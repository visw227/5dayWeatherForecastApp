package com.example.weatherforecastapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Fragment_Show_Locations extends Fragment {
    DataBaseHelper dataBaseHelper;
    ProgressDialog pd;
    String country_code;
    String zip_code;
    String country_name;
    int glob_position;
    RecyclerView recyclerView;
    List<Weather_record> weather_records;
    public Fragment_Show_Locations() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_record, container, false);
        dataBaseHelper=new DataBaseHelper(getActivity());
        pd=new ProgressDialog(getActivity());
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        weather_records=dataBaseHelper.ShowAll();

        final LocationAdapter locationAdapter=new LocationAdapter(getActivity(),weather_records);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //alert for confirm to delete
                    builder.setMessage("Don't Want Me!!!");    //set message for deleting the entry

                    builder.setPositiveButton("Not Anymore", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Weather_record weather_record=weather_records.get(position);
                            int status=0;
                            String zipcode=weather_record.getZip_code();
                            status=dataBaseHelper.deleteSingleData(zipcode);
                            if(status==1) {
                                locationAdapter.notifyItemRemoved(position);    //item removed from recylcerview
                                weather_records.remove(position);
                            }
                            else {

                                locationAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                                locationAdapter.notifyItemRangeChanged(position, locationAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            }


                            return;
                        }
                    }).setNegativeButton("Stay Back", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            locationAdapter.notifyItemRemoved(position +1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            locationAdapter.notifyItemRangeChanged(position, locationAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show();  //show alert dialog
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); //set swipe to recylcerview

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        for(int k=0;k<weather_records.size();k++)
        {
            Weather_record weather_record=weather_records.get(k);
            String coutry=weather_record.getCountry_name();
            String city_name=weather_record.getCity_name();
            Log.i("data",""+coutry+" : city name "+city_name);
        }

        recyclerView.setAdapter(locationAdapter);



        return view;
    }
    private void ItemClickedMethod(final int i) {

        final CharSequence options[] = new CharSequence[]{"EDIT RECORD"
                ,"SHOW FIVE DAYS FORECAST", "CANCEL"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Clear Data");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("EDIT RECORD")) {
                    Fragment_update_Record insert_frag = new Fragment_update_Record();
                    Bundle args = new Bundle();
                    args.putString("country", country_name);
                    args.putString("zipcode", zip_code);
                    insert_frag.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.rl_main_layout, insert_frag).commit();

                } else if (options[which].equals("SHOW FIVE DAYS FORECAST")) {

                    Intent intent = new Intent(getActivity(), Detail_five_Days_Weather_Activity.class);
                    intent.putExtra("country_code", "" + country_code);
                    intent.putExtra("zip_code", "" + zip_code);
                    getActivity().startActivity(intent);
                }
                 else if (options[which].equals("CANCEL")) {

    }
            }
        });
        builder.show();
    }


    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

        Context context;
        List<Weather_record> list_weather_location;
        public LocationAdapter(Context context, List<Weather_record> list_weather_location){
            this.context = context;
            this.list_weather_location = list_weather_location;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_adapter, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Weather_record weather_record= list_weather_location.get(position);
            holder.tv_country.setText(weather_record.getCity_name());
            holder.tv_zipcode.setText(weather_record.getZip_code());
            Double temp=Double.parseDouble(weather_record.getTemperature());
            Double centigrade_temperature=temp-273.15;
            long ln_temp = Math.round(centigrade_temperature);

            holder.tv_teperature.setText(""+ln_temp+"°C");


            holder.ln_row_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    country_code=weather_record.getCountry_code();
                    country_name=weather_record.getCountry_name();
                    zip_code=weather_record.getZip_code();
                    ItemClickedMethod(position);
                    glob_position=position;
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return list_weather_location.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_country,tv_zipcode,tv_teperature;
            LinearLayout ln_row_layout;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv_country =(TextView) itemView.findViewById(R.id.tv_country);
                tv_zipcode =(TextView) itemView.findViewById(R.id.tv_zipcode);
                tv_teperature =(TextView) itemView.findViewById(R.id.tv_teperature);
                ln_row_layout=(LinearLayout)itemView.findViewById(R.id.ln_row_layout);
            }
        }
    }

}


