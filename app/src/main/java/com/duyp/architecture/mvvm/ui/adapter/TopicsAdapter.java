package com.duyp.architecture.mvvm.ui.adapter;

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.helper.ViewHelper;
import com.duyp.architecture.mvvm.ui.adapter.viewholder.SimpleViewHolder;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;

import javax.inject.Inject;

/**
 * Created by Kosh on 11 May 2017, 6:58 PM
 */

public class TopicsAdapter extends BaseAdapter<String> {

    private boolean isLightTheme = false;

    @ColorInt private int cardBackground;

    @Inject
    public TopicsAdapter() {}

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        isLightTheme = !AppHelper.isNightMode(viewGroup.getResources());
        cardBackground = ViewHelper.getCardBackground(viewGroup.getContext());
        return new SimpleViewHolder<String>(BaseViewHolder.getView(viewGroup, R.layout.topics_row_item));
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull String item) {
        // noinspection unchecked
        SimpleViewHolder<String> holder = (SimpleViewHolder<String>)viewHolder;
//        if (isLightTheme) {
//            holder.itemView.setBackgroundColor(cardBackground);
//        }
        holder.itemView.setOnClickListener((view) -> {
//            Intent intent = new Intent(new Intent(App.getInstance().getApplicationContext(), SearchActivity.class));
//            intent.putExtra("search", "topic:\"" + item + "\"");
//            view.getContext().startActivity(intent);
        });
        holder.bind(item);
    }
}
