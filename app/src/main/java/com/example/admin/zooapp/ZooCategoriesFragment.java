package com.example.admin.zooapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ZooCategoriesFragment extends Fragment {

    @BindView(R.id.lvCategories)
    ListView lvCategories;
    @BindView(R.id.btnAddCategory)
    Button btnAddCategory;
    Unbinder unbinder;

    List<Category> categories = new ArrayList<>();
    ArrayList<CategoryAdapterItems> listCategories = new ArrayList<>();
    MyCustomAdapter adapter;

    public ZooCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoo_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CategoryDAO categoryDAO = new CategoryDAO(view.getContext());
        categories = categoryDAO.getAllCategories();

        for (int i = 0; i < categories.size(); i++) {
            listCategories.add(new CategoryAdapterItems(categories.get(i).categoryName, categories.get(i).categoryDescription));
        }

        adapter = new MyCustomAdapter(listCategories);
        lvCategories.setAdapter(adapter);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCategoriesFragment c = new CreateCategoriesFragment();
                getFragmentManager().beginTransaction().replace(R.id.flFrag, c, "ZooCategoriesFragment").addToBackStack(null).commit();
            }
        });

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ZooAnimalsFragment z = new ZooAnimalsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("CategoryId", id);
                z.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFrag, z, "ZooCategoriesFragment").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<CategoryAdapterItems> listCategories;

        public MyCustomAdapter(ArrayList<CategoryAdapterItems> listCategories) {
            this.listCategories = listCategories;
        }


        @Override
        public int getCount() {
            return listCategories.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getActivity().getLayoutInflater();
            View myView = mInflater.inflate(R.layout.listview_categories, null);

            final CategoryAdapterItems s = listCategories.get(position);

            TextView tvCategoryName = (TextView) myView.findViewById(R.id.tvCategoryName);
            tvCategoryName.setText(s.categoryName);
            TextView tvCategoryDescription = (TextView) myView.findViewById(R.id.tvCategoryDescription);
            tvCategoryDescription.setText(s.categoryDescription);

            return myView;
        }

    }

}
