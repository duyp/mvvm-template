package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.codeviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.evernote.android.state.State;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 11/2/17.
 *
 */

@Getter
public class CodeViewerViewModel extends BaseViewModel {

    String url;
    String htmlUrl;

    @Inject
    public CodeViewerViewModel(UserManager userManager) {
        super(userManager);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString(BundleConstant.EXTRA, "Url is null");
            htmlUrl = bundle.getString(BundleConstant.EXTRA_TWO);
        }
    }
}
