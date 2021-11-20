package com.thornbirds.frameworkext.component;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.component.IEventObserver;
import com.thornbirds.component.IParams;

/**
 * Created by yangli on 2017/11/19.
 */
public abstract class ComponentController extends com.thornbirds.component.ComponentController {

    private static final boolean DEBUG = true;

    protected static final int STATE_IDLE = 0;
    protected static final int STATE_CREATED = 1;
    protected static final int STATE_STARTED = 2;
    protected static final int STATE_STOPPED = 3;
    protected static final int STATE_DESTROYED = 4;

    private int mState = STATE_IDLE;

    protected final int getState() {
        return mState;
    }

    @Nullable
    public abstract IRouter getRouter();

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

    @Override
    public final void release() {
        performDestroy();
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

    protected abstract class Action implements IEventObserver {

        protected Action() {
        }

        protected final void registerAction(int event) {
            registerObserverForEvent(event, this);
        }

        protected final void unregisterAction(int event) {
            unregisterObserverForEvent(event, this);
        }

        protected final void register() {
            onRegister();
        }

        protected final void unregister() {
            unregisterObserver(this);
        }

        protected abstract void onRegister();

        @Override
        public abstract boolean onEvent(int event, IParams params);
    }

    public interface IRouter {
        boolean startPage(@NonNull String route);

        boolean startPage(@NonNull String route, @Nullable IPageParams params);

        boolean popPage();

        boolean popPage(@NonNull ComponentController controller);
    }
}
