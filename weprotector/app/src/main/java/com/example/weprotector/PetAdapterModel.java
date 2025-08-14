package com.example.weprotector;

public class PetAdapterModel {
    private String name;
    private String breed;
    private int age;
    private int imageResourceId;

    public PetAdapterModel(String name, String breed, int age, int imageResourceId) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageResourceId = imageResourceId;
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

    public int getImageResourceId() {
        return imageResourceId;
    }
}
