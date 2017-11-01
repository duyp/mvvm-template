package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.release;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import butterknife.BindString;
import butterknife.BindView;
import lombok.Setter;

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class ReleasesViewHolder extends BaseViewHolder<Release> {

    @BindView(R.id.title) FontTextView title;
    @BindView(R.id.details) FontTextView details;
    @BindView(R.id.download) ImageView download;
    @BindString(R.string.released) String released;
    @BindString(R.string.drafted) String drafted;

    private ReleasesViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static ReleasesViewHolder newInstance(ViewGroup viewGroup) {
        return new ReleasesViewHolder(getView(viewGroup, R.layout.releases_row_item));
    }

    @Override public void bind(@NonNull Release item) {
        title.setText(SpannableBuilder.builder().bold(!InputHelper.isEmpty(item.getName()) ? item.getName() : item.getTagName()));
        if (item.getAuthor() != null) {
            details.setText(SpannableBuilder.builder()
                    .append(item.getAuthor().getLogin())
                    .append(" ")
                    .append(item.isDraft() ? drafted : released)
                    .append(" ")
                    .append(ParseDateFormat.getTimeAgo(item.getCreatedAt())));
        } else {
            details.setVisibility(View.GONE);
        }
    }
}
