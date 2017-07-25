package com.thornbirds.component.presenter;

import android.support.annotation.NonNull;

import com.thornbirds.component.ComponentController;
import com.thornbirds.component.Params;
import com.thornbirds.component.view.TemplateView;

/**
 * Created by yangli on 2017/5/28.
 */
public class TemplatePresenter extends ComponentPresenter<TemplateView.IView, ComponentController>
        implements TemplateView.IPresenter {

    public TemplatePresenter(ComponentController eventController) {
        super(eventController);
    }

    @Override
    protected String getTAG() {
        return "TemplatePresenter";
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean onEvent(int event, Params params) {
        return false;
    }
}
