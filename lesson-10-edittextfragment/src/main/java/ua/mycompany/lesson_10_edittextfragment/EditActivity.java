package ua.mycompany.lesson_10_edittextfragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Maxim on 06.04.2016.
 */
public class EditActivity extends AppCompatActivity {

    String RECORD_NAME = "name";
    public String record;
    final String LOG_TAG = "myLogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        Intent intent = getIntent();
        record = intent.getStringExtra(RECORD_NAME);
        Log.d(LOG_TAG, "EditActivity:Intent extra - " + record);

        // Begin the transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.editFrame, new EditTextFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
        }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "--- EditActivity:OnStart ---");
//        Fragment frag2 = getFragmentManager().findFragmentById(R.id.editFrame);
//        ((EditText) frag2.getView().findViewById(R.id.eTEditRecord)).setText("Access to Fragment 2 from Activity");


    }
}
