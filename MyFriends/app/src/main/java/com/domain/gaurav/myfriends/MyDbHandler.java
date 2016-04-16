package com.domain.gaurav.myfriends;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 22/07/15.
 */
public class MyDbHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "friends.db";
    private static final String TABLE_Friends = "myfriends";
    private static final int DATABASE_VERSION = 1;

    public static final String id = "id";
    public static final String name = "name";
    public static final String email = "email";
    public static final String  phone = "phone";


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Friends);
        onCreate(db);
    }

    public MyDbHandler(Context context) {
        super(context, DB_Name, null, DATABASE_VERSION);

        try {
            copyDataBase(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Inspired from: http://stackoverflow.com/questions/16354154/copy-sqlite-database-from-assets-folder
    private void copyDataBase(Context context) throws IOException {
//Log.i("DEBUG", "DB INPUT is: " + context.getAssets().open(DB_Name).toString());

        InputStream dbInput = context.getAssets().open(DB_Name);

        // Creates databases folder if it does not exist
        String destPath = "/data/data/" + context.getPackageName() + "/databases/";

        File destPathFile =  new File(destPath);
        if (!destPathFile.exists())
            destPathFile.mkdirs();

        String outFile = destPath + DB_Name;
        OutputStream dbOutput = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = dbInput.read(buffer))>0) {
            dbOutput.write(buffer,0,length);
        }

        dbOutput.flush();
        dbOutput.close();
        dbInput.close();

    }

    //Inspired from: http://www.vogella.com/tutorials/AndroidSQLite/article.html
    public List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<Friend>();

        SQLiteDatabase database = this.getWritableDatabase();
        String[] allColumns = {id, name, email, phone};

        Cursor cursor = database.query(TABLE_Friends,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend friend = cursorToFriend(cursor);
            friends.add(friend);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return friends;
    }

    private Friend cursorToFriend(Cursor cursor) {
        Friend friend = new Friend();

        friend.setId(cursor.getInt(0));
        friend.setName(cursor.getString(1));
        friend.setEmail(cursor.getString(2));
        friend.setPhone(cursor.getString(3));

        return friend;
    }
}