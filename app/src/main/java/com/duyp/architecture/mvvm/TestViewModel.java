package com.duyp.architecture.mvvm;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.base.BaseUserViewModel;
import com.duyp.architecture.mvvm.data.UserManager;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 *
 */

public class TestViewModel extends BaseUserViewModel {

    @Inject
    public TestViewModel(@NonNull UserManager userManager) {
        super(userManager);
    }
}
