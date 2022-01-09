package com.thornbirds.frameworkext.component.page

import com.thornbirds.component.Params
import com.thornbirds.frameworkext.component.route.IPageParams

/**
 * @author YangLi yanglijd@gmail.com
 */
class PageParams : Params(), IPageParams {

    override fun putItem(item: Any): PageParams {
        return super.putItem(item) as PageParams
    }

    companion object {
        fun from(vararg values: Any): PageParams? {
            return if (values.isNotEmpty()) {
                PageParams().also {
                    values.forEach { elem ->
                        it.putItem(elem)
                    }
                }
            } else null
        }
    }
}