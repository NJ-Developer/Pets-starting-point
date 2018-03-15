package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static com.example.android.pets.data.PetContract.PetEntry.CONTENT_AUTHORITY;
import static com.example.android.pets.data.PetContract.PetEntry.PATH_PETS;
import static com.example.android.pets.data.PetDbHelper.LOG_TAG;

/**
 * Created by Navjot on 11/15/2017.
 */

public class PetProvider extends ContentProvider {

    public static final int PETS = 100;
    public static final int PET_ID = 200;
    public static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private PetDbHelper petDbHelper;

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PETS, PETS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PETS + "/#", PET_ID);
    }

    @Override
    public boolean onCreate() {
        petDbHelper = new PetDbHelper(getContext());
        return true;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = petDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                //FULL Table
                cursor = db.query(PetContract.PetEntry.Table_Name, projection, null, null, null, null, null);
                break;
            case PET_ID:
                selection = PetContract.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))}; //last segment to number
                cursor = db.query(PetContract.PetEntry.Table_Name, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }
        return cursor;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case PETS:
                return PetContract.PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return PetContract.PetEntry.CONTENT_ITEM_TYPE;
                default:
                    return new String("Uri not match"+match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS: {
                return insertPet(uri, values);
            }
            default: {
                throw new IllegalArgumentException("Insertion not supported" + uri);
            }
        }

        // db.insert(PetContract.PetEntry.Table_Name,null,values);
        // db.insert(PetContract.PetEntry.Table_Name,null,values);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
         int match = sUriMatcher.match(uri);
         switch (match)
         {
             case PETS:
                 return deletePet(uri,selection,selectionArgs);
             case  PET_ID:
                 selection = PetContract.PetEntry._ID+"=?";
                 selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                 return deletePet(uri,selection,selectionArgs);
                 default:
                     throw new IllegalArgumentException("Error");
         }

    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return updatePet(uri, values, selection, selectionArgs);
            case PET_ID:
                selection = PetContract.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Error");
        }
        //return 0;
    }
    private Uri insertPet(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = petDbHelper.getWritableDatabase();
        long id = db.insert(PetContract.PetEntry.Table_Name, null, contentValues);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }
    private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = petDbHelper.getWritableDatabase();
        int resultRows = db.update(PetContract.PetEntry.Table_Name, contentValues, selection, selectionArgs);
        Uri uri1;
        if (resultRows > 0) {
            uri1 = ContentUris.withAppendedId(uri, resultRows);
            // Toast.makeText(this,"Updated uri is "+uri1, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, uri1.toString());
        }
        return resultRows;
    }
    private int deletePet(Uri uri,String selection, String[] selectionArgs) {
        SQLiteDatabase db = petDbHelper.getWritableDatabase();
        int result= db.delete(PetContract.PetEntry.Table_Name,selection,selectionArgs);
        return result;
    }
}
