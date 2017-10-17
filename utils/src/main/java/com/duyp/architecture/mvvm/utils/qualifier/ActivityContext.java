package com.duyp.architecture.mvvm.utils.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Activity context qualifier
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {}
