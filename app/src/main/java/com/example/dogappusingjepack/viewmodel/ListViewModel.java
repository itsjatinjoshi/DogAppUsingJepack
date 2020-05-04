package com.example.dogappusingjepack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.model.DogsService;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DogsService dogsService = new DogsService();

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        fetchFromServer();
    }

    private void fetchFromServer() {
        loading.setValue(true);
        compositeDisposable.add(
                dogsService.getDog().
                        subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> dogBreedList) {
                                dogs.setValue(dogBreedList);
                                dogLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                dogLoadError.setValue(true);
                                loading.setValue(false);
                                e.getMessage();
                                e.printStackTrace();
                            }
                        })
        );
    }


}
