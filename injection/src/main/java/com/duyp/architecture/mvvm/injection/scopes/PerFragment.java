package com.duyp.architecture.mvvm.injection.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by air on 5/1/17.
 * Scope for instances which live as long as the fragment
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
