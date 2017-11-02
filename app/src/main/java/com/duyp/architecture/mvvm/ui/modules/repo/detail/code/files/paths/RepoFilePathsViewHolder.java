package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import butterknife.BindView;

/**
 * Created by Kosh on 18 Feb 2017, 2:53 AM
 */

public class RepoFilePathsViewHolder extends BaseViewHolder<RepoFile> {

    @BindView(R.id.pathName)
    FontTextView pathName;

    private RepoFilePathsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static RepoFilePathsViewHolder newInstance(ViewGroup viewGroup) {
        return new RepoFilePathsViewHolder(getView(viewGroup, R.layout.file_path_row_item));
    }

    @Override public void bind(@NonNull RepoFile filesModel) {
        pathName.setText(filesModel.getName());
    }
}
