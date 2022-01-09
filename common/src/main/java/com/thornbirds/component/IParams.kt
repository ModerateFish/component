package com.thornbirds.component

/**
 * Basic interface of Params.
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IParams {
    /**
     * Fetch item at index.
     *
     * @param index index of item
     * @param <T>   item type
     * @return item, maybe null
     */
    fun <T : Any> getItem(index: Int): T?
}