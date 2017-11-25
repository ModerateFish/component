package com.thornbirds.component;

import android.util.Log;

/**
 * Basic definition of Component Controller for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentController extends EventController {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    @Override
    protected void LogE(String msg) {
        Log.e(TAG, msg);
    }
}
