package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repository;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.def.RepoTypes;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by duypham on 9/7/17.
 * Github Rest API retrofit service (See https://developer.github.com/v3/)
 */

public interface GithubService {

    @GET("user")
    Single<Response<User>> login(@Header(RemoteConstants.HEADER_AUTH) String basicToken);

    @GET("users/{username}")
    Single<Response<User>> getUser(@Path("username") String username);

    @GET("repositories")
    Single<Response<List<Repository>>> getAllPublicRepositories(@Query("since") @Nullable Long sinceRepoId);

    @GET("repos/{owner}/{repo}")
    Single<Response<Repository>> getRepository(@Path("owner") String owner, @Path("repo") String repoName
    );

    @GET("user/repos")
    Single<Response<List<Repository>>> getMyRepositories(@Query("type") @RepoTypes String type);

    @GET("users/{username}/repos")
    Single<Response<List<Repository>>> getUserRepositories(
            @Path("username") String userName, @Query("type") @RepoTypes String type
    );

    @GET("repos/{owner}/{repo}/issues")
    Single<Response<List<Issue>>> getRepoIssues(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/commits")
    Single<Response<List<Commit>>> getRepoCommits(@Path("owner") String owner, @Path("repo") String repo);
}
