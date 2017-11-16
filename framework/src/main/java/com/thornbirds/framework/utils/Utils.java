package com.thornbirds.framework.utils;

import android.view.View;

/**
 * Created by yangli on 2017/11/16.
 */
public class Utils {

    private Utils() {
    }

    public static <V extends View> V $(View view, int id) {
        return (V) view.findViewById(id);
    }

    public static void $click(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

}
