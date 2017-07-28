package com.newamber.gracebook.util;

import android.os.Build;
import android.os.Environment;

import com.newamber.gracebook.R;
import com.newamber.gracebook.app.GraceBookApplication;

import org.jetbrains.annotations.Contract;

/**
 * Description: DeviceUtil.<p>
 *
 * Created by Newamber on 2017/5/23.
 */
//@SuppressWarnings("unused")
public class DeviceUtil {

    private static long lastClickTime;

    public static int dp2Px(float dpValue) {
        final float scale = GraceBookApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static int getScreenHeight() {
        return GraceBookApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return GraceBookApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    @Contract(pure = true)
    public static boolean aboveAndroid_5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Contract(pure = true)
    @SuppressWarnings("all")
    public static boolean aboveAndroid_6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isSlowlyClick(int interval) {
        boolean flag;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= interval) {
            flag = true;
        } else {
            flag = false;
            ToastUtil.showShort(R.string.click_too_fast, ToastUtil.ToastMode.INFO);
        }
        lastClickTime = currentClickTime;
        return flag;
    }

}
