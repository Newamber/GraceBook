package com.newamber.gracebook.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newamber.gracebook.GraceBookApplication;
import com.newamber.gracebook.R;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */

public class TypeEditViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public TypeEditViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
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
                .getGraceBookContext()
                .getResources()
                .getStringArray(R.array.typeEdit_viewpager_title)[position];
    }
}
