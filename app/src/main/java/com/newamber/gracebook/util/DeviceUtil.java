package com.newamber.gracebook.util;

import android.content.Context;
import android.os.Environment;

import com.newamber.gracebook.GraceBookApplication;

/**
 * Description: DeviceUtil.<p>
 * Created by Newamber on 2017/5/23.
 */

public class DeviceUtil {

    @SuppressWarnings("all")
    private static Context sContext = GraceBookApplication.getContext();

    public static int dp2Px(float dpValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static int getScreenHeight() {
        return sContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return sContext.getResources().getDisplayMetrics().widthPixels;
    }

}
