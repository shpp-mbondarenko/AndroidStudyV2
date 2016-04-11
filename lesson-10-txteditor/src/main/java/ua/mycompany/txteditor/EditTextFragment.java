package ua.mycompany.txteditor;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Maxim on 06.04.2016.
 */
public class EditTextFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    static String RECORD_NAME = "name";
    EditText eTEditRecord;
    String recordName;
    RecordsHelper recordsHelper;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public static EditTextFragment newInstance(String name) {
        EditTextFragment fragment = new EditTextFragment();
        Bundle args = new Bundle();
        args.putString(RECORD_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(LOG_TAG,"--- EditText OnCreate ---");
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        // connecting to DB
        db = dbHelper.getWritableDatabase();
        recordsHelper = new RecordsHelper();
        recordName = getArguments().getString(RECORD_NAME,"");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_text_fragment, null);
        eTEditRecord = (EditText) v.findViewById(R.id.eTEditRecord);
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(LOG_TAG,"--- EditText:onViewCreated record name - " + recordName +"!");
        String text = recordsHelper.getRecordText(db, recordName);
        eTEditRecord.setText(text);
    }




    public void saveText() {
        Log.d(LOG_TAG, "--- Edit Text Fragment: saveText() ---");
        String text = "";
        text = eTEditRecord.getText().toString();
        recordsHelper.saveRecord(db, recordName, text);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveText:
                saveText();
                Toast.makeText(getActivity().getApplicationContext(), "SAVING TEXT", Toast.LENGTH_LONG).show();
                break;
            case  R.id.exitEditText:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
