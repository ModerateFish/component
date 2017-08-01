package com.thornbirds.component.presenter;

import com.thornbirds.component.Params;
import com.thornbirds.test.TestController;
import com.thornbirds.component.view.TemplateView;

/**
 * Created by yangli on 2017/5/28.
 */
public class TemplatePresenter extends ComponentPresenter<TemplateView.IView, TestController>
        implements TemplateView.IPresenter {

    public TemplatePresenter(TestController controller) {
        super(controller);
    }

    @Override
    protected String getTAG() {
        return "TemplatePresenter";
    }

    @Override
    public void startPresenter() {
    }

    @Override
    public void stopPresenter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean onEvent(int event, Params params) {
        return false;
    }
}
