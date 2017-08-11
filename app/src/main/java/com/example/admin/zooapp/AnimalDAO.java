package com.example.admin.zooapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/10/2017.
 */

public class AnimalDAO {

    public static final String TAG = "AnimalDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private String[] mAllColumns = { DatabaseHelper.ANIMAL_ID,
            DatabaseHelper.ANIMAL_NAME,
            DatabaseHelper.ANIMAL_DETAIL, DatabaseHelper.ANIMAL_IMAGE,
            DatabaseHelper.ANIMAL_SOUND, DatabaseHelper.ANIMAL_CATEGORY_ID };

    public AnimalDAO(Context context) {
        mDbHelper = new DatabaseHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Animal createAnimal(String name, String detail,
                                  byte[] image, byte[] sound,
                                  long categoryId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ANIMAL_NAME, name);
        values.put(DatabaseHelper.ANIMAL_DETAIL, detail);
        values.put(DatabaseHelper.ANIMAL_IMAGE, image);
        values.put(DatabaseHelper.ANIMAL_SOUND, sound);
        values.put(DatabaseHelper.ANIMAL_CATEGORY_ID, categoryId);
        long insertId = mDatabase
                .insert(DatabaseHelper.ANIMAL_TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.ANIMAL_TABLE_NAME, mAllColumns,
                DatabaseHelper.ANIMAL_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Animal animal = cursorToAnimal(cursor);
        cursor.close();
        return animal;
    }

    public void deleteAnimal(Animal animal) {
        long id = animal.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DatabaseHelper.ANIMAL_TABLE_NAME, DatabaseHelper.ANIMAL_CATEGORY_ID
                + " = " + id, null);
    }

    public List<Animal> getAllAnimals() {
        List<Animal> listAnimals = new ArrayList<Animal>();

        Cursor cursor = mDatabase.query(DatabaseHelper.ANIMAL_TABLE_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Animal animal = cursorToAnimal(cursor);
            listAnimals.add(animal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAnimals;
    }

    public List<Animal> getAnimalsOfCategory(long categoryId) {
        List<Animal> listAnimals = new ArrayList<Animal>();

        Cursor cursor = mDatabase.query(DatabaseHelper.ANIMAL_TABLE_NAME, mAllColumns,
                DatabaseHelper.CATEGORY_ID + " = ?",
                new String[] { String.valueOf(categoryId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Animal animal = cursorToAnimal(cursor);
            listAnimals.add(animal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAnimals;
    }

    private Animal cursorToAnimal(Cursor cursor) {
        Animal animal = new Animal();
        animal.setId(cursor.getLong(0));
        animal.setAnimalName(cursor.getString(1));
        animal.setAnimalDetail(cursor.getString(2));
        animal.setAnimalImage(cursor.getBlob(3));
        animal.setAnimalSound(cursor.getBlob(4));


        // get The company by id
        long categoryId = cursor.getLong(5);
        CategoryDAO dao = new CategoryDAO(mContext);
        Category category = dao.getCategoryById(categoryId);
        if (category != null)
            animal.setAnimalCategory(category);

        return animal;
    }
}
