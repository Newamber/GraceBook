package com.newamber.gracebook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newamber.gracebook.GraceBookApplication;
import com.newamber.gracebook.R;

import java.util.List;

/**
 * Description: The adapter of ViewPager to adapts three Fragments.<p>
 *
 * Created by Newamber on 2017/4/25.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList =  mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return GraceBookApplication
                .getContext()
                .getResources()
                .getStringArray(R.array.main_viewpager_title)[position];
    }
}
