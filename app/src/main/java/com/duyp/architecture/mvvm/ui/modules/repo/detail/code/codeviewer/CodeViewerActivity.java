package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.codeviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;

import com.annimon.stream.Objects;
import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.provider.scheme.LinkParserHelper;
import com.duyp.architecture.mvvm.helper.ActivityHelper;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.DownloadHelper;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.RestHelper;
import com.duyp.architecture.mvvm.ui.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.prettifier.ViewerFragment;
import com.evernote.android.state.State;

import javax.inject.Inject;

/**
 * Created by duypham on 11/2/17.
 *
 */

public class CodeViewerActivity extends BaseActivity {

    @State String url;
    @State String htmlUrl;

    @Inject
    Navigator navigator;

    @Override
    public int getLayout() {
        return R.layout.container_with_toolbar;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Intent intent = Objects.requireNonNull(getIntent(), "Intent is null");
            Bundle bundle = Objects.requireNonNull(intent.getExtras());
            //noinspection ConstantConditions
            url = Objects.requireNonNull(bundle.getString(BundleConstant.EXTRA), "Url is null");
            htmlUrl = bundle.getString(BundleConstant.EXTRA_TWO);
            navigator.replaceFragment(R.id.container, ViewerFragment.newInstance(url, htmlUrl), ViewerFragment.TAG, null);
        }
        String title = Uri.parse(url).getLastPathSegment();
        setTitle(title);
        if (toolbar != null) toolbar.setSubtitle(MimeTypeMap.getFileExtensionFromUrl(url));
        setTaskName(title);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download_browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (InputHelper.isEmpty(url)) return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.viewAsCode) {
            ViewerFragment viewerFragment = (ViewerFragment) AppHelper.getFragmentByTag(getSupportFragmentManager(), ViewerFragment.TAG);
            if (viewerFragment != null) {
                viewerFragment.onViewAsCode();
            }
            return true;
        } else if (item.getItemId() == R.id.download) {
            if (ActivityHelper.checkAndRequestReadWritePermission(this)) {
                DownloadHelper.downloadFile(this.getApplicationContext(), url);
            }
            return true;
        } else if (item.getItemId() == R.id.browser) {
            ActivityHelper.openChooser(this, htmlUrl != null ? htmlUrl : url);
            return true;
        } else if (item.getItemId() == R.id.copy) {
            AppHelper.copyToClipboard(this, htmlUrl != null ? htmlUrl : url);
            return true;
        } else if (item.getItemId() == R.id.share) {
            ActivityHelper.shareUrl(this, htmlUrl != null ? htmlUrl : url);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
//            Uri uri = Uri.parse(url);
//            if (uri == null) {
//                finish();
//                return true;
//            }
//            String gistId = LinkParserHelper.getGistId(uri);
//            if (!InputHelper.isEmpty(gistId)) {
//                startActivity(GistActivity.createIntent(this, gistId, isEnterprise()));
//            } else {
//                RepoFilesActivity.startActivity(this, url, isEnterprise());
//            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
