package com.example.dogappusingjepack.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dogappusingjepack.R;
import com.example.dogappusingjepack.model.DogBreed;
import com.example.dogappusingjepack.viewmodel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;
    private DogListAdapter dogListAdapter = new DogListAdapter(new ArrayList<>());
    private DogBreed dogBreed;

    @BindView(R.id.dogsList)
    RecyclerView dogsList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.pgBarLoadList)
    ProgressBar pgBarLoadList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public ListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.refresh();
        dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsList.setAdapter(dogListAdapter);
        observerViewModel();


        onclickItem();

    }

    private void observerViewModel() {
        listViewModel.dogs.observe(this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dog) {
                if (dog != null && dog instanceof List) {
                    dogsList.setVisibility(View.VISIBLE);
                    dogListAdapter.updateDogList(dog);

                }
            }
        });

        listViewModel.dogLoadError.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean instanceof Boolean) {
                tvListError.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        listViewModel.loading.observe(this, loading -> {
            if (loading != null && loading instanceof Boolean) {
                pgBarLoadList.setVisibility(loading ? View.VISIBLE : View.GONE);
                if (loading) {
                    dogsList.setVisibility(View.GONE);
                    pgBarLoadList.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onclickItem() {
        dogListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("LIST_FRAGMENT_POSITION", "" + dogsList.getChildAdapterPosition(view));
//
//                Bundle bundle = new Bundle();
//                bundle.putString("image", String.valueOf(R.mipmap.dog_icon));
//                bundle.putString("dogName","DOGNAME");
//                bundle.putString("dogPurpose","DOGPURPOSE");
//                bundle.putString("dogTemperament","DOGTEMPERMENT");
//                bundle.putString("dogLifespan","LIFESPAN");

                ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail(0);

                Navigation.findNavController(view).navigate(action);
            }
        });
    }


}
