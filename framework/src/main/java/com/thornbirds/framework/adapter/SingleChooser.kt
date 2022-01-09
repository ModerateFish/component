package com.thornbirds.framework.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

/**
 * This class defines a helper for choosing one item from several view.
 *
 * @author YangLi yanglijd@gmail.com
 */
class SingleChooser(
    private val mListener: IChooserListener?
) : View.OnClickListener {

    private var mViewGroup: ViewGroup? = null
    private var mSelectedView: View? = null

    private fun <T : View?> findViewById(@IdRes resId: Int): T? {
        return mViewGroup?.findViewById(resId)
    }

    override fun onClick(view: View) {
        val ret = setSelection(view)
        if (ret && mListener != null) {
            mListener.onItemSelected(view)
        }
    }

    fun setSelection(view: View): Boolean {
        val selectedView = mSelectedView
        if (selectedView != null && selectedView === view) {
            return false
        }
        if (selectedView != null) {
            selectedView.isSelected = false
        }
        mSelectedView = view.also {
            it.isSelected = true
        }
        return true
    }

    fun setSelection(@IdRes selectedId: Int) {
        findViewById<View>(selectedId)?.let {
            setSelection(it)
        }
    }

    fun setup(viewGroup: ViewGroup, @IdRes selectedId: Int) {
        if (mViewGroup != null && mViewGroup != viewGroup) {
            reset()
        }
        mViewGroup = viewGroup
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            val id = view.id
            if (id == View.NO_ID) {
                continue
            }
            if (id == selectedId) {
                setSelection(view)
            }
            view.setOnClickListener(this)
        }
    }

    fun reset() {
        mViewGroup?.let { viewGroup ->
            val childCount = viewGroup.childCount
            for (i in 0 until childCount) {
                val view = viewGroup.getChildAt(i)
                view.setOnClickListener(null)
            }
            mViewGroup = null
            mSelectedView = null
        }
    }

    interface IChooserListener {
        fun onItemSelected(view: View)
    }
}