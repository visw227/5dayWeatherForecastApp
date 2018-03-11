package com.example.weatherforecastapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;


public class LocationFiveDaysAdapter extends RecyclerView.Adapter<LocationFiveDaysAdapter.MyViewHolder> {

    Context context;
    List<Weather_record> list_weather_location;
    Weather_record weather_record;
    public LocationFiveDaysAdapter(Context context, List<Weather_record> list_weather_location){
        this.context = context;
        this.list_weather_location = list_weather_location;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_five_days_adapter, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String day_of_week_string="";
        String weather_condtionss="";
        weather_record= list_weather_location.get(position);
        weather_condtionss=weather_record.getWeather_condition();
        long milli=weather_record.getDate_milliseconds();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(milli);
        int day_of_week=calendar1.get(Calendar.DAY_OF_WEEK);
        if(day_of_week==7){
            day_of_week_string="Saturday";
        }
        else if(day_of_week==1){
            day_of_week_string="Sunday";
        }
        else if(day_of_week==2){
            day_of_week_string="Monday";
        }
        else if(day_of_week==3){
            day_of_week_string="Tuesday";
        }
        else if(day_of_week==4){
            day_of_week_string="Wednesday";
        }
        else if(day_of_week==5){
            day_of_week_string="Thursday";
        }
        else if(day_of_week==6){
            day_of_week_string="Friday";
        }
        if(position==0){

            holder.ln_whole_layout.setVisibility(View.GONE);
            holder.ln_whole_layout_first.setVisibility(View.VISIBLE);
            if(weather_condtionss.toLowerCase().contains("clouds")){
                holder.iv_image_header_weather.setBackgroundResource(R.drawable.cloudyww);
            }
            else if(weather_condtionss.toLowerCase().contains("clear")){
                holder.iv_image_header_weather.setBackgroundResource(R.drawable.sunnyww);
            }
            else if(weather_condtionss.toLowerCase().contains("haze")){
                holder.iv_image_header_weather.setBackgroundResource(R.drawable.hazeww);
            }
            else if(weather_condtionss.toLowerCase().contains("rain")){
                holder.iv_image_header_weather.setBackgroundResource(R.drawable.rainww);
            }
            else if(weather_condtionss.toLowerCase().contains("snow")){
                holder.iv_image_header_weather.setBackgroundResource(R.drawable.snowfallww);
            }
            holder.tv_temperature_header_max.setText(""+weather_record.getTemperature()+"°C");//max
           // holder.tv_temperature_header_min.setText(""+weather_record.getMinimum_temperature()+"°C");//max
            holder.tv_header_day.setText("Today"+","+weather_record.getDate_time());
            holder.tv_header_condition.setText("Condition : "+weather_record.getWeather_condition());//condition


        }
        else if(position==1){
            holder.ln_whole_layout.setVisibility(View.VISIBLE);
            holder.ln_whole_layout_first.setVisibility(View.GONE);
            if(weather_condtionss.toLowerCase().contains("clouds")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.cloudy);
            }
            else if(weather_condtionss.toLowerCase().contains("clear")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.sunny);
            }
            else if(weather_condtionss.toLowerCase().contains("haze")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.haze);
            }
            else if(weather_condtionss.toLowerCase().contains("rain")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.rain);
            }
            else if(weather_condtionss.toLowerCase().contains("snow")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.snowfall);
            }
//            holder.ln_row_second.setVisibility(View.VISIBLE);

//            holder.tv_day.setVisibility(View.VISIBLE);
//            holder.tv_temperature_max.setVisibility(View.VISIBLE);
//            holder.tv_temperature_min.setVisibility(View.VISIBLE);
//            holder.iv_image_weather.setVisibility(View.VISIBLE);
//            holder.tv_condition.setVisibility(View.VISIBLE);
            holder.tv_temperature_max.setText(""+weather_record.getTemperature()+"°C");//max
            //holder.tv_temperature_min.setText(""+weather_record.getMinimum_temperature()+"°C");//max
            holder.tv_day.setText("Tomorrow");
            holder.tv_condition.setText(""+weather_record.getWeather_condition());//condition


        }
        else{
            holder.ln_whole_layout_first.setVisibility(View.GONE);
            holder.ln_whole_layout.setVisibility(View.VISIBLE);
            if(weather_condtionss.toLowerCase().contains("clouds")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.cloudy);
            }
            else if(weather_condtionss.toLowerCase().contains("clear")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.sunny);
            }
            else if(weather_condtionss.toLowerCase().contains("haze")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.haze);
            }
            else if(weather_condtionss.toLowerCase().contains("rain")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.rain);
            }
            else if(weather_condtionss.toLowerCase().contains("snow")){
                holder.iv_image_weather.setBackgroundResource(R.drawable.snowfall);
            }

            holder.ln_row_second.setVisibility(View.VISIBLE);
            holder.ln_whole_layout.setVisibility(View.VISIBLE);
            holder.tv_day.setVisibility(View.VISIBLE);
            holder.tv_temperature_max.setVisibility(View.VISIBLE);
//            holder.tv_temperature_min.setVisibility(View.VISIBLE);
            holder.iv_image_weather.setVisibility(View.VISIBLE);
            holder.tv_condition.setVisibility(View.VISIBLE);
            holder.tv_temperature_max.setText(""+weather_record.getTemperature()+"°C");//max
            //holder.tv_temperature_min.setText(""+weather_record.getMinimum_temperature()+"°C");//max
            holder.tv_day.setText(""+day_of_week_string);
            holder.tv_condition.setText(""+weather_record.getWeather_condition());//condition

        }
        long date_milli=weather_record.getDate_milliseconds();//just checking

        Log.i("dddd",""+date_milli+"day"+day_of_week);



    }
    @Override
    public int getItemCount()
    {
        return list_weather_location.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ln_whole_layout,ln_row_second,ln_whole_layout_first;
        TextView tv_temperature_header_min,tv_temperature_header_max,tv_header_day,tv_header_condition;
        TextView tv_temperature_min,tv_temperature_max,tv_day,tv_condition;
        ImageView iv_image_weather;
        ImageView iv_image_header_weather;
        public MyViewHolder(View itemView) {
            super(itemView);
            //tv_temperature_min =(TextView) itemView.findViewById(R.id.tv_tempe_min);
            tv_temperature_max =(TextView) itemView.findViewById(R.id.tv_temp_max);
            tv_day =(TextView) itemView.findViewById(R.id.tv_dayy);
            tv_condition=(TextView)itemView.findViewById(R.id.tv_conditionn);
            ln_whole_layout=(LinearLayout)itemView.findViewById(R.id.ln_whole_2);
            iv_image_weather=(ImageView)itemView.findViewById(R.id.iv_weather);
            ln_row_second=(LinearLayout)itemView.findViewById(R.id.ln_row_second);


            tv_temperature_header_max =(TextView) itemView.findViewById(R.id.tv_header_max_temperature);
            tv_temperature_header_min =(TextView) itemView.findViewById(R.id.tv_header_min_temperature);
            tv_header_day =(TextView) itemView.findViewById(R.id.tv_header_today);
            tv_header_condition=(TextView)itemView.findViewById(R.id.tv_header_condition);
            ln_whole_layout_first=(LinearLayout)itemView.findViewById(R.id.ln_whole_1);
            iv_image_header_weather=(ImageView)itemView.findViewById(R.id.iv_header_weather);

        }
    }
}