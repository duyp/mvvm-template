package com.duyp.architecture.mvvm.local;//package com.duyp.architecture.mvvm.utils;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.androidutils.realm.LiveRealmResultPair;
import com.duyp.androidutils.realm.LiveRealmResults;

import org.powermock.api.mockito.PowerMockito;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by duypham on 10/17/17.
 * Utilities functions to deal with realm unit testing
 */

public class RealmTestUtils {

    /**
     *
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotated {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * : RealmQuery.class
     *
     * Initialize a mock realm when perform a query. This mock realm will turn a mock {@link RealmQuery}
     * when perform {@link Realm#where(Class)}, and the mock query will return itself when perform {@link RealmQuery#equalTo(String, Long)}
     *
     * @param realm mock realm, should be mocked by {@link PowerMockito#mock(Class)}
     * @param tClass class of realm model
     * @param <T>
     * @return mocked realm query, see {@link #mockRealmQuery()}
     */
    public static <T extends RealmObject> RealmQuery<T> initRealmQuery(Realm realm, Class<T> tClass) {
        RealmQuery<T> query = mockRealmQuery();
        when(realm.where(tClass)).thenReturn(query);
        when(query.equalTo(anyString(), anyLong())).thenReturn(query);
        return query;
    }

    /**
     * see {@link #initFindAllSorted(RealmQuery, List)}
     */
    public static <T extends RealmObject> RealmResults<T> initFindAllSorted(RealmQuery<T> query) throws Exception {
        return initFindAllSorted(query, null);
    }

    /**
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotated {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * : RealmResults.class
     *
     * Initialize findAll and findAllSorted for given {@link RealmQuery}
     *
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotate {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * RealmResults.class, RealmQuery.class
     *
     * @param query mocked query, should be mocked by {@link PowerMockito#mock}, see {@link #mockRealmQuery()}
     * @param returnValue This value will be returned when the query 's RealmResults call {@link RealmResults#iterator()}
     * @param <T> type of object
     * @return mocked {@link RealmResults}
     * @throws Exception
     */
    public static <T extends RealmObject> RealmResults<T> initFindAllSorted(RealmQuery<T> query, @Nullable List<T> returnValue) throws Exception {
        RealmResults<T> realmResults = mockRealmResults();
        initLiveRealmResults(realmResults);
        when(query.findAll()).thenReturn(realmResults);
        when(query.findAllSorted(anyString(), any())).thenReturn(realmResults);

        if (returnValue != null) {
            // The for(...) loop in Java needs an iterator, so we're giving it one that has items,
            // since the mock RealmResults does not provide an implementation. Therefore, anytime
            // anyone asks for the RealmResults Iterator, give them a functioning iterator from the
            // ArrayList of Persons we created above. This will allow the loop to execute.
            when(realmResults.size()).thenReturn(returnValue.size());
            when(realmResults.iterator()).thenReturn(returnValue.iterator());
        }
        return realmResults;
    }

    /**
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotated {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * : RealmResults.class
     *
     * Initialize data for a mock {@link LiveRealmResults} by given RealmResults. This mock liveRealmResults will return given
     * results when perform {@link LiveRealmResults#getData()}
     *
     *
     * @param results mock realm result, should be mocked by {@link PowerMockito#mock}, see {@link #mockRealmResults()}
     * @param <T> type of realm object
     * @return mocked {@link LiveRealmResults}
     * @throws Exception
     */
    public static <T extends RealmObject> LiveRealmResults<T> initLiveRealmResults(RealmResults<T> results) throws Exception {
        // noinspection unchecked
        LiveRealmResultPair<T> pair = mock(LiveRealmResultPair.class);
        PowerMockito.whenNew(LiveRealmResultPair.class).withAnyArguments().thenReturn(pair);
        when(pair.getData()).thenReturn(results);
        return LiveRealmResults.asLiveData(results);
    }


    /**
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     *
     * Initialize data for a mock {@link LiveRealmObject} by given object
     *
     * @param object the object which will be returned when call {@link LiveRealmObject#getData()}
     * @param <T>
     * @return mocked {@link LiveRealmObject}
     * @throws Exception
     */
    public static <T extends RealmObject>LiveRealmObject<T> initLiveRealmObject(T object) throws Exception {
        // noinspection unchecked
        LiveRealmObject<T> liveRealmObject = mock(LiveRealmObject.class);
        when(liveRealmObject.getData()).thenReturn(object);
        return liveRealmObject;
    }

    /**
     *
     * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotated {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * : RealmQuery.class
     *
     * @param <T>
     * @return mocked {@link RealmQuery}
     */
    @SuppressWarnings("unchecked")
    public  static <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    /**
     * * Must be run with {@link org.powermock.modules.junit4.PowerMockRunner} or {@link org.robolectric.RobolectricTestRunner}
     * Test classes should be annotated {@link org.powermock.core.classloader.annotations.PrepareForTest}
     * : RealmResults.class
     *
     * @param <T>
     * @return mocked {@link RealmResults}
     */
    @SuppressWarnings("unchecked")
    public static <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }
}
