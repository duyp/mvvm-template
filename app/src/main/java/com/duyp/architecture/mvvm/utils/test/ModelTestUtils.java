package com.duyp.architecture.mvvm.utils.test;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by duypham on 10/16/17.
 *
 */

public class ModelTestUtils {

    // =======================================================================================
    // Sample Users creator
    // =======================================================================================

    public static User sampleUser(Long id) {
        return sampleUser(id, "duyp");
    }

    public static User sampleUser(Long id, String login) {
        User user = new User();
        user.setLogin(login);
        user.setBio("This is test user");
        user.setId(id);
        return user;
    }

    // =======================================================================================
    // Sample Issues creator
    // =======================================================================================

    public static Issue sampleIssue(Long id) {
        return sampleIssue(id, 123L);
    }

    public static Issue sampleIssue(Long id, Long repoId) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setRepoId(repoId);
        issue.setTitle("This is title of issue " + id);
        issue.setBody("This is body of issue " + id);
        issue.setCreatedAt(new Date());
        issue.setUser(sampleUser(43L));
        issue.setLabels(new RealmList<>());
        return issue;
    }

    public static List<Issue> sampleIssueList(int size, @NonNull Long repoId) {
        return sampleIssueList(0, size, repoId);
    }

    public static List<Issue> sampleIssueList(int startIndex, int size, @NonNull Long repoId) {
        List<Issue> list = new ArrayList<>();
        for (int i = startIndex; i < startIndex + size; i++) {
            list.add(sampleIssue((long)i, repoId));
        }
        return list;
    }



    // =======================================================================================
    // Sample Repositories creator
    // =======================================================================================

    public static Repo sampleRepository(Long id, @NonNull User owner) {
        Repo repo = new Repo();
        repo.setId(id);
        repo.setOwner(owner);
        repo.setName("repo" + id);
        repo.setFullName(owner.getLogin() + "/" + repo.getName());
        return repo;
    }

    public static List<Repo> sampleRepoList(int size, @NonNull User owner) {
        return sampleRepoList(0, size, owner);
    }

    public static List<Repo> sampleRepoList(int startIndex, int size, @NonNull User owner) {
        List<Repo> list = new ArrayList<>();
        for (int i = startIndex; i < startIndex + size; i++) {
            list.add(sampleRepository((long)i, owner));
        }
        return list;
    }
}
