package com.example.weprotector;

import java.io.Serializable;

public class AddPetAdmin implements Serializable {
    private String petId;
    private String name;
    private String breed;
    private int age;
    private String vaccination;
    private String sterilization;
    private String size;
    private String description;
    private String imageUrl;

    public AddPetAdmin() {}

    public AddPetAdmin(String petId, String name, String breed, int age,
                       String vaccination, String sterilization,
                       String size, String description, String imageUrl) {
        this.petId = petId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.vaccination = vaccination;
        this.sterilization = sterilization;
        this.size = size;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getPetId() { return petId; }
    public String getName() { return name; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public String getVaccination() { return vaccination; }
    public String getSterilization() { return sterilization; }
    public String getSize() { return size; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    public void setPetId(String petId) { this.petId = petId; }
    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(int age) { this.age = age; }
    public void setVaccination(String vaccination) { this.vaccination = vaccination; }
    public void setSterilization(String sterilization) { this.sterilization = sterilization; }
    public void setSize(String size) { this.size = size; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
