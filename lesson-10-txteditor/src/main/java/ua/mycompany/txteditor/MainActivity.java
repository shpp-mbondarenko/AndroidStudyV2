package ua.mycompany.txteditor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListFragment.fragmentEventListener{
    Fragment listFragment;
    Fragment editTextFragment;
    FrameLayout editFrame;
    FrameLayout listFrame;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(LOG_TAG, "--- MainActivity OnCreate ---");


        listFrame = (FrameLayout) findViewById(R.id.listFragment);
        editFrame = (FrameLayout) findViewById(R.id.editTextFragment);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ||
                getResources().getConfiguration().screenWidthDp <= 640) {
            editFrame.setVisibility(View.GONE);
        } else {
            Toast.makeText(getApplicationContext(),
            getResources().getConfiguration().screenWidthDp + "",
                    Toast.LENGTH_LONG).show();
        }


        listFragment = new ListFragment();
        editTextFragment = new EditTextFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.listFragment, listFragment);
//        ft.add(R.id.editTextFragment, editTextFragment);
        ft.commit();
    }

    @Override
    public void someEvent(String s) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            editFrame.setVisibility(View.VISIBLE);
            listFrame.setVisibility(View.GONE);
            editTextFragment = EditTextFragment.newInstance(s);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(listFragment);
            ft.replace(R.id.editTextFragment, editTextFragment);
            ft.commit();

        } else {
            editFrame.setVisibility(View.VISIBLE);
            editTextFragment = EditTextFragment.newInstance(s);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.editTextFragment, editTextFragment);
            ft.commit();
        }
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
                    closeEditFrame();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
        public void closeEditFrame() {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.hide(listFragment);
            listFragment = new ListFragment();
            ft.replace(R.id.listFragment, listFragment);
            ft.remove(editTextFragment);
            ft.commit();
    }

//        Fragment frag1 = getFragmentManager().findFragmentById(R.id.editTextFragment);
//        ((TextView)frag1.getView().findViewById(R.id.textView)).setText("Text from Fragment 2:" + s);
    }

