package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */

public class MoneyRepoTypeFragment extends BaseFragment {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_repo_type;

    private RecyclerView mRecyclerView;

    @Override
    public void initView() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return LAYOUT_ID;
    }
}
