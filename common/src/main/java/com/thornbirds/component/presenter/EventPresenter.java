package com.thornbirds.component.presenter;

import com.thornbirds.component.IEventController;
import com.thornbirds.component.IEventObserver;
import com.thornbirds.component.IParams;

/**
 * This class defines the Basic Component Presenter in MVP Pattern, working with a CONTROLLER and a
 * VIEW.
 * <p>
 * CONTROLLER is a subclass of {@link IEventController}, via which presenter can register observer
 * to receive event from other presenter or post event to notify other presenters something happens.
 * <p>
 * VIEW is the view of this presenter, via which presenter can interact with the real view.
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class EventPresenter<VIEW, CONTROLLER extends IEventController>
        implements IEventObserver, IEventPresenter<VIEW> {

    protected CONTROLLER mController;

    protected VIEW mView;

    /**
     * Register this Presenter to Event Controller to receive Event of certain type.
     *
     * @param event the type of event
     */
    protected final void registerAction(int event) {
        mController.registerObserverForEvent(event, this);
    }

    /**
     * Unregister this Presenter from Event Controller to stop receiving Event of certain type.
     *
     * @param event the type of event
     */
    protected final void unregisterAction(int event) {
        mController.unregisterObserverForEvent(event, this);
    }

    /**
     * Unregister this presenter from Event Controller to stop receiving Event of any type.
     */
    protected final void unregisterAllAction() {
        mController.unregisterObserver(this);
    }

    /**
     * Post event to Event Controller.
     *
     * @param event type of the Event to be post
     */
    protected final void postEvent(int event) {
        mController.postEvent(event);
    }

    /**
     * Post event to Event Controller with params.
     *
     * @param event  type of the Event to be post
     * @param params parameters of the Event
     */
    protected final void postEvent(int event, IParams params) {
        mController.postEvent(event, params);
    }

    @Override
    public void setView(VIEW view) {
        mView = view;
    }

    public EventPresenter(CONTROLLER controller) {
        mController = controller;
    }
}
