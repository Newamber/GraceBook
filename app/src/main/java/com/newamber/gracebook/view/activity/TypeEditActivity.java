package com.newamber.gracebook.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.model.adapter.TypeEditViewPagerAdapter;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.view.TypeEditView;
import com.newamber.gracebook.view.fragment.MoneyRepoTypeFragment;
import com.newamber.gracebook.view.fragment.MoneyTypeFragment;

import java.util.ArrayList;
import java.util.List;

public class TypeEditActivity extends BaseActivity<TypeEditView, TypeEditPresenter>
        implements TypeEditView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_type_edit;
    private ViewPager mviewPager;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_typeEdit);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_typeEdit);
        mviewPager = (ViewPager) findViewById(R.id.viewPager_typeEdit);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MoneyTypeFragment());
        fragmentList.add(new MoneyRepoTypeFragment());

        TypeEditViewPagerAdapter pagerAdapter = new TypeEditViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mviewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mviewPager);
    }

    @Override
    protected TypeEditPresenter createPresenter() {
        return new TypeEditPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_type_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_typeEdit_delete:
                break;
            case R.id.toolbar_typeEdit_new:
                getPresenter().newTypeDialog(mviewPager.getCurrentItem());
                break;
            case R.id.toolbar_typeEdit_deleteAll:
                break;
            case android.R.id.home:
                finish();
            default:
        }
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public void showMoneyTypeDialog() {
        // TODO: simplify codes if possible
        AlertDialog.Builder builder = new AlertDialog.Builder(TypeEditActivity.this);
        builder.setTitle(R.string.new_type);
        View view = LayoutInflater.from(TypeEditActivity.this).inflate(R.layout.dialog_money_type, null);
        builder.setView(view);
        final EditText moneyTypeName = (EditText) view.findViewById(R.id.editText_moneyTypeName);

        builder.setPositiveButton(R.string.sure, (dialog, which) -> {
            String a = moneyTypeName.getText().toString().trim();
            Toast.makeText(TypeEditActivity.this, a, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {});
        builder.show();
    }

    @Override
    @SuppressWarnings("all")
    public void showMoneyRepoTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TypeEditActivity.this);
        builder.setTitle(R.string.new_repo_type);
        View view = LayoutInflater.from(TypeEditActivity.this).inflate(R.layout.dialog_money_repo_type, null);
        builder.setView(view);
        final EditText moneyRepoTypeName = (EditText) view.findViewById(R.id.editText_moneyRepoTypeName);

        builder.setPositiveButton(R.string.sure, (dialog, which) -> {
            String a = moneyRepoTypeName.getText().toString().trim();
            Toast.makeText(TypeEditActivity.this, a, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {});
        builder.show();
    }
}
