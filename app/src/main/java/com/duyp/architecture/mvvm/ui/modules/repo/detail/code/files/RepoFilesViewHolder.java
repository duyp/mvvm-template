package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.model.type.FilesType;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Kosh on 15 Feb 2017, 10:29 PM
 */

public class RepoFilesViewHolder extends BaseViewHolder<RepoFile> {

    @BindView(R.id.contentTypeImage) ImageView contentTypeImage;
    @BindView(R.id.title) FontTextView title;
    @BindView(R.id.size) FontTextView size;
    @BindView(R.id.menu) ImageView menu;
    @BindString(R.string.file) String file;

    private RepoFilesViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static RepoFilesViewHolder newInstance(ViewGroup viewGroup) {
        return new RepoFilesViewHolder(getView(viewGroup, R.layout.repo_files_row_item));
    }

    @Override public void bind(@NonNull RepoFile filesModel) {
        contentTypeImage.setContentDescription(String.format("%s %s", filesModel.getName(), file));
        title.setText(filesModel.getName());
        if (filesModel.getType() != null && filesModel.getType().getIcon() != 0) {
            contentTypeImage.setImageResource(filesModel.getType().getIcon());
            if (filesModel.getType() == FilesType.file) {
                size.setText(Formatter.formatFileSize(size.getContext(), filesModel.getSize()));
                size.setVisibility(View.VISIBLE);
            } else {
                size.setVisibility(View.GONE);
            }
        }
    }
}
