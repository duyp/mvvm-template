package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.local.Login;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by Kosh on 08 Feb 2017, 8:54 PM
 */

public interface UserRestService {

    @GET("user")
    Single<Login> getUser();

    @GET("users/{username}")
    Single<UserDetail> getUser(@Path("username") @NonNull String username);

    @GET("users/{username}/received_events")
    Single<Pageable<Event>> getReceivedEvents(@NonNull @Path("username") String userName, @Query("page") int page);

    @GET("users/{username}/events")
    Single<Pageable<Event>> getUserEvents(@NonNull @Path("username") String userName, @Query("page") int page);

    @GET("users/{username}/repos")
    Single<Pageable<Repo>> getRepos(@Path("username") @NonNull String username, @QueryMap(encoded = true) Map<String, String> filterParams,
                                        @Query("page") int page);

    @GET("user/repos")
    Single<Pageable<Repo>> getRepos(@QueryMap(encoded = true) Map<String, String> filterParams, @Query(value = "page") int page);

    @GET("users/{username}/starred")
    Single<Pageable<Repo>>
    getStarred(@Path("username") @NonNull String username, @Query("page") int page);

    @GET("users/{username}/starred?per_page=1")
    Single<Pageable<Repo>>
    getStarredCount(@Path("username") @NonNull String username);

    @GET("users/{username}/following")
    Single<Pageable<User>> getFollowing(@Path("username") @NonNull String username, @Query("page") int page);

    @GET("users/{username}/followers")
    Single<Pageable<User>> getFollowers(@Path("username") @NonNull String username, @Query("page") int page);

    @GET("user/following/{username}")
    Single<Response<Boolean>> getFollowStatus(@Path("username") @NonNull String username);

    @PUT("user/following/{username}")
    Single<Response<Boolean>> followUser(@Path("username") @NonNull String username);

    @DELETE("user/following/{username}")
    Single<Response<Boolean>> unfollowUser(@Path("username") @NonNull String username);

    @GET
    Single<String> getContributions(@Url String url);
}
