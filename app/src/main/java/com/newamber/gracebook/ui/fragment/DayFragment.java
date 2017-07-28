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
import com.newamber.gracebook.util.ColorUtil;
import com.newamber.gracebook.util.DateUtil;
import com.newamber.gracebook.util.NumericUtil;
import com.tomer.fadingtextview.FadingTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * DayFragment which show the account item of every day.
 * It will auto update data in second day.
 */
@SuppressWarnings("unused")
public class DayFragment extends BaseFragment<MainPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_day;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_record_card;

    TextView textViewIncome;
    TextView textViewExpense;
    TextView textViewSurplus;

    private String[] message;
    private List<AccountPO> POList;
    private AccountTodayAdapter mAdapter;

    boolean greaterThanZero;
    Double income, expense, surplus;

    @Override
    public void initView() {
        // findViewById
        CardView todayCardView = (CardView) getRootView().findViewById(R.id.cardview_big);
        FadingTextView fadingTextView = (FadingTextView) getRootView().findViewById(R.id.fadeTextView_today);
        RecyclerView recyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerview_day);
        textViewIncome = (TextView) getRootView().findViewById(R.id.textview_today_income);
        textViewExpense = (TextView) getRootView().findViewById(R.id.textview_today_expense);
        textViewSurplus = (TextView) getRootView().findViewById(R.id.textview_today_surplus);

        // data source
        message = new String[] {
                DateUtil.getTodayWithWeekInCHS(),
                getString(R.string.day_banner_text_1),
                getString(R.string.day_banner_text_2)
        };
        POList = getHostPresenter().getRecordToday();
        mAdapter = new AccountTodayAdapter(POList, ITEM_LAYOUT_ID);

        // show view
        if (!mAdapter.isEmpty()) message[1] = getString(R.string.keep_accounting_is_important);
        updateBigCard();
        fadingTextView.setTexts(message);

        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
        setCompressEffect(false, todayCardView);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected boolean isEnabledEventBus() {
        return true;
    }

    @Subscribe(sticky = true)
    public void onAddAccountEvent(AccountPO record) {
        mAdapter.add(record);
        message[1] = getString(R.string.keep_accounting_is_important);
        updateBigCard();
        EventBus.getDefault().removeStickyEvent(record);
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    private void updateBigCard() {
        expense = 0d;
        income = 0d;
        surplus = 0d;
        for(AccountPO record : POList) {
            if (record.budget)
                expense = NumericUtil.add(expense, record.amount);
            else
                income =  NumericUtil.add(income, record.amount);
        }
        greaterThanZero = NumericUtil.subtract(income, expense) >= 0;
        surplus = Math.abs(NumericUtil.subtract(income, expense));

        textViewIncome.setText(NumericUtil.getCurrencyFormat(income));
        textViewExpense.setText(NumericUtil.getCurrencyFormat(expense));
        textViewSurplus.setText(NumericUtil.getCurrencyFormat(surplus));
        if (greaterThanZero)
            textViewSurplus.setTextColor(ColorUtil.getColor(R.color.colorTodayIncome));
        else
            textViewSurplus.setTextColor(ColorUtil.getColor(R.color.colorTodayExpense));

        startAnimator(textViewIncome, R.animator.anim_upward_show);
        startAnimator(textViewExpense, R.animator.anim_upward_show);
        startAnimator(textViewSurplus, R.animator.anim_upward_show);
    }
}