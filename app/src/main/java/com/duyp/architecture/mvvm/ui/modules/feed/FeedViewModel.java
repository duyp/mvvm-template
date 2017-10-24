package com.duyp.architecture.mvvm.ui.modules.feed;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.repository.FeedRepo;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedViewModel extends BaseListDataViewModel<Event, FeedAdapter>{

    private final FeedRepo feedRepo;

    @Inject
    public FeedViewModel(UserManager userManager, FeedRepo repo) {
        super(userManager);
        this.feedRepo = repo;
    }

    @Override
    protected void callApi(int page, OnCallApiDone onCallApiDone) {

    }
}
