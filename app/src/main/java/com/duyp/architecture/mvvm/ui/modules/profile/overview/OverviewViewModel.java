package com.duyp.architecture.mvvm.ui.modules.profile.overview;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.provider.ServiceFactory;
import com.duyp.architecture.mvvm.data.remote.OrganizationService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.RxHelper;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.organizations.OrganizationAdapter;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.pinned.PinnedAdapter;
import com.duyp.architecture.mvvm.ui.widgets.contributions.ContributionsDay;
import com.duyp.architecture.mvvm.ui.widgets.contributions.ContributionsProvider;

import java.util.List;

import javax.inject.Inject;

import github.GetPinnedReposQuery;
import io.reactivex.Observable;
import lombok.Getter;

/**
 * Created by duypham on 10/27/17.
 *
 */

@Getter
public class OverviewViewModel extends BaseViewModel {

    private static final String CONTRIBUTION_URL = "https://github.com/users/%s/contributions";

    private String user;

    private final MutableLiveData<FollowingState> followState = new MutableLiveData<>();
    private final MutableLiveData<State> organsState = new MutableLiveData<>();
    private final MutableLiveData<State> pinnedState = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<ContributionsDay>>> contributionsData = new MutableLiveData<>();

    private final UserRestService userRestService;
    private final OrganizationService organizationService;
    private final ApolloClient apolloClient;

    private final OrganizationAdapter organizationAdapter = new OrganizationAdapter();
    private final PinnedAdapter pinnedAdapter = new PinnedAdapter();

    @Inject
    OverviewViewModel(UserManager userManager,
                             UserRestService service,
                             OrganizationService organizationService,
                             ApolloClient apolloClient) {
        super(userManager);
        this.userRestService = service;
        this.organizationService = organizationService;
        this.apolloClient = apolloClient;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    void initUser(String userLogin, boolean isMeOrOrganization) {
        if (this.user == null) {
            this.user = userLogin;
            followState.setValue(null);
            new Handler(Looper.myLooper()).postDelayed(() -> {
                if (!isMeOrOrganization) {
                    checkFollowState();
                }
                loadOrganizations();
                loadPinnedRepos();
                getContributions();
            }, 300);
        }
    }

    // =============================================================================================
    // Following
    // =============================================================================================

    private void checkFollowState() {
        followState.setValue(FollowingState.LOADING);
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

    // =============================================================================================
    // Organizations
    // =============================================================================================

    private void loadOrganizations() {
        boolean isMe = isMe(user);
        organsState.setValue(State.loading(null));
        execute(false,
                isMe ? organizationService.getMyOrganizations() : organizationService.getMyOrganizations(user),
                userPageable -> {
                    organizationAdapter.setData(userPageable.getItems());
                    if (userPageable.getItems().size() > 0) {
                        organsState.setValue(State.success(null));
                    } else {
                        organsState.setValue(State.error(null));
                    }
                }, errorEntity -> {
                    organsState.setValue(State.error(null));
                });
    }

    // =============================================================================================
    // Pinned Repos
    // =============================================================================================

    private void loadPinnedRepos() {
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
                    pinnedAdapter.setData(nodes1);
                    if (nodes1.size() > 0) {
                        pinnedState.setValue(State.success(null));
                    } else {
                        pinnedState.setValue(State.error(null));
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    pinnedState.setValue(State.error(null));
                });
    }

    // =============================================================================================
    // Contributions
    // =============================================================================================

    private void getContributions() {
        contributionsData.setValue(Resource.loading(null));
        String url = String.format(CONTRIBUTION_URL, user);
        execute(false, ServiceFactory.getContributionService().getContributions(url), s -> {
            Observable.just(new ContributionsProvider().getContributions(s))
                    .subscribe(contributionsDays -> {
                        contributionsData.setValue(Resource.success(contributionsDays));
                    }, throwable -> {
                        contributionsData.setValue(Resource.error(null, null));
                    });
        }, errorEntity -> {
            contributionsData.setValue(Resource.error(null, null));
        });
    }

}
