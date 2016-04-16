package ua.mycompany.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Maxim on 06.04.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "DB";
    private static final String TABLE_NAME = "records";
    private static final int DB_VERSION = 1;
    final String LOG_TAG = "myLogs";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        //constructor of superclass
        super(context, DATA_BASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // create table with fields
        db.execSQL("create table " + TABLE_NAME + " ("
                + "id integer primary key autoincrement,"
                + "recordHeader text,"
                + "recordText text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion
                + " to " + newVersion + " version --- ");
        if (oldVersion == 1 && newVersion == 2) {
            db.beginTransaction();
            try {

                db.setTransactionSuccessful();
                Log.d(LOG_TAG, " --- ADD successful ---");
            } finally {
                db.endTransaction();
            }
        }
    }
}