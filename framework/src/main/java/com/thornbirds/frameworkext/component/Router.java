package com.thornbirds.frameworkext.component;

import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yangli on 2019/3/23.
 *
 * @mail yanglijd@gmail.com
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Router {
    @NonNull String value();

    @NonNull String main() default "";
}
