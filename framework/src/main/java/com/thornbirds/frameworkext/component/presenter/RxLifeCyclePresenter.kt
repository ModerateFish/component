package com.thornbirds.frameworkext.component.presenter

import androidx.annotation.CallSuper
import com.thornbirds.frameworkext.component.page.BasePageController
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject
import com.thornbirds.component.presenter.ComponentPresenter as Presenter

/**
 * Created by yangli on 2017/8/2.
 *
 * @module 具有Rx生命周期绑定的表现
 */
typealias ComponentPresenter<VIEW> = Presenter<VIEW, BasePageController>

abstract class RxLifeCyclePresenter<VIEW>(
    controller: BasePageController
) : ComponentPresenter<VIEW>(controller) {
    private val mBehaviorSubject: BehaviorSubject<PresenterEvent> = BehaviorSubject.create()

    @CallSuper
    override fun startPresenter() {
        mBehaviorSubject.onNext(PresenterEvent.START)
    }

    @CallSuper
    override fun stopPresenter() {
        mBehaviorSubject.onNext(PresenterEvent.STOP)
    }

    @CallSuper
    override fun destroy() {
        mBehaviorSubject.onNext(PresenterEvent.DESTROY)
    }

    protected fun <T> bindUntilEvent(event: PresenterEvent): ObservableTransformer<T, T> {
        return bindUntilEvent(mBehaviorSubject, event)
    }

    protected enum class PresenterEvent {
        START, STOP, DESTROY
    }

    companion object {
        private fun <T, R> bindUntilEvent(
            subject: BehaviorSubject<R>, event: R
        ): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream.takeUntil(
                    subject.filter { r -> r == event }).take(1)
            }
        }
    }
}