package com.newamber.gracebook.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity<IBaseView.SearchView, SearchPresenter> {

    private final static int LAYOUT_ID = R.layout.activity_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected @LayoutRes int getLayoutId() {
        return LAYOUT_ID;
    }
}
