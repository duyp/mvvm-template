package com.duyp.architecture.mvvm.data.repository;

import android.os.Bundle;

import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.daos.ViewerFileDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.ViewerFile;
import com.duyp.architecture.mvvm.data.provider.markdown.MarkDownProvider;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.InputHelper;

import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class ViewerFileRepo extends BaseRepo<ViewerFile, ViewerFileDao> {

    private String downloadedStream;
    private boolean isMarkdown;
    private boolean isRepo;
    private boolean isImage;
    private String url;
    private String htmlUrl;

    @Inject
    public ViewerFileRepo(UserManager userManager, ViewerFileDao dao) {
        super(userManager, dao);
    }

    public void init(boolean isRepo, String url, String htmlUrl) {
        this.isRepo = isRepo;
        this.url = url;
        this.htmlUrl = htmlUrl;
    }
}
