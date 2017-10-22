package com.duyp.architecture.mvvm.local.base_test;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/17/17.
 *
 */

public class TestModel extends RealmObject{

    @PrimaryKey
    public Long id;

    public String name;
    public int age;
}
