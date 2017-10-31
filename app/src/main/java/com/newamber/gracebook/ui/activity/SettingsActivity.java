package com.newamber.gracebook.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.util.ToastUtil;

/**
 * Description: Setting Activity.<p>
 * Created by Newamber on 2017/5/1.
 */
public class SettingsActivity extends BaseActivity {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        TextView textViewAutoRecord = findViewById(R.id.textView_autoRecord);
        TextView textViewTypeEdit = findViewById(R.id.textView_typeEdit);
        TextView textViewPassword = findViewById(R.id.textView_picturePassword);
        TextView textViewDataBackup = findViewById(R.id.textView_dataBackup);
        TextView textViewPrimaryColor = findViewById(R.id.textView_primaryColor);
        TextView textViewAccentColor = findViewById(R.id.textView_accentColor);
        SwitchCompat switchAutoRecord = findViewById(R.id.switch_auto_record);
        SwitchCompat switchPassword = findViewById(R.id.switch_picture_password);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        bindOnClickListener(textViewAutoRecord, textViewTypeEdit, textViewPassword, textViewDataBackup,
                textViewPrimaryColor, textViewAccentColor);

        switchAutoRecord.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                ToastUtil.inDevelopment();
            } else {
                ToastUtil.inDevelopment();
            }
        });

        switchPassword.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                ToastUtil.inDevelopment();
            } else {
                ToastUtil.inDevelopment();
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.textView_autoRecord:
                ToastUtil.inDevelopment();
                break;
            case R.id.textView_typeEdit:
                startTransitionActivity(TypeEditActivity.class);
                break;
            case R.id.textView_picturePassword:
                ToastUtil.inDevelopment();
                break;
            case R.id.textView_dataBackup:
                ToastUtil.inDevelopment();
                break;
            case R.id.textView_primaryColor:
                ToastUtil.inDevelopment();
                break;
            case R.id.textView_accentColor:
                ToastUtil.inDevelopment();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }

    @Override
    protected @LayoutRes int getLayoutId() {
        return LAYOUT_ID;
    }
}
