package com.thornbirds.component;

import android.util.Log;

/**
 * Created by yangli on 2017/5/21.
 */
public abstract class ComponentController extends EventController {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    @Override
    protected void LogE(String msg) {
        Log.e(TAG, msg);
    }
}
