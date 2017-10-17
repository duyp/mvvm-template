package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.local.BaseRealmTest;
import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Issue;
import com.duyp.architecture.mvvm.model.Repository;
import com.duyp.architecture.mvvm.utils.LiveDataTestUtil;

import static com.duyp.architecture.mvvm.utils.test.TestUtils.sampleIssueList;
import static com.duyp.architecture.mvvm.utils.test.TestUtils.sampleUser;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import javax.inject.Inject;


import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertThat;

import static com.duyp.architecture.mvvm.utils.test.TestUtils.sampleRepository;
/**
 * Created by duypham on 10/17/17.
 *
 */
public class IssuesRepoTest extends BaseRepoTest {

    private static final Long sampleRepoId = 10L;

    @Inject
    IssuesRepo issuesRepo;

    @Mock
    IssueDao issueDao;

    @Override
    protected void inject(TestComponent component) throws Exception {
        component.inject(this);
        when(mockRealmDatabase.newIssueDao()).thenReturn(issueDao);
        LiveDataTestUtil.getValue(null);
    }

    @Test
    public void beAbleToMockData() {
        assertThat(issuesRepo, is(notNullValue()));
        assertThat(issueDao, is(notNullValue()));
    }

    @Test
    public void initRepo() throws Exception {
        Repository sampleRepo = sampleRepository(sampleRepoId, sampleUser(10L));
        List<Issue> issueList = sampleIssueList(10, sampleRepoId);

        RepositoryDao repositoryDao = Mockito.mock(RepositoryDao.class);
        when(mockRealmDatabase.newRepositoryDao()).thenReturn(repositoryDao);
        when(repositoryDao.getById(any())).thenReturn(LiveRealmObject.asLiveData(sampleRepo));

        RealmResults<Issue> issues = BaseRealmTest.mockRealmResults();
        LiveRealmResults<Issue> liveIssues = BaseRealmTest.initLiveRealmResults(issues);
        when(issues.size()).thenReturn(issueList.size());
        when(issues.iterator()).thenReturn(issueList.iterator());

        when(issueDao.getRepoIssues(sampleRepoId)).thenReturn(liveIssues);
        issuesRepo.initRepo(sampleRepoId);

        verify(repositoryDao).closeRealm();

        assertThat(issuesRepo.getData(), is(liveIssues));
        assertThat(liveIssues.getData(), is(issues));
    }

    @Test
    public void getRepoIssues() throws Exception {
    }
}