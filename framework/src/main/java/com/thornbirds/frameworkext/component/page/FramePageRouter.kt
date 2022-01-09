package com.thornbirds.frameworkext.component.page

import androidx.annotation.MainThread
import com.thornbirds.frameworkext.component.route.*
import com.thornbirds.frameworkext.component.route.RouteComponentController.Companion.STATE_DESTROYED

/**
 * @author YangLi yanglijd@gmail.com
 */
internal class FramePageRouter(
    private val mRouterController: RouterController
) : PageRouter<PageEntry, PageCreator, RouterController>() {

    private val mPageMap = mutableMapOf<String, PageEntry>()
    private var mCurrPage: PageEntry? = null

    private fun top(): PageEntry? {
        return mCurrPage
    }

    override fun doStartPage(route: String, params: IPageParams?): Boolean {
        var entry = mPageMap[route]
        if (entry == null) {
            val creator = resolveRoute(route)
            if (creator == null) {
                val parentRouter = mRouterController.parentRouter
                if (parentRouter != null) {
                    return parentRouter.startPage(route, params)
                }
                logE("doStartPage failed, no route found for $route")
                return false
            }
            entry = creator.create(params)
            if (entry == null) {
                logE("doStartPage failed, no page found for $route")
                return false
            }
        }
        return doAddPageInner(route, entry)
    }

    private fun doAddPageInner(route: String, entry: PageEntry): Boolean {
        val state = mRouterController.state
        if (state == STATE_DESTROYED) {
            return false
        }
        val topEntry = top()
        if (topEntry != null) {
            if (topEntry === entry) {
                return true
            }
            topEntry.performStop()
        }
        mCurrPage = entry
        mPageMap[route] = entry
        entry.performCreate(mRouterController)
        if (state == RouteComponentController.STATE_STARTED) {
            entry.performStart()
        }
        return true
    }

    override fun doPopPage(page: RouterController?): Boolean {
        throw UnsupportedOperationException()
    }

    @MainThread
    fun dispatchStart() {
        val entry = top()
        entry?.performStart()
    }

    @MainThread
    fun dispatchStop() {
        val entry = top()
        entry?.performStop()
    }

    @MainThread
    fun dispatchDestroy() {
        onDestroy()
        mPageMap.values.forEach {
            it.performDestroy()
        }
        mPageMap.clear()
    }

    @MainThread
    fun dispatchBackPress(): Boolean {
        val entry = top()
        return entry?.performBackPress() == true
    }
}