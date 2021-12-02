package com.thornbirds.framework.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends from {@link RecyclerView.Adapter}, working with a ITEM, a HOLDER and a LISTENER.
 * <p>
 * ITEM defines the type of the Adapter's item.
 * <p>
 * HOLDER is a subclass of {@link ClickItemAdapter.BaseHolder}, whose super is {@link RecyclerView.ViewHolder}.
 * <p>
 * LISTENER defines listener used when the item is clicked.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ClickItemAdapter<ITEM, HOLDER extends ClickItemAdapter.BaseHolder, LISTENER>
        extends RecyclerView.Adapter<HOLDER> {

    protected static final int ITEM_TYPE_FOOTER = -2;
    protected static final int ITEM_TYPE_HEADER = -1;
    protected static final int ITEM_TYPE_NORMAL = 0;

    protected final List<ITEM> mItems = new ArrayList<>(0);
    protected LayoutInflater mInflater;
    protected LISTENER mListener;

    public ClickItemAdapter() {
    }

    protected abstract HOLDER newViewHolder(ViewGroup parent, int viewType);

    public final boolean isEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public final HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        return newViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(HOLDER holder, int position) {
        holder.bindView(mItems.get(position), mListener);
    }

    public void onItemDataUpdated(ITEM item) {
        final int index = mItems.indexOf(item);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    public void insertItemData(int index, ITEM item) {
        mItems.add(index, item);
        notifyItemInserted(index);
    }

    public void addItemData(List<? extends ITEM> items) {
        if (items != null && !items.isEmpty()) {
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void setItemData(List<? extends ITEM> items) {
        mItems.clear();
        if (items != null) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(LISTENER listener) {
        mListener = listener;
    }

    public static abstract class BaseHolder<ITEM, LISTENER> extends RecyclerView.ViewHolder {

        protected final <T extends View> T findViewById(@IdRes int resId) {
            return (T) itemView.findViewById(resId);
        }

        protected final Resources getResources() {
            return itemView.getResources();
        }

        public BaseHolder(View view) {
            super(view);
        }

        public BaseHolder(View view, int width, int height) {
            this(view);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, height);
            itemView.setLayoutParams(params);
        }

        public abstract void bindView(ITEM item, LISTENER listener);
    }

}
