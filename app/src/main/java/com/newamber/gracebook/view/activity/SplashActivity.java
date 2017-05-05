package com.newamber.gracebook.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BasePresenter;

/**
 * Description: Splash Activity which include some initializing operations.<p>
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
    public void processClick(View v) {

    }

    @Override
    public void initView() {
        setContentView(LAYOUT_ID);
    }

    // The activity has no business logic so there is no presenter.
    @Override
    protected BasePresenter getAttachedPresenter() {
        return null;
    }
}
