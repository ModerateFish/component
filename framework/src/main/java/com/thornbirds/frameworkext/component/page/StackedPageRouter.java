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

import java.util.LinkedList;

/**
 * @author YangLi yanglijd@gmail.com
 */
final class StackedPageRouter extends PageRouter<IPageEntry, IPageCreator<IPageEntry>> {
    private final RouteComponentController<IPageRouter> mRouterController;
    private final LinkedList<IPageEntry> mPageStack = new LinkedList<>();

    public StackedPageRouter(@NonNull RouteComponentController controller) {
        this.mRouterController = controller;
    }

    final boolean isEmpty() {
        return mPageStack.isEmpty();
    }

    private int size() {
        return mPageStack.size();
    }

    private IPageEntry top() {
        return mPageStack.isEmpty() ? null : mPageStack.getFirst();
    }

    private IPageEntry pop() {
        return mPageStack.isEmpty() ? null : mPageStack.removeFirst();
    }

    @Override
    protected final boolean doStartPage(@NonNull String route, @Nullable IPageParams params) {
        final IPageCreator creator = resolveRoute(route);
        if (creator == null) {
            final IPageRouter parentRouter = mRouterController.getParentRouter();
            if (parentRouter != null) {
                return parentRouter.startPage(route, params);
            }
            LogE("gotoPage failed, no route found for " + route);
            return false;
        }
        final IPageEntry entry = creator.create(params);
        if (entry == null) {
            LogE("gotoPage failed, no page found for " + route);
            return false;
        }
        return doAddPageInner(entry);
    }

    private boolean doAddPageInner(@NonNull IPageEntry entry) {
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
        entry.performCreate(mRouterController);
        if (state == STATE_STARTED) {
            entry.performStart();
        }
        mPageStack.push(entry);
        return true;
    }

    @Override
    protected final boolean doPopPage(@Nullable RouteComponentController page) {
        if (page != null && top() != null && top().matchController(page)) {
            return false;
        }
        if (size() <= 1) {
            return mRouterController.exitRouter();
        } else {
            final int state = mRouterController.getState();
            final IPageEntry popped = pop();
            final IPageEntry newCurrent = top();
            if (state == STATE_STARTED) {
                newCurrent.performStart();
                popped.performStop();
            }
            popped.performDestroy();
            return true;
        }
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
        for (IPageEntry entry : mPageStack) {
            entry.performDestroy();
        }
        mPageStack.clear();
    }

    @MainThread
    final boolean dispatchBackPress() {
        final IPageEntry entry = top();
        return entry != null && entry.performBackPress();
    }
}
