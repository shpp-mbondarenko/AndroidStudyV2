package ua.mycompany.notes;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Maxim on 06.04.2016.
 */
public class ListFragment extends Fragment {

    String RECORD_NAME = "name";
    ListView lv;
    Button btnAddRecord;
    EditText eTRecordName;
    ArrayList<String> headers;
    RecordsHelper recordsHelper;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        // connecting to DB
        db = dbHelper.getWritableDatabase();
        recordsHelper = new RecordsHelper();
        headers = recordsHelper.getRecordHeaders(db);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        eTRecordName = (EditText) view.findViewById(R.id.eTRecordName);
        btnAddRecord = (Button) view.findViewById(R.id.btnAddRecord);
        lv = (ListView) view.findViewById(R.id.listViewAllRecords);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                headers);
        lv.setAdapter(adapter);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = eTRecordName.getText().toString();
                recordsHelper.addRecord(db, text, text);
                recordsHelper.viewAllRecords(db);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(), item , Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity().getApplicationContext(), EditActivity.class);
//                intent.putExtra(RECORD_NAME, item);
//                startActivity(intent);
            }
        });
    }
}
