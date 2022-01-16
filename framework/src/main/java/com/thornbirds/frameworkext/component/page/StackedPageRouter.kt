package com.thornbirds.frameworkext.component.page

import androidx.annotation.MainThread
import com.thornbirds.frameworkext.component.route.*
import com.thornbirds.frameworkext.component.route.RouteComponentController.Companion.STATE_DESTROYED
import com.thornbirds.frameworkext.component.route.RouteComponentController.Companion.STATE_STARTED
import java.util.*

/**
 * @author YangLi yanglijd@gmail.com
 */
internal class StackedPageRouter(
    private val mRouterController: RouterController
) : PageRouter<PageEntry, PageCreator, RouterController>() {

    private val mPageStack = LinkedList<PageEntry>()

    private fun size(): Int {
        return mPageStack.size
    }

    private fun top(): PageEntry? {
        return if (mPageStack.isEmpty()) null else mPageStack.first
    }

    private fun pop(): PageEntry? {
        return if (mPageStack.isEmpty()) null else mPageStack.removeFirst()
    }

    private fun push(entry: PageEntry) {
        mPageStack.addFirst(entry)
    }

    override fun doStartPage(route: String, params: IPageParams?): Boolean {
        val creator = resolveRoute(route)
        if (creator == null) {
            val parentRouter = mRouterController.parentRouter
            if (parentRouter != null) {
                return parentRouter.startPage(route, params)
            }
            logE("gotoPage failed, no route found for $route")
            return false
        }
        val entry = creator.create(params)
        if (entry == null) {
            logE("gotoPage failed, no page found for $route")
            return false
        }
        return doAddPageInner(entry)
    }

    private fun doAddPageInner(newEntry: PageEntry): Boolean {
        val state = mRouterController.state
        if (state == STATE_DESTROYED) {
            return false
        }
        val topEntry = top()
        if (topEntry != null) {
            if (topEntry === newEntry) {
                return true
            }
            topEntry.performStop()
        }
        push(newEntry)
        newEntry.performCreate(mRouterController)
        if (state == STATE_STARTED) {
            newEntry.performStart()
        }
        return true
    }

    override fun doPopPage(page: RouterController?): Boolean {
        if (page != null && top()?.matchController(page) == false) {
            return false
        }
        if (size() <= 1) {
            return mRouterController.exitRouter()
        } else {
            val state = mRouterController.state
            val popped = pop() ?: return false
            val newCurrent = top()
            if (state == STATE_STARTED) {
                newCurrent?.performStart()
                popped.performStop()
            }
            popped.performDestroy()
            return true
        }
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
        for (entry in mPageStack) {
            entry.performDestroy()
        }
        mPageStack.clear()
    }

    @MainThread
    fun dispatchBackPress(): Boolean {
        val entry = top() ?: return false
        if (entry.performBackPress()) {
            return true
        }
        if (size() > 1) {
            doPopPage(null)
            return true
        }
        return false
    }
}