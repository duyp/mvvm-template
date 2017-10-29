package com.duyp.architecture.mvvm.data.model;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/29/17.
 *
 */

@Setter
@Getter
@AllArgsConstructor
public class RealmString extends RealmObject {

    public RealmString() {}

    String value;
}
