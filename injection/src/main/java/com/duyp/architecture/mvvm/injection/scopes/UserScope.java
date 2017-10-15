package com.duyp.architecture.mvvm.injection.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Duy Pham on 05/17.
 * Scope for instances which be created after user login and until user logout
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
