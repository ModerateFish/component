package com.thornbirds.framework.fragment

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Basic definition of Component Activity for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ComponentFragment : Fragment() {

    protected abstract val TAG: String

    protected fun <T : View> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }
}