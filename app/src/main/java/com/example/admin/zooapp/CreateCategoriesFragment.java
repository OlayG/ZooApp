package com.example.admin.zooapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCategoriesFragment extends Fragment {


    @BindView(R.id.etCategoryName)
    EditText etCategoryName;
    @BindView(R.id.etCategoryDescription)
    EditText etCategoryDescription;
    @BindView(R.id.btnSaveCategory)
    Button btnSaveCategory;
    Unbinder unbinder;

    public CreateCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDAO categoryDAO = new CategoryDAO(v.getContext());
                Category category = categoryDAO.createCategory("" + etCategoryName.getText(), "" + etCategoryDescription.getText());
                ZooCategoriesFragment z = new ZooCategoriesFragment();
                getFragmentManager().beginTransaction().replace(R.id.flFrag, z, "ZooCategoriesFragment").commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
