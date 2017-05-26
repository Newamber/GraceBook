package com.newamber.gracebook.util;

import android.content.Context;
import android.os.Environment;

import com.newamber.gracebook.GraceBookApplication;

/**
 * Description: DeviceUtil.<p>
 * Created by Newamber on 2017/5/23.
 */
@SuppressWarnings("unused")
public class DeviceUtil {

    public static int dp2Px(float dpValue) {
        Context context = GraceBookApplication.getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static int getScreenHeight() {
        Context context = GraceBookApplication.getContext();
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        Context context = GraceBookApplication.getContext();
        return context.getResources().getDisplayMetrics().widthPixels;
    }

}
