package com.example.coinchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "coinChangeDatabase";
    private static final String TABLE_NAME = "coinTable";
    private static final String ID = "id";
    private static final String COIN_NUMBER = "coin_number";

    public DatabaseHelperClass(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + COIN_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(1,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(2,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(3,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(4,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(5,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(6,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(7,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(8,'0')");
//        db.execSQL("INSERT INTO TABLE_NAME (ID,COIN_NUMBER) VALUES(9,'0')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public int getCoinInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        COIN_NUMBER }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int result = Integer.parseInt(cursor.getString(1));
        //int result = cursor.getCount();
        return result;
    }

    void addCoin(int id,String coins) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(COIN_NUMBER, coins);
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public int updateCoin(int id,String coins) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(COIN_NUMBER,coins);

        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }



    public void deleteCoin(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
