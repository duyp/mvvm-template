package com.duyp.architecture.mvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvvm.R;

/**
 * Created by duypham on 9/11/17.
 *
 */

public class AvatarLoader extends SimpleGlideLoader {
    public AvatarLoader(Activity activity) {
        super(activity);
    }

    public AvatarLoader(Fragment fragment) {
        super(fragment);
    }

    public AvatarLoader(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        super.init();
        setUseFixedSizeThumbnail(false);
        setPlaceHolderRes(R.drawable.ic_github);
    }
}
