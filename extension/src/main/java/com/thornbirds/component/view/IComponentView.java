package com.thornbirds.component.view;

/**
 * This class defines basic interface of Component View in MVP Pattern for android platform.
 *
 * @author YangLi yanglijd@gmail.com
 * @see IEventView
 */
public interface IComponentView<PRESENTER, VIEW extends IViewProxy>
        extends IEventView<PRESENTER, VIEW> {
}
