package com.duyp.architecture.mvvm;

import android.app.Activity;
import android.content.Intent;

import static org.robolectric.Shadows.shadowOf;
import static org.junit.Assert.assertEquals;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class RobolectricHelper {

    public static <T extends Activity> void assertNextActivity(Activity currentActivity, Class<T> nextActivityClass) {
        Intent intent = shadowOf(currentActivity).peekNextStartedActivity();
        assertEquals(nextActivityClass.getCanonicalName(), intent.getComponent().getClassName());
    }
}
