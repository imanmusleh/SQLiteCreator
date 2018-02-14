package com.example.mylibrary;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by iman on 7/31/2017.
 */

public class AppDBHelper  extends SQLiteOpenHelper {


    public AppDBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        while (true) {
            try {
                return super.getWritableDatabase();
            } catch (SQLiteDatabaseLockedException e) {
                System.err.println(e);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public synchronized void close(SQLiteDatabase db) {
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
