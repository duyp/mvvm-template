package com.duyp.architecture.mvvm.data.model;

import java.util.List;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 15 Nov 2016, 7:04 PM
 */


@Getter
@Setter
@NoArgsConstructor
public class Pageable<M> {

    public int first;
    public int next;
    public int prev;
    public int last;
    public int totalCount;
    public boolean incompleteResults;
    public List<M> items;
}
