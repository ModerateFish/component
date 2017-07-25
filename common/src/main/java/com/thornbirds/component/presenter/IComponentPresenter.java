package com.thornbirds.component.presenter;

/**
 * Basic interface of Component Presenter
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IComponentPresenter<VIEW> {
    /**
     * Set View to Presenter
     * <p>
     * Note that the view maybe null if presenter does not need a view
     *
     * @param view target view
     */
    void setComponentView(VIEW view);

    /**
     * Called to let Presenter release resources
     */
    void destroy();
}
