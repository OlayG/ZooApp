package com.example.admin.zooapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 8/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "zoo";

    // Category table info
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_ID = "_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION = "category_description";

    // Animal table info
    public static final String ANIMAL_TABLE_NAME = "animal";
    public static final String ANIMAL_ID = "_id";
    public static final String ANIMAL_NAME = "animal_name";
    public static final String ANIMAL_CATEGORY_ID = "category_id";
    public static final String ANIMAL_DETAIL = "animal_detail";
    public static final String ANIMAL_IMAGE = "animal_image";
    public static final String ANIMAL_SOUND = "animal_sound";

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + CATEGORY_TABLE_NAME + "("
            + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_NAME + " TEXT, "
            + CATEGORY_DESCRIPTION + " TEXT" + ")";

    public static final String CREATE_TABLE_ANIMAL = "CREATE TABLE " + ANIMAL_TABLE_NAME + "("
            + ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ANIMAL_NAME + " TEXT, "
            + ANIMAL_DETAIL + " TEXT, "
            + ANIMAL_IMAGE + " TEXT, "
            + ANIMAL_SOUND + " TEXT, "
            + ANIMAL_CATEGORY_ID + " INTEGER NOT NULL" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ANIMAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ANIMAL_TABLE_NAME);

        onCreate(db);
    }
}
