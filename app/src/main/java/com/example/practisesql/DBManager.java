package com.example.practisesql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    DBHelper dbHelper;
    SQLiteDatabase database;
    Context context;

    public DBManager(Context c)
    {
        context = c;
    }

    public  DBManager Open()
    {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;


    }

    public void Close()
    {
        dbHelper.close();

    }

    public void Insert(String country, String currency)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper._NAME, country);
        values.put(DBHelper._CURRENCY, currency);


        database.insert(DBHelper.TABLE_NAME,null, values);
    }

    public Cursor fetch(){

        String [] coloumns = new String[] {

                DBHelper._ID,
                DBHelper._NAME,
                DBHelper._CURRENCY
        };

        Cursor query = database.query(DBHelper.TABLE_NAME, coloumns, null, null,
                null, null, null);

        if (query != null){

            query.moveToFirst();
        }

        return query;


    }

}
