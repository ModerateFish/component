package com.thornbirds.frameworkext.component;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.framework.activity.ComponentActivity;
import com.thornbirds.frameworkext.component.page.BaseActivityController;

/**
 * Created by yangli on 2017/9/16.
 */
public abstract class BaseAppActivity<C extends BaseActivityController>
        extends ComponentActivity {

    private static final boolean DEBUG = true;

    protected final C mController = onCreateController();

    @Override
    @NonNull
    protected abstract String getTAG();

    @LayoutRes
    protected abstract int getLayoutResId();

    @NonNull
    protected abstract C onCreateController();

    private void setupRequiredComponent() {
        mController.performCreate();
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Log.d(TAG, "onCreate");
        }
        setContentView(getLayoutResId());
        setupRequiredComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) {
            Log.d(TAG, "onStart");
        }
        mController.performStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) {
            Log.d(TAG, "onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (DEBUG) {
            Log.d(TAG, "onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DEBUG) {
            Log.d(TAG, "onStop");
        }
        mController.performStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Log.d(TAG, "onDestroy");
        }
        mController.performDestroy();
    }

    @Override
    public void onBackPressed() {
        if (DEBUG) {
            Log.d(TAG, "onBackPressed");
        }
        if (mController.performBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
