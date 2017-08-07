package com.thornbirds.component.view;

/**
 * Basic interface for Orientation.
 *
 * @author YangLi yanglijd@gmail.com
 */
public interface IOrientationListener {
    /**
     * Called when screen orientation changes.
     *
     * @param isLandscape is landscape
     */
    void onOrientation(boolean isLandscape);
}
