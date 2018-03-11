package com.example.weatherforecastapp;



public class Weather_record {

    String country_code;
    String zip_code;
    String country_name;
    String temperature;
    String city_name;
    String weather_condition;
    String weather_condition_few_c;
    String date_time;
    String humidity;
    String pressure;
    String minimum_temperature;
    String weather_day;

    public long getDate_milliseconds() {
        return date_milliseconds;
    }

    public void setDate_milliseconds(long date_milliseconds) {
        this.date_milliseconds = date_milliseconds;
    }

    long date_milliseconds;
    public String getWeather_day() {
        return weather_day;
    }
    public void setWeather_day(String weather_day) {
        this.weather_day = weather_day;
    }
    public String getMinimum_temperature() {
        return minimum_temperature;
    }

    public void setMinimum_temperature(String minimum_temperature) {
        this.minimum_temperature = minimum_temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }



    public String getWeather_condition() {
        return weather_condition;
    }

    public void setWeather_condition(String weather_condition) {
        this.weather_condition = weather_condition;
    }

    public String getWeather_condition_few_c() {
        return weather_condition_few_c;
    }

    public void setWeather_condition_few_c(String weather_condition_few_c) {
        this.weather_condition_few_c = weather_condition_few_c;
    }


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }



    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }



    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }


}
