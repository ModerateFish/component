package com.thornbirds.component;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.thornbirds.component.presenter.IEventPresenter;

/**
 * Created by yangli on 2017/5/21.
 */
public abstract class ComponentView<VIEW extends View, CONTROLLER extends ComponentController>
        extends CompoundView<VIEW, CONTROLLER> {
    protected final String TAG = getTAG();

    @NonNull
    protected ViewGroup mParentView;

    protected final <T extends View> T $(@IdRes int resId) {
        return (T) mContentView.findViewById(resId);
    }

    protected final <T extends View> T $(@NonNull View view, @IdRes int resId) {
        return (T) view.findViewById(resId);
    }

    protected final void $click(@NonNull View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
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
