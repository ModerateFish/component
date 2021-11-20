package com.thornbirds.frameworkext.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.thornbirds.component.ComponentController;

/**
 * Created by yangli on 2017/9/16.
 */
public abstract class ControllerView<VIEW extends View, CONTROLLER extends ComponentController>
        extends com.thornbirds.component.ControllerView<VIEW, CONTROLLER> {

    @Override
    protected abstract String getTAG();

    protected final Context getContext() {
        return mParentView.getContext();
    }

    protected final VIEW inflateContent(@LayoutRes int layoutRes) {
        return (VIEW) LayoutInflater.from(getContext()).inflate(layoutRes, mParentView, false);
    }

    protected final <T extends View> T inflate(@LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        return inflate(layoutRes, parent, false);
    }

    protected final <T extends View> T inflate(@LayoutRes int layoutRes, @NonNull ViewGroup parent, boolean attachToRoot) {
        return (T) LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, attachToRoot);
    }

    public ControllerView(@NonNull ViewGroup parentView, @NonNull CONTROLLER controller) {
        super(parentView, controller);
    }
}