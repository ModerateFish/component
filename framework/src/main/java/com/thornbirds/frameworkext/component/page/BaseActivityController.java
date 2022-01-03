package com.thornbirds.frameworkext.component.page;

import android.text.TextUtils;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.framework.activity.ComponentActivity;
import com.thornbirds.frameworkext.component.route.IPageCreator;
import com.thornbirds.frameworkext.component.route.IPageEntry;
import com.thornbirds.frameworkext.component.route.IPageParams;
import com.thornbirds.frameworkext.component.route.IPageRouter;
import com.thornbirds.frameworkext.component.route.RouteComponentController;

/**
 * 以Activity为容器提供页面导航支持
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class BaseActivityController extends RouteComponentController<IPageRouter> {
    protected final ComponentActivity mActivity;

    private final StackedPageRouter mPageRouter = new StackedPageRouter(this);

    protected final <T> T findViewById(@IdRes int idRes) {
        return mActivity != null ? (T) mActivity.findViewById(idRes) : null;
    }

    protected final void registerRoute(@NonNull String path, @NonNull IPageCreator creator) {
        mPageRouter.registerRoute(path, creator);
    }

    protected BaseActivityController(@NonNull ComponentActivity activity) {
        super(null);
        mActivity = activity;
    }

    protected abstract void onSetupRouters();

    protected abstract String onGetDefaultRoute();

    @NonNull
    @Override
    public final IPageRouter getRouter() {
        return mPageRouter;
    }

    @Nullable
    @Override
    public IPageRouter getParentRouter() {
        return null;
    }

    @Override
    public boolean exitRouter() {
        if (!mActivity.isFinishing()) {
            mActivity.finish();
        }
        return true;
    }

    @CallSuper
    @Override
    protected void onCreate() {
        onSetupRouters();
        final String defaultRoute = onGetDefaultRoute();
        if (!TextUtils.isEmpty(defaultRoute)) {
            mPageRouter.startPage(defaultRoute);
        }
    }

    @CallSuper
    @Override
    protected void onStart() {
        mPageRouter.dispatchStart();
    }

    @CallSuper
    @Override
    protected void onStop() {
        mPageRouter.dispatchStop();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mPageRouter.dispatchDestroy();
    }

    @Override
    protected boolean onBackPressed() {
        return mPageRouter.dispatchBackPress();
    }

    protected static class PageEntry implements IPageEntry<BaseActivityController> {
        final BasePageController controller;
        final IPageParams pageParams;

        public PageEntry(@NonNull BasePageController controller, IPageParams pageParams) {
            this.controller = controller;
            this.pageParams = pageParams;
        }

        @Override
        public final boolean matchController(@Nullable RouteComponentController controller) {
            return this.controller == controller;
        }

        @Override
        public final void performCreate(@NonNull BaseActivityController activityController) {
            controller.setActivityController(activityController, pageParams);
            controller.performCreate();
        }

        @Override
        public final void performStart() {
            controller.performStart();
        }

        @Override
        public final void performStop() {
            controller.performStop();
        }

        @Override
        public final void performDestroy() {
            controller.performDestroy();
            controller.setActivityController(null, null);
        }

        @Override
        public final boolean performBackPress() {
            return controller.performBackPressed();
        }
    }
}
