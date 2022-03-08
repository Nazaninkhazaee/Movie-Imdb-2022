package com.coding.movie.data;

public class MovieFavItem {

    private String imageResourse;
    private String title;
    private String key_id;
    private String favStatus;
    private String rating;
    private String desc;

    public MovieFavItem(String imageResourse,
                        String title,
                        String key_id,
                        String favStatus,
                        String rating,
                        String desc) {
        this.imageResourse = imageResourse;
        this.title = title;
        this.key_id = key_id;
        this.favStatus = favStatus;
        this.rating = rating;
        this.desc = desc;
    }

    public String getImageResourse() {
        return imageResourse;
    }

    public void setImageResourse(String imageResourse) {
        this.imageResourse = imageResourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
