package com.duyp.architecture.mvvm.data.remote;

import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kosh on 08 Dec 2016, 9:07 PM
 */

public interface SearchService {

    @GET("search/repositories")
    Single<Pageable<Repo>> searchRepositories(@Query(value = "q", encoded = true) String query, @Query("page") long page);

//    @GET("search/code")
//    Single<Pageable<SearchCodeModel>> searchCode(@Query(value = "q", encoded = true) String query, @Query("page") long page);

    @GET("search/issues")
    Single<Pageable<Issue>> searchIssues(@Query(value = "q", encoded = true) String query, @Query("page") long page);

    @GET("search/users")
    Single<Pageable<User>> searchUsers(@Query(value = "q", encoded = true) String query, @Query("page") long page);
}
