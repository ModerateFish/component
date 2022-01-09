package com.thornbirds.component

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

/**
 * Basic definition of Component View for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ComponentView<VIEW : View, CONTROLLER : ComponentController>(
    protected val mParentView: ViewGroup,
    controller: CONTROLLER
) : CompoundView<VIEW, CONTROLLER>(controller) {

    protected fun <T : View> findViewById(@IdRes resId: Int): T {
        return ensureContentView.findViewById(resId)
    }
}