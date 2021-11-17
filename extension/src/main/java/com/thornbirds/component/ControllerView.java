package com.thornbirds.component;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.thornbirds.component.presenter.IEventPresenter;

/**
 * Basic definition of Controller View for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ControllerView<VIEW extends View, CONTROLLER extends EventController>
        extends CompoundView<VIEW, CONTROLLER> implements IEventObserver {
    protected final String TAG = getTAG();

    @NonNull
    protected ViewGroup mParentView;

    protected abstract String getTAG();

    protected final <T extends View> T $(@IdRes int resId) {
        return (T) mContentView.findViewById(resId);
    }

    protected final void registerAction(int event) {
        mController.registerObserverForEvent(event, this);
    }

    protected final void unregisterAction(int event) {
        mController.unregisterObserverForEvent(event, this);
    }

    /**
     * Register Component which has presenter with a view, but not IEventView.
     *
     * @param view      target view
     * @param presenter target presenter
     */
    protected final void registerHybridComponent(IEventPresenter presenter, View view) {
        presenter.setView(view);
        mPresenterSet.add(presenter);
    }

    public ControllerView(@NonNull ViewGroup parentView, @NonNull CONTROLLER controller) {
        super(controller);
        mParentView = parentView;
    }

    @CallSuper
    @Override
    public void stopView() {
        super.stopView();
        mController.unregisterObserver(this);
    }
}
