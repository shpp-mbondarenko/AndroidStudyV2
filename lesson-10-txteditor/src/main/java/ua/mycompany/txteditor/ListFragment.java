package ua.mycompany.txteditor;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Maxim on 06.04.2016.
 */
public class ListFragment extends Fragment {

    String RECORD_NAME = "name";
    ListView lv;
    TextView tVRecord;
    Button btnAddRecord;
    EditText eTRecordName;
    ArrayList<String> headers;
    RecordsHelper recordsHelper;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ArrayAdapter<String> adapter;
    int visibility = 1;

    public interface fragmentEventListener {
        public void someEvent(String s);
    }
    fragmentEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        someEventListener = (fragmentEventListener) activity;
    }



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
        tVRecord = (TextView) view.findViewById(R.id.tVRecord);
        eTRecordName = (EditText) view.findViewById(R.id.eTRecordName);
        btnAddRecord = (Button) view.findViewById(R.id.btnAddRecord);
        lv = (ListView) view.findViewById(R.id.listViewAllRecords);

        tVRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tVRecord:
                        if (visibility == 0) {
                            btnAddRecord.setVisibility(View.GONE);
                            eTRecordName.setVisibility(View.GONE);
                            visibility = 1;
                        } else {
                            btnAddRecord.setVisibility(View.VISIBLE);
                            eTRecordName.setVisibility(View.VISIBLE);
                            visibility = 0;
                        }

                }
            }
        });

        reBuildList(headers);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = eTRecordName.getText().toString();
                recordsHelper.addRecord(db, text, text);
                recordsHelper.viewAllRecords(db);
                btnAddRecord.setVisibility(View.GONE);
                eTRecordName.setVisibility(View.GONE);
                visibility = 1;
                    //after adding new record refresh listView
                headers = recordsHelper.getRecordHeaders(db);
                reBuildList(headers);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(), item, Toast.LENGTH_LONG).show();
                someEventListener.someEvent(item);
            }
        });
    }

    private void reBuildList(ArrayList<String> headers) {
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                headers);
        lv.setAdapter(adapter);
    }
}
