package com.newamber.gracebook.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;

/**
 * Description: Splash Activity which include some initial operations.<p>
 *
 * Created by Newamber on 2017/5/4.
 */
public class SplashActivity extends BaseActivity {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
    }

    @Override
    protected @LayoutRes int getLayoutId() {
        return LAYOUT_ID;
    }
}
