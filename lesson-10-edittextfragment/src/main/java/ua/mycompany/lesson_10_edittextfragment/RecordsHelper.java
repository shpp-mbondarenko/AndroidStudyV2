package ua.mycompany.lesson_10_edittextfragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Maxim on 06.04.2016.
 */
public class RecordsHelper  {
    private static final String TABLE_NAME = "records";

    final String LOG_TAG = "myLogs";


    public ArrayList<String> getRecordHeaders(SQLiteDatabase db){
        Cursor c = null;
        ArrayList<String> tmp = new ArrayList<String>();
        c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            //defining column's number by theirs name
            int recordHeaderIndex = c.getColumnIndex("recordHeader");
            do {
                // retrieving values by number of column
                tmp.add(c.getString(recordHeaderIndex));
            } while (c.moveToNext());
        } else {
            Log.d(LOG_TAG, "0 rows");
        }
        c.close();
        return tmp;
    }

    public void addRecord(SQLiteDatabase db, String recordHeader, String recordText){
        Log.d(LOG_TAG, "--- Insert in records: ---" + recordHeader + "!");

        // create object for data
        ContentValues cv = new ContentValues();
        // prepare the data to be inserted, in the form of pairs: Column name - value
        cv.put("recordHeader", recordHeader);
        cv.put("recordText", recordText);
        // insert one record and retrieving ID
        long rowID = db.insert(TABLE_NAME, null, cv);
        Log.d(LOG_TAG, "Row inserted, ID = " + rowID);
    }

    public void viewAllRecords(SQLiteDatabase db){
        Cursor c = null;
        Log.d(LOG_TAG, "--- Rows in records: ---");
        // make query to DB retrieving all users
        c = db.query(TABLE_NAME, null, null, null, null, null, null);

        //place cursor on first position
        // if c is null return false
        if (c != null && c.moveToFirst()) {
            //defining column's number by theirs name
            int idColIndex = c.getColumnIndex("id");
            int recordHeaderIndex = c.getColumnIndex("recordHeader");
            int recordTextIndex = c.getColumnIndex("recordText");
            do {
                // retrieving values by number of column
                Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) +
                        ", recordHeader = " + c.getString(recordHeaderIndex) +
                        ", recordText = " + c.getString(recordTextIndex));
                // go to next string
                // if she don't exists  then false - out from cycle
            } while (c.moveToNext());
        } else {
            Log.d(LOG_TAG, "0 rows");
        }
        c.close();
    }

    public String getRecordText(SQLiteDatabase db, String recordName) {
        Cursor c = null;
        String res = "";
        String selection = "recordHeader = ?";
        String[] selectionArgs = new String[] { recordName };
        c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        //place cursor on first position
        // if c is null return false
        if (c != null && c.moveToFirst()) {
            int recordTextIndex = c.getColumnIndex("recordText");
            res = c.getString(recordTextIndex);
        } else {
            Log.d(LOG_TAG, "No record!");
        }
        c.close();
        return res;
    }

    public void saveRecord(SQLiteDatabase db, String recordHeader, String recordText) {
        ContentValues cv = new ContentValues();
        String newRecordHeader = "";
        if (recordText.length() >= 6 ) {
            newRecordHeader = recordText.substring(0, 6);
        } else {
            newRecordHeader = recordText;
        }
        cv.put("recordHeader", newRecordHeader);
        cv.put("recordText", recordText);
        // обновляем по id
        int updCount = db.update(TABLE_NAME, cv, "recordHeader = ?", new String[] { recordHeader });

    }
}
