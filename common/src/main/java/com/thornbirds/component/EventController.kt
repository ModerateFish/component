package com.thornbirds.component

import java.util.*

/**
 * Basic definition of Event Controller.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class EventController : IEventController {
    private val mEventActionMap: MutableMap<Int, MutableSet<IEventObserver>> = HashMap()

    protected abstract fun logE(msg: String)

    override fun registerObserverForEvent(event: Int, observer: IEventObserver) {
        var actionSet = mEventActionMap[event]
        if (actionSet == null) {
            actionSet = LinkedHashSet() // 保序
            mEventActionMap.put(event, actionSet)
        }
        actionSet.add(observer)
    }

    override fun unregisterObserverForEvent(event: Int, observer: IEventObserver) {
        mEventActionMap[event]?.remove(observer)
    }

    override fun unregisterObserver(observer: IEventObserver) {
        for (actionSet in mEventActionMap.values) {
            actionSet.remove(observer)
        }
    }

    override fun postEvent(event: Int, params: IParams?): Boolean {
        val actionSet = mEventActionMap[event]
        if (actionSet == null || actionSet.isEmpty()) {
            logE("no action registered for source $event")
            return false
        }
        var result = false
        for (action in actionSet) {
            result = result or action.onEvent(event, params)
        }
        return result
    }

    override fun release() {
        mEventActionMap.clear()
    }
}