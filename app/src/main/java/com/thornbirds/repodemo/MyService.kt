package com.thornbirds.repodemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * Created by yangli on 2017/12/2.
 */
private const val TAG = "MyService"

class MyService : Service() {
    private val mService = MyServiceImpl()

    override fun onBind(intent: Intent): IBinder {
        return mService
    }

    inner class MyServiceImpl : Binder() {
        fun print(msg: String) {
            Log.d(TAG, msg)
        }
    }
}