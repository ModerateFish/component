package com.thornbirds.component.presenter

import com.thornbirds.component.IEventController

/**
 * This class defines Component Presenter in MVP Pattern for Android Platform
 *
 * @see EventPresenter
 * @author YangLi yanglijd@gmail.com
 */
abstract class ComponentPresenter<VIEW, CONTROLLER : IEventController>(
    controller: CONTROLLER
) : EventPresenter<VIEW, CONTROLLER>(controller) {
    protected abstract val TAG: String

    override fun startPresenter() {
    }

    override fun stopPresenter() {
    }

    override fun destroy() {
    }
}