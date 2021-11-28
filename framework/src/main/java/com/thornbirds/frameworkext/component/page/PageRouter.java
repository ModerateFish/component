package com.thornbirds.frameworkext.component.page;

import androidx.annotation.CallSuper;
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
abstract class PageRouter<ENTRY extends IPageEntry,
        CREATOR extends IPageCreator<ENTRY>> implements IPageRouter {
    private final Map<String, CREATOR> mRouteMap = new HashMap<>();

    protected final void LogE(String msg) {
        throw new RuntimeException(msg);
    }

    protected final void registerRoute(@NonNull String path, @NonNull CREATOR creator) {
        if (mRouteMap.containsKey(path)) {
            LogE("registerRoute failed, multi route found for " + path);
            return;
        }
        mRouteMap.put(path, creator);
    }

    @Nullable
    protected final CREATOR resolveRoute(@NonNull String route) {
        if (mRouteMap.containsKey(route)) {
            return mRouteMap.get(route);
        }
        return null;
    }

    @CallSuper
    protected final void onDestroy() {
        mRouteMap.clear();
    }

    protected abstract boolean doStartPage(@NonNull String route, @Nullable IPageParams params);

    protected abstract boolean doPopPage(@Nullable RouteComponentController page);

    @Override
    public final boolean startPage(@NonNull String route) {
        return doStartPage(route, null);
    }

    @Override
    public final boolean startPage(@NonNull String route, @Nullable IPageParams params) {
        return doStartPage(route, params);
    }

    @Override
    public final boolean popPage() {
        return doPopPage(null);
    }

    @Override
    public final boolean popPage(@NonNull RouteComponentController page) {
        return doPopPage(page);
    }
}