package com.thornbirds.component;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.thornbirds.component.presenter.IEventPresenter;

/**
 * Created by yangli on 2017/9/16.
 */
public abstract class ControllerView<VIEW extends View, CONTROLLER extends ComponentController>
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
