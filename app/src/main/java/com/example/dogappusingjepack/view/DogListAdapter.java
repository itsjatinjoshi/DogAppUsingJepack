package com.example.dogappusingjepack.view;

import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogappusingjepack.R;
import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.util.LoadImages;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.DogViewHolder> {

    ArrayList<DogBreed> dogBreeds;
    private View.OnClickListener onClickListener;


    public DogListAdapter(ArrayList<DogBreed> dogBreeds) {
        this.dogBreeds = dogBreeds;
    }


    public void updateDogList(List<DogBreed> dogBreedList) {
        dogBreeds.clear();
        dogBreeds.addAll(dogBreedList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ImageView dogImage = holder.view.findViewById(R.id.ivDog);
        TextView dogName = holder.view.findViewById(R.id.tvDogName);
        TextView dogLifeSpan = holder.view.findViewById(R.id.tvDogLifeSpan);

        LoadImages.loadImages(dogImage, dogBreeds.get(position).imageUrl,
                LoadImages.getCircularProgressDrawable(dogImage.getContext()));
        dogName.setText(dogBreeds.get(position).dogBreed);
        dogLifeSpan.setText(dogBreeds.get(position).lifespan);

        LinearLayout linlay = holder.itemView.findViewById(R.id.dogLayout);
        linlay.setOnClickListener(view -> {
            ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail(dogBreeds.get(position).uUid);
            Navigation.findNavController(linlay).navigate(action);
        });
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        if (dogBreeds != null) {
            return dogBreeds.size();
        }
        return 0;
    }


    public class DogViewHolder extends RecyclerView.ViewHolder {
        View view;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            view.setOnClickListener(onClickListener);
        }

    }


}
