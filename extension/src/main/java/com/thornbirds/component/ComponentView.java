package com.thornbirds.component;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.thornbirds.component.presenter.IEventPresenter;

/**
 * Basic definition of Component View for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentView<VIEW extends View, CONTROLLER extends ComponentController>
        extends CompoundView<VIEW, CONTROLLER> {
    protected final String TAG = getTAG();

    @NonNull
    protected ViewGroup mParentView;

    protected final <T extends View> T $(@IdRes int resId) {
        return (T) mContentView.findViewById(resId);
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

    protected abstract String getTAG();

    public ComponentView(@NonNull ViewGroup parentView, @NonNull CONTROLLER controller) {
        super(controller);
        mParentView = parentView;
    }
}
