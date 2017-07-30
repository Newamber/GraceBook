package com.newamber.gracebook.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MainViewPagerAdapter;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.ui.fragment.ChartFragment;
import com.newamber.gracebook.ui.fragment.DayFragment;
import com.newamber.gracebook.ui.fragment.FlowFragment;
import com.newamber.gracebook.util.ActivityCollectorUtil;
import com.newamber.gracebook.util.DeviceUtil;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends BaseActivity<BaseView.MainView, MainPresenter>
        implements BaseView.MainView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_main;

    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fabAdd;

    private MainPresenter mPresenter;

    // State bit to control the fab_record's appearance way.
    private boolean isFromFirstTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.fab_record:
                mPresenter.clickRecordButton();
            default:
                break;
        }
    }

    @Override
    public void initView() {
        isFromFirstTab = true;
        mPresenter = getPresenter();

        // ----------------------------findViewByID-------------------------------------------------
        Toolbar toolbarMain = findView(R.id.toolbar_main);
        mDrawerLayout = findView(R.id.drawer_layout);
        NavigationView navigationView = findView(R.id.navigationView);
        fabAdd = findView(R.id.fab_record);
        TabLayout tabLayout = findView(R.id.tablayout_main);
        mViewPager = findView(R.id.viewPager_main);
        ImageView imageViewHeader = (ImageView) navigationView.getHeaderView(0)
                .findViewById(R.id.imageview_navigation_header);
        Glide.with(this).load(R.drawable.bg_navigationview).into(imageViewHeader);

        // --------------------------setOnClickListener---------------------------------------------
        fabAdd.setOnClickListener(this);

        // --------------------------Toolbar & DrawerLayout-----------------------------------------
        setSupportActionBar(toolbarMain);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        // Set an animated toolbar navigation icon. Though not that useful...
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbarMain,
                R.string.open,
                R.string.close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        // ------------------------------NavigationView---------------------------------------------
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.drawer_list_color));
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigationView_like:
                    ToastUtil.showShort(R.string.like, ToastMode.ERROR);
                    break;
                case R.id.navigationView_settings:
                    startTransitionActivity(SettingsActivity.class, 200);
                    break;
                case R.id.navigationView_donation:
                    ToastUtil.showShort("donation", ToastMode.ERROR);
                    break;
                case R.id.navigationView_share:
                    ToastUtil.showShort(R.string.share, ToastMode.ERROR);
                    break;
                case R.id.navigationview_about:
                    ToastUtil.showShort(R.string.about, ToastMode.ERROR);
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false;
        });

        // ------------------------------TabLayout & ViewPager----------------------------------------
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DayFragment());
        fragmentList.add(new FlowFragment());
        fragmentList.add(new ChartFragment());

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    startAnimator(fabAdd, R.animator.anim_bounce_show);
                    fabAdd.setCompatElevation(DeviceUtil.dp2Px(6.0f));
                    fabAdd.setVisibility(View.VISIBLE);
                    isFromFirstTab = true;
                    //fabAdd.show();
                } else {
                    if (isFromFirstTab) {
                        startAnimator(fabAdd, R.animator.anim_bounce_hide);
                        fabAdd.setCompatElevation(0);
                        isFromFirstTab = false;
                    } else {
                        fabAdd.setVisibility(View.INVISIBLE);
                    }
                    //fabAdd.hide();
                }
                // Bring the changes of Toolbar into effect.
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.lack_of_condition)
                .setMessage(R.string.no_type_no_recording)
                .setPositiveButton(R.string.go, (dialog, which) -> startTransitionActivity(TypeEditActivity.class))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    /*@Override
    protected boolean isEnabledEventBus() {
        return true;
    }*/

    // Toolbar Created.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    // Toolbar dynamic Changes.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(true);
                menu.findItem(R.id.toolbar_main_settings).setVisible(true);
                menu.setGroupVisible(R.id.toolbar_group_flow, false);
                break;
            case 1:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);
                menu.setGroupVisible(R.id.toolbar_group_flow, true);
                break;
            case 2:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);

                menu.setGroupVisible(R.id.toolbar_group_flow, false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
        /*List<boolean[]> situation = new ArrayList<>();
        situation.add(new boolean[] {true, true, false});
        situation.add(new boolean[] {false, false, true});
        situation.add(new boolean[] {false, false, false});

        // TODO: Toolbar change with animation
        boolean[] isVisible = situation.get(mViewPager.getCurrentItem());
        menu.findItem(R.id.toolbar_main_editbookname).setVisible(isVisible[0]);
        menu.findItem(R.id.toolbar_main_settings).setVisible(isVisible[1]);
        menu.setGroupVisible(R.id.toolbar_group_stream, isVisible[2]);

        return super.onPrepareOptionsMenu(menu);*/
    }

    // Toolbar items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_main_search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_main_exit:
                ActivityCollectorUtil.finishAllActivity();
                break;
            case R.id.toolbar_main_delete_all:
                Toast.makeText(this, "delete all", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}