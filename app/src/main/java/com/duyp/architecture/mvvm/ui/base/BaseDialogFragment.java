package com.duyp.architecture.mvvm.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.ui.widgets.dialog.ProgressDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duypham on 10/31/17.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    Unbinder unbinder;
    protected boolean suppressAnimation = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (!PrefGetter.isAppAnimationDisabled() && !(this instanceof ProgressDialogFragment)) {
            dialog.setOnShowListener(dialogInterface -> AnimHelper.revealDialog(dialog,
                    getResources().getInteger(android.R.integer.config_longAnimTime)));
        }
        return dialog;
    }

    protected abstract int getLayout();

    @Override public void dismiss() {
        if(suppressAnimation){
            super.dismiss();
            return;
        }
        if (PrefGetter.isAppAnimationDisabled()) {
            super.dismiss();
        } else {
            AnimHelper.dismissDialog(this, getResources().getInteger(android.R.integer.config_shortAnimTime),
                    new AnimatorListenerAdapter() {
                        @Override public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            BaseDialogFragment.super.dismiss();
                        }
                    });
        }
    }

    public void setFullscreen() {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(cancelable);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

}
