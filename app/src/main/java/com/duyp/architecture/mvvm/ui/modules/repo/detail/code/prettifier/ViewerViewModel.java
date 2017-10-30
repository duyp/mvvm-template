package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.prettifier;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.MimeTypeMap;

import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.MarkdownModel;
import com.duyp.architecture.mvvm.data.model.ViewerFile;
import com.duyp.architecture.mvvm.data.provider.markdown.MarkDownProvider;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by duypham on 10/30/17.
 * 
 */

@Getter
public class ViewerViewModel extends BaseViewModel {

    private String downloadedStream;
    private boolean isMarkdown;
    private boolean isRepo;
    private boolean isImage;
    private String url;
    private String htmlUrl;

    private final MutableLiveData<ImageValue> imageUrl = new MutableLiveData<>();
    private final MutableLiveData<MdTextValue> mdTextValue = new MutableLiveData<>();
    private final MutableLiveData<String> code = new MutableLiveData<>();

    private final RepoService repoService;

    @Inject
    public ViewerViewModel(UserManager userManager, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle intent) {
        if (intent == null) return;
        isRepo = intent.getBoolean(BundleConstant.EXTRA);
        url = intent.getString(BundleConstant.ITEM);
        htmlUrl = intent.getString(BundleConstant.EXTRA_TWO);
        if (!InputHelper.isEmpty(url)) {
            if (MarkDownProvider.isArchive(url)) {
                publishState(State.error(App.getInstance().getString(R.string.archive_file_detected_error)));
                return;
            }
            if (isRepo) {
                url = url.endsWith("/") ? (url + "readme") : (url + "/readme");
            }
            onWorkOnline();
        }
    }

    public void onWorkOnline() {
        isImage = MarkDownProvider.isImage(url);
        if (isImage) {
            if ("svg".equalsIgnoreCase(MimeTypeMap.getFileExtensionFromUrl(url))) {
                execute(true, repoService.getFileAsStream(url),
                        s -> imageUrl.setValue(new ImageValue(s, true)));
                return;
            }
            imageUrl.setValue(new ImageValue(url, false));
            return;
        }
        Single<String> stringSingle = MarkDownProvider.isMarkdown(url)
                ? repoService.getFileAsHtmlStream(url)
                : repoService.getFileAsStream(url);

        Single<String> request = isRepo ? repoService.getReadmeHtml(url) : stringSingle;
        execute(true, request, content -> {
            downloadedStream = content;
            ViewerFile fileModel = new ViewerFile();
            fileModel.setContent(downloadedStream);
            fileModel.setFullUrl(url);
            fileModel.setRepo(isRepo);
            if (isRepo) {
                fileModel.setMarkdown(true);
                isMarkdown = true;
                isRepo = true;
                mdTextValue.setValue(new MdTextValue(downloadedStream, htmlUrl == null ? url : htmlUrl, false));
            } else {
                isMarkdown = MarkDownProvider.isMarkdown(url);
                if (isMarkdown) {
                    MarkdownModel model = new MarkdownModel();
                    model.setText(downloadedStream);
                    Uri uri = Uri.parse(url);
                    StringBuilder baseUrl = new StringBuilder();
                    for (String s : uri.getPathSegments()) {
                        if (!s.equalsIgnoreCase(uri.getLastPathSegment())) {
                            baseUrl.append("/").append(s);
                        }
                    }
                    model.setContext(baseUrl.toString());
                    execute(true, repoService.convertReadmeToHtml(model), string -> {
                        isMarkdown = true;
                        downloadedStream = string;
                        fileModel.setMarkdown(true);
                        fileModel.setContent(downloadedStream);
                        mdTextValue.setValue(new MdTextValue(downloadedStream, htmlUrl == null ? url : htmlUrl, true));
                    });
                    return;
                }
                fileModel.setMarkdown(false);
                code.setValue(downloadedStream);
            }
        });
    }

     public void onLoadContentAsStream() {
        boolean isImage = MarkDownProvider.isImage(url) && !"svg".equalsIgnoreCase(MimeTypeMap.getFileExtensionFromUrl(url));
        if (isImage || MarkDownProvider.isArchive(url)) {
            return;
        }
        execute(true, repoService.getFileAsStream(url),
                body -> {
                    downloadedStream = body;
                    code.setValue(downloadedStream);
                });
    }

    @AllArgsConstructor
    @Getter
    public static final class MdTextValue {
        private String text;
        private String baseUrl;
        private boolean replace;
    }

    @AllArgsConstructor
    @Getter
    public static final class ImageValue {
        private String url;
        private boolean isSvg;
    }
}
