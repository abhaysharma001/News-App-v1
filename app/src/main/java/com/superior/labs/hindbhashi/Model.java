package com.superior.labs.hindbhashi;

public class Model {

    public static final int IMAGE_TYPE = 1;
    public String subtitle,image,title;
    public int type;
    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Model(int type, String title, String subtitle, String image,String date) {
        this.subtitle = subtitle;
        this.image = image;
        this.title = title;
        this.type = type;
        this.date = date;
    }
}
