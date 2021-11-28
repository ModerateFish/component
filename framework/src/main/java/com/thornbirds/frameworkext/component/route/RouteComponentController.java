package com.thornbirds.frameworkext.component.route;

import android.util.Log;

import androidx.annotation.Nullable;

import com.thornbirds.component.ComponentController;
import com.thornbirds.component.IEventController;

/**
 * Created by yangli on 2017/11/19.
 */
public abstract class RouteComponentController<T extends IRouter> extends ComponentController {

    public static final int STATE_IDLE = 0;
    public static final int STATE_CREATED = 1;
    public static final int STATE_STARTED = 2;
    public static final int STATE_STOPPED = 3;
    public static final int STATE_DESTROYED = 4;

    @Nullable
    public abstract T getRouter();

    @Nullable
    public abstract T getParentRouter();

    public abstract boolean exitRouter();

    private int mState = STATE_IDLE;

    public final int getState() {
        return mState;
    }

    protected abstract void onCreate();

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void onDestroy();

    protected abstract boolean onBackPressed();

    public final void performCreate() {
        if (mState == STATE_CREATED) {
            return;
        }
        if (mState == STATE_IDLE) {
            mState = STATE_CREATED;
            if (DEBUG) {
                Log.d(TAG, "onCreate");
            }
            onCreate();
        }
    }

    public final void performStart() {
        if (mState == STATE_STARTED) {
            return;
        }
        if (mState == STATE_CREATED || mState == STATE_STOPPED) {
            mState = STATE_STARTED;
            if (DEBUG) {
                Log.d(TAG, "onStart");
            }
            onStart();
        }
    }

    public final void performStop() {
        if (mState == STATE_STOPPED) {
            return;
        }
        if (mState == STATE_STARTED) {
            mState = STATE_STOPPED;
            if (DEBUG) {
                Log.d(TAG, "onStop");
            }
            onStop();
        }
    }

    public final void performDestroy() {
        if (mState == STATE_DESTROYED) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "onDestroy");
        }
        mState = STATE_DESTROYED;
        super.release();
        onDestroy();
    }

    public final boolean performBackPressed() {
        if (mState == STATE_DESTROYED) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, "onBackPressed");
        }
        return onBackPressed();
    }

    public RouteComponentController(@Nullable IEventController eventController) {
        super(eventController);
    }
}
