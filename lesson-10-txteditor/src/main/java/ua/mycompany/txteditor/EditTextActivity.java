package ua.mycompany.txteditor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by Maxim on 15.04.2016.
 */
public class EditTextActivity extends AppCompatActivity {


    Fragment editTextFragment;
    FrameLayout editFrame;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text_layout);
        Log.d(LOG_TAG, "--- MainActivity OnCreate ---");

        editFrame = (FrameLayout) findViewById(R.id.editTextFrame);
        editTextFragment = new EditTextFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.editTextFrame, editTextFragment);
        ft.commit();

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveText:

                break;
            case  R.id.exitEditText:
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "HURRAY!!222", Toast.LENGTH_LONG).show();
//                    closeEditFrame();
                }
                break;
            case R.id.addNew:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
