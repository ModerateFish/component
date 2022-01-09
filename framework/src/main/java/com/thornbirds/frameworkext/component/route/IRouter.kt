package com.thornbirds.frameworkext.component.route

/**
 * @author YangLi yanglijd@gmail.com
 */
interface IRouter<T> {
    fun startPage(route: String, params: IPageParams? = null): Boolean
    fun popPage(page: T? = null): Boolean
}