package com.jasonleetoronto.capstone.springpostgreshrservice.model;

public enum Gender {
M("Male"), F("Female");

    private String fullDescription;

    Gender(String description) {
        this.fullDescription = description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String description) {
        this.fullDescription = description;
    }
}
