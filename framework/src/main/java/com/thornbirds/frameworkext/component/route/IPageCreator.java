package com.thornbirds.frameworkext.component.route;

import androidx.annotation.Nullable;

/**
 * @author YangLi yanglijd@gmail.com
 */
public interface IPageCreator<T extends IPageEntry> {
    T create(@Nullable IPageParams params);
}