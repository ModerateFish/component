package com.thornbirds.component

import android.util.Log

/**
 * Basic definition of Component Controller for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ComponentController(
    eventController: IEventController?
) : IEventController {

    protected abstract val TAG: String

    private val mEventController: IEventController = eventController ?: object : EventController() {
        override fun logE(msg: String) {
            throw RuntimeException(msg)
        }
    }

    protected fun logE(msg: String) {
        Log.e(TAG, msg)
    }

    override fun registerObserverForEvent(event: Int, observer: IEventObserver) {
        mEventController.registerObserverForEvent(event, observer)
    }

    override fun unregisterObserverForEvent(event: Int, observer: IEventObserver) {
        mEventController.unregisterObserverForEvent(event, observer)
    }

    override fun unregisterObserver(observer: IEventObserver) {
        mEventController.unregisterObserver(observer)
    }

    override fun postEvent(event: Int, params: IParams?): Boolean {
        return mEventController.postEvent(event, params)
    }

    override fun release() {
        mEventController.release()
    }

    protected abstract inner class Action : IEventObserver {
        protected fun registerAction(event: Int) {
            registerObserverForEvent(event, this)
        }

        protected fun unregisterAction(event: Int) {
            unregisterObserverForEvent(event, this)
        }

        protected fun register() {
            onRegister()
        }

        protected fun unregister() {
            unregisterObserver(this)
        }

        protected abstract fun onRegister()

        abstract override fun onEvent(event: Int, params: IParams?): Boolean
    }
}