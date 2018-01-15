package com.spaikergrn.vkclient.db.annotations;

import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface dbLong {

    String value() default Constants.Db.BIGINT;
}