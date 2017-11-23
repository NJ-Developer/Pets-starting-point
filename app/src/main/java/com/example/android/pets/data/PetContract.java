package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Navjot on 11/15/2017.
 */

public final  class PetContract
{
 public static  abstract class PetEntry implements BaseColumns{
     public static  final String CONTENT_AUTHORITY = "com.example.android.pets";
     public static  final  String Table_Name="pets";
     public static  final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
     public static  final String PATH_PETS= PetContract.PetEntry.Table_Name;
     public static  final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PETS);
     public static final  String _ID = BaseColumns._ID;
     public static  final String Column_Pet_Name = "name";
     public static  final  String Column_Pet_Breed = "breed";
     public static final String Column_Pet_Weight="weight";
     public static  final String Column_Pet_Gender="gender";
     public static  final  int gender_Male =1;
     public static  final  int gender_Female =2;
     public static  final  int gender_Unknown =0;
 }
}
