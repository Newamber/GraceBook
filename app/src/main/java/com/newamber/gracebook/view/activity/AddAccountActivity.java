package com.newamber.gracebook.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.presenter.AddAccountPresenter;
import com.newamber.gracebook.view.AddAccountView;

public class AddAccountActivity extends BaseActivity<AddAccountView, AddAccountPresenter>
        implements AddAccountView {

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
        setContentView(LAYOUT_ID);

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
