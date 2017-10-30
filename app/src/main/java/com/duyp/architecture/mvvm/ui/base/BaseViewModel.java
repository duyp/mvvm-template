package com.duyp.architecture.mvvm.ui.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.androidutils.rx.functions.PlainAction;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.ErrorEntity;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.RestHelper;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.SafeMutableLiveData;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

/**
 * Created by duypham on 10/19/17.
 * Base class that provides base implementation for handling loading status, and hole api request disposable.
 * All api requests called from {@link com.duyp.architecture.mvvm.data.source.SimpleRemoteSourceMapper}
 * will be added to a composite disposable which is disposed when view model is cleared
 *
 * Reference: https://developer.android.com/topic/libraries/architecture/viewmodel.html
 */

public abstract class BaseViewModel extends ViewModel {

    protected final String TAG;

    private boolean isFirstTimeUiCreate = true;

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Getter
    @NonNull
    protected final SafeMutableLiveData<State> stateLiveData = new SafeMutableLiveData<>();

    @Getter
    protected final UserManager userManager;

    protected NavigatorHelper navigatorHelper;

    public BaseViewModel(UserManager userManager) {
        this.userManager = userManager;
        TAG = this.getClass().getSimpleName();
    }

    /**
     * called after fragment / activity is created with input bundle arguments
     * @param bundle argument data
     */
    @CallSuper
    public void onCreate(@Nullable Bundle bundle, NavigatorHelper navigatorHelper) {
        Log.d(TAG, "onCreate: UI creating...");
        this.navigatorHelper = navigatorHelper;
        if (isFirstTimeUiCreate) {
            onFirsTimeUiCreate(bundle);
            isFirstTimeUiCreate = false;
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     * @param bundle
     */
    protected abstract void onFirsTimeUiCreate(@Nullable Bundle bundle);

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    public void onDestroyView() {
        navigatorHelper = null;
    }

    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
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

    /**
     * Return true if given user is my user
     * @param userLogin user to be checked
     * @return is me or not
     */
    public boolean isMe(String userLogin) {
        return getUserManager().getCurrentUser() != null && getUserManager().getCurrentUser().equals(userLogin);
    }

    public void addDisposable(@NonNull Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    /**
     * Add and execute an resource flowable created by
     * {@link RestHelper#createRemoteSourceMapper(Single, PlainConsumer)}
     * Loading, error, success status will be updated automatically via {@link #stateLiveData} which should be observed
     * by fragments / activities to update UI appropriately
     * @param showProgress true if should show progress when executing, false if not
     * @param resourceFlowable flowable resource, see {@link com.duyp.architecture.mvvm.data.source.SimpleRemoteSourceMapper}
     * @param responseConsumer consume response data
     * @param <T> type of response
     */
    protected  <T> void execute(boolean showProgress, Flowable<Resource<T>> resourceFlowable,
                               @Nullable PlainConsumer<T> responseConsumer) {
        Disposable disposable = resourceFlowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(resource -> {
                    if (resource != null) {
                        Log.d("source", "addRequest: resource changed: " + resource.toString());
                        if (resource.data != null && responseConsumer != null) {
                            responseConsumer.accept(resource.data);
                        }
                        if (resource.state.getStatus() == Status.LOADING && !showProgress) {
                            // do nothing if progress showing is not allowed
                        } else {
                            stateLiveData.setValue(resource.state);

                            if (!InputHelper.isEmpty(resource.state.getMessage())) {
                                // if state has a message, after show it, we should reset to prevent
                                // message will still be shown if fragment / activity is rotated (re-observe state live data)
                                new Handler().postDelayed(() -> stateLiveData.setValue(State.error(null)), 100);
                            }
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    protected <T> void execute(boolean showProgress, Single<T> request, @NonNull PlainConsumer<T> responseConsumer) {
        execute(showProgress, request, responseConsumer, null);
    }

    protected <T> void execute(boolean showProgress, Single<T> request,
                               @NonNull PlainConsumer<T> responseConsumer,
                               @Nullable PlainConsumer<ErrorEntity> errorConsumer) {
        // execute(showProgress, RestHelper.createRemoteSourceMapper(request, null), responseConsumer);
        if (showProgress) {
            stateLiveData.setValue(State.loading(null));
        }
        Disposable disposable = RestHelper.makeRequest(request, true, response -> {
            stateLiveData.setValue(State.success(null));
            responseConsumer.accept(response);
        }, errorEntity -> {
            if (errorConsumer != null) {
                errorConsumer.accept(errorEntity);
            }
            stateLiveData.setValue(State.error(errorEntity.getMessage()));

            if (!InputHelper.isEmpty(errorEntity.getMessage())) {
                // if state has a message, after show it, we should reset to prevent
                // message will still be shown if fragment / activity is rotated (re-observe state live data)
                new Handler().postDelayed(() -> stateLiveData.setValue(State.error(null)), 100);
            }
        });
        mCompositeDisposable.add(disposable);
    }
}