package com.thornbirds.frameworkext.component.presenter;

import com.thornbirds.frameworkext.component.page.BasePageController;

/**
 * Created by yangli on 2017/9/18.
 */
public abstract class ComponentPresenter<VIEW> extends
        com.thornbirds.component.presenter.ComponentPresenter<VIEW, BasePageController> {

    public ComponentPresenter(BasePageController controller) {
        super(controller);
    }

}
