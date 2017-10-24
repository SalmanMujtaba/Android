
//Salman Mujtaba 800969897
//Prerana Singh
//Ryan Mcpeck
//InClass07
//Group09

package com.example.salman.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MusicDao {

    private SQLiteDatabase db;

    public MusicDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Music music){
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,music.getName());
        values.put(FilterTable.COLUMN_PRICE,music.getPrice());
        values.put(FilterTable.COLUMN_THUMB_URL,music.getIcon());
        values.put(FilterTable.COLUMN_BIG_ICON,music.getBigIcon());


        return db.insert(FilterTable.TABLENAME,null,values);

    }

    public boolean update(Music music){

        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,music.getName());
        values.put(FilterTable.COLUMN_PRICE,music.getPrice());
        values.put(FilterTable.COLUMN_THUMB_URL,music.getIcon());
        values.put(FilterTable.COLUMN_BIG_ICON,music.getBigIcon());

        return db.update(FilterTable.TABLENAME,null,FilterTable.COLUMN_ID+"=?",new String[]{music.getId()+""})>0;


    }

    //returns no of rows affected by the condition ,0 otherwise
    public boolean delete(Music music){

        return db.delete(FilterTable.TABLENAME,FilterTable.COLUMN_ID+"=?",new String[]{music.getId()+""})>0;

    }



    private Music buildNoteFromCursor(Cursor c){
        Music music = null;
        if(c!= null){
            music = new Music();
            music.setId(c.getInt(0));
            music.setName(c.getString(1));
            music.setPrice(c.getString(2));
            music.setIcon(c.getString(3));
            music.setBigIcon(c.getString(4));
        }

        return music;
    }

    public List<Music> getAll(){
        List<Music> musicList = new ArrayList<>();

        Cursor c = db.query(FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_ID,FilterTable.COLUMN_NAME,FilterTable.COLUMN_PRICE,FilterTable.COLUMN_THUMB_URL,FilterTable.COLUMN_BIG_ICON},null,null,null,null,null);

        if(c!=null && c.moveToFirst()){
            do {
                Music music = buildNoteFromCursor(c);

                if(music != null){
                    musicList.add(music);
                }

            }while(c.moveToNext());
        }

        if(!c.isClosed()){
            c.close();
        }

        return musicList;
    }

}
