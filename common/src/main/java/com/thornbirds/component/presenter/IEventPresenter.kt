package com.thornbirds.component.presenter

/**
 * Basic interface of Component Presenter
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IEventPresenter<VIEW> {
    /**
     * Set View to Presenter.
     *
     * Note that the view maybe null if presenter does not need a view
     *
     * @param view target view
     */
    fun setView(view: VIEW)

    /**
     * Start this Presenter.
     */
    fun startPresenter()

    /**
     * Stop this Presenter.
     */
    fun stopPresenter()

    /**
     * Called to let Presenter release resources.
     */
    fun destroy()
}