package com.duyp.architecture.mvvm.data.repository;


import com.duyp.architecture.mvvm.data.BaseRealmTest;
import com.duyp.architecture.mvvm.data.local.dao.IssueDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(RobolectricTestRunner.class)
public class IssuesRepoTest extends BaseRealmTest {

    IssueDao issueDao;

    @Override
    public void setup() {
        super.setup();
        testComponent.inject(this);
        issueDao = realmDatabase.newIssueDao();
    }

    @Test
    public void shouldBeAbleToInjectRealmDatabase() {
        assertThat(realmDatabase, is(notNullValue()));
        assertThat(issueDao, is(notNullValue()));
    }
}