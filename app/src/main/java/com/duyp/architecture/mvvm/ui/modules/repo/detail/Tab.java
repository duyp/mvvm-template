package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.CODE;
import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.ISSUES;
import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.PROJECTS;
import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.PULL_REQUEST;

/**
 * Created by duypham on 10/30/17.
 *
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({CODE, ISSUES, PULL_REQUEST, PROJECTS})
public @interface Tab {
    int CODE = 0;
    int ISSUES = 1;
    int PULL_REQUEST = 2;
    int PROJECTS = 3;
}
