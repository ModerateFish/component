package com.thornbirds.framework.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.NO_ID;

/**
 * This class defines a helper for choosing one item from several view.
 *
 * @author YangLi yanglijd@gmail.com
 */
public class SingleChooser implements View.OnClickListener {
    private static final String TAG = "SingleChooser";

    private IChooserListener mListener;

    protected ViewGroup mViewGroup;
    protected View mSelectedView;

    private <T extends View> T $(@IdRes int resId) {
        return mViewGroup != null ? (T) mViewGroup.findViewById(resId) : null;
    }

    @Override
    public void onClick(View view) {
        boolean ret = setSelection(view);
        if (ret && mListener != null) {
            mListener.onItemSelected(mSelectedView);
        }
    }

    public SingleChooser(IChooserListener listener) {
        mListener = listener;
    }

    public boolean setSelection(View view) {
        if (mSelectedView != null && mSelectedView == view) {
            return false;
        }
        if (mSelectedView != null) {
            mSelectedView.setSelected(false);
        }
        mSelectedView = view;
        mSelectedView.setSelected(true);
        return true;
    }

    public void setSelection(@IdRes int selectedId) {
        View view = $(selectedId);
        if (view != null) {
            setSelection(view);
        }
    }

    public void setup(@NonNull ViewGroup viewGroup, @IdRes int selectedId) {
        if (mViewGroup != null && mViewGroup != viewGroup) {
            reset();
        }
        mViewGroup = viewGroup;

        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = mViewGroup.getChildAt(i);
            int id = view.getId();
            if (id == NO_ID) {
                continue;
            }
            if (id == selectedId) {
                mSelectedView = view;
                mSelectedView.setSelected(true);
            }
            view.setOnClickListener(this);
        }
    }

    public void reset() {
        if (mViewGroup == null) {
            return;
        }
        int childCount = mViewGroup.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = mViewGroup.getChildAt(i);
            view.setOnClickListener(null);
        }
        mViewGroup = null;
        mSelectedView = null;
    }

    public interface IChooserListener {

        void onItemSelected(View view);

    }
}
