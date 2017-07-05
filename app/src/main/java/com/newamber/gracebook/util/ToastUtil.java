package com.newamber.gracebook.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.newamber.gracebook.GraceBookApplication;

import es.dmoral.toasty.Toasty;

/**
 * Description: Repackaging of 3rd party library "Toasty".<p>
 *
 * Created by Newamber on 2017/5/23.
 */
@SuppressWarnings("unused")
public class ToastUtil {

    public static void showShort(CharSequence text, ToastMode modeEnum) {
        Context context = GraceBookApplication.getContext();
        switch (modeEnum) {
            case NORMAL:
                Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
                break;
            case INFO:
                Toasty.info(context, text, Toast.LENGTH_SHORT).show();
                break;
            case WARNING:
                Toasty.warning(context, text, Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                Toasty.success(context, text, Toast.LENGTH_SHORT).show();
                break;
            case ERROR:
                Toasty.error(context, text, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public static void showShort(@StringRes int stringId, ToastMode modeEnum) {
        Context context = GraceBookApplication.getContext();
        String text = context.getResources().getString(stringId);
        switch (modeEnum) {
            case NORMAL:
                Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
                break;
            case INFO:
                Toasty.info(context, text, Toast.LENGTH_SHORT).show();
                break;
            case WARNING:
                Toasty.warning(context, text, Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                Toasty.success(context, text, Toast.LENGTH_SHORT).show();
                break;
            case ERROR:
                Toasty.error(context, text, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public static void showLong(CharSequence text, ToastMode modeEnum) {
        Context context = GraceBookApplication.getContext();
        switch (modeEnum) {
            case NORMAL:
                Toasty.normal(context, text, Toast.LENGTH_LONG).show();
                break;
            case INFO:
                Toasty.info(context, text, Toast.LENGTH_LONG).show();
                break;
            case WARNING:
                Toasty.warning(context, text, Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                Toasty.success(context, text, Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                Toasty.error(context, text, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public static void showLong(@StringRes int stringId, ToastMode modeEnum) {
        Context context = GraceBookApplication.getContext();
        String text = context.getResources().getString(stringId);
        switch (modeEnum) {
            case NORMAL:
                Toasty.normal(context, text, Toast.LENGTH_LONG).show();
                break;
            case INFO:
                Toasty.info(context, text, Toast.LENGTH_LONG).show();
                break;
            case WARNING:
                Toasty.warning(context, text, Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                Toasty.success(context, text, Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                Toasty.error(context, text, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public enum ToastMode {
        NORMAL,
        INFO,
        WARNING,
        SUCCESS,
        ERROR
    }
}