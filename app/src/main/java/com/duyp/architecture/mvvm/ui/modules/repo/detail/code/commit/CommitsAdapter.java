package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.commit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class CommitsAdapter extends BaseAdapter<Commit> {

    private final AvatarLoader avatarLoader;

    @Inject
    public CommitsAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper, AvatarLoader avatarLoader) {
        super(context, navigatorHelper);
        this.avatarLoader = avatarLoader;
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return CommitsViewHolder.newInstance(viewGroup, avatarLoader, navigatorHelper);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Commit commit) {
        ((CommitsViewHolder)viewHolder).bind(commit);
    }
}
