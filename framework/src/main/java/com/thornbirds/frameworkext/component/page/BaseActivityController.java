package com.thornbirds.frameworkext.component.page;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.framework.activity.ComponentActivity;
import com.thornbirds.frameworkext.component.ComponentController;
import com.thornbirds.frameworkext.component.IPageParams;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by yangli on 2019/3/20.
 *
 * @mail yanglijd@gmail.com
 */
public abstract class BaseActivityController extends ComponentController {
    protected final ComponentActivity mActivity;

    private final PageRouter mPageRouter = new PageRouter();
    private final PageStack mPageStack = new PageStack();

    protected final <T> T findViewById(@IdRes int idRes) {
        return mActivity != null ? (T) mActivity.findViewById(idRes) : null;
    }

    protected final void registerRoute(@NonNull String path, @NonNull ICreator creator) {
        mPageRouter.registerRoute(path, creator);
    }

    protected BaseActivityController(@NonNull ComponentActivity activity) {
        mActivity = activity;
    }

    protected abstract void onSetupRouters();

    @NonNull
    protected abstract String onGetDefaultRoute();

    @NonNull
    @Override
    public final IRouter getRouter() {
        return mPageRouter;
    }

    @CallSuper
    @Override
    protected void onCreate() {
        onSetupRouters();
    }

    @CallSuper
    @Override
    protected void onStart() {
        mPageStack.dispatchStart();
        if (mPageStack.isEmpty()) {
            final String defaultRoute = onGetDefaultRoute();
            mPageRouter.startPage(defaultRoute);
        }
    }

    @CallSuper
    @Override
    protected void onStop() {
        mPageStack.dispatchStop();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mPageRouter.onDestroy();
        mPageStack.onDestroy();
    }

    @Override
    protected boolean onBackPressed() {
        return mPageStack.dispatchBackPress();
    }

    private final class PageRouter implements IRouter {

        private final Map<String, ICreator> mRouteMap = new HashMap<>();

        final void registerRoute(@NonNull String path, @NonNull ICreator creator) {
            if (mRouteMap.containsKey(path)) {
                LogE("addRouter failed, multi route found for " + path);
                return;
            }
            mRouteMap.put(path, creator);
        }

        @Nullable
        final ICreator resolveRoute(@NonNull String route) {
            if (mRouteMap.containsKey(route)) {
                return mRouteMap.get(route);
            }
            return null;
        }

        final void onDestroy() {
            mRouteMap.clear();
        }

        private boolean startPageInner(@NonNull String route, @Nullable IPageParams params) {
            final ICreator creator = resolveRoute(route);
            if (creator == null) {
                LogE("gotoPage failed, no route found for " + route);
                return false;
            }
            final PageEntry entry = creator.create(params);
            if (entry == null) {
                LogE("gotoPage failed, no page found for " + route);
                return false;
            }
            return mPageStack.addPageInner(entry);
        }

        @Override
        public final boolean startPage(@NonNull String route) {
            return startPageInner(route, null);
        }

        @Override
        public final boolean startPage(@NonNull String route, @Nullable IPageParams params) {
            return startPageInner(route, params);
        }

        @Override
        public final boolean popPage() {
            return mPageStack.popPageInner(null);
        }

        @Override
        public boolean popPage(@NonNull ComponentController controller) {
            return mPageStack.popPageInner(controller);
        }
    }

    private final class PageStack {
        private final LinkedList<PageEntry> mPageStack = new LinkedList<>();

        private int size() {
            return mPageStack.size();
        }

        private PageEntry top() {
            return mPageStack.isEmpty() ? null : mPageStack.getFirst();
        }

        private PageEntry pop() {
            return mPageStack.isEmpty() ? null : mPageStack.removeFirst();
        }

        boolean isEmpty() {
            return mPageStack.isEmpty();
        }

        final boolean addPageInner(@NonNull PageEntry entry) {
            final int state = getState();
            if (state == STATE_DESTROYED) {
                return false;
            }
            final PageEntry topEntry = top();
            if (topEntry != null) {
                if (topEntry == entry) {
                    return true;
                }
                topEntry.performStop();
            }
            entry.performCreate(BaseActivityController.this);
            if (state == STATE_STARTED) {
                entry.performStart();
            }
            mPageStack.push(entry);
            return true;
        }

        final boolean popPageInner(@Nullable ComponentController controller) {
            if (controller != null && top() != null && controller != top().controller) {
                return false;
            }
            if (size() <= 1) {
                if (!mActivity.isFinishing()) {
                    mActivity.finish();
                }
            } else {
                final int state = getState();
                final PageEntry popped = pop();
                final PageEntry current = top();
                if (state == STATE_STARTED) {
                    current.performStart();
                    popped.performStop();
                }
                popped.performDestroy();
            }
            return true;
        }

        @MainThread
        final void dispatchStart() {
            final PageEntry entry = top();
            if (entry != null) {
                entry.performStart();
            }
        }

        @MainThread
        final void dispatchStop() {
            final PageEntry entry = top();
            if (entry != null) {
                entry.performStop();
            }
        }

        @MainThread
        final void onDestroy() {
            for (PageEntry entry : mPageStack) {
                entry.performDestroy();
            }
            mPageStack.clear();
        }

        @MainThread
        final boolean dispatchBackPress() {
            final PageEntry entry = top();
            if (entry == null) {
                return false;
            }
            return entry.performBackPress();
        }
    }

    protected interface ICreator<T extends PageEntry> {
        T create(@Nullable IPageParams params);
    }

    protected static class PageEntry {
        final BasePageController controller;
        final IPageParams pageParams;

        public PageEntry(@NonNull BasePageController controller, IPageParams pageParams) {
            this.controller = controller;
            this.pageParams = pageParams;
        }

        final void performCreate(@NonNull BaseActivityController activityController) {
            controller.setActivityController(activityController, pageParams);
            controller.performCreate();
        }

        final void performStart() {
            controller.performStart();
        }

        final void performStop() {
            controller.performStop();
        }

        final void performDestroy() {
            controller.performDestroy();
            controller.setActivityController(null, null);
        }

        final boolean performBackPress() {
            return controller.performBackPressed();
        }
    }
}
