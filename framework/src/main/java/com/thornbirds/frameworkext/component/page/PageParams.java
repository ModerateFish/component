package com.thornbirds.frameworkext.component.page;

import com.thornbirds.component.Params;
import com.thornbirds.frameworkext.component.route.IPageParams;

/**
 * @author YangLi yanglijd@gmail.com
 */
public class PageParams extends Params implements IPageParams {
    @Override
    public PageParams putItem(Object object) {
        return (PageParams) super.putItem(object);
    }

    public static PageParams from(Object... values) {
        if (values != null && values.length > 0) {
            final PageParams params = new PageParams();
            for (Object elem : values) {
                params.putItem(elem);
            }
            return params;
        }
        return null;
    }
}
