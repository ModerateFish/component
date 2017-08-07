package com.thornbirds.component;

/**
 * Basic interface of Params.
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IParams {
    /**
     * Fetch item at index.
     *
     * @param index index of item
     * @param <T>   item type
     * @return item, maybe null
     */
    <T extends Object> T getItem(int index);
}
