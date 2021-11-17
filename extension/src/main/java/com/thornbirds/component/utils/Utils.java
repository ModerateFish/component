package com.thornbirds.component.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import java.lang.ref.Reference;

/**
 * Created by yangli on 2017/11/16.
 *
 * @author YangLi yanglijd@gmail.com
 */
public class Utils {

    private Utils() {
    }

    public static <T> T $ref(Reference<T> reference) {
        return reference != null ? reference.get() : null;
    }

    public static <V extends View> V $view(@NonNull View view, @IdRes int id) {
        return (V) view.findViewById(id);
    }

    public static void $click(@NonNull View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    public static void addViewAboveAnchor(
            @NonNull ViewGroup container,
            @NonNull View view,
            ViewGroup.LayoutParams params,
            View anchorView) {
        int pos = anchorView != null ? container.indexOfChild(anchorView) : -1;
        if (pos >= 0) {
            container.addView(view, pos + 1, params);
        } else {
            container.addView(view, params);
        }
    }

    public static void addViewUnderAnchor(
            @NonNull ViewGroup container,
            @NonNull View view,
            ViewGroup.LayoutParams params,
            View anchorView) {
        int pos = anchorView != null ? container.indexOfChild(anchorView) : -1;
        if (pos >= 0) {
            container.addView(view, pos, params);
        } else {
            container.addView(view, 0, params);
        }
    }

}
