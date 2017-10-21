package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.model.Commit;
import com.duyp.architecture.mvvm.model.Issue;
import com.duyp.architecture.mvvm.model.Repo;
import com.duyp.architecture.mvvm.model.User;
import com.duyp.architecture.mvvm.model.def.RepoTypes;
import com.duyp.architecture.mvvm.utils.RemoteConstants;

import java.util.List;

import io.reactivex.Single;
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
    Single<User> login(@Header(RemoteConstants.HEADER_AUTH) String basicToken);

    @GET("users/{username}")
    Single<User> getUser(@Path("username") String username);

    @GET("repositories")
    Single<List<Repo>> getAllPublicRepositories(@Query("since") @Nullable Long sinceRepoId);

    @GET("repos/{owner}/{repo}")
    Single<Repo> getRepository(@Path("owner") String owner, @Path("repo") String repoName
    );

    @GET("user/repos")
    Single<List<Repo>> getMyRepositories(@Query("type") @RepoTypes String type);

    @GET("users/{username}/repos")
    Single<List<Repo>> getUserRepositories(
            @Path("username") String userName, @Query("type") @RepoTypes String type
    );

    @GET("repos/{owner}/{repo}/issues")
    Single<List<Issue>> getRepoIssues(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/commits")
    Single<List<Commit>> getRepoCommits(@Path("owner") String owner, @Path("repo") String repo);
}
