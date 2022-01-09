package com.thornbirds.frameworkext.component.page

import android.text.TextUtils
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.thornbirds.framework.activity.ComponentActivity
import com.thornbirds.frameworkext.component.route.*

/**
 * 以Activity为容器提供页面导航支持
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class BaseActivityController(
    protected val mActivity: ComponentActivity
) : RouterController(null) {

    private val mPageRouter = StackedPageRouter(this)

    fun <T : View> findViewById(@IdRes idRes: Int): T? {
        return mActivity.findViewById(idRes)
    }

    protected fun registerRoute(path: String, creator: (params: IPageParams?) -> BasePageEntry) {
        mPageRouter.registerRoute(path, object : PageCreator {
            override fun create(params: IPageParams?): PageEntry {
                return creator(params) as PageEntry
            }
        })
    }

    protected abstract fun onSetupRouters()
    protected abstract fun onGetDefaultRoute(): String

    override val router: IPageRouter<RouterController> = mPageRouter

    override val parentRouter: IPageRouter<RouterController>? = null

    override fun exitRouter(): Boolean {
        if (!mActivity.isFinishing) {
            mActivity.finish()
        }
        return true
    }

    @CallSuper
    override fun onCreate() {
        onSetupRouters()
        val defaultRoute = onGetDefaultRoute()
        if (!TextUtils.isEmpty(defaultRoute)) {
            mPageRouter.startPage(defaultRoute)
        }
    }

    @CallSuper
    override fun onStart() {
        mPageRouter.dispatchStart()
    }

    @CallSuper
    override fun onStop() {
        mPageRouter.dispatchStop()
    }

    @CallSuper
    override fun onDestroy() {
        mPageRouter.dispatchDestroy()
    }

    override fun onBackPressed(): Boolean {
        return mPageRouter.dispatchBackPress()
    }

    protected class BasePageEntry(
        val controller: BasePageController,
        val pageParams: IPageParams?
    ) : IPageEntry<BaseActivityController> {

        override fun matchController(controller: BaseActivityController?): Boolean {
            return this.controller === controller
        }

        override fun performCreate(parentController: BaseActivityController) {
            controller.setActivityController(parentController, pageParams)
            controller.performCreate()
        }

        override fun performStart() {
            controller.performStart()
        }

        override fun performStop() {
            controller.performStop()
        }

        override fun performDestroy() {
            controller.performDestroy()
            controller.setActivityController(null, null)
        }

        override fun performBackPress(): Boolean {
            return controller.performBackPressed()
        }
    }
}