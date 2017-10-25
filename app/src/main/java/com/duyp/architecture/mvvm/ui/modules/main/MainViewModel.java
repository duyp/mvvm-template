package com.duyp.architecture.mvvm.ui.modules.main;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/23/17.
 * ViewModel for {@link MainActivity}, deals with fragments manager, bottom navigation
 */

@Getter
@Setter
public class MainViewModel extends BaseViewModel {

    private int currentTab = 0;

    @Inject
    public MainViewModel(UserManager userManager) {
        super(userManager);
    }
}
