package com.example.mylibrary;
import java.util.concurrent.atomic.AtomicInteger;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by iman on 7/31/2017.
 */

public class DatabaseManager {

    private final AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void openConnection(String databaseName, int databaseVersion, DBCreator dbObj) {
        closeConnection();
        AppDBHelper dbHelper = new AppDBHelper(dbObj.getContext(), databaseName, databaseVersion);
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = dbHelper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1)
            mDatabase = mDatabaseHelper.getWritableDatabase();
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0)
            mDatabase.close();
    }

    public static synchronized void closeConnection() {
        if (mDatabaseHelper != null)
            mDatabaseHelper.close();
        mDatabaseHelper = null;
        instance = null;
    }
}
