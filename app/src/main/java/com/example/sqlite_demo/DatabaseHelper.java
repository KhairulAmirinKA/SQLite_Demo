package com.example.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    //to retrieve data from db
    public List<CustomerModel> retrieveData(){

        List<CustomerModel> list= new ArrayList<>();

        //get data from db
        String queryString= "SELECT * FROM "+ CUSTOMER_TABLE;

        //improve performance than getWritableDatabase()
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        //cursor is the result set from a SQL statement

        if (cursor.moveToFirst() ){ //returns true if there were items selected

            //TODO: loop through the result set and create a customer object and put them into the list
            do{
                int customerID= cursor.getInt(0); //0 is the position

                String customerName= cursor.getString(1);

                int customerAge= cursor.getInt(2);

                //SQlite: 1 is true, 0 is false.
                boolean customerActive= cursor.getInt(3)==1 ? true : false;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);

                //ad to the list
                list.add(newCustomer);

            }
            while (cursor.moveToNext() );
        }

        //close cursor and db when done
        cursor.close();
        db.close();

        return list;
    }





}
