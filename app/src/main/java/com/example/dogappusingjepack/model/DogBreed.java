package com.example.dogappusingjepack.model;

import com.google.gson.annotations.SerializedName;

public class DogBreed {
    @SerializedName("id")
    public String breedId;

    @SerializedName("name")
    public String dogBreed;

    @SerializedName("life_span")
    public String lifespan;

    @SerializedName("breed_group")
    public String breedGroup;

    @SerializedName("bred_for")
    public String bredFor;

    @SerializedName("temperament")
    public String temperament;

    @SerializedName("url")
    public String imageUrl;

    public int uUid;

    public DogBreed(String breedId, String dogBreed, String lifespan, String breedGroup, String bredFor, String temperament, String imageUrl) {
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifespan = lifespan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }


}
