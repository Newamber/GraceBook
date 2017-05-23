package com.newamber.gracebook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BasePresenter;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/1.
 */

public class SettingsActivity extends BaseActivity {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.textView_typeEdit:
                startActivity(new Intent(this, TypeEditActivity.class));
                break;
            default:
        }
    }

    @Override
    public void initView() {
        setContentView(LAYOUT_ID);

        TextView textViewTypeEdit = (TextView) findViewById(R.id.textView_typeEdit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        textViewTypeEdit.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    // The Activity has no business logic so there is no presenter.
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
