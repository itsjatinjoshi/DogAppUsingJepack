package com.example.dogappusingjepack.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.model.DogDao;
import com.example.dogappusingjepack.model.DogDatabase;
import com.example.dogappusingjepack.model.DogsService;
import com.example.dogappusingjepack.util.SharedPreferenceHelper;

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
    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveTask;

    private SharedPreferenceHelper preferenceHelper = SharedPreferenceHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        long updateTime = preferenceHelper.getSavedTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFromDatabase();
        } else {
            fetchFromServer();
        }
    }

    public void refreshDataByPassCache(){
        fetchFromServer();
    }

    private void fetchFromDatabase() {
        loading.setValue(true);
        retrieveTask = new RetrieveDogTask();
        retrieveTask.execute();
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
                                insertTask = new InsertDogTask();
                                insertTask.execute(dogBreedList);
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

    public void retrieveDogList(List<DogBreed> dogBreedList) {
        dogs.setValue(dogBreedList);
        dogLoadError.setValue(false);
        loading.setValue(false);

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }

    public class InsertDogTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];
            DogDao doa = DogDatabase.getInstance(getApplication()).dogDao();
            doa.deleteAllDogs();

            ArrayList<DogBreed> dog = new ArrayList<>(list);
            List<Long> result = doa.insertAll(dog.toArray(new DogBreed[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).uUid = result.get(i).intValue();
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            retrieveDogList(dogBreedList);
            preferenceHelper.savedUpdateTime(System.nanoTime());
        }
    }

    public class RetrieveDogTask extends AsyncTask<Void, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).dogDao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            retrieveDogList(dogBreedList);
        }
    }

}
