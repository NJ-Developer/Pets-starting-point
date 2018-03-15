package com.example.android.pets.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.R;

/**
 * Created by Navjot on 1/11/2018.
 */

public class PetCursorAdaptor extends CursorAdapter {
    TextView name, breed;
    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public PetCursorAdaptor(Context context, Cursor c) {
        super(context, c,0);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pet_item,parent,false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
     name = (TextView)view.findViewById(R.id.name);
     breed = (TextView)view.findViewById(R.id.breed);
     int nameIndex = cursor.getColumnIndex(PetContract.PetEntry.Column_Pet_Name);
     int breedIndex = cursor.getColumnIndex(PetContract.PetEntry.Column_Pet_Breed);
     String name1 = cursor.getString(nameIndex);
     String breed1 = cursor.getString(breedIndex);
     name.setText(name1);
     breed.setText(breed1);
    }
}
