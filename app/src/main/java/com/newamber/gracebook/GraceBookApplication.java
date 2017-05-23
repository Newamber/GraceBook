package com.newamber.gracebook;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.ref.WeakReference;

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

        // Initialize the database with DBFlow.
        FlowManager.init(this);
    }

    public static Context getContext() {
        return sContext.get();
    }
}
