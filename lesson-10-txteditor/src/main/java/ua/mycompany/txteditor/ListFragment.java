package ua.mycompany.txteditor;

import android.app.Activity;
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
    ArrayList<String> headers;
    RecordsHelper recordsHelper;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ArrayAdapter<String> adapter;
    final String LOG_TAG = "myLogs";

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
        Log.d(LOG_TAG, "--- ListFragmrnt: onCreate ---");
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

        lv = (ListView) view.findViewById(R.id.listViewAllRecords);

        reBuildList();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Toast.makeText(getActivity().getApplicationContext(), item, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), EditTextActivity.class);
                    intent.putExtra(RECORD_NAME, item);
                    startActivity(intent);
                }
                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                    someEventListener.someEvent(item);
                }
            }
        });
    }

    public void reBuildList() {
        headers = recordsHelper.getRecordHeaders(db);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.list_item,
                headers);
        lv.setAdapter(adapter);
    }


}
