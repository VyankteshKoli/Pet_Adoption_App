package com.example.weprotector;

public class AdoptionRequest {

    public String requestId;
    public String petName, breed, age;
    public String adopterName, adopterPhone, adopterEmail;
    public String status;

    public AdoptionRequest() {}

    public AdoptionRequest(String requestId, String petName, String breed, String age,
                           String adopterName, String adopterPhone, String adopterEmail,
                           String status) {
        this.requestId = requestId;
        this.petName = petName;
        this.breed = breed;
        this.age = age;
        this.adopterName = adopterName;
        this.adopterPhone = adopterPhone;
        this.adopterEmail = adopterEmail;
        this.status = status;
    }
}
