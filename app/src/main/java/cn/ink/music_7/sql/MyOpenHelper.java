package cn.ink.music_7.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "music.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(user_id integer primary key autoincrement,name varchar(20),email varchar(20),age int(4),sex varchar(4),is_login int(2))");
        db.execSQL("create table song_info(song_id integer primary key autoincrement,song_name varchar(20),singer varchar(20),album varchar(20),lyrics varchar(20),img varchar(256),play_url varchar(256))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
