package com.example.admin.zooapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/11/2017.
 */


public class AnimalAdapterItems extends RecyclerView.Adapter<AnimalAdapterItems.ViewHolder>{

    List<Animal> animalList = new ArrayList<>();

    public AnimalAdapterItems(List<Animal> animalList) {
        this.animalList = animalList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvAnimalName, tvAnimalDetail, tvAnimalCategory;
        ImageButton ibAnimalPhoto;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAnimalName = (TextView) itemView.findViewById(R.id.tvAnimalName);
            tvAnimalCategory = (TextView) itemView.findViewById(R.id.tvAnimalCatergory);
            tvAnimalDetail = (TextView) itemView.findViewById(R.id.tvAnimalDetail);
            ibAnimalPhoto = (ImageButton) itemView.findViewById(R.id.ibAnimalPhoto);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_animals, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Animal animal = animalList.get(position);

        holder.tvAnimalName.setText("" + animal.getAnimalName());
        holder.tvAnimalCategory.setText("" + animal.getAnimalCategory());
        holder.tvAnimalDetail.setText("" + animal.getAnimalDetail());
        byte[] animalPhoto = animal.getAnimalImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(animalPhoto, 0, animalPhoto.length);
        holder.ibAnimalPhoto.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Animal", animal);

            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
}
