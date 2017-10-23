package com.duyp.architecture.mvvm.ui.modules.main;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainViewModel extends BaseViewModel {

    @Inject
    public MainViewModel(UserManager userManager) {
        super(userManager);
    }
}
