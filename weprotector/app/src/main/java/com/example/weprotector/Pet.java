package com.example.weprotector;

public class Pet {
    public String dogName;  // Matches Firebase
    public String breed;    // Matches Firebase
    public String age;      // Matches Firebase
    public String imageUrl; // Optional if you store images

    // Default constructor required for Firebase
    public Pet() {
    }

    public Pet(String dogName, String breed, String age, String imageUrl) {
        this.dogName = dogName;
        this.breed = breed;
        this.age = age;
        this.imageUrl = imageUrl;
    }

    // Getters & Setters
    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
