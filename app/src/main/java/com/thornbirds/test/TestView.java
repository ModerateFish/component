package com.thornbirds.test;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thornbirds.component.ComponentView;
import com.thornbirds.component.CompoundView;
import com.thornbirds.component.annotation.RegisterComponent;
import com.thornbirds.test.presenter.IComponentPresenter;
import com.thornbirds.test.presenter.TemplatePresenter;
import com.thornbirds.test.view.TemplateView;

/**
 * Created by yangli on 2017/5/21.
 */
public class TestView extends ComponentView<View, TestController> {

    @Override
    protected String getTAG() {
        return "TestView";
    }

    public TestView(@NonNull ViewGroup parentView, @NonNull TestController controller) {
        super(parentView, controller);
    }

    @RegisterComponent(name = "Test", viewOn = false, presenterOn = false)
    @Override
    public void setupView() {
        // 装配TemplateView和TemplatePresenter
        TemplateView view = new TemplateView(mParentView.getContext());
        TemplatePresenter presenter = new TemplatePresenter(mController);
        registerComponent(view, presenter);
    }
}
