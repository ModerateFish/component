package com.thornbirds.component.view;

import com.thornbirds.component.presenter.IEventPresenter;

/**
 * This class defines basic interface of Component View in MVP Pattern, working with a PRESENTER
 * and a VIEW.
 * <p>
 * PRESENTER is a subclass of {@link IEventPresenter}, and is the Presenter of this Component View.
 * <p>
 * VIEW is a Proxy of this Component View. Usually, VIEW defines a interface for the Presenter to
 * interact with the real view.
 * <p>
 * @author YangLi yanglijd@gmail.com
 */
public interface IEventView<PRESENTER, VIEW> {
    /**
     * Returns a proxy of the ComponentView.
     * <p>
     * Note that VIEW defines the interface a ComponentView should implement when interacting with
     * its Presenter.
     *
     * @return view proxy
     */
    VIEW getViewProxy();

    /**
     * Set Presenter to this ComponentView.
     * <p>
     * Note that the Presenter maybe null if view does not need a Presenter.
     *
     * @param presenter Presenter
     */
    void setPresenter(PRESENTER presenter);
}
