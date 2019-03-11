package com.example.freelancepracticemusic.models;

import java.io.Serializable;

public class SongModel implements Serializable {
    String path;
    String title;
    String artist;

    public SongModel() {
    }

    public SongModel(String path, String title, String artist) {
        this.path = path;
        this.title = title;
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
