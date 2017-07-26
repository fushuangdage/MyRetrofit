package com.example.admin.myretrofit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fushuang on 2017/7/25.
 */


/**
 * 自定义注解
 * 用于修饰方法,运行时注解
 * 注解传递一个String类型的参数
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlString {
    String value();
}
