package com.newamber.gracebook.ui.activity;

import android.annotation.SuppressLint;
import android.support.annotation.DrawableRes;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MainViewPagerAdapter;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.ui.fragment.ChartFragment;
import com.newamber.gracebook.ui.fragment.DayFragment;
import com.newamber.gracebook.ui.fragment.FlowFragment;
import com.newamber.gracebook.util.DeviceUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.newamber.gracebook.util.ActivityUtil.finishAllActivity;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthDayHourMinInCHS;
import static com.newamber.gracebook.util.DeviceUtil.isNormalClickSpeed;
import static com.newamber.gracebook.util.NumericUtil.formatCurrency;

@SuppressWarnings("unused")
public class MainActivity extends BaseActivity<IBaseView.MainView, MainPresenter>
        implements IBaseView.MainView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_main;
    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;
    private static final int THIRD_PAGE = 2;
    private static final int MAX_ACCOUNT_BOOK_NAME_SIZE = 10;

    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fabAdd;
    private Toolbar toolbarMain;

    private MainPresenter mPresenter;

    // State bit to control the fab_record's appearance way.
    private boolean isFromFirstTab;
    private String dateRangeText;
    private String totalIncome, totalExpense, surplus;
    private int incomeCount, expenseCount;
    private Calendar start, end;
    private boolean isCurrentPeriodEmpty;

    @Override
    public void initViews() {
        isFromFirstTab = true;
        mPresenter = getPresenter();

        // ----------------------------findViewByID-------------------------------------------------
        toolbarMain = findViewById(R.id.toolbar_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        fabAdd = findViewById(R.id.fab_record);
        mViewPager = findViewById(R.id.viewPager_main);
        TabLayout tabLayout = findViewById(R.id.tablayout_main);
        NavigationView navigationView = findViewById(R.id.navigationView);
        ImageView imageViewHeader = navigationView.getHeaderView(0)
                .findViewById(R.id.imageview_navigation_header);
        TextView textViewTime = navigationView.getHeaderView(0)
                .findViewById(R.id.textview_navigationview_time);
        String lastRecordTime = getString(R.string.last_record_time) +
                LocalStorage.getString(GlobalConstant.LAST_RECORD_TIME, "");
        textViewTime.setText(lastRecordTime);
        setImageByGlide(imageViewHeader, R.drawable.bg_navigation_header);

        // --------------------------setOnClickListener---------------------------------------------
        bindOnClickListener(fabAdd);

        // --------------------------Toolbar & DrawerLayout-----------------------------------------
        setSupportActionBar(toolbarMain);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        String temp = LocalStorage.getString(GlobalConstant.ACCOUNT_BOOK_NAME, R.string.app_name);
        String name = temp;
        if (temp.length() > MAX_ACCOUNT_BOOK_NAME_SIZE) {
            name = temp.substring(0, MAX_ACCOUNT_BOOK_NAME_SIZE - 1) + "...";
        }
        toolbarMain.setTitle(name);
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
                    ToastUtil.inDevelopment();
                    break;
                case R.id.navigationView_settings:
                    startTransitionActivity(SettingsActivity.class, 300);
                    break;
                case R.id.navigationView_donation:
                    ToastUtil.inDevelopment();
                    break;
                case R.id.navigationView_share:
                    ToastUtil.inDevelopment();
                    break;
                case R.id.navigationview_about:
                    ToastUtil.inDevelopment();
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false;
        });

        // ------------------------------TabLayout & ViewPager--------------------------------------
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
                if (position == FIRST_PAGE) {
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
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.fab_record:
                mPresenter.newAccount();
                break;
            default:
                break;
        }
    }

    // Toolbar items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_main_search:
                ToastUtil.inDevelopment();
                break;
            case R.id.toolbar_main_exit:
                finishAllActivity();
                break;
            case R.id.toolbar_main_summary:
                if (isNormalClickSpeed()) mPresenter.showSummaryReport();
                break;
            case R.id.toolbar_main_delete_all:
                mPresenter.deleteAllRecords();
                break;
            case R.id.toolbar_main_delete_current:
                post(GlobalConstant.REQUEST_IS_CURRENT_PERIOD_EMPTY);
                mPresenter.deleteCurrent(isCurrentPeriodEmpty);
                break;
            case R.id.toolbar_main_resume:
                mPresenter.resumeCurrent();
                break;
            case R.id.toolbar_main_editbookname:
                mPresenter.changeAccountBookName();
                break;
            case R.id.toolbar_main_settings:
                startTransitionActivity(SettingsActivity.class);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void showNewAccountNameDialog() {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_account_name, null);
        EditText editText = dialogView.findViewById(R.id.editText_accountName);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle(R.string.change_book_name)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    String temp = editText.getText().toString();
                    LocalStorage.put(GlobalConstant.ACCOUNT_BOOK_NAME, temp);
                    String name = temp;
                    if (temp.length() > MAX_ACCOUNT_BOOK_NAME_SIZE) {
                        name = temp.substring(0, MAX_ACCOUNT_BOOK_NAME_SIZE - 1) + "...";
                    }
                    toolbarMain.setTitle(name);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.lack_of_condition)
                .setMessage(R.string.no_type_no_recording)
                .setPositiveButton(R.string.go, (dialog, which) -> startTransitionActivity(TypeEditActivity.class))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public void showSummaryDialog() {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_flow_summary, null);

        ImageView titleBackground = dialogView.findViewById(R.id.imageView_flow_title);
        TextView textViewDate = dialogView.findViewById(R.id.textView_date_text);
        TextView textViewTitle = dialogView.findViewById(R.id.textView_flow_summary_tile);
        TextView textViewSummary = dialogView.findViewById(R.id.textView_summary_text);
        setImageByGlide(titleBackground, R.drawable.bg_summary);

        textViewDate.setText(dateRangeText);
        String totalRepoBalance = "\n\n钱库总余额：" + mPresenter.getTotalRepoBalance();
        String summary = "共收入 " + incomeCount + " 笔，总计 " + totalIncome +
                "\n共支出 " + expenseCount + " 笔，总计 " + totalExpense + "\n结余 " + surplus +
                totalRepoBalance;
        if (incomeCount == 0 && expenseCount == 0) summary = getString(R.string.no_income_or_expense)
                + totalRepoBalance;

        textViewSummary.setText(summary);

        startAnimator(titleBackground, R.animator.anim_alpha_show);
        startAnimator(textViewTitle, R.animator.anim_downward_show);
        startAnimators(R.animator.anim_upward_show, textViewDate, textViewSummary);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton(R.string.close, null)
                .show();
    }

    @Override
    public void showDeleteAllDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.sure_delete_all_accounts)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    mPresenter.deleteAll();
                    postSticky(GlobalConstant.DELETE_ALL_ACCOUNT_ITEMS);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showDeleteCurrentPageDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.sure_delete_current_page_accounts)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    post(GlobalConstant.REQUEST_FLOW_DATE_RANGE);
                    mPresenter.deleteRecordByDate(start, end);
                    post(GlobalConstant.DELETE_CURRENT_PAGE_ACCOUNT_ITEMS);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showAccountInfoDialog(Object entity) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_account_info, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView textViewTitle = dialogView.findViewById(R.id.textView_accountInfo_title);
        TextView textViewDate = dialogView.findViewById(R.id.textView_accountInfo_date);
        TextView arrow = dialogView.findViewById(R.id.textView_accountInfo_arrow);
        TextView textViewTextInfo = dialogView.findViewById(R.id.textView_accountInfo_textInfo);
        ImageView imageViewMoneyType = dialogView.findViewById(R.id.imageView_accountInfo_moneyType);
        ImageView imageViewMoneyRepo = dialogView.findViewById(R.id.imageView_accountInfo_moneyRepoType);
        ImageView imageViewBackground = dialogView.findViewById(R.id.imageView_accountInfo_bg);
        FloatingActionButton fabEdit = dialogView.findViewById(R.id.fab_edit_account);

        String textInfo;
        String note;
        AccountPO record = (AccountPO) entity;

        note = record.note;
        if (record.note.isEmpty()) note = "这笔账没有备注...";
        textViewDate.setText(formatYearMonthDayHourMinInCHS(record.calendar));
        if (record.isExpense) {
            textInfo = "从 [" + record.moneyRepoType + "] 支出" + formatCurrency(record.amount)
                    + " 用于 [" + record.moneyType + "]。" + "\n摘要：" + note;
        } else {
            textInfo = "因 [" + record.moneyType + "] 收入" + formatCurrency(record.amount)
                    + " 并存入 [" + record.moneyRepoType + "]。" + "\n摘要：" + note;
            startAnimator(arrow, R.animator.anim_arrow_rotate);
        }

        setImageByGlide(imageViewMoneyType, record.moneyTypeImageId);
        setImageByGlide(imageViewMoneyRepo, record.moneyRepoTypeImageId);
        @DrawableRes int background = record.isExpense ? R.drawable.bg_account_expense :
                R.drawable.bg_account_income;
        setImageByGlide(imageViewBackground, background);
        textViewTextInfo.setText(textInfo);
        textViewTitle.setText(record.moneyType);

        startAnimators(R.animator.anim_downward_show, textViewTitle, textViewDate);
        startAnimators(R.animator.anim_upward_show, arrow, textViewTextInfo, imageViewMoneyType,
                imageViewMoneyRepo);
        startAnimator(fabEdit, R.animator.anim_slide_to_left);

        AlertDialog dialog = builder.setView(dialogView)
                .setPositiveButton(R.string.close, null)
                .show();

        fabEdit.setOnClickListener(v -> {
            boolean isMoneyTypeExist = mPresenter.isMoneyTypeExist(record.moneyType);
            boolean isRepoTypeExist = mPresenter.isRepoTypeExist(record.moneyRepoType);

            if (isMoneyTypeExist && isRepoTypeExist) {
                LocalStorage.put(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, true);
                // AddAccountActivity
                postSticky(record);
                dialog.dismiss();
                startTransitionActivity(AddAccountActivity.class);
            } else {
                dialog.dismiss();
                String message = "很遗憾，您暂时不能编辑这笔账目，这可能是因为 [" + record.moneyType + "] " +
                        "或着 [" + record.moneyRepoType + "] 类型已经被您删除了，您可以点击“前往”去重建缺失的相关类型。";
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.caution)
                        .setIcon(R.drawable.ic_dialog_warning)
                        .setMessage(message)
                        .setPositiveButton(R.string.go, (dialog1, which) -> startTransitionActivity(TypeEditActivity.class))
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });
    }


    // ----------------------------------------event------------------------------------------------
    @Subscribe
    public void onReceiveDateText(List<String> dateRangeTexts) {
        dateRangeText = dateRangeTexts.get(0);
        cancelEventDelivery(dateRangeTexts);
    }

    @Subscribe
    public void onReceiveDateRange(List<Calendar> calendars) {
        start = calendars.get(0);
        end = calendars.get(1);
        cancelEventDelivery(calendars);
    }

    @Subscribe
    public void onReceiveAccountList(List<AccountPO> poList) {
        totalIncome = formatCurrency(mPresenter.getTotalIncome(poList));
        totalExpense = formatCurrency(mPresenter.getTotalExpense(poList));
        surplus = formatCurrency(mPresenter.getSurplus(poList));
        incomeCount = mPresenter.getIncomeCount(poList);
        expenseCount = mPresenter.getExpenseCount(poList);
        cancelEventDelivery(poList);
    }

    @Subscribe
    public void onReceiveCurrentPageIsEmpty(String message) {
        if (message.equals(GlobalConstant.CURRENT_PAGE_IS_EMPTY)) {
            isCurrentPeriodEmpty = true;
            cancelEventDelivery(message);
        } else if (message.equals(GlobalConstant.CURRENT_PAGE_IS_NOT_EMPTY)) {
            isCurrentPeriodEmpty = false;
            cancelEventDelivery(message);
        }
    }
    // ---------------------------------------------------------------------------------------------

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    // Toolbar dynamic Changes.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mViewPager.getCurrentItem()) {
            case FIRST_PAGE:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(true);
                menu.findItem(R.id.toolbar_main_settings).setVisible(true);
                menu.findItem(R.id.toolbar_main_exit).setVisible(true);
                menu.setGroupVisible(R.id.toolbar_group_flow, false);
                break;
            case SECOND_PAGE:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);
                menu.findItem(R.id.toolbar_main_exit).setVisible(false);
                menu.setGroupVisible(R.id.toolbar_group_flow, true);
                break;
            case THIRD_PAGE:
                menu.findItem(R.id.toolbar_main_editbookname).setVisible(false);
                menu.findItem(R.id.toolbar_main_settings).setVisible(false);
                menu.findItem(R.id.toolbar_main_exit).setVisible(false);
                menu.setGroupVisible(R.id.toolbar_group_flow, false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected @LayoutRes int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected boolean isEventBusEnabled() {
        return true;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }
}