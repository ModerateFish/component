package com.thornbirds.framework.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.thornbirds.framework.adapter.ClickItemAdapter.BaseHolder

/**
 * This class extends from [RecyclerView.Adapter], working with a ITEM, a HOLDER and a LISTENER.
 *
 * ITEM defines the type of the Adapter's item.
 *
 * HOLDER is a subclass of [ClickItemAdapter.BaseHolder], whose super is [RecyclerView.ViewHolder].
 *
 * LISTENER defines listener used when the item is clicked.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ClickItemAdapter<ITEM, HOLDER : BaseHolder<ITEM, LISTENER>, LISTENER> :
    RecyclerView.Adapter<ViewHolder>() {

    protected val mItems = mutableListOf<ITEM>()
    private var mInflater: LayoutInflater? = null

    private var mListener: LISTENER? = null

    val isEmpty: Boolean
        get() = mItems.isEmpty()

    val dataSize: Int
        get() = mItems.size

    protected fun ensureInflater(context: Context): LayoutInflater {
        return mInflater ?: LayoutInflater.from(context).also {
            mInflater = it
        }
    }

    fun setClickListener(listener: LISTENER?) {
        mListener = listener
    }

    fun findDataPosition(item: ITEM): Int {
        return mItems.indexOf(item)
    }

    fun getItem(position: Int): ITEM {
        if (position < 0 || position >= dataSize) {
            throw IndexOutOfBoundsException("dataSize=$dataSize position=$position")
        }
        return mItems[position]
    }

    fun setData(items: List<ITEM>?) {
        mItems.clear()
        if (items?.isNotEmpty() == true) {
            mItems.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun addData(items: List<ITEM>?) {
        if (items?.isNotEmpty() == true) {
            mItems.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        if (mItems.isNotEmpty()) {
            mItems.clear()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    protected abstract fun doCreateViewHolder(parent: ViewGroup, viewType: Int): HOLDER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return doCreateViewHolder(parent, viewType)
    }

    protected open fun doBindViewHolder(holder: HOLDER, position: Int) {
        holder.bindView(mItems[position], mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        doBindViewHolder(holder as HOLDER, position)
    }

    abstract class BaseHolder<ITEM, LISTENER>(view: View) : ViewHolder(view) {
        private var mData: ITEM? = null
        protected var mListener: LISTENER? = null
            private set

        protected val ensureData: ITEM
            get() = mData ?: throw NullPointerException("mData is null")

        protected val resources: Resources
            get() = itemView.resources

        protected fun <T : View> findViewById(@IdRes resId: Int): T {
            return itemView.findViewById(resId)
        }

        protected abstract fun onBindView(item: ITEM)

        fun bindView(item: ITEM, listener: LISTENER?) {
            mData = item
            mListener = listener
            onBindView(item)
        }
    }

    companion object {
        internal const val ITEM_TYPE_FOOTER = -2
        internal const val ITEM_TYPE_HEADER = -1
        const val ITEM_TYPE_NORMAL = 0
    }
}