package com.thornbirds.component

/**
 * Definition of Params which is used to pass parameters for Event Controller when dispatch event.
 *
 * @author YangLi yanglijd@gmail.com
 */
open class Params : IParams {
    private val params by lazy {
        mutableListOf<Any>()
    }

    open fun putItem(item: Any): Params? {
        params.add(item)
        return this
    }

    override fun <T : Any> getItem(index: Int): T? {
        if (index < 0 || index >= params.size) {
            throw IndexOutOfBoundsException("index=$index")
        }
        try {
            return params[index] as T
        } catch (e: ClassCastException) {
            // just ignore
        }
        return null
    }

    companion object {
        fun from(vararg values: Any): Params? {
            return if (values.isNotEmpty()) {
                Params().also {
                    values.forEach { elem ->
                        it.putItem(elem)
                    }
                }
            } else null
        }
    }
}