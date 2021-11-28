package com.thornbirds.frameworkext.component.route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author YangLi yanglijd@gmail.com
 */
public interface IPageEntry<T extends RouteComponentController> {
    boolean matchController(@Nullable RouteComponentController controller);

    void performCreate(@NonNull T parentController);

    void performStart();

    void performStop();

    void performDestroy();

    boolean performBackPress();
}
