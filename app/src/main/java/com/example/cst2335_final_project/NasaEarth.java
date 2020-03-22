package com.example.cst2335_final_project;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class NasaEarth {

    private String Latitude, Longitude, date, url;
 //   private ImageView image;

    public NasaEarth(String latitude, String longitude, String date, String url) {
        Latitude = latitude;
        Longitude = longitude;
        this.date = date;
        this.url = url;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
