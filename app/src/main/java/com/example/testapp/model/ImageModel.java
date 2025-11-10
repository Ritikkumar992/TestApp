package com.example.testapp.model;

public class ImageModel {
    private String text;
    private String img_url;
    private String title;

    public ImageModel(String text, String img_url) {
        this.text = text;
        this.img_url = img_url;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
