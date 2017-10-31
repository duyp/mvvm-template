package com.duyp.architecture.mvvm.ui.widgets.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.ui.base.BaseDialogFragment;

public class ProgressDialogFragment extends BaseDialogFragment {

    public ProgressDialogFragment() {
        suppressAnimation = true;
    }

    public static final String TAG = ProgressDialogFragment.class.getSimpleName();

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(0);
        }
        return dialog;
    }

    @Override
    protected int getLayout() {
        return R.layout.progress_dialog_layout;
    }
}
