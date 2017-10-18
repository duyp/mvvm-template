package com.duyp.architecture.mvvm.data;

import android.app.NotificationManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.model.User;
import com.duyp.architecture.mvvm.utils.Events;
import com.duyp.architecture.mvvm.utils.qualifier.ApplicationContext;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Duy Pham on 5/19/17.
 * Injected class for managing User by using User Component
 */

@Singleton
public class UserManager {

    // debug tag
    private static final String TAG = "UserManager";

    protected Context mContext;

    // user data store
    protected final UserDataStore mUserDataStore;

    protected EventBus mEventBus;

    protected GithubService mGithubService;

    @Inject
    public UserManager(@ApplicationContext Context context, UserDataStore userDataStore, GithubService service) {
        this.mContext = context;
        this.mUserDataStore = userDataStore;
        this.mEventBus = EventBus.getDefault();
        this.mGithubService = service;
    }

    @NonNull
    public GithubService getGithubService() {
        return mGithubService;
    }

    @NonNull
    public UserDataStore getUserRepo() {
        return mUserDataStore;
    }


    /**
     * Start user session in test environment
     * @param token user token
     */
    public LiveData<User> startUserSession(User user, String token) {
        Log.d(TAG, "User session started!");
        mUserDataStore.setUserToken(token);
        return mUserDataStore.setUser(user);
    }

    /**
     * Stop user session (clear user data from both memory and shared pref)
     */
    private void stopUserSession() {
        mUserDataStore.clearUser();
        Log.d(TAG, "User session stopped!");
    }

    public boolean isUserSessionStarted() {
        return mUserDataStore.getUserLiveData().getValue() != null;
    }

    /**
     * @return current user data
     */
    @Nullable
    public User getCurrentUser() {
        return mUserDataStore.getUser();
    }

    /**
     * Check for current user session has started or not
     * Check if has saved user -> start new session
     * @return true if user session started or be able to start new user session (has saved user)
     */
    public boolean checkForSavedUserAndStartSessionIfHas() {
        if (isUserSessionStarted()) {
            return true;
        }
        User savedUser = mUserDataStore.getUser();
        if (savedUser != null) {
            startUserSession(savedUser, mUserDataStore.getUserToken());
            return true;
        }
        return false;
    }

    /**
     * Update user data by new user if are the same
     * @param newUser new User data
     * @return true if user updated
     */
    public boolean updateUserIfEquals(User newUser) {
        return mUserDataStore.updateUserIfEquals(newUser);
    }

    /*
    * Refresh current user account from server
    * All errors will be ignored
     */
    public void refreshUser() {
        if (isUserSessionStarted()) {
            // TODO: 10/18/17 refresh user by calling rest api
            Log.d(TAG, "Refreshing user...");
            // noinspection ConstantConditions
            // ApiUtils.makeRequest(mUserComponent.getUserService().updateUser(), false, this::updateUserIfEquals);
        }
    }

    /**
     * Logout user
     */
    public void logout() {
        reset();

        // send logout broadcast (tutor service will stopSelf)
        mEventBus.post(new Events.LogoutEvent());

        // navigate user to Login Page
    }

    public void reset() {
        logOutApi();

        // stop user session
        stopUserSession();

        // cancel all notification
        NotificationManager notifManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notifManager != null) {
            notifManager.cancelAll();
        }
    }

    /**
     * Logout by our back-end api
     */
    private void logOutApi() {
        // TODO: 10/18/17 call logout api
    }
}