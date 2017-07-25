package com.thornbirds.component.presenter;

import com.thornbirds.component.IEventController;
import com.thornbirds.component.IEventObserver;

/**
 * This class defines Component Presenter in MVP Pattern, working with a CONTROLLER and a VIEW.
 * <p>
 * CONTROLLER is a subclass of {@link IEventController}, via which presenter can register observer
 * to receive event from other presenter or post event to notify other presenters something happened.
 * <p>
 * VIEW is the view of this presenter.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class ComponentPresenter<VIEW, CONTROLLER extends IEventController>
        implements IEventObserver, IComponentPresenter<VIEW> {
    protected final String TAG = getTAG();

    protected CONTROLLER mEventController;

    protected VIEW mView;

    /**
     * Register this Presenter to Event Controller to receive Event of certain type
     *
     * @param event the type of event
     */
    protected final void registerAction(int event) {
        mEventController.registerObserverForEvent(event, this);
    }

    /**
     * Unregister this Presenter from Event Controller to stop receiving Event of certain type
     *
     * @param event the type of event
     */
    protected final void unregisterAction(int event) {
        mEventController.unregisterObserverForEvent(event, this);
    }

    /**
     * Unregister this presenter from Event Controller to stop receiving Event of any type
     */
    protected final void unregisterAllAction() {
        mEventController.unregisterObserver(this);
    }

    protected abstract String getTAG();

    @Override
    public void setComponentView(VIEW view) {
        mView = view;
    }

    public ComponentPresenter(CONTROLLER eventController) {
        mEventController = eventController;
    }
}
