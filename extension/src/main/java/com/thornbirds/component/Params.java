package com.thornbirds.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of Params which is used to pass parameters for Event Controller when dispatches a event
 *
 * @author YangLi yanglijd@gmail.com
 */
public class Params implements IParams {
    private List<Object> params;

    public Params() {
    }

    public Params putItem(Object object) {
        if (params == null) {
            params = new ArrayList<Object>();
        }
        params.add(object);
        return this;
    }

    public <T extends Object> T firstItem() {
        return getItem(0);
    }

    public <T extends Object> T getItem(int index) {
        if (params == null || index >= params.size()) {
            return null;
        }
        try {
            T elem = (T) params.get(index);
            return elem;
        } catch (ClassCastException e) {
            // just ignore
        }
        return null;
    }
}