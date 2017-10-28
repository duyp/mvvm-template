package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.daos.UserReposDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.FilterOptionsModel;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by duypham on 10/25/17.
 * Repository layer for User's github repositories
 */

public class UserReposRepo extends BaseRepo<Repo, UserReposDao> {

    @Getter
    private LiveRealmResults<Repo> data;
    private final UserRestService userRestService;

    private String targetUser;
    private boolean isOwner;

    @Inject
    public UserReposRepo(UserManager userManager, UserReposDao dao, UserRestService userRestService) {
        super(userManager, dao);
        this.userRestService = userRestService;
    }

    public void initUser(@Nullable String userLogin) {
        String currentUser = getCurrentUserLogin();
        if (userLogin == null && currentUser == null) {
            throw new IllegalStateException("Both targetUser and currentUser are NULL is not allowed!");
        }
        isOwner = userLogin == null || currentUser != null && currentUser.equals(userLogin);
        targetUser = isOwner ? currentUser : userLogin;
        data = dao.getUserRepositories(targetUser);
    }

    /**
     * Get repositories of given user
     */
    public Flowable<Resource<Pageable<Repo>>> getUserRepositories(FilterOptionsModel filterOptions, int page) {
        Single<Pageable<Repo>> remote = isOwner ? userRestService.getRepos(filterOptions.getQueryMap(), page) :
                userRestService.getRepos(targetUser, filterOptions.getQueryMap(), page);

        return RestHelper.createRemoteSourceMapper(remote, repositories -> {
            if (isOwner) {
                for (Repo repo : repositories.getItems()) {
                    // store member login (target user is currentUser)
                    if (!repo.getOwner().getLogin().equals(targetUser)) {
                        repo.setMemberLoginName(targetUser);
                    }
                }
            }
            dao.addAllAsync(repositories.getItems());
        });
    }
}
