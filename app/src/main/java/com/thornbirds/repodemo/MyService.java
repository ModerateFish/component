package com.thornbirds.repodemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yangli on 2017/12/2.
 */
public class MyService extends Service {
    private final static String TAG = "MyService";

    private final MyServiceImpl mService = new MyServiceImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mService;
    }

    public class MyServiceImpl extends Binder {

        public void print(String msg) {
            Log.d(TAG, msg);
        }
    }

}
