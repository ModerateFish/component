package com.thornbirds.test.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yangli on 2017/5/28.
 */
public class TemplateView extends View implements
        IComponentView<TemplateView.IPresenter, TemplateView.IView> {

    protected IPresenter mPresenter;

    public TemplateView(Context context) {
        super(context);
    }

    public TemplateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TemplateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        mPresenter = iPresenter;
    }

    @Override
    public IView getViewProxy() {
        return null;
    }

    public interface IView extends IViewProxy {
    }

    public interface IPresenter {
    }
}
