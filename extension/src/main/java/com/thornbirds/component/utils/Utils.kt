package com.thornbirds.component.utils

import android.view.View
import android.view.ViewGroup

/**
 * Created by yangli on 2022/1/8.
 *
 * @author YangLi yanglijd@gmail.com
 */
object Utils {

    fun addViewAboveAnchor(
        container: ViewGroup,
        view: View,
        params: ViewGroup.LayoutParams?,
        anchorView: View?
    ) {
        val pos = if (anchorView != null) container.indexOfChild(anchorView) else -1
        if (pos >= 0) {
            container.addView(view, pos + 1, params)
        } else {
            container.addView(view, params)
        }
    }

    fun addViewUnderAnchor(
        container: ViewGroup,
        view: View,
        params: ViewGroup.LayoutParams?,
        anchorView: View?
    ) {
        val pos = if (anchorView != null) container.indexOfChild(anchorView) else -1
        if (pos >= 0) {
            container.addView(view, pos, params)
        } else {
            container.addView(view, 0, params)
        }
    }
}