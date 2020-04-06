package com.example.cst2335_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NasaEarthMyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NasaEarthDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "NASAEARTH";
    public final static String COL_ID = "ID";
    public final static String COL_LATITUDE = "LATITUDE";
    public final static String COL_LONGITUDE = "LONGITUDE";
    public final static String COL_DATE = "DATE";
    public final static String COL_URL = "URL";

    public NasaEarthMyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    //This function gets called if no database file exists.
    //Look on your device in the /data/data/package-name/database directory.
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_LATITUDE + " text,"
                + COL_LONGITUDE + " text,"
                + COL_DATE + " text,"
                + COL_URL  + " text);");
    }

    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String latitude, String longitude, String date, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_LATITUDE, latitude);
        cv.put(COL_LONGITUDE, longitude);
        cv.put(COL_DATE, date);
        cv.put(COL_URL, url);
        Long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1)
            return false;
        else
            return true;
    }

}
