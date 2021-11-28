package com.thornbirds.frameworkext.component.page;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.component.IEventController;
import com.thornbirds.frameworkext.component.route.IPageCreator;
import com.thornbirds.frameworkext.component.route.IPageEntry;
import com.thornbirds.frameworkext.component.route.IPageParams;
import com.thornbirds.frameworkext.component.route.IPageRouter;
import com.thornbirds.frameworkext.component.route.RouteComponentController;

/**
 * @author YangLi yanglijd@gmail.com
 */
public abstract class BasePageController extends RouteComponentController<IPageRouter> {

    private BaseActivityController mActivityController;
    private IPageParams mPageParams;
    private BasePage mPage;

    private final FramePageRouter mSubPageRouter = new FramePageRouter(this);

    public BasePageController(@Nullable IEventController eventController) {
        super(eventController);
    }

    protected final <T> T findViewById(@IdRes int idRes) {
        return mActivityController != null ? (T) mActivityController.findViewById(idRes) : null;
    }

    protected final void registerSubRoute(@NonNull String path, @NonNull IPageCreator creator) {
        mSubPageRouter.registerRoute(path, creator);
    }

    final void setActivityController(
            @Nullable BaseActivityController controller, @Nullable IPageParams params) {
        mActivityController = controller;
        mPageParams = params;
    }

    protected abstract void onSetupRouters();

    @Nullable
    @Override
    public final IPageRouter getRouter() {
        return mSubPageRouter;
    }

    @Nullable
    @Override
    public IPageRouter getParentRouter() {
        return mActivityController != null ? mActivityController.getRouter() : null;
    }

    @Override
    public boolean exitRouter() {
        return getParentRouter() != null && getParentRouter().popPage(this);
    }

    protected abstract BasePage onCreatePage(@Nullable IPageParams pageParams);

    @CallSuper
    @Override
    protected void onCreate() {
        onSetupRouters();
        mPage = onCreatePage(mPageParams);
        mPage.setController(this);
        mPage.setupView();
    }

    @CallSuper
    @Override
    protected void onStart() {
        mPage.startView();
        mSubPageRouter.dispatchStart();
    }

    @CallSuper
    @Override
    protected void onStop() {
        mPage.stopView();
        mSubPageRouter.dispatchStop();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mPage.release();
        mSubPageRouter.dispatchDestroy();
    }

    @Override
    protected boolean onBackPressed() {
        if (mSubPageRouter.dispatchBackPress()) {
            return true;
        }
        if (mPage.onBackPressed()) {
            return true;
        }
        return false;
    }

    protected static class PageEntry implements IPageEntry<BasePageController> {
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
        public final void performCreate(@NonNull BasePageController pageController) {
            controller.setActivityController(pageController.mActivityController, pageParams);
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