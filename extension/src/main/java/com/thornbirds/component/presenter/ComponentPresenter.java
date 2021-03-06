package com.thornbirds.component.presenter;

import com.thornbirds.component.IEventController;

/**
 * This class defines Component Presenter in MVP Pattern for Android Platform
 *
 * @author YangLi yanglijd@gmail.com
 * @see EventPresenter
 */
public abstract class ComponentPresenter<VIEW, CONTROLLER extends IEventController>
        extends EventPresenter<VIEW, CONTROLLER> {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    public ComponentPresenter(CONTROLLER controller) {
        super(controller);
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
}
