package com.newamber.gracebook.ui.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.AccountTodayAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.util.DateUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.util.event.UpdateDayIconEvent;
import com.newamber.gracebook.util.event.UpdateDayItemEvent;
import com.tomer.fadingtextview.FadingTextView;

import org.greenrobot.eventbus.Subscribe;

import static com.newamber.gracebook.util.ColorUtil.getColor;
import static com.newamber.gracebook.util.NumericUtil.add;
import static com.newamber.gracebook.util.NumericUtil.formatCurrency;
import static com.newamber.gracebook.util.NumericUtil.subtract;

/**
 * DayFragment which show the account item of every day.
 * It will auto update data in second day.
 */
@SuppressWarnings("unused")
public class DayFragment extends BaseFragment<MainPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_day;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_record_today;

    private TextView mTextViewIncome;
    private TextView mTextViewExpense;
    private TextView mTextViewSurplus;

    private String[] message;
    private AccountTodayAdapter mAdapter;

    @Override
    public void initView() {
        // findViewById
        CardView todayCardView = findViewById(R.id.cardview_big);
        FadingTextView fadingTextView = findViewById(R.id.fadeTextView_today);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_day);
        mTextViewIncome = findViewById(R.id.textview_today_income);
        mTextViewExpense = findViewById(R.id.textview_today_expense);
        mTextViewSurplus = findViewById(R.id.textview_today_surplus);

        // data source
        message = new String[] {
                DateUtil.formatTodayWithWeekInCHS(),
                getString(R.string.day_banner_text_1),
                getString(R.string.day_banner_text_2)
        };
        mAdapter = new AccountTodayAdapter(getHostPresenter().getRecordOfToday(), ITEM_LAYOUT_ID);
        mAdapter.setOnClickListener((view, entity, position) -> getHostPresenter().showAccountInfo(entity));

        // show view
        if (!mAdapter.isEmpty()) {
            message[1] = getString(R.string.keep_accounting_is_important);
            message[2] = getString(R.string.wish_you_prosperity);
        }
        updateBigCard();
        fadingTextView.setTexts(message);

        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
        setCompressEffect(false, todayCardView);
    }

    @Override
    public void processClick(View v) {}

    // -------------------------------------------event---------------------------------------------
    @Subscribe(sticky = true)
    public void onAddAccount(AccountPO record) {
        if (!LocalStorage.getBoolean(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, false)) {
            mAdapter.add(record);
            message[1] = getString(R.string.keep_accounting_is_important);
            message[2] = getString(R.string.wish_you_prosperity);
            updateBigCard();
            ToastUtil.showShort(R.string.new_account_success, ToastMode.SUCCESS);
            removeStickyEvent(record);
        }
    }

    @Subscribe(sticky = true)
    public void onUpdateAccount(UpdateDayItemEvent event) {
        mAdapter.setEntityList(getHostPresenter().getRecordOfToday());
        updateBigCard();
        ToastUtil.showShort(R.string.update_account_success, ToastMode.SUCCESS);
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true)
    public void onUpdateAccountIcon(UpdateDayIconEvent event) {
        mAdapter.setEntityList(getHostPresenter().getRecordOfToday());
        removeStickyEvent(event);
    }

    @Subscribe(sticky = true, priority = 2)
    public void onDeleteAccount(String message) {
        if (message.equals(GlobalConstant.DELETE_ALL_ACCOUNT_ITEMS)
                || message.equals(GlobalConstant.DELETE_CURRENT_PAGE_ACCOUNT_ITEMS)) {
            mAdapter.setEntityList(getHostPresenter().getRecordOfToday());
            updateBigCard();
        }
    }
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected boolean isEventBusEnabled() {
        return true;
    }

    // -------------------------------------private API---------------------------------------------
    private void updateBigCard() {
        Double expense = 0d;
        Double income = 0d;
        Double surplus;
        for(AccountPO record : mAdapter.getEntityList()) {
            if (record.isExpense)
                expense = add(expense, record.amount);
            else
                income =  add(income, record.amount);
        }
        boolean greaterThanZero = subtract(income, expense) >= 0;
        surplus = Math.abs(subtract(income, expense));

        mTextViewIncome.setText(formatCurrency(income));
        mTextViewExpense.setText(formatCurrency(expense));
        mTextViewSurplus.setText(formatCurrency(surplus));
        if (greaterThanZero)
            mTextViewSurplus.setTextColor(getColor(R.color.colorTodayIncome));
        else
            mTextViewSurplus.setTextColor(getColor(R.color.colorTodayExpense));

        startAnimators(R.animator.anim_upward_show, mTextViewIncome, mTextViewExpense, mTextViewSurplus);
    }
}