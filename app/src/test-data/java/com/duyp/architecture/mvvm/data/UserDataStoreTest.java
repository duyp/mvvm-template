package com.duyp.architecture.mvvm.data;


import android.arch.lifecycle.MutableLiveData;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.data.local.Constants;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.UserDetailDao;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import static com.duyp.architecture.mvvm.test_utils.ModelTestUtils.sampleUser;
/**
 * Created by duypham on 10/18/17.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Gson.class)
public class UserDataStoreTest {

    private UserDataStore userDataStore;

    @Mock
    private CustomSharedPreferences mockSharedPreferences;

    private Gson gson; // final class, should be mocked by PowerMockito

    @Mock
    private MutableLiveData<User> mockUserLiveData;

    private final User sampleUser = sampleUser(1L, "duyp");

    @Before
    public void setup() {
        mockStatic(Gson.class);
        gson = mock(Gson.class);
        userDataStore = new UserDataStore(mockSharedPreferences, gson);
//        Whitebox.setInternalState(userDataStore, "mUserLiveData", mockUserLiveData);
    }

    @Test
    public void beAbleToMockData() {
        assertThat(userDataStore, is(notNullValue()));
        assertThat(userDataStore.getSharedPreferences(), is(mockSharedPreferences));
        assertThat(userDataStore.getUserLiveData(), is(mockUserLiveData));
    }

    @Test
    public void setUser() throws Exception {
        userDataStore.setUser(sampleUser);
        verifySetUserInteractionWithSharedPreferenceAndDao(sampleUser);
    }

    @Test
    public void getUserInCaseOfNoUserExists() throws Exception {
        assertThat(userDataStore.getUser(), is(nullValue()));
    }

    @Test
    public void getUserInCaseOfHasUserInMemory() throws Exception {
        when(mockUserLiveData.getValue()).thenReturn(sampleUser); // make sure that userLiveData work
        assertThat(userDataStore.getUser(), is(sampleUser));
    }

    @Test
    public void getUserInCaseOfHasSavedUserInSharedPreference() {
        String notNullString = "not null string";
        when(mockSharedPreferences.getPreferences(Constants.PREF_USER, "")).thenReturn(notNullString);
        when(gson.fromJson(notNullString, User.class)).thenReturn(sampleUser);

        assertThat(userDataStore.getUser(), is(sampleUser));
    }

    @Test
    public void updateUserIfEqualsInCaseOfNotEquals() throws Exception {
        getUserInCaseOfHasUserInMemory(); // init an existed user

        User testUser = sampleUser(111L, "abcd");

        boolean set = userDataStore.updateUserIfEquals(testUser);

        verifyZeroInteractions(mockSharedPreferences);
        assertThat(set, is(false));
    }

    @Test
    public void updateUserIfEqualsInCaseOfEquals() throws Exception {
        getUserInCaseOfHasUserInMemory();

        User testUser = sampleUser(sampleUser.getId(), "abcd");

        boolean set = userDataStore.updateUserIfEquals(testUser);

        verifySetUserInteractionWithSharedPreferenceAndDao(testUser);
        assertThat(set, is(true));
    }

    @Test
    public void setUserToken() throws Exception {
        userDataStore.setUserToken("abc");
        verify(mockSharedPreferences).setPreferences(Constants.PREF_USER_TOKEN, "abc");
    }

    @Test
    public void getUserToken() throws Exception {
        when(mockSharedPreferences.getPreferences(Constants.PREF_USER_TOKEN, "")).thenReturn("abc");
        assertThat(userDataStore.getUserToken(), is("abc"));
    }

    @Test
    public void clearUserShouldClearUserInMemoryAndAllSharedPreferenceAndRealm() throws Exception {
        getUserInCaseOfHasUserInMemory(); // make sure we had an user;

        String emptyString = "";

        userDataStore.clearUser();

        verify(mockSharedPreferences).setPreferences(Constants.PREF_USER_TOKEN, emptyString);
        verify(mockSharedPreferences).setPreferences(Constants.PREF_USER_ID, UserDataStore.USER_ID_NOT_EXIST);
        verify(mockSharedPreferences).setPreferences(Constants.PREF_USER, emptyString);

        verify(mockUserLiveData).setValue(null);
    }


    private void verifySetUserInteractionWithSharedPreferenceAndDao(User userToBeSet) {
        verify(mockSharedPreferences).setPreferences(any(), any());
//        verify(userDao).addOrUpdate(userToBeSet);
        verify(mockUserLiveData).setValue(userToBeSet);
    }
}
