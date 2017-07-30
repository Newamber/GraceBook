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
import com.tomer.fadingtextview.FadingTextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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

    TextView mTextViewIncome;
    TextView mTextViewExpense;
    TextView mTextViewSurplus;

    private String[] message;
    private List<AccountPO> POList;
    private AccountTodayAdapter mAdapter;

    @Override
    public void initView() {
        // findViewById
        CardView todayCardView = findView(R.id.cardview_big);
        FadingTextView fadingTextView = findView(R.id.fadeTextView_today);
        RecyclerView recyclerView = findView(R.id.recyclerview_day);
        mTextViewIncome = findView(R.id.textview_today_income);
        mTextViewExpense = findView(R.id.textview_today_expense);
        mTextViewSurplus = findView(R.id.textview_today_surplus);

        // data source
        message = new String[] {
                DateUtil.getTodayWithWeekInCHS(),
                getString(R.string.day_banner_text_1),
                getString(R.string.day_banner_text_2)
        };
        POList = getHostPresenter().getRecordToday();
        mAdapter = new AccountTodayAdapter(POList, ITEM_LAYOUT_ID);
        mAdapter.setOnLongClickListener((view, entity, position) -> {
            //post();

        });

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
        removeStickyEvent(record);
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    private void updateBigCard() {
        Double expense = 0d;
        Double income = 0d;
        Double surplus;
        for(AccountPO record : POList) {
            if (record.budget)
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
            mTextViewSurplus.setTextColor(ColorUtil.getColor(R.color.colorTodayIncome));
        else
            mTextViewSurplus.setTextColor(ColorUtil.getColor(R.color.colorTodayExpense));

        startAnimator(mTextViewIncome, R.animator.anim_upward_show);
        startAnimator(mTextViewExpense, R.animator.anim_upward_show);
        startAnimator(mTextViewSurplus, R.animator.anim_upward_show);
    }
}