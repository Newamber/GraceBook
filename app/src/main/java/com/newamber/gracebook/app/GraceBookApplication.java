package com.newamber.gracebook.app;

import android.app.Application;
import android.content.Context;

import com.newamber.gracebook.R;
import com.newamber.gracebook.util.ColorUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import es.dmoral.toasty.Toasty;

/**
 * Description: Some global operations are relevant to this Application.<p>
 *
 * Created by Newamber on 2017/4/24.
 */
public class GraceBookApplication extends Application {

    private static Reference<Context> sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = new WeakReference<>(getApplicationContext());
        initConfig();
    }

    public static Context getContext() {
        return sContext.get();
    }


    // Some init operations.
    private void initConfig() {
        // Initialize Toasty with custom configuration.
        Toasty.Config.getInstance()
                .setInfoColor(ColorUtil.getColor(R.color.colorToastyInfo))
                .apply();

        // Initialize DBFlow.
        FlowManager.init(this);

        if (LocalStorage.getBoolean(GlobalConstant.IS_FIRST_ENTER_APP, true))
        LocalStorage.put(GlobalConstant.IS_FIRST_ENTER_APP, false);
    }
}
