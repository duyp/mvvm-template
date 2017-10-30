package com.duyp.architecture.mvvm.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by duypham on 10/30/17.
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class ViewerFile extends RealmObject{
    @PrimaryKey
    String fullUrl;
    boolean markdown;
    String content;
    boolean repo;
}
