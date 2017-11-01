package com.duyp.architecture.mvvm.ui.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.databinding.RepoSearchBinding;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.adapter.RepoAdapter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class RepoSearchFragment extends BaseRecyclerViewFragment<RepoSearchBinding, Repo, RepoAdapter, RepoSearchViewModel> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RxTextView.textChanges(binding.edtSearch)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(charSequence -> charSequence.length() > 0)
                .map(charSequence -> "*" + charSequence + "*") // realm search
                .subscribe(s -> viewModel.searchLocal(s));

        RxTextView.textChanges(binding.edtSearch)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(charSequence -> charSequence.length() > 0)
                .map(CharSequence::toString)
                .subscribe(s -> viewModel.searchRemote(s));

        setNoDataText("No result");
    }

    @Override
    protected Class<RepoSearchViewModel> getViewModelClass() {
        return RepoSearchViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.repo_search;
    }
}
