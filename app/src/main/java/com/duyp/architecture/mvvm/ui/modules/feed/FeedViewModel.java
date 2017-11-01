package com.duyp.architecture.mvvm.ui.modules.feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.EventRepo;
import com.duyp.architecture.mvvm.data.model.PayloadModel;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.type.EventType;
import com.duyp.architecture.mvvm.data.model.type.EventsType;
import com.duyp.architecture.mvvm.data.provider.scheme.SchemeParser;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.repository.FeedRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.NameParser;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailActivity;
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
    public void onItemClick(View v, Event item) {

        if (item.getType().equals(EventType.ForkEvent)) {
            NameParser parser = new NameParser(item.getPayload().getForkee().getHtmlUrl());
            RepoDetailActivity.startRepoDetailActivity(v.getContext(), parser);
        } else {
            if (item.getRepo() != null) {
                NameParser parser = new NameParser(item.getRepo().getUrl());
                RepoDetailActivity.startRepoDetailActivity(v.getContext(), parser);
            } else {
                AlertUtils.showToastShortMessage(App.getInstance(), "Coming soon...");
            }
        }
//            PayloadModel payloadModel = item.getPayload();
//            if (payloadModel != null) {
//                if (payloadModel.getHead() != null) {
//                    if (payloadModel.getCommits() != null && payloadModel.getCommits().size() > 1) {
////                        sendToView(view -> view.onOpenCommitChooser(payloadModel.getCommits()));
//                    } else {
////                        EventRepo repoModel = item.getRepo();
////                        NameParser nameParser = new NameParser(repoModel.getUrl());
////                        Intent intent = CommitPagerActivity.createIntent(v.getContext(), nameParser.getName(),
////                                nameParser.getUsername(), payloadModel.getHead(), true,
////                                LinkParserHelper.isEnterprise(repoModel.getUrl()));
////                        v.getContext().startActivity(intent);
//                    }
//                } else if (payloadModel.getIssue() != null) {
//                    SchemeParser.launchUri(v.getContext(), Uri.parse(payloadModel.getIssue().getHtmlUrl()), true);
//                } else if (payloadModel.getPullRequest() != null) {
//                    SchemeParser.launchUri(v.getContext(), Uri.parse(payloadModel.getPullRequest().getHtmlUrl()), true);
//                } else if (payloadModel.getComment() != null) {
//                    SchemeParser.launchUri(v.getContext(), Uri.parse(payloadModel.getComment().getHtmlUrl()), true);
//                } else if (item.getType().equals(EventType.ReleaseEvent) && payloadModel.getRelease() != null) {
//                    NameParser nameParser = new NameParser(payloadModel.getRelease().getHtmlUrl());
////                    v.getContext().startActivity(ReleasesListActivity.getIntent(v.getContext(), nameParser.getUsername(), nameParser.getName(),
////                            payloadModel.getRelease().getId(), LinkParserHelper.isEnterprise(payloadModel.getRelease().getHtmlUrl())));
//
//                } else if (item.getType().equals(EventType.CreateEvent) && "tag".equalsIgnoreCase(payloadModel.getRefType())) {
//                    EventRepo repoModel = item.getRepo();
//                    NameParser nameParser = new NameParser(repoModel.getUrl());
////                    v.getContext().startActivity(ReleasesListActivity.getIntent(v.getContext(), nameParser.getUsername(), nameParser.getName(),
////                            payloadModel.getRef(), LinkParserHelper.isEnterprise(repoModel.getUrl())));
//                } else {
//                    EventRepo repoModel = item.getRepo();
//                    NameParser parser = new NameParser(repoModel.getUrl());
//                    RepoDetailActivity.startRepoDetailActivity(v.getContext(), parser);
//                }
//            }
//        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        feedRepo.onDestroy();
    }
}
