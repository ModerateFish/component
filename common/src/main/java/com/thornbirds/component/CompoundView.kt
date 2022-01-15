package com.thornbirds.component

import android.view.View
import com.thornbirds.component.presenter.IEventPresenter
import com.thornbirds.component.view.IEventView
import java.util.*

/**
 * This class defines basic Compound View of this Structure, working with a CONTROLLER and a VIEW.
 *
 * CONTROLLER is a subclass of [IEventController], and usually used when creating a component
 * presenter for a component view.
 *
 * VIEW is the view this compound view manages, and usually is a concrete view of a real system,
 * such as RelativeLayout in Android.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class CompoundView<VIEW, CONTROLLER : IEventController>(
    protected var mController: CONTROLLER
) {
    protected abstract val TAG: String

    protected var mContentView: VIEW? = null
    private val mPresenterSet: MutableList<IEventPresenter<*>> = ArrayList<IEventPresenter<*>>()

    protected val ensureContentView: VIEW
        get() = mContentView ?: throw NullPointerException("mContentView in null")

    /**
     * Register Component which has both view and presenter
     *
     * @param view      target view
     * @param presenter target presenter
     */
    protected fun <P, V> registerComponent(presenter: IEventPresenter<V>, view: IEventView<P, V>) {
        presenter.setView(view.viewProxy)
        view.setPresenter(presenter as P)
        mPresenterSet.add(presenter)
    }

    /**
     * Register Component which has presenter with a view, but not IEventView.
     *
     * @param view      target view
     * @param presenter target presenter
     */
    protected fun <T : View> registerComponent(presenter: IEventPresenter<T>, view: T) {
        presenter.setView(view)
        mPresenterSet.add(presenter)
    }

    /**
     * Register Component which has only presenter.
     *
     * @param presenter target presenter
     */
    protected fun registerComponent(presenter: IEventPresenter<*>) {
        mPresenterSet.add(presenter)
    }

    fun setController(controller: CONTROLLER) {
        mController = controller
    }

    /**
     * Setup all components for Compound View.
     */
    abstract fun setupView()

    /**
     * Start all components for Compound View.
     */
    open fun startView() {
        mPresenterSet.forEach {
            it.startPresenter()
        }
    }

    /**
     * Stop all components for Compound View.
     */
    open fun stopView() {
        mPresenterSet.forEach {
            it.stopPresenter()
        }
    }

    /**
     * Called to let Compound View release resources.
     */
    open fun release() {
        mPresenterSet.forEach {
            it.destroy()
        }
        mPresenterSet.clear()
    }
}