package com.thornbirds.component.view

import android.view.View

/**
 * Basic interface of View Proxy
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IViewProxy {
    /**
     * Returns the real view of the ViewProxy.
     *
     * @param <T> type of real view
     * @return real view
     */
    fun <T : View> getRealView(): T
}

/**
 * This class defines basic interface of Component View in MVP Pattern for android platform.
 *
 * @see IEventView
 * @author YangLi yanglijd@gmail.com
 */
interface IComponentView<PRESENTER, VIEW : IViewProxy> : IEventView<PRESENTER, VIEW>