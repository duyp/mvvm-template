package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.model.BranchesModel;
import com.duyp.architecture.mvvm.data.model.Comment;
import com.duyp.architecture.mvvm.data.model.CommentRequestModel;
import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.data.model.CreateMilestoneModel;
import com.duyp.architecture.mvvm.data.model.Label;
import com.duyp.architecture.mvvm.data.model.MarkdownModel;
import com.duyp.architecture.mvvm.data.model.MilestoneModel;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.model.RepoSubscriptionModel;
import com.duyp.architecture.mvvm.data.model.TreeResponseModel;
import com.duyp.architecture.mvvm.data.model.User;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Kosh on 10 Dec 2016, 3:16 PM
 */
public interface RepoService {


    @NonNull @GET @Headers("Accept: application/vnd.github.VERSION.raw")
    Single<String> getFileAsStream(@Url String url);

    @NonNull @GET @Headers("Accept: application/vnd.github.html")
    Single<String> getFileAsHtmlStream(@Url String url);

    @NonNull @POST("markdown")
    Single<String> convertReadmeToHtml(@Body MarkdownModel model);

    @NonNull @GET("repos/{login}/{repoId}")
    @Headers({"Accept: application/vnd.github.drax-preview+json, application/vnd.github.mercy-preview+json"})
    Single<Repo> getRepo(@Path("login") String login, @Path("repoId") String repoId);

    @NonNull @DELETE("repos/{login}/{repoId}")
    Single<Response<Boolean>> deleteRepo(@Path("login") String login, @Path("repoId") String repoId);

    @NonNull @GET @Headers("Accept: application/vnd.github.html")
    Single<String> getReadmeHtml(@NonNull @Url String url);

    @NonNull @GET("user/starred/{owner}/{repo}")
    Single<Response<Boolean>> checkStarring(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @PUT("user/starred/{owner}/{repo}")
    Single<Response<Boolean>> starRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @DELETE("user/starred/{owner}/{repo}")
    Single<Response<Boolean>> unstarRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @POST("/repos/{owner}/{repo}/forks")
    Single<Repo> forkRepo(@NonNull @Path("owner") String login, @NonNull @Path("repo") String repoId);

    @NonNull @GET("repos/{owner}/{repo}/subscription")
    Single<RepoSubscriptionModel> isWatchingRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @PUT("user/subscriptions/{owner}/{repo}")
    Single<Response<Boolean>> watchRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @DELETE("user/subscriptions/{owner}/{repo}")
    Single<Response<Boolean>> unwatchRepo(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/commits")
    Single<Pageable<Commit>> getCommits(@Path("owner") String owner, @Path("repo") String repo,
                                        @NonNull @Query("sha") String branch, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/commits")
    Single<Pageable<Commit>> getCommits(@Path("owner") String owner, @Path("repo") String repo,
                                            @NonNull @Query("sha") String branch,
                                            @NonNull @Query("path") String path,
                                            @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/releases")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Single<Pageable<Release>> getReleases(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/releases/{id}")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Single<Release> getRelease(@Path("owner") String owner, @Path("repo") String repo, @Path("id") long id);

    @NonNull @GET("repos/{owner}/{repo}/releases/latest")
    Single<Release> getLatestRelease(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/tags")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Single<Pageable<Release>> getTagReleases(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/tags/{tag}")
    @Headers("Accept: application/vnd.github.VERSION.full+json")
    Single<Release> getTagRelease(@Path("owner") String owner, @Path("repo") String repo, @Path("tag") String tag);

    @NonNull @GET("repos/{owner}/{repo}/contributors")
    Single<Pageable<User>> getContributors(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/commits/{sha}")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Single<Commit> getCommit(@Path("owner") String owner, @Path("repo") String repo, @Path("sha") String sha);

    @NonNull @GET("repos/{owner}/{repo}/commits/{sha}/comments")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Single<Pageable<Comment>> getCommitComments(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                @NonNull @Path("sha") String ref, @Query("page") int page);

    @NonNull @POST("repos/{owner}/{repo}/commits/{sha}/comments")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Single<Comment> postCommitComment(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                          @NonNull @Path("sha") String ref, @Body CommentRequestModel model);


    @NonNull @PATCH("repos/{owner}/{repo}/comments/{id}")
    @Headers("Accept: application/vnd.github.VERSION.full+json, application/vnd.github.squirrel-girl-preview")
    Single<Comment> editCommitComment(@Path("owner") String owner, @Path("repo") String repo, @Path("id") long id,
                                          @Body CommentRequestModel body);

    @NonNull @DELETE("repos/{owner}/{repo}/comments/{id}")
    Single<Response<Boolean>> deleteComment(@Path("owner") String owner, @Path("repo") String repo, @Path("id") long id);

    @NonNull @GET("repos/{owner}/{repo}/contents/{path}")
    Single<Pageable<RepoFile>> getRepoFiles(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                            @NonNull @Path(value = "path", encoded = true) String path,
                                            @NonNull @Query("ref") String ref);

    @NonNull @GET("repos/{owner}/{repo}/git/trees/{sha}?recursive=1")
    Single<TreeResponseModel> getRepoTree(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                          @NonNull @Path("sha") String sha);

    @NonNull @GET("repos/{owner}/{repo}/labels?per_page=100")
    Single<Pageable<Label>> getLabels(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/labels?per_page=100")
    Single<Pageable<Label>> getLabels(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo, @Query("page") int page);

    @NonNull @POST("repos/{owner}/{repo}/labels")
    Single<Label> addLabel(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo, @Body Label body);

    @NonNull @GET("repos/{owner}/{repo}/collaborators/{username}")
    Single<Response<Boolean>> isCollaborator(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                 @NonNull @Path("username") String username);

    @NonNull @GET("repos/{owner}/{repo}/collaborators?per_page=100")
    Single<Pageable<User>> getCollaborator(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo);


    @NonNull @GET("repos/{owner}/{repo}/branches")
    Single<Pageable<BranchesModel>> getBranches(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/tags")
    Single<Pageable<BranchesModel>> getTags(@NonNull @Path("owner") String owner, @NonNull @Path("repo") String repo,
                                                @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/milestones")
    Single<Pageable<MilestoneModel>> getMilestones(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @POST("repos/{owner}/{repo}/milestones")
    Single<MilestoneModel> createMilestone(@Path("owner") String owner, @Path("repo") String repo,
                                               @Body CreateMilestoneModel create);

    @NonNull @GET("repos/{owner}/{repo}/assignees")
    Single<Pageable<User>> getAssignees(@Path("owner") String owner, @Path("repo") String repo);

    @NonNull @GET("repos/{owner}/{repo}/commits?per_page=1")
    Single<Pageable<Commit>> getCommitCounts(@Path("owner") String owner, @Path("repo") String repo, @Query("sha") String ref);

    @NonNull @GET("/repos/{owner}/{repo}/stargazers")
    Single<Pageable<User>> getStargazers(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("/repos/{owner}/{repo}/subscribers")
    Single<Pageable<User>> getWatchers(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("/repos/{owner}/{repo}/forks")
    Single<Pageable<Repo>> getForks(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @NonNull @GET("repos/{owner}/{repo}/license") @Headers("Accept: application/vnd.github.html")
    Single<String> getLicense(@Path("owner") String owner, @Path("repo") String repo);
}
