package com.thornbirds.frameworkext.component.page;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.thornbirds.frameworkext.component.ComponentController;
import com.thornbirds.frameworkext.component.IPageParams;

/**
 * Created by yangli on 2019/3/20.
 *
 * @mail yanglijd@gmail.com
 */
public abstract class BasePageController extends ComponentController {

    private BaseActivityController mActivityController;
    private IPageParams mPageParams;
    private BasePage mPage;

    protected final <T> T findViewById(@IdRes int idRes) {
        return mActivityController != null ? (T) mActivityController.findViewById(idRes) : null;
    }

    final void setActivityController(@Nullable BaseActivityController controller, @Nullable IPageParams params) {
        mActivityController = controller;
        mPageParams = params;
    }

    @Nullable
    @Override
    public final IRouter getRouter() {
        return mActivityController != null ? mActivityController.getRouter() : null;
    }

    protected abstract BasePage onCreatePage(@Nullable IPageParams pageParams);

    @CallSuper
    @Override
    protected void onCreate() {
        mPage = onCreatePage(mPageParams);
        mPage.setController(this);
        mPage.setupView();
    }

    @CallSuper
    @Override
    protected void onStart() {
        mPage.startView();
    }

    @CallSuper
    @Override
    protected void onStop() {
        mPage.stopView();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mPage.release();
    }

    @Override
    protected boolean onBackPressed() {
        if (mPage.onBackPressed()) {
            return true;
        }
        return getRouter().popPage(this);
    }
}