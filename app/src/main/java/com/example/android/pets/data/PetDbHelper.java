package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Navjot on 11/15/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper
{
    public  static final  String LOG_TAG = PetDbHelper.class.getSimpleName();

    private static  final  String Database_Name = "Shelter.db";
    private static  final int Db_Version =1;
    public PetDbHelper(Context context)
    {
        super(context, Database_Name,null,Db_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE "+ PetContract.PetEntry.Table_Name+"("  + PetContract.PetEntry._ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PetContract.PetEntry.Column_Pet_Name
                +" TEXT NOT NULL, "+ PetContract.PetEntry.Column_Pet_Breed+" TEXT, "
                + PetContract.PetEntry.Column_Pet_Gender+" INTEGER NOT NULL, "
                + PetContract.PetEntry.Column_Pet_Weight+" INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
