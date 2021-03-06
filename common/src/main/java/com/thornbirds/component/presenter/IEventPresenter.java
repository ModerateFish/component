package com.thornbirds.component.presenter;

/**
 * Basic interface of Component Presenter
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IEventPresenter<VIEW> {
    /**
     * Set View to Presenter.
     * <p>
     * Note that the view maybe null if presenter does not need a view
     *
     * @param view target view
     */
    void setView(VIEW view);

    /**
     * Start this Presenter.
     */
    void startPresenter();

    /**
     * Stop this Presenter.
     */
    void stopPresenter();

    /**
     * Called to let Presenter release resources.
     */
    void destroy();
}
