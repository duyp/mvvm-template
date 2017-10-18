package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Repository;

import org.junit.Test;

import java.util.List;

import javax.inject.Inject;


import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static com.duyp.architecture.mvvm.test_utils.RealmTestUtils.mockRealmResults;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.errorResponse;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static  org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.verify;

import static com.duyp.architecture.mvvm.test_utils.RealmTestUtils.initLiveRealmResults;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.successResponse;
import static com.duyp.architecture.mvvm.model.ModelUtils.sampleRepoList;

/**
 * Created by duypham on 10/18/17.
 *
 */
public class RepositoriesRepoTest extends BaseRepoTest {

    @Inject
    RepositoriesRepo repositoriesRepo;

    private RepositoryDao repositoryDao;

    @Override
    protected void inject(TestComponent component) throws Exception {
        repositoryDao = mock(RepositoryDao.class);
        when(mockRealmDatabase.newRepositoryDao()).thenReturn(repositoryDao);
        component.inject(this);
    }

    @Test
    public void beAbleToBeMockedAndInjected() {
        assertThat(repositoriesRepo, is(notNullValue()));
        assertThat(repositoryDao, is(notNullValue()));
        assertThat(repositoriesRepo.getRepositoryDao(), is(repositoryDao));
    }

    @Test
    public void getAllRepositoriesSuccess() throws Exception {
        Long sinceId = 1L;

        // local
        LiveRealmResults<Repository> liveRealmResults = initLiveRealmResults(mockRealmResults());
        when(repositoryDao.getAll()).thenReturn(liveRealmResults);

        doNothing().when(repositoryDao).addAll(any());
        List<Repository> repositories = sampleRepoList(10, sampleUser(2L));
        when(githubService.getAllPublicRepositories(sinceId)).thenReturn(successResponse(repositories));

        repositoriesRepo.getAllRepositories(sinceId).subscribe();

        assertThat(repositoriesRepo.getData(), is(liveRealmResults));
        verify(repositoryDao, times(1)).addAll(repositories);
    }

    @Test
    public void getAllRepositoriesError() throws Exception {
        Long sinceId = 1L;

        // local
        LiveRealmResults<Repository> liveRealmResults = initLiveRealmResults(mockRealmResults());
        when(repositoryDao.getAll()).thenReturn(liveRealmResults);

        when(githubService.getAllPublicRepositories(sinceId)).thenReturn(errorResponse(405));

        repositoriesRepo.getAllRepositories(sinceId).subscribe();

        assertThat(repositoriesRepo.getData(), is(liveRealmResults));
        verify(repositoryDao, times(0)).addAll(any());
    }

    @Test
    public void findRepositoriesSuccess() throws Exception {

        // local
        LiveRealmResults<Repository> liveRealmResults = initLiveRealmResults(mockRealmResults());
        when(repositoryDao.getRepositoriesWithNameLike("name")).thenReturn(liveRealmResults);
        doNothing().when(repositoryDao).addAll(any());

        // remote
        List<Repository> list = sampleRepoList(10, sampleUser(1L));
        when(githubService.getAllPublicRepositories(any())).thenReturn(successResponse(list));

        repositoriesRepo.findRepositories("name").subscribe();

        assertThat(repositoriesRepo.getData(), is(liveRealmResults));
        verify(repositoryDao, times(1)).addAll(list);
    }

    @Test
    public void findRepositoriesError() throws Exception {

        // local
        LiveRealmResults<Repository> liveRealmResults = initLiveRealmResults(mockRealmResults());
        when(repositoryDao.getRepositoriesWithNameLike("name")).thenReturn(liveRealmResults);

        // remote
        when(githubService.getAllPublicRepositories(any())).thenReturn(errorResponse(405));

        repositoriesRepo.findRepositories("name").subscribe();

        assertThat(repositoriesRepo.getData(), is(liveRealmResults));
        verify(repositoryDao, times(0)).addAll(any());
    }

    @Test
    public void getMyUserRepositoriesSuccess() throws Exception {

        List<Repository> list = sampleRepoList(10, sampleUser(111L, "abcd"));
        when(githubService.getMyRepositories(any())).thenReturn(successResponse(list));

        repositoriesRepo.getUserRepositories(mUser.getLogin()).subscribe();

        verify(githubService).getMyRepositories(any());
        verify(githubService, times(0)).getUserRepositories(anyString(), any());
        verify(repositoryDao).addAll(list);

        for (Repository repository : list) {
            assertEquals(repository.getMemberLoginName(), mUser.getLogin());
        }
    }

    @Test
    public void getMyUserRepositoriesError() throws Exception {

        when(githubService.getMyRepositories(any())).thenReturn(errorResponse(444));

        repositoriesRepo.getUserRepositories(mUser.getLogin()).subscribe();

        verify(githubService).getMyRepositories(any());
        verify(githubService, times(0)).getUserRepositories(anyString(), any());
        verify(repositoryDao, times(0)).addAll(any());
    }

    @Test
    public void getOtherUserRepositoriesSuccess() throws Exception {
        String sampleLogin = "abcd";

        List<Repository> list = sampleRepoList(10, sampleUser(111L, sampleLogin));
        when(githubService.getUserRepositories(anyString(), any())).thenReturn(successResponse(list));

        repositoriesRepo.getUserRepositories(sampleLogin).subscribe();

        verify(githubService, times(0)).getMyRepositories(any());
        verify(githubService, times(1)).getUserRepositories(anyString(), any());
        verify(repositoryDao).addAll(list);

        for (Repository repository : list) {
            assertNotEquals(repository.getMemberLoginName(), mUser.getLogin());
        }
    }

    @Test
    public void getOtherUserRepositoriesError() throws Exception {
        String sampleLogin = "abcd";

        when(githubService.getUserRepositories(anyString(), any())).thenReturn(errorResponse(444));

        repositoriesRepo.getUserRepositories(sampleLogin).subscribe();

        verify(githubService, times(0)).getMyRepositories(any());
        verify(githubService, times(1)).getUserRepositories(anyString(), any());
        verify(repositoryDao, times(0)).addAll(any());
    }

    @Test
    public void shouldCloseRealmWhenDestroy() throws Exception {
        repositoriesRepo.onDestroy();
        verify(repositoryDao, times(1)).closeRealm();
    }
}