package com.thornbirds.framework.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Basic definition of Component Activity for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentActivity extends AppCompatActivity {
    protected final String TAG = getTAG();

    protected final <V extends View> V $(int id) {
        return (V) findViewById(id);
    }

    protected abstract String getTAG();
}
