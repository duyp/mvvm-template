package com.duyp.architecture.mvvm.ui.modules.profile.overview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.OrganizationService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.RxHelper;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.organizations.OrganizationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import github.GetPinnedReposQuery;
import io.reactivex.Observable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/27/17.
 *
 */

@Getter
public class OverviewViewModel extends BaseViewModel {

    private String user;

    private MutableLiveData<FollowingState> followState = new MutableLiveData<>();
    private MutableLiveData<State> organsState = new MutableLiveData<>();
    private MutableLiveData<State> pinnedState = new MutableLiveData<>();

    private final UserRestService userRestService;
    private final OrganizationService organizationService;
    private final ApolloClient apolloClient;

    @Getter
    private final OrganizationAdapter organizationAdapter;

    private List<GetPinnedReposQuery.Node> pinnedRepos = new ArrayList<>();

    @Inject
    public OverviewViewModel(UserManager userManager,
                             UserRestService service,
                             OrganizationService organizationService,
                             ApolloClient apolloClient,
                             OrganizationAdapter adapter) {
        super(userManager);
        this.userRestService = service;
        this.organizationService = organizationService;
        this.apolloClient = apolloClient;
        this.organizationAdapter = adapter;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public void initUser(String userLogin, boolean isMeOrOrganization) {
        this.user = userLogin;
        followState.setValue(null);
        new Handler(Looper.myLooper()).postDelayed(() -> {
            if (!isMeOrOrganization) {
                checkFollowState();
            }
            loadOrganizations();
            loadPinnedRepos();
        }, 300);
    }

    // ===========================
    // Following
    // ===========================

    public void checkFollowState() {
        execute(false, userRestService.getFollowStatus(user), booleanResponse -> {
            followState.setValue(booleanResponse.code() == 204 ? FollowingState.FOLLOWED : FollowingState.UNFOLLOWED);
        }, errorEntity -> {
            followState.setValue(null);
        });
    }

    public void onFollowClick() {
        final FollowingState oldState = followState.getValue();
        final boolean followed = oldState == FollowingState.FOLLOWED;
        followState.setValue(FollowingState.LOADING);
        execute(false, followed ? userRestService.unfollowUser(user) : userRestService.followUser(user), booleanResponse -> {
            boolean success = booleanResponse.code() == 204;
            if (followed) {
                followState.setValue(success ? FollowingState.UNFOLLOWED : FollowingState.FOLLOWED);
            } else {
                followState.setValue(success ? FollowingState.FOLLOWED : FollowingState.UNFOLLOWED);
            }
        }, errorEntity -> {
            followState.setValue(oldState);
        });
    }

    public enum FollowingState {
        LOADING,
        FOLLOWED,
        UNFOLLOWED
    }

    // ===========================
    // Organizations
    // ===========================

    public void loadOrganizations() {
        boolean isMe = isMe(user);
        organsState.setValue(State.loading(null));
        execute(false,
                isMe ? organizationService.getMyOrganizations() : organizationService.getMyOrganizations(user),
                userPageable -> {
                    organizationAdapter.setData(userPageable.getItems());
                    organsState.setValue(State.success(null));
                }, errorEntity -> {
                    organsState.setValue(State.error(null));
                });
    }

    // ===========================
    // Pinned Repos
    // ===========================

    public void loadPinnedRepos() {
        pinnedState.setValue(State.loading(null));
        ApolloCall<GetPinnedReposQuery.Data> apolloCall = apolloClient.query(GetPinnedReposQuery.builder()
                        .login(user)
                        .build());
        RxHelper.getObservable(Rx2Apollo.from(apolloCall))
                .filter(dataResponse -> !dataResponse.hasErrors())
                .flatMap(dataResponse -> {
                    GetPinnedReposQuery.Data data = dataResponse.data();
                    if (data != null && data.user() != null) {
                        return Observable.fromIterable(data.user().pinnedRepositories().edges());
                    }
                    return Observable.empty();
                })
                .map(GetPinnedReposQuery.Edge::node)
                .toList()
                .toObservable()
                .subscribe(nodes1 -> {
                    pinnedRepos.clear();
                    pinnedRepos.addAll(nodes1);
                    pinnedState.setValue(State.success(null));
                }, throwable -> {
                    throwable.printStackTrace();
                    pinnedState.setValue(State.error(null));
                });
    }

}
