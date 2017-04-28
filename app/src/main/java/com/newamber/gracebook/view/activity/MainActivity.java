package com.newamber.gracebook.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.model.adapter.ViewPagerAdapter;
import com.newamber.gracebook.util.ActivityCollectorUtil;
import com.newamber.gracebook.view.fragment.ChartFragment;
import com.newamber.gracebook.view.fragment.DayFragment;
import com.newamber.gracebook.view.fragment.StreamFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_main;
    @SuppressWarnings("all")
    private Toolbar mToolbarMain;
    private DrawerLayout mDrawerLayout;
    @SuppressWarnings("all")
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    @SuppressWarnings("all")
    private NavigationView mNavigationView;
    @SuppressWarnings("all")
    private TabLayout mTabLayout;
    @SuppressWarnings("all")
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private List<Fragment> fragmentList;

    // State bit to control the fab's appearance.
    private boolean isFromFirstTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.fab_record:
                Snackbar.make(v, "fab",  Snackbar.LENGTH_SHORT)
                        .setAction("Undo", v1 ->
                                Toast.makeText(this, "fab", Toast.LENGTH_SHORT).show()).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        setContentView(LAYOUT_ID);
        isFromFirstTab = true;

        // ----------------------------findViewByID-------------------------------------------------
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        fab = (FloatingActionButton) findViewById(R.id.fab_record);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // --------------------------setOnClickListener---------------------------------------------
        fab.setOnClickListener(this);

        // --------------------------Toolbar&DrawerLayout-------------------------------------------
        setSupportActionBar(mToolbarMain);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbarMain,
                R.string.open,
                R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        // ------------------------------NavigationView---------------------------------------------
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.drawer_list_color));
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigationview_like:
                    Toast.makeText(MainActivity.this, "like", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationview_settings:
                    Toast.makeText(MainActivity.this, "settings", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationview_donation:
                    Toast.makeText(MainActivity.this, "donation", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationview_share:
                    Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigationview_about:
                    Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false;
        });

        // ---------------------------TabLayout&ViewPager---------------------------------
        fragmentList = new ArrayList<>();
        fragmentList.add(new DayFragment());
        fragmentList.add(new StreamFragment());
        fragmentList.add(new ChartFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Animator animator;
                if (position == 0) {
                    animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.anim_fab_show);
                    animator.setTarget(fab);
                    animator.start();
                    fab.setCompatElevation(20);
                    fab.setVisibility(View.VISIBLE);
                    isFromFirstTab = true;
                } else {
                    if (isFromFirstTab) {
                        animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.anim_fab_hide);
                        animator.setTarget(fab);
                        animator.start();
                        fab.setCompatElevation(0);
                        isFromFirstTab = false;
                    } else {
                        fab.setVisibility(View.GONE);
                    }
                }
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // The activity has no business logic so there is no presenter.
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

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
            // TODO: Toolbar change with animation.
            case 0:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(true);
                menu.findItem(R.id.toolbar_main_settings).setVisible(true);
                menu.setGroupVisible(R.id.toolbar_group_stream, false);
                break;
            case 1:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);
                menu.setGroupVisible(R.id.toolbar_group_stream, true);
                break;
            case 2:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);
                menu.setGroupVisible(R.id.toolbar_group_stream, false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
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