package com.newamber.gracebook.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.presenter.AddAccountPresenter;

public class AddAccountActivity extends BaseActivity<BaseView.AddAccountView, AddAccountPresenter>
        implements BaseView.AddAccountView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_add_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addAccount);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return new AddAccountPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
