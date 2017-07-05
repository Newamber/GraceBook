package com.newamber.gracebook.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.newamber.gracebook.GraceBookApplication;

import java.util.Set;

/**
 * Description: Local storage manager util.<p>
 * Created by Newamber on 2017/6/28.
 */
@SuppressWarnings("unused")
public class LocalStorageUtil {

    private static SharedPreferences sp = GraceBookApplication.getContext()
            .getSharedPreferences(GlobalConstant.PREFERENCE_FILE, Context.MODE_PRIVATE);


    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static void putLong(String key, Long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static void putStringSet(String key, Set<String> values) {
        sp.edit().putStringSet(key, values).apply();
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return sp.getStringSet(key, defaultValues);
    }

    public static boolean contains(String key) {
        return sp.contains(key);
    }
}

