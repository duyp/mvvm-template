package com.duyp.architecture.mvvm.ui.modules.profile.overview.pinned;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;

import java.text.NumberFormat;

import butterknife.BindView;
import github.GetPinnedReposQuery;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class PinnedViewHolder extends BaseViewHolder<GetPinnedReposQuery.Node> {

    @BindView(R.id.title)
    FontTextView title;
    @BindView(R.id.stars)
    FontTextView stars;
    @BindView(R.id.forks)
    FontTextView forks;
    @BindView(R.id.issues)
    FontTextView issues;
    @BindView(R.id.pullRequests)
    FontTextView pullRequests;
    @BindView(R.id.language)
    FontTextView language;

    private final NumberFormat format;

    protected PinnedViewHolder(@NonNull View itemView, NumberFormat format) {
        super(itemView);
        this.format = format;
    }

    public static PinnedViewHolder createInstance(ViewGroup viewGroup, NumberFormat format) {
        return new PinnedViewHolder(getView(viewGroup, R.layout.profile_pinned_repo_row_item), format);
    }

    public void bind(@NonNull GetPinnedReposQuery.Node t) {
        title.setText(t.name());
        issues.setText(format.format(t.issues().totalCount()));
        pullRequests.setText(format.format(t.pullRequests().totalCount()));
        stars.setText(format.format(t.stargazers().totalCount()));
        forks.setText(format.format(t.forks().totalCount()));
        GetPinnedReposQuery.PrimaryLanguage primaryLanguage= t.primaryLanguage();
        if (primaryLanguage != null) {
            language.setVisibility(View.VISIBLE);
            language.setText(primaryLanguage.name());
            String color = primaryLanguage.color();
            if (color != null) {
                if (!color.startsWith("#")) {
                    color = "#" + color;
                }
                language.tintDrawables(Color.parseColor(color));
            }
        } else {
            language.setVisibility(View.GONE);
        }
    }
}
