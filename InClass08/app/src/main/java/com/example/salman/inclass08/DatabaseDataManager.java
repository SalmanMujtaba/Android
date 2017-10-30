//Salman Mujtaba 800969897
//Prerana Singh 800973733
//Ryan Mcpeck 800968503
//InClass07
//Group09

package com.example.salman.inclass08;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private MusicDao musicDao;

    public DatabaseDataManager(Context context){
        this.mContext=context;

        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        musicDao = new MusicDao(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public MusicDao getNoteDao(){
        return this.musicDao;
    }

    public long save(Music music){
        return this.musicDao.save(music);
    }

    public boolean update(Music music){
        return this.musicDao.update(music);
    }

    public boolean delete(Music music){
        return this.musicDao.delete(music);
    }


    public List<Music> getAll(){
        return this.musicDao.getAll();
    }
}
