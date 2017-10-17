package com.duyp.architecture.mvvm.local.base_test;

import com.duyp.architecture.mvvm.local.BaseRealmTest;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by duypham on 10/17/17.
 *
 */

public class BaseRealmDaoTest extends BaseRealmTest {

    private TestDao dao;

    private RealmQuery<TestModel> query;

    @Override
    public void setup() throws Exception {
        super.setup();
        testComponent.inject(this);
        dao = realmDatabase.newTestDao();

        query = initRealmQuery(mockRealm, TestModel.class);
        doNothing().when(mockRealm).beginTransaction();
        doNothing().when(mockRealm).commitTransaction();
        doNothing().when(mockRealm).insertOrUpdate(Matchers.any(RealmModel.class));
        // noinspection unchecked
        doNothing().when(mockRealm).insertOrUpdate(Matchers.any(Collection.class));
    }

    @Test
    public void shouldBeAbleToMockRealmInstance() {
        assertThat(dao.getRealm(), is(mockRealm));
    }

    @Test
    public void test_GetAll() throws Exception {
        List<TestModel> models = sampleModelList(20);
        RealmResults<TestModel> results = initFindAllSorted(query, models);

        assertThat(dao.getAll().getData(), is(results));
        assertThat(results.size(), is(20));
    }

    @Test
    public void test_getById() {
        TestModel model = sampleModel(1L);
        when(query.findFirst()).thenReturn(model);

        assertThat(dao.getById(2L).getData(), is(model));
    }

    @Test
    public void test_add() {
        TestModel model = sampleModel(1L);
        dao.addOrUpdate(model);
        verifyRealmTransaction();
        verify(mockRealm, times(1)).insertOrUpdate(model);
    }

    @Test
    public void test_AddAll() {
        List<TestModel> models = sampleModelList(20);
        dao.addAll(models);

        verifyRealmTransaction();
        verify(mockRealm, times(1)).insertOrUpdate(models);
    }

    @Test
    public void test_deleteItem() throws Exception {
        RealmResults<TestModel> results = initFindAllSorted(query, null);

        when(results.deleteAllFromRealm()).thenReturn(true);

        dao.delete(1L);

        verifyRealmTransaction();
        verify(results).deleteAllFromRealm();
    }

    @Test
    public void test_deleteAll() throws Exception{
        doNothing().when(mockRealm).delete(TestModel.class);

        dao.deleteAll();

        verifyRealmTransaction();
        verify(mockRealm).delete(TestModel.class);
    }

    @Test
    public void shouldBeAbleToCloseRealm() {
        dao.closeRealm();
        verify(mockRealm,times(1)).close();
    }

    private static TestModel sampleModel(Long id) {
        TestModel model = new TestModel();
        model.id = id;
        model.name = "test model";
        model.age = 10;
        return model;
    }

    private static List<TestModel> sampleModelList(int size) {
        List<TestModel> models = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            models.add(sampleModel((long)i));
        }
        return models;
    }
}
