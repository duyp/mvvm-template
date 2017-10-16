package com.duyp.architecture.mvvm.data.local.dao;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.BaseRealmTest;
import com.duyp.architecture.mvvm.data.model.Issue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.internal.Collection;

import static com.duyp.architecture.mvvm.data.TestUtils.sampleIssueList;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static com.duyp.architecture.mvvm.data.TestUtils.sampleIssue;
/**
 * Created by duypham on 10/16/17.
 *
 */
@RunWith(RobolectricTestRunner.class)
public class IssueDaoTest extends BaseRealmTest {

    private static final Long REPO_ID = 100L;

    IssueDao issueDao;
    Realm realm;

    RealmQuery<Issue> mockQuery;
    RealmResults<Issue> mockResults;

    private final List<Issue> issues = sampleIssueList(20, REPO_ID);

    @Override
    public void setup() {
        super.setup();
        testComponent.inject(this);
        issueDao = realmDatabase.newIssueDao();
        realm = issueDao.getRealm();
        mockQuery = initRealmQuery(realm, Issue.class);
        mockResults = initFindAllSorted(mockQuery);

        when(mockQuery.findFirst()).thenReturn(issues.get(0));
        when(mockQuery.equalTo(anyString(), anyLong())).thenReturn(mockQuery);

        when(mockQuery.findAll()).thenReturn(mockResults);
        when(mockQuery.findAllSorted(anyString(), Matchers.any())).thenReturn(mockResults);

        // The for(...) loop in Java needs an iterator, so we're giving it one that has items,
        // since the mock RealmResults does not provide an implementation. Therefore, anytime
        // anyone asks for the RealmResults Iterator, give them a functioning iterator from the
        // ArrayList of Persons we created above. This will allow the loop to execute.
    }

    @Test
    public void shouldBeAbleToInjectRealmDatabase() {
        assertThat(realm, is(notNullValue()));
    }

    @Test
    public void shouldBeAbleToQuery() {
        assertThat(realm.where(Issue.class), is(mockQuery));
        assertThat(mockQuery.findAll(), is(mockResults));
    }

    @Test
    public void getRepoIssuesTest() {
        RealmResults<Issue> results = mockRealmResults();
        when(results.size()).thenReturn(issues.size());
//        when(mockResults.size()).thenReturn(issues.size());
//        when(mockResults.iterator()).thenReturn(issues.iterator());

//        LiveRealmResults<Issue> results = issueDao.getRepoIssues(12L);
//        assertThat(results.getData(), is(mockResults));
    }
}