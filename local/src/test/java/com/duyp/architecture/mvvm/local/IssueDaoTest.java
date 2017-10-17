package com.duyp.architecture.mvvm.local;

import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.model.Issue;

import org.junit.Test;

import java.util.List;

import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.duyp.architecture.mvvm.utils.TestUtils.sampleIssueList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;

/**
 * Created by duypham on 10/16/17.
 *
 */
public class IssueDaoTest extends BaseRealmTest {

    private static final Long REPO_ID = 100L;

    IssueDao issueDao;

    RealmQuery<Issue> mockQuery;
    RealmResults<Issue> mockResults;

    private final List<Issue> issues = sampleIssueList(20, REPO_ID);

    @Override
    public void setup() throws Exception{
        super.setup();
        testComponent.inject(this);
        issueDao = realmDatabase.newIssueDao();

        mockQuery = initRealmQuery(mockRealm, Issue.class);
        mockResults = initFindAllSorted(mockQuery, issues);

        when(mockQuery.findFirst()).thenReturn(issues.get(0));
    }

    @Test
    public void shouldBeAbleToInjectRealmDatabase() {
        assertThat(mockRealm, is(issueDao.getRealm()));
    }

    @Test
    public void shouldBeAbleToQuery() {
        assertThat(mockRealm.where(Issue.class), is(mockQuery));
        assertThat(mockQuery.findAll(), is(mockResults));
    }

    @Test
    public void getRepoIssuesTest() {

        issueDao.getRepoIssues(1L);

        verify(mockQuery).equalTo(anyString(), anyLong());
        verify(mockQuery).findAllSorted("createdAt", Sort.DESCENDING);

        assertThat(mockResults.size(), is(issues.size()));
    }
}