package com.thornbirds.component.presenter

import com.thornbirds.component.IEventController
import com.thornbirds.component.IEventObserver
import com.thornbirds.component.IParams

/**
 * This class defines the Basic Component Presenter in MVP Pattern, working with a CONTROLLER and a
 * VIEW.
 *
 * CONTROLLER is a subclass of [IEventController], via which presenter can register observer
 * to receive event from other presenter or post event to notify other presenters something happens.
 *
 * VIEW is the view of this presenter, via which presenter can interact with the real view.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class EventPresenter<VIEW, CONTROLLER : IEventController>(
    protected var mController: CONTROLLER
) : IEventObserver, IEventPresenter<VIEW> {

    protected var mView: VIEW? = null

    protected val ensureView: VIEW
        get() = mView ?: throw NullPointerException("mView is null")

    /**
     * Register this Presenter to Event Controller to receive Event of certain type.
     *
     * @param event the type of event
     */
    protected fun registerAction(event: Int) {
        mController.registerObserverForEvent(event, this)
    }

    /**
     * Unregister this Presenter from Event Controller to stop receiving Event of certain type.
     *
     * @param event the type of event
     */
    protected fun unregisterAction(event: Int) {
        mController.unregisterObserverForEvent(event, this)
    }

    /**
     * Unregister this presenter from Event Controller to stop receiving Event of any type.
     */
    protected fun unregisterAllAction() {
        mController.unregisterObserver(this)
    }

    /**
     * Post event to Event Controller.
     *
     * @param event type of the Event to be post
     */
    protected fun postEvent(event: Int) {
        mController.postEvent(event)
    }

    /**
     * Post event to Event Controller with params.
     *
     * @param event  type of the Event to be post
     * @param params parameters of the Event
     */
    protected fun postEvent(event: Int, params: IParams?) {
        mController.postEvent(event, params)
    }

    override fun setView(view: VIEW) {
        mView = view
    }
}