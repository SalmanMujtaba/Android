//Salman Mujtaba 800969897
//Prerana Singh
//Ryan Mcpeck
//InClass07
//Group09

package com.example.salman.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    static final String DB_NAME = "filterMusic.DB";
    static final int DB_VERSION = 13;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           FilterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilterTable.onUpgrade(db,oldVersion,newVersion);
    }
}
