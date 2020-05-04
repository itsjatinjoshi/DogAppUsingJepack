package com.example.dogappusingjepack.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogappusingjepack.R;
import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.viewmodel.DetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    int dogUuid;
    private DetailViewModel detailViewModel;

    @BindView(R.id.ivDogImage)
    ImageView ivDogImage;

    @BindView(R.id.tvDogName)
    TextView tvDogName;

    @BindView(R.id.tvDogPurpose)
    TextView tvDogPurpose;

    @BindView(R.id.tvTemperament)
    TextView tvTemperament;

    @BindView(R.id.tvLifeSpan)
    TextView tvLifeSpan;

    String image, dogName, dogPurpose, dogTemperament, dogLifespan;



    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
                dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
//            image = getArguments().getString("image");
//            dogName = getArguments().getString("dogName");
//            dogPurpose = getArguments().getString("dogPurpose");
//            dogTemperament = getArguments().getString("dogTemperament");
//            dogLifespan = getArguments().getString("dogLifespan");
//
//            System.out.println("image" + image);
//            System.out.println("dogName" + dogName);
//            System.out.println("dogPurpose" + dogPurpose);
//            System.out.println("dogTemperament" + dogTemperament);
//            System.out.println("dogLifespan" + dogLifespan);


        }
//        ivDogImage.setTag(image);
//        tvDogName.setText(dogName);
//        tvDogPurpose.setText(dogPurpose);
//        tvTemperament.setText(dogTemperament);
//        tvLifeSpan.setText(dogLifespan);

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.fetch();
        observerData();
    }

    public void observerData(){
        detailViewModel.dogBreed.observe(this, new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {
                if(dogBreed != null && dogBreed instanceof DogBreed){
                    ivDogImage.setTag(dogBreed.imageUrl);
                    tvDogName.setText(dogBreed.dogBreed);
                    tvDogPurpose.setText(dogBreed.bredFor);
                    tvTemperament.setText(dogBreed.temperament);
                    tvLifeSpan.setText(dogBreed.lifespan);
                }
            }
        });
    }

}
