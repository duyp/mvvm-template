package com.duyp.architecture.mvvm.ui.base.interfaces;

/**
 *
 */
public interface PaginationListener {
        int getCurrentPage();

        int getPreviousTotal();

        void setCurrentPage(int page);

        void setPreviousTotal(int previousTotal);

        boolean onCallApi(int page);
}