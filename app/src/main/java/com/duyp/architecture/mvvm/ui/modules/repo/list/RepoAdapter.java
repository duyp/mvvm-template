package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.androidutils.glide.loader.GlideLoader;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lombok.Setter;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class RepoAdapter extends BaseAdapter<Repo> {

    private final GlideLoader glideLoader;

    @Setter
    private boolean hasAvatar = true;

    private final RealmConfiguration realmConfiguration;

    @Inject
    public RepoAdapter(@ActivityContext Context context,
                       AvatarLoader avatarLoader, NavigatorHelper navigatorHelper, RealmConfiguration realmConfiguration) {
        super(context, navigatorHelper);
        this.glideLoader = avatarLoader;
        this.realmConfiguration = realmConfiguration;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Repo repo) {
        ((ReposViewHolder)viewHolder).bind(repo);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int i) {
        ReposViewHolder holder = ReposViewHolder.newInstance(navigatorHelper, glideLoader, viewGroup, false, hasAvatar);
        if (holder.imvAvatar != null) {
            holder.imvAvatar.setOnClickListener(v -> {
                Repo repo = getItem(holder.getAdapterPosition());
                if (repo != null) {
//                    Realm realm = Realm.getInstance(realmConfiguration);
//                    navigatorHelper.navigateUserProfile(realm.copyFromRealm(repo.getOwner()));
//                    realm.close();
                    navigatorHelper.navigateUserProfile(repo.getOwner());
                }
            });
        }
        return holder;
    }
}
