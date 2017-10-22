package com.duyp.architecture.mvvm.local.dao;

import com.duyp.architecture.mvvm.data.local.daos.IssueDao;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.local.dagger.TestAppComponent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.duyp.architecture.mvvm.utils.test.ModelTestUtils.sampleIssue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by duypham on 9/21/17.
 *
 */

public class IssueDaoTest extends BaseDaoTest{

    private IssueDao issueDao;

    @Override
    public void inject(TestAppComponent appComponent) {
        appComponent.inject(this);
        issueDao = realmDatabase.newIssueDao();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        issueDao.closeRealm();
    }

    @Test
    public void saveAndReadIssue() {
        Issue issue = sampleIssue(1L);
        issueDao.addOrUpdate(issue);

        Issue savedIssue = issueDao.getById(issue.getId()).getData();

        assertEquals(issue.getId(), savedIssue.getId());
        assertEquals(issue.getRepoId(), savedIssue.getRepoId());
    }

    @Test
    public void getIssueByRepository() throws Exception{
        Long repoId1 = 4L;
        Long repoId2 = 5L;
        List<Issue> issues = new ArrayList<>();

        int i;
        for  (i = 0; i < 10; i++) {
            issues.add(sampleIssue((long)i, repoId1));
        }

        for (; i < 30; i++) {
            issues.add(sampleIssue((long)i, repoId2));
        }
        issueDao.addAll(issues);

        List<Issue> repo1Issues = issueDao.getRepoIssues(repoId1).getData();
        assertEquals(10, repo1Issues.size());

        List<Issue> repo2Issues = issueDao.getRepoIssues(repoId2).getData();
        assertEquals(20, repo2Issues.size());

        assertEquals(0, issueDao.getRepoIssues(-1L).getData().size());
    }
}
