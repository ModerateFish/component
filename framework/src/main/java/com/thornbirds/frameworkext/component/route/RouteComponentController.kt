package com.thornbirds.frameworkext.component.route

import android.util.Log
import com.thornbirds.component.ComponentController
import com.thornbirds.component.IEventController
import com.thornbirds.component.utils.DebugUtils

/**
 * Created by yangli on 2017/11/19.
 */
internal typealias RouterController = RouteComponentController<IPageRouter<*>>
internal typealias PageEntry = IPageEntry<RouterController>
internal typealias PageCreator = IPageCreator<PageEntry>

abstract class RouteComponentController<T : IRouter<*>>(
    eventController: IEventController?
) : ComponentController(eventController), IRouterController {

    abstract val router: T?
    abstract val parentRouter: T?

    abstract fun exitRouter(): Boolean

    var state = STATE_IDLE
        private set

    protected abstract fun onCreate()
    protected abstract fun onStart()
    protected abstract fun onStop()
    protected abstract fun onDestroy()
    protected abstract fun onBackPressed(): Boolean

    fun performCreate() {
        if (state == STATE_CREATED) {
            return
        }
        if (state == STATE_IDLE) {
            state = STATE_CREATED
            if (DebugUtils.DEBUG) {
                Log.d(TAG, "onCreate")
            }
            onCreate()
        }
    }

    fun performStart() {
        if (state == STATE_STARTED) {
            return
        }
        if (state == STATE_CREATED || state == STATE_STOPPED) {
            state = STATE_STARTED
            if (DebugUtils.DEBUG) {
                Log.d(TAG, "onStart")
            }
            onStart()
        }
    }

    fun performStop() {
        if (state == STATE_STOPPED) {
            return
        }
        if (state == STATE_STARTED) {
            state = STATE_STOPPED
            if (DebugUtils.DEBUG) {
                Log.d(TAG, "onStop")
            }
            onStop()
        }
    }

    fun performDestroy() {
        if (state == STATE_DESTROYED) {
            return
        }
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onDestroy")
        }
        state = STATE_DESTROYED
        super.release()
        onDestroy()
    }

    fun performBackPressed(): Boolean {
        if (state == STATE_DESTROYED) {
            return false
        }
        if (DebugUtils.DEBUG) {
            Log.d(TAG, "onBackPressed")
        }
        return onBackPressed()
    }

    companion object {
        const val STATE_IDLE = 0
        const val STATE_CREATED = 1
        const val STATE_STARTED = 2
        const val STATE_STOPPED = 3
        const val STATE_DESTROYED = 4
    }
}