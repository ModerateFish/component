package com.thornbirds.frameworkext.component.page

import android.view.View
import android.view.ViewGroup
import com.thornbirds.component.ControllerView

/**
 * @author YangLi yanglijd@gmail.com
 */
abstract class BasePage<VIEW : View, CONTROLLER : BasePageController>(
    parentView: ViewGroup,
    controller: CONTROLLER
) : ControllerView<VIEW, CONTROLLER>(parentView, controller) {

    override fun startView() {
        super.startView()
        val contentView = ensureContentView
        if (contentView.parent == null) {
            mParentView.addView(contentView)
        }
        contentView.visibility = View.VISIBLE
    }

    override fun stopView() {
        super.stopView()
        ensureContentView.visibility = View.GONE
    }

    override fun release() {
        super.release()
        mParentView.removeView(ensureContentView)
    }

    fun onBackPressed(): Boolean {
        return false
    }
}