package com.example.cst2335_final_project;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class NasaEarth {

    private String latitude, longitude, date, url;
    private long id;
 //   private ImageView image;

    public NasaEarth(String latitude, String longitude, String date, String url) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.url = url;
    }

    public NasaEarth(Long id, String latitude, String longitude, String date, String url) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
