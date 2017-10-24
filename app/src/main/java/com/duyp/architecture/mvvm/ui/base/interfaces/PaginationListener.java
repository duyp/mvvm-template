package com.duyp.architecture.mvvm.ui.base.interfaces;

import android.support.annotation.Nullable;

/**
 *
 * @param <P>
 */
public interface PaginationListener<P> {
        int getCurrentPage();

        int getPreviousTotal();

        void setCurrentPage(int page);

        void setPreviousTotal(int previousTotal);

        boolean onCallApi(int page, @Nullable P parameter);
}