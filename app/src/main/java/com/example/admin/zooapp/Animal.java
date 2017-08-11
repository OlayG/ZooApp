package com.example.admin.zooapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 8/10/2017.
 */

public class Animal implements Parcelable {

    long id;
    String animalName, animalDetail;
    byte[] animalImage, animalSound;
    Category animalCategory;

    public Animal() {
    }

    public Animal(String animalName, String animalDetail, byte[] animalImage, byte[] animalSound) {
        this.animalName = animalName;
        this.animalDetail = animalDetail;
        this.animalImage = animalImage;
        this.animalSound = animalSound;
    }

    protected Animal(Parcel in) {
        id = in.readLong();
        animalName = in.readString();
        animalDetail = in.readString();
        animalImage = in.createByteArray();
        animalSound = in.createByteArray();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalDetail() {
        return animalDetail;
    }

    public void setAnimalDetail(String animalDetail) {
        this.animalDetail = animalDetail;
    }

    public byte[] getAnimalImage() {
        return animalImage;
    }

    public void setAnimalImage(byte[] animalImage) {
        this.animalImage = animalImage;
    }

    public byte[] getAnimalSound() {
        return animalSound;
    }

    public void setAnimalSound(byte[] animalSound) {
        this.animalSound = animalSound;
    }

    public Category getAnimalCategory() {
        return animalCategory;
    }

    public void setAnimalCategory(Category animalCategory) {
        this.animalCategory = animalCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(animalName);
        dest.writeString(animalDetail);
        dest.writeByteArray(animalImage);
        dest.writeByteArray(animalSound);
    }
}
