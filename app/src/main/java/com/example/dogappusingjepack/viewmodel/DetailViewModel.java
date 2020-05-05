package com.example.dogappusingjepack.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.model.DogDao;
import com.example.dogappusingjepack.model.DogDatabase;
import com.example.dogappusingjepack.view.DogListAdapter;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {
    public MutableLiveData<DogBreed> dogBreedLive = new MutableLiveData<DogBreed>();
    private retrieveDogDetail dogDetail;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int uuid) {
        dogDetail = new retrieveDogDetail();
        dogDetail.execute(uuid);
    }

    @Override
    protected void onCleared() {
        if (dogDetail != null) {
            dogDetail.cancel(true);
            dogDetail = null;
        }
    }

    private class retrieveDogDetail extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int uuid = integers[0];
            return DogDatabase.getInstance(getApplication()).dogDao().getDogs(uuid);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogBreedLive.setValue(dogBreed);
        }
    }

}
