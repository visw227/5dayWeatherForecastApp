package com.example.weatherforecastapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "DataBaseBills";

    static final String TB_NAME = "weathertable";
    static final String ID = "id";
    static final String COUNTRY_CODE = "country_code";
    static final String ZIP_CODE = "zip_code";
    static final String CITY_NAME = "city_name";
    static final String CITY_TEMPERATURE = "city_temperature";
    static final String COUNTRY_NAME="country_name";
    String zip_c;
    int id_c;
    Context context;

    final static String QRY = " CREATE TABLE " + TB_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COUNTRY_CODE + " TEXT, " + ZIP_CODE + " TEXT, " + CITY_NAME + " TEXT, " + CITY_TEMPERATURE + " TEXT, "
            + COUNTRY_NAME + " TEXT) ";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QRY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long InsertData(Weather_record obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COUNTRY_CODE, obj.getCountry_code());
        cv.put(ZIP_CODE, obj.getZip_code());
        cv.put(CITY_NAME, obj.getCity_name());
        cv.put(CITY_TEMPERATURE, obj.getTemperature());
        cv.put(COUNTRY_NAME, obj.getCountry_name());

        long k =
                db.insert(TB_NAME, null, cv);

        if (k > 0) {
              Toast.makeText(context, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
             Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    public List<Weather_record> ShowAll() {
        List<Weather_record> weather_records=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME, null);
        while (c.moveToNext()) {
            Weather_record weather_record=new Weather_record();
            int id = c.getInt(0);
            weather_record.setCountry_code(c.getString(1));
            weather_record.setZip_code(c.getString(2));
            weather_record.setCity_name(c.getString(3));
            weather_record.setTemperature(c.getString(4));
            weather_record.setCountry_name(c.getString(5));
            weather_records.add(weather_record);
        }
        return weather_records;
    }
    public String showSingle(String zip_code) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME + " WHERE " + ZIP_CODE + " = '" + zip_code+"'", null);
        while (c.moveToNext()) {
             zip_c = c.getString(2);


        }
        return zip_c;
    }
    public int getRecordId(String zip_code) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME + " WHERE " + ZIP_CODE + " = '" + zip_code+"'", null);
        while (c.moveToNext()) {
            zip_c = c.getString(2);
            id_c=c.getInt(0);

        }
        return id_c;
    }


    public int deleteSingleData(String nn) {
        int status=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("fff",""+nn);
        long id = db.delete(TB_NAME, ZIP_CODE + " = '" + nn+"'", null);

        if (id > 0) {
            status=1;
            //Toast.makeText(context, "Data Successfully \n Deleted", Toast.LENGTH_SHORT).show();
        }
            else {
            status=0;
            //Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
        return status;
        }


        public void updateData(Weather_record obj, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

            cv.put(COUNTRY_CODE, obj.getCountry_code());
            cv.put(ZIP_CODE, obj.getZip_code());
            cv.put(CITY_NAME, obj.getCity_name());
            cv.put(CITY_TEMPERATURE, obj.getTemperature());
            cv.put(COUNTRY_NAME, obj.getCountry_name());

            long k = db.update(TB_NAME, cv, ID + "=?", new String[]{id + ""});
        if (k > 0)
            Toast.makeText(context, "Data Successfully \n Updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }
}



