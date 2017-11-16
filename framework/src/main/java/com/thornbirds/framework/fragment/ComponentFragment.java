package com.thornbirds.framework.fragment;

import android.app.Fragment;
import android.view.View;

/**
 * Created by yangli on 2017/9/7.
 */
public abstract class ComponentFragment extends Fragment {
    protected final String TAG = getTAG();

    protected abstract String getTAG();

    protected final <V extends View> V $(int id) {
        return (V) getView().findViewById(id);
    }
}
