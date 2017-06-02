package com.newamber.gracebook;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.ref.WeakReference;

import es.dmoral.toasty.Toasty;

/**
 * Description: Some global operations are relevant to this Application.<p>
 *
 * Created by Newamber on 2017/4/24.
 */
public class GraceBookApplication extends Application {

    private static WeakReference<Context> sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = new WeakReference<>(getApplicationContext());
        initConfig();
    }

    public static Context getContext() {
        return sContext.get();
    }

    private void initConfig() {
        // Initialize Toasty with custom config.
        Toasty.Config
                .getInstance()
                .setInfoColor(Color.parseColor("#42a5f5"))
                .apply();

        // Initialize DBFlow.
        FlowManager.init(this);
    }
}
