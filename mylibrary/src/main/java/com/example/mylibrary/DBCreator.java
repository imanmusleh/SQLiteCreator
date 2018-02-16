package com.example.mylibrary;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * Created by iman on 7/31/2017.
 */


public class DBCreator {
    String dbName; 
    int version;
    xmlParser parserObj;
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    Context context ;

    public DBCreator( Context context, String dbName, int version ){
        setContext(context);
        createXmlParser();
        DatabaseManager.openConnection(dbName,  version, this);
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  void createXmlParser(){
         parserObj = new     xmlParser(getContext());
    }

    private   void createTable() throws SQLException {
        SQLiteDatabase db = null;
        try {
            db = DatabaseManager.getInstance().openDatabase();
            String st = null ; // "CREATE TABLE IF NOT EXISTS " +map.get("table") + " (";
            for (Map<String, String> object : parserObj.getMap()) {

                st = "CREATE TABLE IF NOT EXISTS " +object.get("Table") + " (";
                int i= 0 ;
                for (String key : object.keySet()) {
                    if ( key == "Table")
                        continue;
                    else {
                        st += key.toUpperCase() + " " + object.get(key);
                        if (key.equals("VARCHAR") )
                            st += "(" +10+ ")";
                        if (i != object.size() - 2)
                            st += ",";
                        i++;
                    }
                }
                st += ")";
                db.execSQL(st);
                st = null ;
            }
        } finally {
            if (db != null)
                DatabaseManager.getInstance().closeDatabase();
        }
    }

    public void insertRecords( String tableName , List<Map> rows)throws IOException  {//List<Map> rows, List<String> fieldNames, String tableName)
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
//            db.delete(tableName, null, null);
            for (Map m : rows) {
                ContentValues values = new ContentValues();
//                for (String fldName : m) {
                for( int index =0 ; index < m.size(); index ++) {
                    Object key =  m.keySet().toArray()[index];
                    Object valueForFirstKey = m.get(key);
                    addFieldValue(values, (String) key,valueForFirstKey);

                }
                // Do things with the list
                long insert = db.insert(tableName, null, values);
                Log.i("insertt >>.", insert+">> table : "+tableName);
            }
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }
    public void updateRecord (String tableName, Map<String,String> row, String column, String value ){
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
             ContentValues values = new ContentValues();
             for( int index =0 ; index < row.size(); index ++) {
                Object key =  row.keySet().toArray()[index];
                Object valueForFirstKey = row.get(key);
                addFieldValue(values, (String) key,valueForFirstKey);

            }
            // Do things with the list
            long update = db.update(tableName, values," Eid = "+value,null );
            Log.i("update >>.", update+"");

        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }

    }
    public void deleteRecord (String tableName, String clmn , String value ){
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            long delete = db.delete(tableName, clmn+"="+value,null );
            Log.i("delete >>.", delete+"");

        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }

    }
    private void addFieldValue(ContentValues cValues, String fldName, Object val) {
        cValues.put(fldName, (String) val);
    }
}
