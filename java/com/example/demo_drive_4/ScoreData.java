package com.example.demo_drive_4;

public class ScoreData {
    String name, image;
    Long score;

    public ScoreData(){

    }

    public ScoreData(String name, String image, Long score) {
        this.name = name;
        this.image = image;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
