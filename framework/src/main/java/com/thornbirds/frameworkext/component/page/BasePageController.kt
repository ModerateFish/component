package com.thornbirds.frameworkext.component.page

import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.thornbirds.component.IEventController
import com.thornbirds.frameworkext.component.route.*

/**
 * @author YangLi yanglijd@gmail.com
 */
abstract class BasePageController(
    eventController: IEventController?
) : RouterController(eventController) {

    private var mActivityController: BaseActivityController? = null
    private var mPageParams: IPageParams? = null
    private var mPage: BasePage<*, BasePageController>? = null

    private val mSubPageRouter = FramePageRouter(this)

    protected fun <T> findViewById(@IdRes idRes: Int): T {
        return mActivityController?.findViewById(idRes) ?: throw NullPointerException()
    }

    protected fun registerSubRoute(path: String, creator: (params: IPageParams?) -> BasePageEntry) {
        mSubPageRouter.registerRoute(path, object : PageCreator {
            override fun create(params: IPageParams?): PageEntry {
                return creator(params) as PageEntry
            }
        })
    }

    fun setActivityController(controller: BaseActivityController?, params: IPageParams?) {
        mActivityController = controller
        mPageParams = params
    }

    protected abstract fun onSetupRouters()

    override val router: IPageRouter<RouterController>? = mSubPageRouter

    override val parentRouter: IPageRouter<RouterController>?
        get() = mActivityController?.router

    override fun exitRouter(): Boolean {
        return parentRouter?.popPage(this) == true
    }

    protected abstract fun onCreatePage(pageParams: IPageParams?): BasePage<*, *>?

    @CallSuper
    override fun onCreate() {
        onSetupRouters()
        mPage = onCreatePage(mPageParams) as BasePage<*, BasePageController>
        mPage!!.setController(this)
        mPage!!.setupView()
    }

    @CallSuper
    override fun onStart() {
        mPage!!.startView()
        mSubPageRouter.dispatchStart()
    }

    @CallSuper
    override fun onStop() {
        mPage!!.stopView()
        mSubPageRouter.dispatchStop()
    }

    @CallSuper
    override fun onDestroy() {
        mPage?.release()
        mSubPageRouter.dispatchDestroy()
    }

    override fun onBackPressed(): Boolean {
        if (mSubPageRouter.dispatchBackPress()) {
            return true
        }
        return mPage!!.onBackPressed()
    }

    protected class BasePageEntry(
        private val controller: BasePageController,
        private val pageParams: IPageParams?
    ) : IPageEntry<BasePageController> {

        override fun matchController(controller: IRouterController): Boolean {
            return this.controller == controller
        }

        override fun performCreate(parentController: BasePageController) {
            controller.setActivityController(parentController.mActivityController, pageParams)
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