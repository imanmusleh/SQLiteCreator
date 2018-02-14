package com.example.iman.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mylibrary.DBCreator;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.example.iman.androidapp.R;



public class MainActivity extends AppCompatActivity {
public Context context ;
    private XmlPullParserFactory xmlPullParserFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        context = this;
        DBCreator obj =  new DBCreator( context, "database" ,1  );
        List<Map>list= new ArrayList  <Map>();
        Map<String,String>   row = new HashMap<String,String>();
        row.put("Eid", "102030");
        row.put("Name", "Ahmad");
        row.put("'Gender'", "Male");
        row.put("'Address'", "Ramallah/btn elhawa str");
        row.clear();
        list.add(row);
        row.put("Eid", "100200");
        row.put("Name", "Ali");
        row.put("'Gender'", "Male");
        row.put("'Address'", "Jenin/alward str");
        list.add(row);
        try {
            obj.insertRecords("Employee",list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        obj.deleteRecord("Employee","Eid", "100200");//String tableName, String id , String value ){


        Map<String,String> updateRow = new HashMap<String,String>();
        updateRow.put("Eid", "102030");
        updateRow.put("Name", "Ahmad ahmed");
        updateRow.put("'Gender'", "Male");
        updateRow.put("'Address'", "Ramallah/btn elhawa str 100");

        obj.updateRecord("Employee",updateRow,  "Eid", "102030");//String tableName, Map<String,String> row, String code)

    }

}
