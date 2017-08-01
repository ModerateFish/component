package com.thornbirds.component;

/**
 * Basic interface of Event Controller
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IEventController {
    /**
     * Register observer to Event Controller to receive event
     *
     * @param event    type of the Event
     * @param observer target Observer
     */
    void registerObserverForEvent(int event, IEventObserver observer);

    /**
     * Unregister observer from Event Controller to stop receiving event
     *
     * @param event    type of the Event
     * @param observer target Observer
     */
    void unregisterObserverForEvent(int event, IEventObserver observer);

    /**
     * Unregister observer from Event Controller
     *
     * @param observer target Observer
     */
    void unregisterObserver(IEventObserver observer);

    /**
     * Post event to Event Controller
     *
     * @param event type of the Event to be dispatched
     * @return true if event if processed by at least on Observer, false otherwise
     */
    boolean postEvent(int event);

    /**
     * Post event to Event Controller
     *
     * @param event type of the Event to be dispatched
     * @param params parameters of the Event
     * @return true if event if processed by at least on Observer, false otherwise
     */
    boolean postEvent(int event, IParams params);

    /**
     * Called to let Event Controller release resources
     */
    void release();
}
