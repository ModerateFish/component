package com.thornbirds.frameworkext.component

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.thornbirds.component.utils.DebugUtils
import com.thornbirds.framework.activity.ComponentActivity
import com.thornbirds.frameworkext.component.page.BaseActivityController

/**
 * Created by yangli on 2022/1/8.
 */
abstract class BaseAppActivity<T : BaseActivityController> : ComponentActivity() {
    protected val mController = onCreateController()

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract fun onCreateController(): T

    private fun setupRequiredComponent() {
        mController.performCreate()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onCreate")
        }
        setContentView(layoutResId)

        setupRequiredComponent()
    }

    override fun onStart() {
        super.onStart()
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onStart")
        }
        mController.performStart()
    }

    override fun onResume() {
        super.onResume()
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onResume")
        }
    }

    override fun onPause() {
        super.onPause()
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onPause")
        }
    }

    override fun onStop() {
        super.onStop()
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onStop")
        }
        mController.performStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onDestroy")
        }
        mController.performDestroy()
    }

    override fun onBackPressed() {
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onBackPressed")
        }
        if (mController.performBackPressed()) {
            return
        }
        super.onBackPressed()
    }
}