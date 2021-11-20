package com.thornbirds.frameworkext.component.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.thornbirds.component.ControllerView;

/**
 * Created by yangli on 2019/3/20.
 *
 * @mail yanglijd@gmail.com
 */
public abstract class BasePage<VIEW extends View, CONTROLLER extends BasePageController>
        extends ControllerView<VIEW, CONTROLLER> {
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

    public BasePage(@NonNull ViewGroup parentView, @NonNull CONTROLLER controller) {
        super(parentView, controller);
    }

    @Override
    public void startView() {
        super.startView();
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopView() {
        super.stopView();
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void release() {
        super.release();
        mParentView.removeView(mContentView);
    }

    protected boolean onBackPressed() {
        return false;
    }
}
