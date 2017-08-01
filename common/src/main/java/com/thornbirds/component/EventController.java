package com.thornbirds.component;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Basic definition of Event Controller
 *
 * @author YangLi yanglijd@gmail.com
 */
public abstract class EventController implements IEventController {

    public static final int MSG_DEFAULT = 0;

    private final Map<Integer, Set<IEventObserver>> mEventActionMap =
            new HashMap<Integer, Set<IEventObserver>>();

    protected abstract void LogE(String msg);

    @Override
    public void registerObserverForEvent(int event, IEventObserver observer) {
        if (observer == null) {
            LogE("registerObserverForEvent but action is null for event=" + event);
            return;
        }
        Set<IEventObserver> actionSet = mEventActionMap.get(event);
        if (actionSet == null) {
            actionSet = new LinkedHashSet<IEventObserver>(); // 保序
            mEventActionMap.put(event, actionSet);
        }
        actionSet.add(observer);
    }

    @Override
    public void unregisterObserverForEvent(int event, IEventObserver observer) {
        if (observer == null) {
            return;
        }
        Set<IEventObserver> actionSet = mEventActionMap.get(event);
        if (actionSet != null) {
            actionSet.remove(observer);
        }
    }

    @Override
    public void unregisterObserver(IEventObserver observer) {
        if (observer == null) {
            return;
        }
        for (Set<IEventObserver> actionSet : mEventActionMap.values()) {
            actionSet.remove(observer);
        }
    }

    @Override
    public boolean postEvent(int event) {
        return postEvent(event, null);
    }

    @Override
    public boolean postEvent(int event, IParams params) {
        Set<IEventObserver> actionSet = mEventActionMap.get(event);
        if (actionSet == null || actionSet.isEmpty()) {
            LogE("no action registered for source " + event);
            return false;
        }
        boolean result = false;
        for (IEventObserver action : actionSet) {
            result |= action.onEvent(event, params);
        }
        return result;
    }

    @Override
    public void release() {
        mEventActionMap.clear();
    }
}
