package com.thornbirds.framework.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thornbirds.framework.R
import com.thornbirds.framework.adapter.ClickItemAdapter.BaseHolder

/**
 * This class extends from [ClickItemAdapter], and offers a item to indicate loading status.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class LoadingItemAdapter<ITEM, HOLDER : BaseHolder<ITEM, LISTENER>, LISTENER> :
    ClickItemAdapter<ITEM, HOLDER, LISTENER>() {

    private val mFooterItem = FooterItem()

    override fun getItemCount(): Int {
        return dataSize + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSize == position) ITEM_TYPE_FOOTER else ITEM_TYPE_NORMAL
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_FOOTER -> {
                val view = ensureInflater(parent.context).inflate(
                    R.layout.loading_status_item, parent, false
                )
                FooterHolder(view)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterHolder) {
            holder.bindView(mFooterItem, null)
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    private fun updateLoadingStateInner(state: Int) {
        if (mFooterItem.state == state) {
            return
        }
        mFooterItem.state = state
        notifyItemChanged(dataSize)
    }

    fun hideLoading() {
        updateLoadingStateInner(FooterItem.STATE_HIDDEN)
    }

    fun showLoading() {
        updateLoadingStateInner(FooterItem.STATE_LOADING)
    }

    fun onLoadingDone(hasMore: Boolean) {
        if (hasMore) {
            updateLoadingStateInner(FooterItem.STATE_DONE)
        } else {
            updateLoadingStateInner(FooterItem.STATE_NO_MORE)
        }
    }

    fun onLoadingFailed() {
        updateLoadingStateInner(FooterItem.STATE_FAILED)
    }

    protected class FooterItem {
        var state = 0

        companion object {
            const val STATE_HIDDEN = 0
            const val STATE_LOADING = 1
            const val STATE_DONE = 2
            const val STATE_NO_MORE = 3
            const val STATE_FAILED = 4
        }
    }

    protected class FooterHolder(view: View) : BaseHolder<FooterItem, View.OnClickListener>(view) {
        private val mStatusView: TextView = findViewById(R.id.status_view)

        override fun onBindView(item: FooterItem) {
            itemView.visibility = View.VISIBLE
            when (item.state) {
                FooterItem.STATE_LOADING -> {
                    mStatusView.setText(R.string.loading_tips_loading)
                }
                FooterItem.STATE_DONE -> {
                    mStatusView.setText(R.string.loading_tips_done)
                }
                FooterItem.STATE_NO_MORE -> {
                    mStatusView.setText(R.string.loading_tips_no_more)
                }
                FooterItem.STATE_FAILED -> {
                    mStatusView.setText(R.string.loading_tips_failed)
                }
                else -> itemView.visibility = View.GONE
            }
        }
    }
}