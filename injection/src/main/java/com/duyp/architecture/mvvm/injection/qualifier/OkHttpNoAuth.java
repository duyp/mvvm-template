package com.duyp.architecture.mvvm.injection.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by duypham on 9/8/17.
 * OK HTTP no auth (no token in request header)
 */

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface OkHttpNoAuth {
}