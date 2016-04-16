package ua.mycompany.txteditor;

import android.app.Activity;
import android.app.DialogFragment;
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
    String RECORD_NAME = "name";
    Fragment listFragment;
    Fragment editTextFragment;
    FrameLayout editFrame;
    FrameLayout listFrame;
    DialogFragment dlg;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(LOG_TAG, "--- MainActivity OnCreate ---");

        dlg = new AddDialogFragment();


        listFrame = (FrameLayout) findViewById(R.id.listFragment);
        editFrame = (FrameLayout) findViewById(R.id.editTextFragment);

//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ||
//                getResources().getConfiguration().screenWidthDp <= 640) {
//            editFrame.setVisibility(View.GONE);
//        } else {
//            Toast.makeText(getApplicationContext(),
//            getResources().getConfiguration().screenWidthDp + "",
//                    Toast.LENGTH_LONG).show();
//        }


        listFragment = new ListFragment();
        editTextFragment = new EditTextFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.listFragment, listFragment);
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
           Log.d(LOG_TAG, getResources().getConfiguration().screenWidthDp + "");
            if (getResources().getConfiguration().screenWidthDp <= 640) {
                Intent intent = new Intent(getApplicationContext(), EditTextActivity.class);
                intent.putExtra(RECORD_NAME, s);
                startActivity(intent);
            } else {
                editFrame.setVisibility(View.VISIBLE);
                editTextFragment = EditTextFragment.newInstance(s);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.editTextFragment, editTextFragment);
                ft.commit();
            }
        }
    }




    public void closeEditFrame() {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.hide(listFragment);
            listFragment = new ListFragment();
            ft.replace(R.id.listFragment, listFragment);
            ft.remove(editTextFragment);
            ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            menu.setGroupVisible(R.id.group1, true);
            menu.setGroupVisible(R.id.group2, false);
        } else {
            menu.setGroupVisible(R.id.group2, true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNew:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Toast.makeText(getApplicationContext(), "ADD BUT 2", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), EditTextActivity.class);
                    String t = "1";
                    intent.putExtra(RECORD_NAME, t);
                    startActivity(intent);
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    editTextFragment = EditTextFragment.newInstance("1");
                    ft.replace(R.id.editTextFragment, editTextFragment);
                    ft.commit();
                }

                break;
            case R.id.saveText:
                break;
            case R.id.exitEditText:
                closeEditFrame();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "MainActivity: onResume()");
        listFragment = new ListFragment();
        editTextFragment = new EditTextFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.listFragment, listFragment);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MainActivity: onDestroy()");
    }
}




    //        Fragment frag1 = getFragmentManager().findFragmentById(R.id.editTextFragment);
//        ((TextView)frag1.getView().findViewById(R.id.textView)).setText("Text from Fragment 2:" + s);


