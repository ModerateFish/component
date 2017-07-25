package com.thornbirds.component;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thornbirds.component.annotation.RegisterComponent;
import com.thornbirds.component.presenter.IComponentPresenter;
import com.thornbirds.component.presenter.TemplatePresenter;
import com.thornbirds.component.view.TemplateView;

/**
 * Created by yangli on 2017/5/21.
 */
public abstract class ComponentView<VIEW extends View, CONTROLLER extends ComponentController>
        extends CompoundView<VIEW, CONTROLLER> {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    protected final <T extends View> T $(@IdRes int resId) {
        return (T) mRootView.findViewById(resId);
    }

    public ComponentView(ViewGroup parentView, @LayoutRes int resource) {
        super((VIEW) LayoutInflater.from(parentView.getContext()).inflate(resource, parentView));
    }

    public ComponentView(VIEW rootView) {
        super(rootView);
    }

    @RegisterComponent(name = "Test", viewOn = false, presenterOn = false)
    @Override
    public void setup() {
        // 装配TemplateView和TemplatePresenter
        TemplateView view = new TemplateView(mRootView.getContext());
        TemplatePresenter presenter = new TemplatePresenter(mController);
        registerComponent(view, presenter);
    }

    @CallSuper
    public void release() {
        for (IComponentPresenter presenter : mPresenterSet) {
            presenter.destroy();
        }
        mPresenterSet.clear();
    }
}
