package com.example.mylibrary;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iman on 8/13/2017.
 */

public class xmlParser {
    ArrayList<Map<String, String>> map;

    public xmlParser(Context context){
         XmlPullParserFactory xmlPullParserFactory;

        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
             InputStream is = openXmlInputStream(context);
            parser.setInput(is, null);
            getLoadedXmlValues(parser);
         } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Map<String, String>> getMap() {
        return map;
    }

    public void setMap(ArrayList<Map<String, String>> map) {
        this.map = map;
    }
    private InputStream openXmlInputStream(Context context  ) throws IOException {
//        AssetManager assetManager= contaxt.getAssets().open("parse.xml");//getAssets();
        InputStream is =  context.getAssets().open("database.xml");
        return is;
    }
    private  void getLoadedXmlValues(XmlPullParser parser) throws XmlPullParserException, IOException {//ArrayList<Map<String, String>>
        Map<String,String> map = null;
        ArrayList<Map<String, String>>arrayList= new ArrayList<Map<String, String>>();

        String key = null, value = null, column= null , type = null ;
        int eventType = parser.getEventType();
        String name = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                Log.d("utils","Start document");
            } else if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("Table")) {
                    String tableName = parser.getAttributeValue(null, "name");//)(null, "linked", false);

                    map = new HashMap<String,String>();//: new HashMap<String, String>();
                    map.put("Table", tableName);

                } else if (parser.getName().equals("column")) {
                    column  = parser.getAttributeValue(null, "name");

                    if (null == column) {//        <column name="code">String</column>

                    }//type
                }

            } else if (eventType == XmlPullParser.END_TAG) {

                if (parser.getName().equals("column")) {
                    map.put(column, type);
                    key = null;
                    value = null;
                }
                else  if (parser.getName().equals("Table")) {
                    arrayList.add(map);
                }
            } else if (eventType == XmlPullParser.TEXT) {
                if (null != column) {
                    type = parser.getText();
                }
            }
            eventType = parser.next();
        }
        setMap(arrayList);
    }

}
