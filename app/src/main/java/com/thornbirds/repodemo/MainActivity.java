package com.thornbirds.repodemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Fragment mPersistFragment;
    private Fragment mAddFragment1;
    private Fragment mAddFragment2;
    private Fragment mReplaceFragment1;
    private Fragment mReplaceFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent intent = new Intent(MainActivity.this, com.thornbirds.framework.MainActivity.class);
//                startActivity(intent);
                FragmentManager fragmentManager = getFragmentManager();
                if (mAddFragment1 == null) {
                    Fragment fragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 1);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().add(R.id.root_container, fragment).commit();
                    mAddFragment1 = fragment;
                } else if (mAddFragment2 == null) {
                    Fragment fragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 2);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().add(R.id.root_container, fragment).commit();
                    mAddFragment2 = fragment;
                } else if (mReplaceFragment1 == null) {
                    Fragment fragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 3);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.root_container, fragment).commit();
                    mReplaceFragment1 = fragment;
                } else if (mReplaceFragment2 == null) {
                    Fragment fragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 4);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.root_container, fragment).commit();
                    mReplaceFragment2 = fragment;
                }
            }
        });

        mPersistFragment = getFragmentManager().findFragmentById(R.id.test_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
