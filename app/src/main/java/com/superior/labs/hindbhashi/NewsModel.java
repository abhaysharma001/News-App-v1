package com.superior.labs.hindbhashi;

public class NewsModel {

    private String image,heading,id;

    public NewsModel(String image, String heading,String id) {
        this.image = image;
        this.heading = heading;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
