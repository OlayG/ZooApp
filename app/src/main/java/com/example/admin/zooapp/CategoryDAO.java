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

public class CategoryDAO {

    public static final String TAG = "CategoryDAO";

    //Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;
    private String[] columns = { DatabaseHelper.CATEGORY_ID, DatabaseHelper.CATEGORY_NAME,
    DatabaseHelper.CATEGORY_DESCRIPTION};

    public CategoryDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);

        // open Database
        try {
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Category createCategory(String name, String description){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.CATEGORY_NAME, name);
        cv.put(DatabaseHelper.CATEGORY_DESCRIPTION, description);

        long insertId = database.insert(DatabaseHelper.CATEGORY_TABLE_NAME, null, cv);
        Cursor cursor = database.query(DatabaseHelper.CATEGORY_TABLE_NAME, columns,
                DatabaseHelper.CATEGORY_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();
        return newCategory;
    }

    public void deleteCategory(Category category){
        long id = category.getId();

        AnimalDAO animalDAO = new AnimalDAO(context);
        List<Animal> listAnimals = animalDAO.getAnimalsOfCategory(id);
        if (listAnimals != null && !listAnimals.isEmpty()) {
            for (Animal e : listAnimals) {
                animalDAO.deleteAnimal(e);
            }
        }

        database.delete(DatabaseHelper.CATEGORY_TABLE_NAME, DatabaseHelper.ANIMAL_CATEGORY_ID
                + " = " + id, null);
    }

    public List<Category> getAllCategories() {
        List<Category> listCategories = new ArrayList<Category>();

        Cursor cursor = database.query(DatabaseHelper.CATEGORY_TABLE_NAME, columns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Category category = cursorToCategory(cursor);
                listCategories.add(category);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCategories;
    }

    public Category getCategoryById(long id) {
        Cursor cursor = database.query(DatabaseHelper.CATEGORY_TABLE_NAME, columns,
                DatabaseHelper.CATEGORY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Category category = cursorToCategory(cursor);
        return category;
    }

    protected Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setCategoryName(cursor.getString(1));
        category.setCategoryDescription(cursor.getString(2));
        return category;
    }}
