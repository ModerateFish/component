package com.thornbirds.repodemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.thornbirds.framework.activity.ComponentActivity
import com.thornbirds.repodemo.MyService.MyServiceImpl

class MainActivity : ComponentActivity() {

    override val TAG: String = "MainActivity"

    private var mPersistFragment: Fragment? = null
    private var mAddFragment1: Fragment? = null
    private var mAddFragment2: Fragment? = null
    private var mReplaceFragment1: Fragment? = null
    private var mReplaceFragment2: Fragment? = null
    private var mMyService: MyServiceImpl? = null
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mMyService = service as MyServiceImpl
            Log.d(TAG, "onServiceDisconnected mMyService")
            mMyService!!.print("log onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mMyService = null
            Log.d(TAG, "onServiceDisconnected mMyService")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            //                Intent intent = new Intent(MainActivity.this, com.thornbirds.framework.MainActivity.class);
            //                startActivity(intent);
            val fragmentManager =
                supportFragmentManager
            if (mAddFragment1 == null) {
                val fragment: Fragment = TestFragment()
                val bundle = Bundle()
                bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 1)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().add(R.id.root_container, fragment).commit()
                mAddFragment1 = fragment
            } else if (mAddFragment2 == null) {
                val fragment: Fragment = TestFragment()
                val bundle = Bundle()
                bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 2)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().add(R.id.root_container, fragment).commit()
                mAddFragment2 = fragment
            } else if (mReplaceFragment1 == null) {
                val fragment: Fragment = TestFragment()
                val bundle = Bundle()
                bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 3)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.root_container, fragment).commit()
                mReplaceFragment1 = fragment
            } else if (mReplaceFragment2 == null) {
                val fragment: Fragment = TestFragment()
                val bundle = Bundle()
                bundle.putInt(TestFragment.EXTRA_FRAGMENT_ID, 4)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.root_container, fragment).commit()
                mReplaceFragment2 = fragment
            }
        }
        mPersistFragment = supportFragmentManager.findFragmentById(R.id.test_fragment)
        val intent = Intent(this, MyService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume mMyService")
        if (mMyService != null) {
            mMyService!!.print("log onResume")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop mMyService")
        if (mMyService != null) {
            mMyService!!.print("log onStop")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy mMyService")
        if (mMyService != null) {
            mMyService!!.print("log onDestroy")
        }
        unbindService(mServiceConnection)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}