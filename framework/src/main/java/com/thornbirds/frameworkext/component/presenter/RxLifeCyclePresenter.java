package com.thornbirds.frameworkext.component.presenter;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.thornbirds.frameworkext.component.page.BasePageController;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by yangli on 2017/8/2.
 *
 * @module 具有Rx生命周期绑定的表现
 */
public abstract class RxLifeCyclePresenter<VIEW> extends ComponentPresenter<VIEW> {

    protected final BehaviorSubject<PresenterEvent> mBehaviorSubject = BehaviorSubject.create();

    public RxLifeCyclePresenter(BasePageController controller) {
        super(controller);
    }

    @Override
    @CallSuper
    public void startPresenter() {
        mBehaviorSubject.onNext(PresenterEvent.START);
    }

    @Override
    @CallSuper
    public void stopPresenter() {
        mBehaviorSubject.onNext(PresenterEvent.STOP);
    }

    @Override
    @CallSuper
    public void destroy() {
        mBehaviorSubject.onNext(PresenterEvent.DESTROY);
    }

    protected final <T> ObservableTransformer<T, T> bindUntilEvent(PresenterEvent event) {
        return bindUntilEvent(mBehaviorSubject, event);
    }

    private static <T, R> ObservableTransformer<T, T> bindUntilEvent(
            final BehaviorSubject<R> subject, final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.takeUntil(
                        subject.filter(new Predicate<R>() {
                            @Override
                            public boolean test(@NonNull R r) throws Exception {
                                return r == event;
                            }
                        })).take(1);
            }
        };
    }

    protected enum PresenterEvent {
        START, STOP, DESTROY
    }
}
