package com.duyp.architecture.mvvm.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.helper.BundleConstant;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class FragmentUtils {

    public static <T extends Fragment> T createFragmentInstance(T fragment, PlainConsumer<Bundle> bundlePlainConsumer) {
        Bundle bundle = new Bundle();
        bundlePlainConsumer.accept(bundle);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static <T extends Fragment> T createFragmentInstance(T fragment, String extra) {
        return createFragmentInstance(fragment, bundle -> bundle.putString(BundleConstant.EXTRA, extra));
    }
}
