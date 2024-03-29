package com.thornbirds.component

/**
 * Basic interface of Event Observer.
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IEventObserver {
    /**
     * Event Controller calls this to notify the Observer an event arrives.
     *
     * @param event  type of the Event
     * @param params parameters of the Event
     * @return true is observer precessed the event, false otherwise
     */
    fun onEvent(event: Int, params: IParams? = null): Boolean
}