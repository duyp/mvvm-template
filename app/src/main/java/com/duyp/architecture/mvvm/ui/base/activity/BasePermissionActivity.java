package com.duyp.architecture.mvvm.ui.base.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.duyp.androidutils.rx.Rx;
import com.duyp.androidutils.rx.functions.PlainAction;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

/**
 * Created by phamd on 8/10/2017.
 * Base activity to deal with android runtime permission
 */

public abstract class BasePermissionActivity extends AppCompatActivity {

    RxPermissions rxPermissions;

    boolean isWaitingForPermissionSetting = false;
    PlainAction requestPermissionOnGranted;
    String[] requestingPermissions;
    PlainAction requestPermissionOnFailToForce;

    int grantedCount = 0;
    int deniedCount = 0;
    boolean deniedForever = false;
    Disposable permissionDisposable;

    public RxPermissions getRxPermissions() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        return rxPermissions;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isWaitingForPermissionSetting) {
            permissionDisposable.dispose();
            requestPermission(requestPermissionOnGranted, true, requestPermissionOnFailToForce, requestingPermissions);
        }
    }

    public void requestPermission(@NonNull PlainConsumer<Boolean> onNext, String... permissions) {
        getRxPermissions().request(permissions).subscribe(onNext);
    }

    /**
     * Do nothing on fail to force request permission
     */
    public void requestPermission(@NonNull PlainAction onGranted, String... permissions) {
        requestPermission(onGranted, false, null, permissions);
    }


    /**
     * Request a list of permissions at runtime
     * If user denied and selected "don't ask again", user will be asked
     * for navigating to app setting to enable needed permissions
     *
     * @param onGranted action on permissions successfully granted
     *
     * @param isMandatory * TRUE:  the request will be shown again if user click deny
     *                    * FALSE: just show a toast that this feature can not be perform
     *                              without needed permissions
     *
     * @param onFailToForce action run after user deny and select "don't ask again" and click cancel on alert dialog
     *                      - if null, do nothing
     *
     * @param permissions permissions to request
     */
    public void requestPermission(@NonNull PlainAction onGranted, boolean isMandatory,
                                  @Nullable PlainAction onFailToForce, String... permissions) {

        isWaitingForPermissionSetting = false;
        requestPermissionOnGranted = onGranted;
        requestingPermissions = permissions;
        requestPermissionOnFailToForce = onFailToForce;

        grantedCount = 0;
        deniedCount = 0;
        deniedForever = false;
        permissionDisposable = getRxPermissions().requestEach(permissions)
                .subscribe(permission -> {

                    if (permission.granted) {
                        grantedCount ++;
                    } else {
                        deniedCount++;
                        if (!permission.shouldShowRequestPermissionRationale) {
                            deniedForever = true;
                        }
                    }

                    if (grantedCount == permissions.length) {
                        onGranted.run();
                    } else if (grantedCount + deniedCount == permissions.length) {
                        if (deniedForever) {
                            // TODO: 10/19/17 create alert string
//                            AlertUtils.showConfirmDialog(this, null, getString(R.string.alert_permission_open_app_setting),
//                                    (dialog, which) -> {
//                                        isWaitingForPermissionSetting = true;
//                                        NavigationUtils.openAppSetting(this);
//                                    },
//                                    (dialog, which) -> {
//                                        if (onFailToForce != null) {
//                                            onFailToForce.run();
//                                        }
//                                    });
                        } else if (isMandatory) {
                            requestPermission(onGranted, true, onFailToForce, permissions);
                        } else {
//                            AlertUtils.showToastShortMessage(BasePermissionActivity.this, getString(R.string.alert_permission_not_granted));
                        }
                    }
                });
    }

    public boolean isPermissionGranted(String permission) {
        return getRxPermissions().isGranted(permission);
    }

    /**
     *
     * @param permissions permissions to check
     * @return true if all permissions request will be shown to user
     */
    public boolean shouldShowRequestPermissionRationale(String... permissions) {
        return Rx.filter(permissions, permission -> ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                .size() == permissions.length;
    }
}
