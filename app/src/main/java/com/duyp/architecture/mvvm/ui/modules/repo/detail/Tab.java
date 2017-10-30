package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.*;

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
