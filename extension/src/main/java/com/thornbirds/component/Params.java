package com.thornbirds.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of Params which is used to pass parameters for Event Controller when dispatch event.
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

    @Override
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

    public static Params from(Object... values) {
        if (values != null && values.length > 0) {
            final Params params = new Params();
            for (Object elem : values) {
                params.putItem(elem);
            }
            return params;
        }
        return null;
    }
}