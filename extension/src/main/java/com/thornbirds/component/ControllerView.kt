package com.thornbirds.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * Basic definition of Controller View for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ControllerView<VIEW : View, CONTROLLER : IEventController>(
    protected val mParentView: ViewGroup,
    controller: CONTROLLER
) : CompoundView<VIEW, CONTROLLER>(controller), IEventObserver {

    protected val context: Context = mParentView.context

    protected fun <T : View> findViewById(@IdRes resId: Int): T {
        return ensureContentView.findViewById(resId)
    }

    private val inflater by lazy {
        LayoutInflater.from(context)
    }

    protected fun inflateContent(@LayoutRes layoutRes: Int): VIEW {
        return inflater.inflate(layoutRes, mParentView, false) as VIEW
    }

    protected fun <T : View> inflate(@LayoutRes layoutRes: Int, parent: ViewGroup): T {
        return inflate(layoutRes, parent, false)
    }

    protected fun <T : View> inflate(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup,
        attachToRoot: Boolean
    ): T {
        return inflater.inflate(layoutRes, parent, attachToRoot) as T
    }

    protected fun registerAction(event: Int) {
        mController.registerObserverForEvent(event, this)
    }

    protected fun unregisterAction(event: Int) {
        mController.unregisterObserverForEvent(event, this)
    }

    @CallSuper
    override fun stopView() {
        super.stopView()
        mController.unregisterObserver(this)
    }
}