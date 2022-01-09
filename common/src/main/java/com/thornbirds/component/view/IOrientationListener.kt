package com.thornbirds.component.view

/**
 * Basic interface for Orientation.
 *
 * @author YangLi yanglijd@gmail.com
 */
interface IOrientationListener {
    /**
     * Called when screen orientation changes.
     *
     * @param isLandscape is landscape
     */
    fun onOrientation(isLandscape: Boolean)
}