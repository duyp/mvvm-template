package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.TeamsModel;
import com.duyp.architecture.mvvm.data.model.User;

import java.util.Map;

import io.reactivex.Observable;
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
    Observable<Response<Boolean>> isMember(@NonNull @Path("org") String org, @NonNull @Path("username") String username);

    @GET("orgs/{org}")
    Observable<User> getOrganization(@NonNull @Path("org") String org);

    @GET("user/orgs?per_page=200")
    Observable<Pageable<User>> getMyOrganizations();

    @GET("users/{user}/orgs")
    Observable<Pageable<User>> getMyOrganizations(@NonNull @Path("user") String user);

    @GET("orgs/{org}/teams")
    Observable<Pageable<TeamsModel>> getOrgTeams(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("orgs/{org}/members")
    Observable<Pageable<User>> getOrgMembers(@NonNull @Path("org") String org, @Query("page") int page);

    @GET("teams/{id}/members")
    Observable<Pageable<User>> getTeamMembers(@Path("id") long id, @Query("page") int page);

    @GET("teams/{id}/repos")
    Observable<Pageable<Repo>> getTeamRepos(@Path("id") long id, @Query("page") int page);

    @GET("users/{username}/events/orgs/{org}")
    Observable<Pageable<Event>> getReceivedEvents(@NonNull @Path("username") String userName,
                                                  @NonNull @Path("org") String org, @Query("page") int page);

    @GET("orgs/{org}/repos")
    Observable<Pageable<Repo>> getOrgRepos(@NonNull @Path("org") String org,
                                           @QueryMap(encoded = true) Map<String, String> filterParams,
                                           @Query("page") int page);

}
