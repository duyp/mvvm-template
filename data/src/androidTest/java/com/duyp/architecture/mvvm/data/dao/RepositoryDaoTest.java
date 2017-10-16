package com.duyp.architecture.mvvm.data.dao;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.dagger.TestAppComponent;
import com.duyp.architecture.mvvm.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.data.model.Repository;
import com.duyp.architecture.mvvm.data.model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by duypham on 9/21/17.
 *
 */

public class RepositoryDaoTest extends BaseDaoTest {

    private RepositoryDao repositoryDao;

    @Override
    public void inject(TestAppComponent appComponent) {
        appComponent.inject(this);
        repositoryDao = realmDatabase.newRepositoryDao();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        repositoryDao.closeRealm();
    }

    @Test
    public void addAndGetRepository() throws Exception{
        Repository repository = sampleRepository(1L, sampleUser(11L));
        repositoryDao.addOrUpdate(repository);

        Repository savedRepo = repositoryDao.getById(repository.getId()).getData();

        assertThat(savedRepo.getFullName(), equalTo(repository.getFullName()));
    }

    @Test
    public void addRepositories() throws Exception{
        repositoryDao.addAll(sampleRepoList(20, sampleUser(1L)));

        assertThat(repositoryDao.getAll().getData().size(), equalTo(20));
    }

    @Test
    public void ownerRepositories() throws Exception {
        User owner1 = sampleUser(1L, "duy1");
        repositoryDao.addAll(sampleRepoList(0, 10, owner1));

        User owner2 = sampleUser(2L, "duy2");
        repositoryDao.addAll(sampleRepoList(10, 30, owner2));

        assertThat(repositoryDao.getUserRepositories(owner1.getLogin()).getData().size(), equalTo(10));
        assertThat(repositoryDao.getUserRepositories(owner2.getLogin()).getData().size(), equalTo(30));
        assertThat(repositoryDao.getUserRepositories("duyp").getData().size(), equalTo(0));
    }

    @Test
    public void memberRepositories() throws Exception {
        String memberLogin = "duy_member";
        User owner = sampleUser(1L, "owner");
        List<Repository> list = sampleRepoList(20, owner);
        for (Repository repository : list) {
            repository.setMemberLoginName(memberLogin);
        }

        // distraction
        list.addAll(sampleRepoList(20, 30, owner));

        // distraction
        list.addAll(sampleRepoList(50, 10, sampleUser(2L, "justSomeDistractedUsers")));

        // add all items to db
        repositoryDao.addAll(list);

        // we have total 60 added items
        assertThat(repositoryDao.getAll().getData().size(), equalTo(60));

        // 20 items belong to duy_member
        assertThat(repositoryDao.getUserRepositories(memberLogin).getData().size(), equalTo(20));
    }

    @Test
    public void searchRepoByName() throws Exception {
        List<Repository> repositories = new ArrayList<>();
        User owner = sampleUser(2L, "user");
        for (long i = 0; i < 100; i++) {
            Repository repo = sampleRepository(i, owner);
            repo.setName("test" + i);
            repositories.add(repo);
        }

        repositoryDao.addAll(repositories);

        // distraction
        repositoryDao.addAll(sampleRepoList(200, 200, owner));

        assertThat(repositoryDao.getRepositoriesWithNameLike("test").getData().size(), equalTo(100));

        assertThat(repositoryDao.getRepositoriesWithNameLike("test0").getData().size(), equalTo(1));
        assertThat(repositoryDao.getRepositoriesWithNameLike("test1").getData().size(), equalTo(11));
        assertThat(repositoryDao.getRepositoriesWithNameLike("test2").getData().size(), equalTo(11));
        assertThat(repositoryDao.getRepositoriesWithNameLike("test3").getData().size(), equalTo(11));
        assertThat(repositoryDao.getRepositoriesWithNameLike("test30").getData().size(), equalTo(1));
        assertThat(repositoryDao.getRepositoriesWithNameLike("st30").getData().size(), equalTo(1));
        assertThat(repositoryDao.getRepositoriesWithNameLike("st3").getData().size(), equalTo(11));
    }

    private List<Repository> sampleRepoList(int size, @NonNull User owner) {
        return sampleRepoList(0, size, owner);
    }

    private List<Repository> sampleRepoList(int startIndex, int size, @NonNull User owner) {
        List<Repository> list = new ArrayList<>();
        for (int i = startIndex; i < startIndex + size; i++) {
            list.add(sampleRepository((long)i, owner));
        }
        return list;
    }
}
