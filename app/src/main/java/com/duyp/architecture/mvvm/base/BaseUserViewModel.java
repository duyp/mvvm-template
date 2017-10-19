package com.duyp.architecture.mvvm.base;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.duyp.androidutils.rx.functions.PlainAction;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.UserManager;
import com.duyp.architecture.mvvm.model.User;

import lombok.Getter;

/**
 * Created by duypham on 10/19/17.
 * Base ViewModel class in user scope (for activities and fragments after user logging in)
 * For example: User Profile fragment, User repositories fragment....
 * It holes an instance of {@link UserManager} which is provided by dagger to access and manage current user
 */

public abstract class BaseUserViewModel extends BaseViewModel{

    @NonNull
    @Getter
    protected final UserManager userManager;

    public BaseUserViewModel(@NonNull UserManager userManager) {
        super();
        this.userManager = userManager;
    }

    /**
     * ensure we are in user scope (has saved user - user logged in)
     * should be called when activity / fragment is created
     * @param userConsumer consume user live data which would be observed to update UI
     * @param onError will be run if user data isn't exist
     *               (show no login button, or navigate user to login page...)
     */
    public void ensureInUserScope(PlainConsumer<LiveData<User>> userConsumer, PlainAction onError) {
        if (userManager.checkForSavedUserAndStartSessionIfHas()) {
            userConsumer.accept(userManager.getUserRepo().getUserLiveData());
        } else {
            onError.run();
        }
    }
}
