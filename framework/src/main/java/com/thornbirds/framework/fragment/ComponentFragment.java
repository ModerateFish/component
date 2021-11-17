package com.thornbirds.framework.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * Basic definition of Component Activity for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentFragment extends Fragment {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    protected final <V extends View> V $(int id) {
        return (V) getView().findViewById(id);
    }
}
