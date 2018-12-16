package com.example.myinterview.common.model;


public class Photo {

    private String name;
    private int photoId;

    public Photo(String name, int photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public int getPhotoId() {
        return photoId;
    }
}
