package com.thornbirds.component;

import com.thornbirds.test.presenter.IComponentPresenter;
import com.thornbirds.test.view.IComponentView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines basic Compound View of this Structure, working with a CONTROLLER and a VIEW.
 * <p>
 * CONTROLLER is a subclass of {@link IEventController}, and usually used when creating a component
 * presenter for a component view.
 * <p>
 * VIEW is the view this compound view manages, and usually is a concrete view of a real system,
 * such as RelativeLayout in Android.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class CompoundView<VIEW, CONTROLLER extends IEventController> {

    protected VIEW mContentView;

    protected CONTROLLER mController;

    protected final List<IComponentPresenter> mPresenterSet = new ArrayList<>();

    /**
     * Register Component which has both view and presenter
     *
     * @param view      target view
     * @param presenter target presenter
     */
    protected final void registerComponent(IComponentView view, IComponentPresenter presenter) {
        view.setPresenter(presenter);
        presenter.setComponentView(view.getViewProxy());
        mPresenterSet.add(presenter);
    }

    /**
     * Register Component which has only presenter
     *
     * @param presenter target presenter
     */
    protected final void registerComponent(IComponentPresenter presenter) {
        mPresenterSet.add(presenter);
    }

    public void setController(CONTROLLER controller) {
        mController = controller;
    }

    public CompoundView(CONTROLLER controller) {
        mController = controller;
    }

    /**
     * Setup all components for Compound View
     */
    public abstract void setupView();

    /**
     * Start all components for Compound View
     */
    public abstract void startView();

    /**
     * Stop all components for Compound View
     */
    public abstract void stopView();

    /**
     * Called to let Compound View release resources
     */
    public abstract void release();
}
