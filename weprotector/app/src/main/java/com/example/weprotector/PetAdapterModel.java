package com.example.weprotector;

import java.io.Serializable;

public class PetAdapterModel implements Serializable {

    private String name;
    private String breed;
    private int age;
    private String imageBase64; 

    public PetAdapterModel() {
        
    }

    public PetAdapterModel(String name, String breed, int age, String imageBase64) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageBase64 = imageBase64;
    }

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
