package com.example.weprotector;

public class Pet {
    public String dogName;
    public String dogBreed;

    public Pet() {
        // Default constructor required for calls to DataSnapshot.getValue(Pet.class)
    }

    public Pet(String dogName, String dogBreed) {
        this.dogName = dogName;
        this.dogBreed = dogBreed;
    }
}
