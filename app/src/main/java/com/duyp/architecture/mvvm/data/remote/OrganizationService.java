package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.TeamsModel;
import com.duyp.architecture.mvvm.data.model.User;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Kosh on 22 Mar 2017, 6:44 PM
 */
public interface OrganizationService {

    @GET("orgs/{org}/members/{username}")
    Single<Response<Boolean>> isMember(@NonNull @Path("org") String org, @NonNull @Path("username") String username);

    @GET("orgs/{org}")
    Single<User> getOrganization(@NonNull @Path("org") String org);

    @GET("user/orgs?per_page=200")
    Single<Pageable<User>> getMyOrganizations();

    @GET("users/{user}/orgs")
    Single<Pageable<User>> getMyOrganizations(@NonNull @Path("user") String user);

    @GET("orgs/{org}/teams")
    Single<Pageable<TeamsModel>> getOrgTeams(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("orgs/{org}/members")
    Single<Pageable<User>> getOrgMembers(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("teams/{id}/members")
    Single<Pageable<User>> getTeamMembers(@Path("id") long id, @Query("page") int page);

    @GET("teams/{id}/repos")
    Single<Pageable<Repo>> getTeamRepos(@Path("id") long id, @Query("page") int page);

    @GET("users/{username}/events/orgs/{org}")
    Single<Pageable<Event>> getReceivedEvents(@NonNull @Path("username") String userName,
                                                  @NonNull @Path("org") String org, @Query("page") int page);

    @GET("orgs/{org}/repos")
    Single<Pageable<Repo>> getOrgRepos(@NonNull @Path("org") String org,
                                           @QueryMap(encoded = true) Map<String, String> filterParams,
                                           @Query("page") int page);

}
