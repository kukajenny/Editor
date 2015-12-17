package com.example.zjp.editor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by zjp on 15-10-11.
 */
public class Database extends SQLiteOpenHelper{
    public static final String create ="create table book(id integer primary key autoincrement,num integer,title varchar(150),message varchar(100))";
    private Context mcontext;
    private int version;

    public Database(Context context,String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context, name, factory, version);
        Log.d("dfg", "dgd");
        mcontext=context;
        this.version=version;

    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(create);
    }
    public int getversion(){
        return version;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        onCreate(db);
    }

}
