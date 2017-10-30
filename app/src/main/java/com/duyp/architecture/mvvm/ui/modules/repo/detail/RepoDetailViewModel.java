package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.androidutils.rx.Rx;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.RealmString;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.repository.RepoDetailRepo;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.TopicsAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import lombok.Getter;
import retrofit2.Response;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailViewModel extends BaseViewModel{

    private final RepoDetailRepo repoDetailRepo;

    @Getter private LiveRealmObject<RepoDetail> data;
    private String owner;
    private String repoName;

    @Getter private final MutableLiveData<Boolean> watchStatus = new MutableLiveData<>();
    @Getter private final MutableLiveData<Boolean> starStatus = new MutableLiveData<>();
    @Getter private final MutableLiveData<Boolean> folkStatus = new MutableLiveData<>();
    @Getter private final MutableLiveData<List<String>> topics = new MutableLiveData<>();

    private final RepoService repoService;

    @Inject
    public RepoDetailViewModel(UserManager userManager, RepoDetailRepo repo, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
        this.repoDetailRepo = repo;
        watchStatus.setValue(null);
        starStatus.setValue(null);
        folkStatus.setValue(null);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass repo data to use this fragment");
        }
        data = repoDetailRepo.initRepo(bundle.getParcelable(BundleConstant.EXTRA));
        owner = data.getData().getOwner().getLogin();
        repoName = data.getData().getName();
        topics.setValue(Rx.map(data.getData().getTopics(), RealmString::getValue));
        new Handler(Looper.myLooper()).postDelayed(this::refresh, 300);
        new Handler(Looper.myLooper()).postDelayed(() -> {
            checkStarred();
            checkWatched();
        }, 100);
    }

    public void refresh() {
        execute(false, repoDetailRepo.getRepo(owner, repoName), null);
    }

    private void checkWatched() {
        execute(false, repoService.isWatchingRepo(owner, repoName), repoSubscriptionModel -> {
               watchStatus.setValue(repoSubscriptionModel.isSubscribed());
        }, errorEntity -> {
            watchStatus.setValue(false);
        });
    }

    private void checkStarred() {
        execute(false, repoService.checkStarring(owner, repoName), booleanResponse -> {
            starStatus.setValue(booleanResponse.code() == 204);
        }, errorEntity -> {
            starStatus.setValue(false);
        });
    }

    public void starRepoClick() {
        if (starStatus.getValue() != null) {
            boolean star = !starStatus.getValue();
            repoDetailRepo.updateStarred(star);
            starStatus.setValue(star);
            execute(false, star ? repoService.starRepo(owner, repoName) : repoService.unstarRepo(owner, repoName), booleanResponse -> {
                if (booleanResponse.code() != 204) {
                    repoDetailRepo.updateStarred(!star);
                    starStatus.setValue(!star); // reverse if error
                }
            }, errorEntity -> {
                repoDetailRepo.updateStarred(!star);
                starStatus.setValue(!star); // reverse if error
            });
        }
    }

    public void watchRepoClick() {
        if (watchStatus.getValue() != null) {
            boolean watch = !watchStatus.getValue();
            repoDetailRepo.updateWatched(watch);
            watchStatus.setValue(watch);
            execute(false, watch ? repoService.watchRepo(owner, repoName) : repoService.unwatchRepo(owner, repoName), booleanResponse -> {
                if (booleanResponse.code() != 204) {
                    repoDetailRepo.updateWatched(!watch);
                    watchStatus.setValue(!watch); // reverse if error
                }
            }, errorEntity -> {
                repoDetailRepo.updateWatched(!watch);
                watchStatus.setValue(!watch); // reverse if error
            });
        }
    }

    public void forkRepoClick() {

    }

    public void pinRepoClick() {

    }

    @Nullable
    public RepoDetail getRepoDetail() {
        if (data != null) {
            return data.getData();
        }
        return null;
    }

//    public enum State {
//        NONE,
//        TRUE,
//        FALSE
//    }
}
