package com.example.dogappusingjepack.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogappusingjepack.model.DogBreed;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<DogBreed> dogBreed = new MutableLiveData<DogBreed>();
    DogBreed dog1 = new DogBreed("3", "Dog 9", "14 years", "", "BREDFOR", "TEMPERMENT", "");

    public void fetch() {
        dogBreed.setValue(dog1);
    }
}
