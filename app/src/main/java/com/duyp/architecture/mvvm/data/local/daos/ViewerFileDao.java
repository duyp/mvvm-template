package com.duyp.architecture.mvvm.data.local.daos;

import com.duyp.androidutils.realm.BaseRealmDao;
import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.architecture.mvvm.data.model.ViewerFile;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class ViewerFileDao extends BaseRealmDaoImpl<ViewerFile> {

    @Inject
    public ViewerFileDao(RealmConfiguration realmConfiguration) {
        super(Realm.getInstance(realmConfiguration), ViewerFile.class);
    }
}
