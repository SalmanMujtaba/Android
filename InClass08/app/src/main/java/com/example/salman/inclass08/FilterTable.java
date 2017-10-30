
package com.example.salman.inclass08;

import android.database.sqlite.SQLiteDatabase;

public class FilterTable {

    static final String TABLENAME = "Filter";
    static final String COLUMN_ID="id";
    static final String COLUMN_NAME = "Name";
    static final String COLUMN_PRICE = "Price";
    static final String COLUMN_THUMB_URL = "Thumb_URL";
    static final String COLUMN_BIG_ICON = "Big_Icon";

    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb =  new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " ( ");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_PRICE+ " text not null, ");
        sb.append(COLUMN_THUMB_URL + " text not null, ");
        sb.append(COLUMN_BIG_ICON+ " text not null);");


        try {
            db.execSQL(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        FilterTable.onCreate(db);
    }
}
