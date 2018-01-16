package com.newamber.gracebook.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: An Activity manager that can easily add and delete Activities.<p>
 *
 * Created by Newamber on 2017/4/26.
 */

public class ActivityUtil {

    private ActivityUtil() {}

    private static List<Activity> sActivityList = new ArrayList<>();

    /**
     * No description.
     * @param activity need added Activity
     */
    public static void addActivity(Activity activity) {
        sActivityList.add(activity);
    }

    /**
     * No description.
     * @param activity need deleted Activity
     */
    public static void removeActivity(Activity activity) {
        sActivityList.remove(activity);
    }

    /**
     * Destroy all Activities.
     *
     */
    @SuppressWarnings("all")
    public static void finishAllActivity() {
        for (Activity activity : sActivityList) {
            if (! activity.isFinishing()) activity.finish();
        }
    }
}
