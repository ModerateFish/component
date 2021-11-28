package com.thornbirds.frameworkext.component.route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author YangLi yanglijd@gmail.com
 */
public interface IRouter {
    boolean startPage(@NonNull String route);

    boolean startPage(@NonNull String route, @Nullable IPageParams params);

    boolean popPage();

    boolean popPage(@NonNull RouteComponentController page);
}