package com.example.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //called the 1st time a db is accessed. codes are here to create db
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatement= "CREATE TABLE " + CUSTOMER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CUSTOMER_NAME + " TEXT," +
                COLUMN_CUSTOMER_AGE + " INT," +
                COLUMN_ACTIVE_CUSTOMER + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);

    }

    //called when the db version number changes
    //prevents apps users from breaking when you change the db design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    //add to db
    public boolean addOne(CustomerModel customerModel){

        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues cv= new ContentValues(); //looks like a hashmap

        //id no need to provide because it is auto increment
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive() );

        //insert
        long insert = db.insert(CUSTOMER_TABLE, null, cv);

        if (insert==-1){ //if not working
            return false;
        }


        return true;


    }
}
