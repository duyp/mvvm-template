package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Issue;
import com.duyp.architecture.mvvm.model.Repository;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleIssueList;
import static com.duyp.architecture.mvvm.model.ModelUtils.sampleRepository;
import static com.duyp.architecture.mvvm.test_utils.RealmTestUtils.initLiveRealmResults;
import static com.duyp.architecture.mvvm.test_utils.RealmTestUtils.mockRealmResults;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.List;

import javax.inject.Inject;


import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import static org.junit.Assert.assertThat;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.*;

/**
 * Created by duypham on 10/17/17.
 *
 */
public class IssuesRepoTest extends BaseRepoTest {

    private static final Long sampleRepoId = 10L;

    @Inject
    IssuesRepo issuesRepo;

    private IssueDao issueDao;

    private Repository mSampleRepository = sampleRepository(sampleRepoId, sampleUser(1L));

    @Override
    protected void inject(TestComponent component) throws Exception {
        issueDao = PowerMockito.mock(IssueDao.class);
        when(mockRealmDatabase.newIssueDao()).thenReturn(issueDao);
        component.inject(this);
    }

    @Test
    public void beAbleToMockData() {
        assertThat(issuesRepo, is(notNullValue()));
        assertThat(issueDao, is(notNullValue()));
        assertThat(issuesRepo.getMIssuesDao(), is(issueDao));
    }

    @Test
    public void initRepo() throws Exception {
        List<Issue> issueList = sampleIssueList(10, sampleRepoId);

        RepositoryDao repositoryDao = mock(RepositoryDao.class);
        when(mockRealmDatabase.newRepositoryDao()).thenReturn(repositoryDao);
        when(repositoryDao.getById(sampleRepoId)).thenReturn(LiveRealmObject.asLiveData(mSampleRepository));

        RealmResults<Issue> issues = mockRealmResults();
        LiveRealmResults<Issue> liveIssues = initLiveRealmResults(issues);
        when(issues.size()).thenReturn(issueList.size());
        when(issues.iterator()).thenReturn(issueList.iterator());

        when(issueDao.getRepoIssues(sampleRepoId)).thenReturn(liveIssues);

        // do action
        issuesRepo.initRepo(sampleRepoId);

        verify(repositoryDao).closeRealm();

        assertThat(issuesRepo.getData(), is(liveIssues));
        assertThat(liveIssues.getData(), is(issues));
    }

    @Test
    public void getRepoIssuesSuccess() throws Exception {
        initRepo();

        List<Issue> issueList = sampleIssueList(10, sampleRepoId);
        when(githubService.getRepoIssues(mSampleRepository.getOwner().getLogin(), mSampleRepository.getName()))
                .thenReturn(successResponse(issueList));

        doNothing().when(issueDao).addAll(any());

        issuesRepo.getRepoIssues().subscribe();
        verify(issueDao, times(1)).addAll(issueList);
    }

    @Test
    public void getRepoIssuesError() throws Exception {
        initRepo();

        when(githubService.getRepoIssues(mSampleRepository.getOwner().getLogin(), mSampleRepository.getName()))
                .thenReturn(errorResponse(401));

        issuesRepo.getRepoIssues().subscribe();
        verify(issueDao, times(0)).addAll(any());
    }


    @Test
    public void shouldCloseRealmWhenDestroy() {
        issuesRepo.onDestroy();
        verify(issueDao).closeRealm();
    }
}