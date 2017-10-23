package com.duyp.architecture.mvvm.ui.modules.main.feed;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedViewModel extends BaseViewModel{

    @Inject
    public FeedViewModel(UserManager userManager, FeedAdapter feedAdapter) {
        super(userManager);
    }
}
