package com.duyp.architecture.mvvm.data.repository;

import com.duyp.architecture.mvvm.data.local.daos.AllReposDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.remote.SearchService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class AllReposRepo extends BaseRepo<Repo, AllReposDao> {

    private final SearchService searchService;

    @Inject
    public AllReposRepo(UserManager userManager, AllReposDao dao, SearchService searchService) {
        super(userManager, dao);
        this.searchService = searchService;
    }

    public RealmResults<Repo> searchLocal(String query) {
        return dao.searchRepo(query);
    }

    public Flowable<Resource<Pageable<Repo>>> searchRemote(String query, int page) {
        return RestHelper.createRemoteSourceMapper(searchService.searchRepositories(query, page), repoPageable -> {
            dao.addAllAsync(repoPageable.getItems());
        });
    }
}
