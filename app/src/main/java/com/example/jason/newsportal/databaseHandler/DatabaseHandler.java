package com.example.jason.newsportal.databaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jason.newsportal.Models.NewsModelDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 17/08/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "newsManager";
    private static final String TABLE_NEWS = "news";

    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DESC = "description";
    private static final String KEY_IMAGE_URL = "urlToImage";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_TITLE + " TEXT,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_IMAGE_URL + " TEXT" +")";
        db.execSQL(CREATE_NEWS_TABLE);

    }

    //Upgrading Table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);

    }


    public void addNewsToDatabase(JSONObject jsonObject) {
//
        try {
            SQLiteDatabase mSqlDatabase = this.getWritableDatabase();
            ContentValues mContentValues = new ContentValues();
            JSONArray array = jsonObject.getJSONArray("articles");

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                mContentValues.put(KEY_TITLE, object.getString("title"));
                mContentValues.put(KEY_AUTHOR, object.getString("author"));
                mContentValues.put(KEY_DESC, object.getString("description"));
                mContentValues.put(KEY_IMAGE_URL, object.getString("urlToImage"));
                mSqlDatabase.insert(TABLE_NEWS, null, mContentValues);
            }

            mSqlDatabase.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public List<NewsModelDTO> getNewsFromDatabase() {

        List<NewsModelDTO> newsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NewsModelDTO mNewsModelDTO = new NewsModelDTO();
                mNewsModelDTO.setTitle(cursor.getString(0));
                mNewsModelDTO.setSource(cursor.getString(1));
                mNewsModelDTO.setDescription(cursor.getString(2));
                mNewsModelDTO.setImageURL(cursor.getString(3));
                // Adding contact to list
                newsList.add(mNewsModelDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mSQLiteDatabase.close();


        return newsList;
    }

    public int getNewsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void deleteOldNewsFromDatabase() {

        String query = "DELETE FROM " + TABLE_NEWS;
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        mSQLiteDatabase.execSQL(query);
        mSQLiteDatabase.close();

    }
}
