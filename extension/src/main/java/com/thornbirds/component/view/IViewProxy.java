package com.thornbirds.component.view;

import android.view.View;

/**
 * Basic interface of View Proxy
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IViewProxy {
    /**
     * Returns the real view of the ViewProxy
     *
     * @param <T> type of real view
     * @return real view
     */
    <T extends View> T getRealView();
}
