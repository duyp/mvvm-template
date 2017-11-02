package com.duyp.architecture.mvvm.helper;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.provider.scheme.LinkParserHelper;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;

import java.io.File;

/**
 * Created by duypham on 11/2/17.
 */

public class DownloadHelper {

    /**
     * Download a file from given url
     * @param context application context
     * @param url target file url
     */
    public static void downloadFile(@NonNull @ApplicationContext Context context, @NonNull String url) {
        if (InputHelper.isEmpty(url)) return;
        boolean isEnterprise = LinkParserHelper.isEnterprise(url);
        Uri uri = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        if (isEnterprise) {
            String authToken = PrefGetter.getEnterpriseToken();
            request.addRequestHeader("Authorization", authToken.startsWith("Basic") ? authToken : "token " + authToken);
        }
        File direct = new File(Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.app_name));
        if (!direct.isDirectory() || !direct.exists()) {
            boolean isCreated = direct.mkdirs();
            if (!isCreated) {
                Toast.makeText(App.getInstance(), "Unable to create directory to download file", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String fileName = new File(url).getName();
        request.setDestinationInExternalPublicDir(context.getString(R.string.app_name), fileName);
        request.setTitle(fileName);
        request.setDescription(context.getString(R.string.downloading_file));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}
