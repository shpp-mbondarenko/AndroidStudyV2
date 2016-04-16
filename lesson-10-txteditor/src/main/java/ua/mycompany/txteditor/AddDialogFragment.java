package ua.mycompany.txteditor;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Maxim on 15.04.2016.
 */
public class AddDialogFragment extends DialogFragment implements View.OnClickListener {


    RecordsHelper recordsHelper;
    DBHelper dbHelper;
    SQLiteDatabase db;
    EditText eTNewRecord;
    Button btnNewRecord;
    final String LOG_TAG = "myLogs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        // connecting to DB
        db = dbHelper.getWritableDatabase();
        recordsHelper = new RecordsHelper();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, null);
        getDialog().setTitle("Add new record");
        btnNewRecord = (Button) v.findViewById(R.id.btnAddRecord);
        eTNewRecord = (EditText) v.findViewById(R.id.eTRecordName);
        btnNewRecord.setOnClickListener(this);
        eTNewRecord.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddRecord:
                String text = eTNewRecord.getText().toString();
                recordsHelper.addRecord(db, text, text);
                recordsHelper.viewAllRecords(db);

                dismiss();
                break;

        }

    }

}
