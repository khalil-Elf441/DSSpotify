package com.example.dsspotify.ui.playlist.objects;

public class Song {


    private String title;
    private String artist;
    private String filename;
    private String image;
    private int ressourceId;

    public Song(String title, String artist, int ressourceId) {
        this.title = title;
        this.artist = artist;
        this.ressourceId = ressourceId;
    }

    public int getRessourceId() {
        return ressourceId;
    }

    public void setRessourceId(int ressourceId) {
        this.ressourceId = ressourceId;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
