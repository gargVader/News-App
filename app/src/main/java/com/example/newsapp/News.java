package com.example.newsapp;


public class News {

    String title;
    String trailText;
    String section;
    String author;
    String date;
    String imageUrl;
    String webUrl;

    public News(String title, String trailText, String section, String author, String date, String imageUrl, String webUrl) {
        this.title = title;
        this.trailText = trailText;
        this.section = section;
        this.author = author;
        this.date = date;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailText() {
        return trailText;
    }

    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
