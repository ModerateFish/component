package com.thornbirds.framework.fragment;

import android.app.Fragment;
import android.view.View;

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
