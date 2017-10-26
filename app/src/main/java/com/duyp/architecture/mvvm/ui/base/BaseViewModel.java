package com.duyp.architecture.mvvm.ui.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.androidutils.rx.functions.PlainAction;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
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
    public void onCreate(@Nullable Bundle bundle) {

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
     * Initialize navigator helper
     * @param helper not null value
     */
    public void initNavigatorHelper(@NonNull NavigatorHelper helper) {
        this.navigatorHelper = helper;
    }

    /**
     * Add and execute an resource flowable created by
     * {@link RestHelper#createRemoteSiourceMapper(boolean, Single, RestHelper.OnSaveCallResult)}
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
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    /**
     * see {@link #execute(boolean, Flowable, PlainConsumer)}
     */
    protected  <T> void execute(Flowable<Resource<T>> resourceFlowable, PlainConsumer<T> response) {
        execute(true, resourceFlowable, response);
    }

    protected <T> void execute(boolean showProgress, Single<T> request,
                               @Nullable PlainConsumer<T> responseConsumer) {
        Disposable disposable = RestHelper.makeRequest(request, true, tApiResponse -> {

        }, errorEntity -> {

        });
        mCompositeDisposable.add(disposable);
    }
}