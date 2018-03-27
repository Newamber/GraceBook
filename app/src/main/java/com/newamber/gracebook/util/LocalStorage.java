package com.newamber.gracebook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.newamber.gracebook.app.GraceBookApplication;

import java.util.Set;

/**
 * Description: Local storage manager util.<p>
 *
 * Created by Newamber on 2017/6/28.
 */
@SuppressWarnings("unused")
public class LocalStorage {

    private LocalStorage() {}

    private static SharedPreferences sp = GraceBookApplication.getContext()
            .getSharedPreferences(GlobalConstant.PREFERENCE_FILE, Context.MODE_PRIVATE);

    private static SharedPreferences.Editor sEditor = sp.edit();

    @SuppressWarnings("unchecked")
    public static <T> void put(String key, T value) {
        switch (value.getClass().getSimpleName()) {
            case "Integer":
                sEditor.putInt(key, (Integer) value).apply();
                break;
            case "Long":
                sEditor.putLong(key, (Long) value).apply();
                break;
            case "Float":
                sEditor.putFloat(key, (Float) value).apply();
                break;
            case "Boolean":
                sEditor.putBoolean(key, (Boolean) value).apply();
                break;
            case "String":
                sEditor.putString(key, (String) value).apply();
                break;
            case "Set":
                sEditor.putStringSet(key, (Set<String>) value).apply();
                break;
            default:
                break;
        }
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

    @NonNull
    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    @NonNull
    public static String getString(String key, @StringRes int defaultValue) {
        return sp.getString(key, GraceBookApplication.getContext().getString(defaultValue));
    }

    @NonNull
    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return sp.getStringSet(key, defaultValues);
    }

    public static boolean contains(String key) {
        return sp.contains(key);
    }

    public static void remove(String key) {
        sEditor.remove(key).apply();
    }

    public static void removeAll() {
        sEditor.clear().apply();
    }
}

