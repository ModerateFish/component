package com.thornbirds.frameworkext.component.page

import androidx.annotation.CallSuper
import com.thornbirds.frameworkext.component.route.*

/**
 * @author YangLi yanglijd@gmail.com
 */
internal abstract class PageRouter<ENTRY : IPageEntry<*>, CREATOR : IPageCreator<ENTRY>,
        CONTROLLER : RouteComponentController<IPageRouter<*>>>
    : IPageRouter<CONTROLLER> {

    private val mRouteMap = mutableMapOf<String, CREATOR>()

    protected fun logE(msg: String) {
        throw RuntimeException(msg)
    }

    fun registerRoute(path: String, creator: CREATOR) {
        if (mRouteMap.containsKey(path)) {
            logE("registerRoute failed, multi route found for $path")
            return
        }
        mRouteMap[path] = creator
    }

    protected fun resolveRoute(route: String): CREATOR? {
        return if (mRouteMap.containsKey(route)) {
            mRouteMap[route]
        } else null
    }

    @CallSuper
    protected fun onDestroy() {
        mRouteMap.clear()
    }

    protected abstract fun doStartPage(route: String, params: IPageParams?): Boolean
    protected abstract fun doPopPage(page: CONTROLLER?): Boolean

    final override fun startPage(route: String, params: IPageParams?): Boolean {
        return doStartPage(route, params)
    }

    final override fun popPage(page: CONTROLLER?): Boolean {
        return doPopPage(page)
    }
}