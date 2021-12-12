package com.thornbirds.frameworkext.component.page;

import static com.thornbirds.frameworkext.component.route.RouteComponentController.STATE_DESTROYED;
import static com.thornbirds.frameworkext.component.route.RouteComponentController.STATE_STARTED;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thornbirds.frameworkext.component.route.IPageCreator;
import com.thornbirds.frameworkext.component.route.IPageEntry;
import com.thornbirds.frameworkext.component.route.IPageParams;
import com.thornbirds.frameworkext.component.route.IPageRouter;
import com.thornbirds.frameworkext.component.route.RouteComponentController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YangLi yanglijd@gmail.com
 */
final class FramePageRouter extends PageRouter<IPageEntry, IPageCreator<IPageEntry>> {
    private final RouteComponentController<IPageRouter> mRouterController;
    private final Map<String, IPageEntry> mPageMap = new HashMap();
    private IPageEntry mCurrPage;

    public FramePageRouter(@NonNull RouteComponentController controller) {
        this.mRouterController = controller;
    }

    private IPageEntry top() {
        return mCurrPage;
    }

    @Override
    protected final boolean doStartPage(@NonNull String route, @Nullable IPageParams params) {
        IPageEntry entry = mPageMap.get(route);
        if (entry == null) {
            final IPageCreator creator = resolveRoute(route);
            if (creator == null) {
                final IPageRouter parentRouter = mRouterController.getParentRouter();
                if (parentRouter != null) {
                    return parentRouter.startPage(route, params);
                }
                LogE("doStartPage failed, no route found for " + route);
                return false;
            }
            entry = creator.create(params);
            if (entry == null) {
                LogE("doStartPage failed, no page found for " + route);
                return false;
            }
        }
        return doAddPageInner(route, entry);
    }

    private boolean doAddPageInner(@NonNull String route, @NonNull IPageEntry entry) {
        final int state = mRouterController.getState();
        if (state == STATE_DESTROYED) {
            return false;
        }
        final IPageEntry topEntry = top();
        if (topEntry != null) {
            if (topEntry == entry) {
                return true;
            }
            topEntry.performStop();
        }
        mCurrPage = entry;
        mPageMap.put(route, entry);
        entry.performCreate(mRouterController);
        if (state == STATE_STARTED) {
            entry.performStart();
        }
        return true;
    }

    @Override
    protected final boolean doPopPage(@Nullable RouteComponentController page) {
        throw new UnsupportedOperationException();
    }

    @MainThread
    final void dispatchStart() {
        final IPageEntry entry = top();
        if (entry != null) {
            entry.performStart();
        }
    }

    @MainThread
    final void dispatchStop() {
        final IPageEntry entry = top();
        if (entry != null) {
            entry.performStop();
        }
    }

    @MainThread
    final void dispatchDestroy() {
        onDestroy();
        for (IPageEntry entry : mPageMap.values()) {
            entry.performDestroy();
        }
        mPageMap.clear();
    }

    @MainThread
    final boolean dispatchBackPress() {
        final IPageEntry entry = top();
        return entry != null && entry.performBackPress();
    }
}
