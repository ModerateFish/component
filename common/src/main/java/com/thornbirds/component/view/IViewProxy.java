package com.thornbirds.component.view;

/**
 * Basic interface of View Proxy
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IViewProxy<VIEW> {
    /**
     * Returns the real view of the ViewProxy
     *
     * @param <T> type of real view
     * @return real view
     */
    <T extends VIEW> T getRealView();
}
