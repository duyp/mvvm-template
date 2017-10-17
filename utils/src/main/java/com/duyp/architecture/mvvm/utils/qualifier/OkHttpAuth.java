package com.duyp.architecture.mvvm.utils.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by duypham on 9/8/17.
 * Authorization header with request token
 */

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface OkHttpAuth {
}
