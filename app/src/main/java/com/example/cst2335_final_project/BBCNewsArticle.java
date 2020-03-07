package com.example.cst2335_final_project;

public class BBCNewsArticle {
    private int id;
    private String title;
    private String description;
    private String date;
    private String url;

    public BBCNewsArticle(int id, String title, String description, String date, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public BBCNewsArticle(String title, String description, String date, String url) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
