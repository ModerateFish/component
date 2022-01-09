package com.thornbirds.component

/**
 * Basic interface of Event Controller.
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IEventController {
    /**
     * Register observer to Event Controller to receive event.
     *
     * @param event    type of the Event
     * @param observer target Observer
     */
    fun registerObserverForEvent(event: Int, observer: IEventObserver)

    /**
     * Unregister observer from Event Controller to stop receiving event.
     *
     * @param event    type of the Event
     * @param observer target Observer
     */
    fun unregisterObserverForEvent(event: Int, observer: IEventObserver)

    /**
     * Unregister observer from Event Controller.
     *
     * @param observer target Observer
     */
    fun unregisterObserver(observer: IEventObserver)

    /**
     * Post event to Event Controller.
     *
     * @param event  type of the Event to be dispatched
     * @param params parameters of the Event
     * @return true if event if processed by at least on Observer, false otherwise
     */
    fun postEvent(event: Int, params: IParams? = null): Boolean

    /**
     * Called to let Event Controller release resources.
     */
    fun release()
}