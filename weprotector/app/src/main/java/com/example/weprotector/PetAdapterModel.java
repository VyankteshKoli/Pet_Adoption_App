package com.example.weprotector;

import java.io.Serializable;

public class PetAdapterModel implements Serializable {

    private String name;
    private String breed;
    private int age;
    private String imageBase64; // Base64 image string

    public PetAdapterModel() {
        // Empty constructor required for Firebase if needed
    }

    public PetAdapterModel(String name, String breed, int age, String imageBase64) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageBase64 = imageBase64;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
