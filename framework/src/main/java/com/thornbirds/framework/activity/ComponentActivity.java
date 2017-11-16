package com.thornbirds.framework.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yangli on 2017/9/16.
 */
public abstract class ComponentActivity extends AppCompatActivity {
    protected final String TAG = getTAG();

    protected final <V extends View> V $(int id) {
        return (V) findViewById(id);
    }

    protected abstract String getTAG();
}
