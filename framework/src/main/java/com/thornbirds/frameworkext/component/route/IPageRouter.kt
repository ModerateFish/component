package com.thornbirds.frameworkext.component.route

import com.thornbirds.component.IParams

/**
 * @author YangLi yanglijd@gmail.com
 */
interface IRouterController

interface IPageRouter<T : IRouterController> : IRouter<T>

interface IPageParams : IParams

interface IPageEntry<T : IRouterController> {
    fun matchController(controller: IRouterController): Boolean
    fun performCreate(parentController: T)
    fun performStart()
    fun performStop()
    fun performDestroy()
    fun performBackPress(): Boolean
}

interface IPageCreator<T : IPageEntry<*>> {
    fun create(params: IPageParams?): T
}