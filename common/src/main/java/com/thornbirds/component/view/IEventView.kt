package com.thornbirds.component.view

import com.thornbirds.component.presenter.IEventPresenter

/**
 * This class defines basic interface of Component View in MVP Pattern, working with a PRESENTER
 * and a VIEW.
 *
 * PRESENTER is a subclass of [IEventPresenter], and is the Presenter of this Component View.
 *
 * VIEW is a Proxy of this Component View. Usually, VIEW defines an interface for the Presenter to
 * interact with the real view.
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IEventView<PRESENTER, VIEW> {
    /**
     * Returns a proxy of the ComponentView.
     *
     * Note that VIEW defines the interface a ComponentView should implement when interacting with
     * its Presenter.
     *
     * @return view proxy
     */
    val viewProxy: VIEW

    /**
     * Set Presenter to this ComponentView.
     *
     * Note that the Presenter maybe null if view does not need a Presenter.
     *
     * @param presenter Presenter
     */
    fun setPresenter(presenter: PRESENTER?)
}