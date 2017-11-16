package com.thornbirds.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangli on 2017/5/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@interface RegisterComponent {
    String name();
    boolean viewOn() default true;
    boolean presenterOn() default true;
}
