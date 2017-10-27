package com.duyp.architecture.mvvm.ui.modules.feed;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Event;
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

    @Inject
    public FeedViewModel(UserManager userManager, FeedRepo repo, FeedAdapter adapter) {
        super(userManager, adapter);
        this.feedRepo = repo;
        Log.d(TAG, "FeedViewModel: creating..." + this);
        new Handler().postDelayed(this::refresh, 300);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        Log.d(TAG, "onFirsTimeUiCreate: ");
        String targetUser = null;
        if (bundle != null) {
            targetUser = bundle.getString(BundleConstant.EXTRA);
        }
        feedRepo.initTargetUser(targetUser);
        setData(feedRepo.getData().getData(), true);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Event> onCallApiDone) {
        execute(feedRepo.getEvents(page), eventPageable -> {
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
