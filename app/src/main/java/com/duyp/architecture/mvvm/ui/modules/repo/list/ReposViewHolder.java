package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.glide.loader.GlideLoader;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.provider.color.ColorsProvider;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.ui.widgets.LabelSpan;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;

import java.text.NumberFormat;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class ReposViewHolder extends BaseViewHolder<Repo> {

    @BindView(R.id.title) FontTextView title;
    @BindView(R.id.date) FontTextView date;
    @BindView(R.id.stars) FontTextView stars;
    @BindView(R.id.forks) FontTextView forks;
    @BindView(R.id.language) FontTextView language;
    @BindView(R.id.size) FontTextView size;
    @Nullable @BindView(R.id.avatarLayout) AvatarLayout avatarLayout;
    @BindString(R.string.forked) String forked;
    @BindString(R.string.private_repo) String privateRepo;
    @BindColor(R.color.material_indigo_700) int forkColor;
    @BindColor(R.color.material_grey_700) int privateColor;
    private boolean isStarred;
    private boolean withImage;

    private final GlideLoader loader;

    private ReposViewHolder(GlideLoader glideLoader, @NonNull View itemView, boolean isStarred, boolean withImage) {
        super(itemView);
        this.isStarred = isStarred;
        this.withImage = withImage;
        this.loader = glideLoader;
    }

    public static ReposViewHolder newInstance(GlideLoader glideLoader, ViewGroup viewGroup, boolean isStarred, boolean withImage) {
        if (withImage) {
            return new ReposViewHolder(glideLoader, getView(viewGroup, R.layout.repos_row_item), isStarred, true);
        } else {
            return new ReposViewHolder(glideLoader, getView(viewGroup, R.layout.repos_row_no_image_item), isStarred, false);
        }
    }

    @Override public void bind(@NonNull Repo repo) {
        if (repo.isFork() && !isStarred) {
            title.setText(SpannableBuilder.builder()
                    .append(" " + forked + " ", new LabelSpan(forkColor))
                    .append(" ")
                    .append(repo.getName(), new LabelSpan(Color.TRANSPARENT)));
        } else if (repo.is_private()) {
            title.setText(SpannableBuilder.builder()
                    .append(" " + privateRepo + " ", new LabelSpan(privateColor))
                    .append(" ")
                    .append(repo.getName(), new LabelSpan(Color.TRANSPARENT)));
        } else {
            title.setText(!isStarred ? repo.getName() : repo.getFullName());
        }
        if (withImage) {
            // boolean isOrg = repo.getOwner() != null && repo.getOwner().isOrganizationType();
            if (avatarLayout != null) {
                avatarLayout.setVisibility(View.VISIBLE);
                avatarLayout.bindData(loader, repo.getOwner());
            }
        }
        long repoSize = repo.getSize() > 0 ? (repo.getSize() * 1000) : repo.getSize();
        size.setText(Formatter.formatFileSize(size.getContext(), repoSize));
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        stars.setText(numberFormat.format(repo.getStargazersCount()));
        forks.setText(numberFormat.format(repo.getForks()));
        date.setText(ParseDateFormat.getTimeAgo(repo.getUpdatedAt()));
        if (!InputHelper.isEmpty(repo.getLanguage())) {
            language.setText(repo.getLanguage());
            language.setTextColor(ColorsProvider.getColorAsColor(repo.getLanguage(), language.getContext()));
            language.setVisibility(View.VISIBLE);
        }
    }
}
