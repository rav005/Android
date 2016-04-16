package com.domain.gaurav.myfriends;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by gaurav on 05/08/15.
 */

//Inspired from: http://developer.android.com/intl/ja/guide/topics/providers/content-provider-creating.html
//  http://www.tutorialspoint.com/android/android_content_providers.htm
//  https://www.youtube.com/watch?v=90HZKWx8Ex8
public class Content extends ContentProvider {

    static final String PROVIDER_NAME = "com.domain.gaurav.myfriends.friends";
    static final String URL = "content://" + PROVIDER_NAME + "/myfriends";
    static final Uri CONTENT_URI = Uri.parse(URL);

    private SQLiteDatabase db;
    private MyDbHandler dbHandler;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "myfriends", 1);
    }

    @Override
    public boolean onCreate() {
        dbHandler = new MyDbHandler(getContext());
        db = dbHandler.getWritableDatabase();

        return (db == null)? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.query("myfriends", projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	"myfriends", "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        if(uriMatcher.match(uri) == 1){
            count = db.delete("myfriends", selection, selectionArgs);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        if(uriMatcher.match(uri) == 1){
            count = db.update("myfriends", values, selection, selectionArgs);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
