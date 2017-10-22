package com.duyp.architecture.mvvm.data;

import android.arch.lifecycle.LiveData;

import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
/**
 * Created by duypham on 10/18/17.
 *
 */
@RunWith(PowerMockRunner.class)
public class UserManagerTest extends BaseDataModuleTest {

    @Inject
    UserManager userManager;

    @Override
    protected void inject(TestComponent component) throws Exception {
        component.inject(this);
    }

    @Test
    public void beAbleToMockData() {
        assertThat(userManager.getUserRepo(), is(mockUserDataStore));
    }

    @Test

    public void startUserSession() throws Exception {
        initUserSession();

        when(mockUserDataStore.setUser(mUser)).thenReturn(mMockUserLiveData);
        LiveData<User> userLiveData = userManager.startUserSession(mUser, "abcd");

        verify(mockUserDataStore).setUser(mUser);
        verify(mockUserDataStore).setUserToken("abcd");

        assertThat(userLiveData, is(mMockUserLiveData));
    }

    @Test
    public void isUserSessionStartedSuccess() throws Exception {
        startUserSession();

        assertThat(userManager.isUserSessionStarted(), is(true));
    }

    @Test
    public void isUserSessionNotStartedYet() throws Exception {
        assertThat(userManager.isUserSessionStarted(), is(false));
    }

    @Test
    public void getCurrentUserWhenUserSessionNotStartedYet() throws Exception {
        assertThat(userManager.getCurrentUser(), is(nullValue()));
    }

    @Test
    public void getCurrentUserWhenUserSessionHasStarted() throws Exception {
        startUserSession();

        assertThat(userManager.getCurrentUser(), is(mUser));
    }

    @Test
    public void checkForSavedUserAndStartSessionIfHasInCaseOfNoSavedUser() throws Exception {
        assertThat(userManager.checkForSavedUserAndStartSessionIfHas(), is(false));
    }

    @Test
    public void checkForSavedUserAndStartSessionIfHasInCaseOfSessionStartedAlready() throws Exception {
        startUserSession();

        assertThat(userManager.checkForSavedUserAndStartSessionIfHas(), is(true));
    }

    @Test
    public void checkForSavedUserAndStartSessionIfHasInCaseOfSessionNotStartedButHasSavedUser() throws Exception {
        when(mMockUserLiveData.getValue()).thenReturn(null); // make sure isSessionStarted return false

        when(mockUserDataStore.getUser()).thenReturn(mUser); // assume we have a saved user
        when(mockUserDataStore.getUserToken()).thenReturn("abcd");

        assertThat(userManager.checkForSavedUserAndStartSessionIfHas(), is(true));

        // verify we should start user session
        verify(mockUserDataStore, times(1)).setUser(mUser);
        verify(mockUserDataStore, times(1)).setUserToken("abcd");
    }

    @Test
    public void updateUserIfEquals() throws Exception {
        User sampleUser = sampleUser(mUser.getId(), "abcd");

        userManager.updateUserIfEquals(sampleUser);
        verify(mockUserDataStore).updateUserIfEquals(sampleUser);
    }

    @Test
    public void logout() throws Exception {
        userManager.logout();

        verify(mockUserDataStore).clearUser();
    }
}