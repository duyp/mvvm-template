package com.duyp.architecture.mvvm.data.model.type;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.duyp.architecture.mvvm.data.model.type.IssueState.*;

/**
 * Created by duypham on 10/30/17.
 *
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({OPEN, CLOSED, ALL})
public @interface IssueState {
    String OPEN = "open";
    String CLOSED = "closed";
    String ALL = "all";
}
