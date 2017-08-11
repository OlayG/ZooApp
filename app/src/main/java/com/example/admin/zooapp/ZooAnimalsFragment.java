package com.example.admin.zooapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ZooAnimalsFragment extends Fragment {

    @BindView(R.id.rvAnimalsList)
    RecyclerView rvAnimalsList;
    Unbinder unbinder;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;

    List<Animal> animalList = new ArrayList<>();
    AnimalAdapterItems animalAdapterItems;
    @BindView(R.id.btnAddNewAnimal)
    Button btnAddNewAnimal;

    public ZooAnimalsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoo_animals, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long categoryId = getArguments().getLong("CategoryId");

        layoutManager = new LinearLayoutManager(view.getContext());
        itemAnimator = new DefaultItemAnimator();
        rvAnimalsList.setLayoutManager(layoutManager);
        rvAnimalsList.setItemAnimator(itemAnimator);

        AnimalDAO animalDAO = new AnimalDAO(view.getContext());
        animalList = animalDAO.getAnimalsOfCategory(categoryId);

        animalAdapterItems = new AnimalAdapterItems(animalList);
        rvAnimalsList.setAdapter(animalAdapterItems);

        animalAdapterItems.notifyDataSetChanged();

        btnAddNewAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAnimalFragment z = new CreateAnimalFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("CategoryId", categoryId);
                z.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFrag, z, "ZooAnimalsFragment").addToBackStack(null).commit();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
