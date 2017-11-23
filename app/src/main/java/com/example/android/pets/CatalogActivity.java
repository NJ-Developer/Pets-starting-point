
package com.example.android.pets;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

import static com.example.android.pets.data.PetContract.PetEntry.BASE_CONTENT_URI;
import static com.example.android.pets.data.PetContract.PetEntry.CONTENT_URI;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity
{
    String[] projection={PetContract.PetEntry.Column_Pet_Name, PetContract.PetEntry._ID, PetContract.PetEntry.Column_Pet_Weight};
    String selection = PetContract.PetEntry.Column_Pet_Name + " =?";
    String sortOrder = PetContract.PetEntry._ID + " DESC";
    String[] selectionArgs={"gagan"};
    ContentValues contentValues,contentValues1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        contentValues = new ContentValues();
        contentValues.put(PetContract.PetEntry.Column_Pet_Name,"gagan");
        contentValues.put(PetContract.PetEntry.Column_Pet_Breed,"Terri");
        contentValues.put(PetContract.PetEntry.Column_Pet_Gender, PetContract.PetEntry.gender_Male);
        contentValues.put(PetContract.PetEntry.Column_Pet_Weight,7);
        displayDatabaseInfo();
         //PetDbHelper petDbHelper = new PetDbHelper(this);
         //SQLiteDatabase db = petDbHelper.getReadableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo()
    {
        TextView displayView = (TextView)findViewById(R.id.text_view_pet);

       // SQLiteDatabase db = petDbHelper.getReadableDatabase();
       // Cursor cursor = db.rawQuery("SELECT * FROM "+ PetContract.PetEntry.Table_Name,null);
       // Cursor cursor = db.query(PetContract.PetEntry.Table_Name,projection,selection,selectionArgs,null,null,sortOrder);
       /* try {
              int idColumnINdex = cursor.getColumnIndex(PetContract.PetEntry._ID);
              int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.Column_Pet_Name);


                displayView.setText("Number of rows on pets database table: "+ cursor.getCount());
                while (cursor.moveToNext())
                {
                    int currentId = cursor.getInt(idColumnINdex);
                    String currentNAME = cursor.getString(nameColumnIndex);
                    displayView.append("\n"+currentId+"-"+currentNAME);

                }
            }
            finally {
            cursor.close();
        }*/
       Cursor cursor = getContentResolver().query(PetContract.PetEntry.CONTENT_URI,projection,null,null,null);
       displayView.setText(" "+cursor.getCount());

    }
    /* private  void insertPet()
     {
         SQLiteDatabase db = petDbHelper.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(PetContract.PetEntry.Column_Pet_Name,"Toto");
         contentValues.put(PetContract.PetEntry.Column_Pet_Breed,"Terrir");
         contentValues.put(PetContract.PetEntry.Column_Pet_Gender, PetContract.PetEntry.gender_Male);
         contentValues.put(PetContract.PetEntry.Column_Pet_Weight,4);
         long newRowid = db.insert(PetContract.PetEntry.Table_Name,null,contentValues);
         Log.v("Catalog Activity","New Row id "+newRowid);
     }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                //  insertPet();
                if(contentValues.containsKey(PetContract.PetEntry.Column_Pet_Name)) {
                    String name = contentValues.getAsString(PetContract.PetEntry.Column_Pet_Name);
                    //String breed = contentValues.getAsString(PetContract.PetEntry.Column_Pet_Breed);
                    //int gender = contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Gender);
                    //int weight = contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Weight);
                    //if (name == null || breed == null || weight == 0 || gender == -1)
                    if (name == null) {
                        throw new IllegalArgumentException("one of the field is missing");
                    } else {
                        Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, contentValues);
                        if (newUri == null) {
                            Toast.makeText(this, getString(R.string.fail_to_save), Toast.LENGTH_LONG).show();
                        } else {
                            displayDatabaseInfo();
                            Toast.makeText(this, getString(R.string.pet_saved), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                 return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_update_data:
                contentValues1 = new ContentValues();
                contentValues1.put(PetContract.PetEntry.Column_Pet_Name,"Pinko");
                if(contentValues1.containsKey(PetContract.PetEntry.Column_Pet_Name)) {
                    String name_Update = contentValues1.getAsString(PetContract.PetEntry.Column_Pet_Name);
                    //        String breed_Update = contentValues.getAsString(PetContract.PetEntry.Column_Pet_Breed);
                    //      int gender_Update= contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Gender);
                    //    int weight_Update= contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Weight);
                    if (name_Update == null) {
                        throw new IllegalArgumentException("Please provide name to update");
                    } else {
                        // String[] selectionArgs = {"gagan"};
                        int rows = getContentResolver().update(PetContract.PetEntry.CONTENT_URI, contentValues1, selection, selectionArgs);
                        //Uri byId= Uri.withAppendedPath(CONTENT_URI,"13");
                        // int rows = getContentResolver().update(byId, contentValues1, null, null);
                        // Log.v("path",.toString());
                        if (rows == 0) {
                            Toast.makeText(this, getString(R.string.fail_to_save), Toast.LENGTH_LONG).show();
                        } else {
                            displayDatabaseInfo();
                            Toast.makeText(this, "Updated By Name", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            case R.id.action_updateby_id:
                contentValues1 = new ContentValues();
                contentValues1.put(PetContract.PetEntry.Column_Pet_Name,"Pinko");
                String name_Update1 = contentValues1.getAsString(PetContract.PetEntry.Column_Pet_Name);
                //        String breed_Update = contentValues.getAsString(PetContract.PetEntry.Column_Pet_Breed);
                //      int gender_Update= contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Gender);
                //    int weight_Update= contentValues.getAsInteger(PetContract.PetEntry.Column_Pet_Weight);
                if (name_Update1 == null)
                {
                    throw new IllegalArgumentException("Please provide name to update");
                }
                else
                    {
                    // String[] selectionArgs = {"gagan"};
                    //int rows = getContentResolver().update(PetContract.PetEntry.CONTENT_URI, contentValues1, selection, selectionArgs);
                    Uri byId= Uri.withAppendedPath(CONTENT_URI,"13");
                    int rows = getContentResolver().update(byId, contentValues1, null, null);
                    Log.v("path",byId.toString());
                    if (rows==0) {
                        Toast.makeText(this, getString(R.string.fail_to_save), Toast.LENGTH_LONG).show();
                    } else {
                        displayDatabaseInfo();
                        Toast.makeText(this,"Updated By id ", Toast.LENGTH_LONG).show();
                    }
                }
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
