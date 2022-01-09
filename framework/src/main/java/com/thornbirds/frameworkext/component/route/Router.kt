package com.thornbirds.frameworkext.component.route

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author YangLi yanglijd@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class Router(val value: String, val main: String = "")