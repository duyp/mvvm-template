package com.duyp.architecture.mvvm.ui.modules.feed;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.repository.FeedRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedViewModel extends BaseListDataViewModel<Event, FeedAdapter>{

    private final FeedRepo feedRepo;
    private boolean hasImage = true;

    protected final UserRestService userRestService;

    @Inject
    public FeedViewModel(UserManager userManager, FeedRepo repo, UserRestService userRestService) {
        super(userManager);
        this.userRestService = userRestService;
        this.feedRepo = repo;
        refresh(300);
    }

    @Override
    public void initAdapter(@NonNull FeedAdapter adapter) {
        super.initAdapter(adapter);
        adapter.setHasAvatar(hasImage);
        setData(feedRepo.getData().getData(), true);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        String targetUser = userManager.extractUser(bundle);
        if (bundle != null) {
            hasImage = bundle.getBoolean(BundleConstant.EXTRA_TWO, true);
        }
        feedRepo.initTargetUser(targetUser);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Event> onCallApiDone) {
        execute(true, feedRepo.getEvents(page), eventPageable -> {
            onCallApiDone.onDone(eventPageable.getLast(), page == 1, eventPageable.getItems());
        });
    }

    @Override
    protected void onItemClick(Event item) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        feedRepo.onDestroy();
    }
}
