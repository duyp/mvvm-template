package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.BaseDataModuleTest;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Repo;

import org.junit.Test;

import javax.inject.Inject;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static com.duyp.architecture.mvvm.test_utils.RealmTestUtils.initLiveRealmObject;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.errorResponse;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleRepository;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.successResponse;

/**
 * Created by duypham on 10/18/17.
 *
 */
public class RepoDetailRepoTest extends BaseDataModuleTest {

    @Inject
    RepositoryDetailRepo repositoryDetailRepo;

    private RepositoryDao repositoryDao;

    private final Repo mRepo = sampleRepository(1L, sampleUser(1L));

    @Override
    protected void inject(TestComponent component) throws Exception {
        repositoryDao = mock(RepositoryDao.class);
        when(mockRealmDatabase.newRepositoryDao()).thenReturn(repositoryDao);
        component.inject(this);
    }

    @Test
    public void beAbleToMockAndInject() {
        assertThat(repositoryDetailRepo, is(notNullValue()));
        assertThat(repositoryDetailRepo.getMRepositoryDao(), is(repositoryDao));
    }

    @Test
    public void shouldCloseRealmWhenDestroy() throws Exception {
        repositoryDetailRepo.onDestroy();
        verify(repositoryDao).closeRealm();
    }

    @Test
    public void initRepo() throws Exception {

        // noinspection unchecked
        LiveRealmObject<Repo> liveRealmObject = initLiveRealmObject(mRepo);

        when(repositoryDao.getById(mRepo.getId())).thenReturn(liveRealmObject);

        repositoryDetailRepo.initRepo(mRepo.getId());

        assertThat(repositoryDetailRepo.getData(), is(liveRealmObject));
        assertThat(repositoryDetailRepo.getData().getData(), is(mRepo));
    }

    @Test
    public void getRepositorySuccess() throws Exception {
        initRepo();

        mRepo.setMemberLoginName("abcd");

        Repo repo = sampleRepository(mRepo.getId(), mRepo.getOwner());

        when(githubService.getRepository(mRepo.getOwner().getLogin(), mRepo.getName()))
                .thenReturn(successResponse(repo));

        repositoryDetailRepo.getRepository().subscribe();

        verify(repositoryDao, times(1)).addOrUpdate(repo);
        assertThat(repo.getMemberLoginName(), is("abcd"));
    }

    @Test
    public void getRepositoryError() throws Exception {
        initRepo();

        when(githubService.getRepository(mRepo.getOwner().getLogin(), mRepo.getName()))
                .thenReturn(errorResponse(435));

        verify(repositoryDao, times(0)).addOrUpdate(any());
    }

}