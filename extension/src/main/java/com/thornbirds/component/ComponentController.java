package com.thornbirds.component;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Basic definition of Component Controller for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentController implements IEventController {
    protected static final boolean DEBUG = true;

    protected final String TAG = getTAG();

    protected abstract String getTAG();

    @NonNull
    private final IEventController mEventController;

    protected final void LogE(String msg) {
        Log.e(TAG, msg);
    }

    public ComponentController(@Nullable IEventController eventController) {
        if (eventController != null) {
            this.mEventController = eventController;
        } else {
            this.mEventController = new EventController() {
                @Override
                protected void LogE(String msg) {
                    throw new RuntimeException(msg);
                }
            };
        }
    }

    @Override
    public final void registerObserverForEvent(int event, IEventObserver observer) {
        mEventController.registerObserverForEvent(event, observer);
    }

    @Override
    public final void unregisterObserverForEvent(int event, IEventObserver observer) {
        mEventController.unregisterObserverForEvent(event, observer);
    }

    @Override
    public final void unregisterObserver(IEventObserver observer) {
        mEventController.unregisterObserver(observer);
    }

    @Override
    public final boolean postEvent(int event) {
        return mEventController.postEvent(event);
    }

    @Override
    public final boolean postEvent(int event, IParams params) {
        return mEventController.postEvent(event, params);
    }

    @Override
    public final void release() {
        mEventController.release();
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
}
