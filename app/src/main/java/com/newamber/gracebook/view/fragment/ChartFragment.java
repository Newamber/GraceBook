package com.newamber.gracebook.view.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends BaseFragment {


    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public void processClick(View v) {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_chart;
    }
}
