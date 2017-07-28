package com.newamber.gracebook.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.AddAccountPresenter;
import com.newamber.gracebook.util.DateUtil;
import com.newamber.gracebook.util.DeviceUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.other.Rotate3dAnimation;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import static com.newamber.gracebook.util.ColorUtil.setGradientColor;

public class AddAccountActivity extends BaseActivity<BaseView.AddAccountView, AddAccountPresenter>
        implements BaseView.AddAccountView {

    // constant
    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_add_account;
    private static final int FIRST_ITEM = 0;
    private int itemRepoSelected = FIRST_ITEM;
    private int flagRepo;
    private int itemMoneySelected = FIRST_ITEM;
    private int flagMoney;

    // judgement conditions
    private boolean isAmountCorrect;
    private boolean isAmountEmpty;
    private boolean isAmountLegal;
    private boolean isAmountZero;
    private boolean isAmountOverMax;
    private boolean isAmountOverBalance;
    private boolean isNoteInLimit;
    private boolean isExpense = true;

    // data to save
    String[] repoNameArray = new String[] {};
    String[] moneyNameArray = new String[] {};
    private Double amount;
    private String note, moneyRepoType, moneyType;
    private Calendar mCalendar = Calendar.getInstance();

    // View
    private TextInputLayout mBudgetInputLayout, mNoteInputLayout;
    private EditText mBudgetEditText, mNoteEditText;
    private Button mDateButton, mMoneyRepoButton, mMoneyTypeButton;
    private TextView mTextViewBudget;

    private AddAccountPresenter mPresenter;
    public boolean canSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                if (DeviceUtil.isSlowlyClick(800)) mPresenter.clickSaveButton();
                break;
            case R.id.cardview_budget_button:
                rotateView(v);
                break;
            case R.id.button_year_month_day:
                mPresenter.pickDate();
                break;
            case R.id.button_moneyRepoTypes:
                mPresenter.pickMoneyRepo();
                break;
            case R.id.button_moneyTypes:
                mPresenter.pickMoneyType();
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        mPresenter = getPresenter();
        moneyNameArray = mPresenter.getTypeNameArray(true);
        repoNameArray = mPresenter.getTypeNameArray(false);

        // ----------------------------findViewByID-------------------------------------------------
        mBudgetInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout_amount);
        mNoteInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout_note);
        CardView budgetCardView = (CardView) findViewById(R.id.cardview_budget_button);
        mBudgetEditText = (EditText) findViewById(R.id.editText_amount);
        mNoteEditText = (EditText) findViewById(R.id.editText_note);
        mDateButton = (Button) findViewById(R.id.button_year_month_day);
        mMoneyRepoButton = (Button) findViewById(R.id.button_moneyRepoTypes);
        mMoneyTypeButton = (Button) findViewById(R.id.button_moneyTypes);
        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        mTextViewBudget = (TextView) findViewById(R.id.textView_budget);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addAccount);

        // --------------------------------Toolbar--------------------------------------------------
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // --------------------------setOnClickListener---------------------------------------------
        fabSave.setOnClickListener(this);
        budgetCardView.setOnClickListener(this);
        mDateButton.setOnClickListener(this);
        mMoneyRepoButton.setOnClickListener(this);
        mMoneyTypeButton.setOnClickListener(this);

        // -----------------------------show text---------------------------------------------------
        // set text underline
        mDateButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mDateButton.getPaint().setAntiAlias(true);
        mMoneyRepoButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mMoneyRepoButton.getPaint().setAntiAlias(true);
        mMoneyTypeButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mMoneyTypeButton.getPaint().setAntiAlias(true);

        // initialize default data
        moneyRepoType = repoNameArray[FIRST_ITEM];
        moneyType = moneyNameArray[FIRST_ITEM];
        mDateButton.setText(DateUtil.getTodayInCHS());
        mMoneyRepoButton.setText(moneyRepoType);
        mMoneyTypeButton.setText(moneyType);
        startAnimator(mDateButton, R.animator.anim_downward_show);
        startAnimator(mMoneyRepoButton, R.animator.anim_downward_show);
        startAnimator(mMoneyTypeButton, R.animator.anim_downward_show);
    }

    @Override
    public void checkInput() {
        Double balance = mPresenter.getBalanceByName(moneyRepoType);
        Double tempExpense = 0d;

        // check budget amount
        String budgetAmount = mBudgetEditText.getText().toString();
        if (budgetAmount.isEmpty()) {
            isAmountEmpty = true;
            isAmountLegal = true;
            isAmountZero = false;
            isAmountOverMax = false;
            isAmountOverBalance = false;
        } else if (budgetAmount.length() == 1) {
            isAmountEmpty = false;
            isAmountOverMax = false;
            if (budgetAmount.contains(".")) {
                isAmountLegal = false;
                isAmountZero = false;
                isAmountOverBalance = false;
            } else if (budgetAmount.contains("0")) {
                isAmountLegal = true;
                isAmountZero = true;
                isAmountOverBalance = false;
            } else {
                isAmountLegal = true;
                isAmountZero = false;
                amount = Double.parseDouble(budgetAmount);
                if (isExpense) {
                    if (amount > balance) {
                        isAmountOverBalance = true;
                    } else {
                        isAmountOverBalance = false;
                        tempExpense = - amount;
                    }
                } else {
                    isAmountOverBalance = false;
                }
            }
        } else {
            isAmountLegal = true;
            isAmountEmpty = false;
            amount = Double.parseDouble(budgetAmount);
            if (amount == 0) {
                isAmountZero = true;
                isAmountOverMax = false;
                isAmountOverBalance = false;
            } else {
                isAmountZero = false;
                isAmountOverMax = amount > GlobalConstant.MAX_SINGLE_RECORD_AOMUNT;
                if (isExpense) {
                    if (amount > balance) {
                        isAmountOverBalance = true;
                    } else {
                        isAmountOverBalance = false;
                        tempExpense = - amount;
                    }
                } else {
                    isAmountOverBalance = false;
                }
            }
        }

        isAmountCorrect = !isAmountEmpty && !isAmountZero && !isAmountOverBalance
                && !isAmountOverMax && isAmountLegal;

        // check note
        note = mNoteEditText.getText().toString();
        isNoteInLimit = note.length() <= mNoteInputLayout.getCounterMaxLength();

        canSave = isAmountCorrect && isNoteInLimit;

        if (canSave) {
            if (isExpense) mPresenter.updateRepoBalance(moneyRepoType, tempExpense);
            else mPresenter.updateRepoBalance(moneyRepoType, amount);
        }
    }

    @Override
    public void setErrorHint() {
        if (!isAmountCorrect) {
            if (isAmountEmpty) mBudgetInputLayout.setError(getString(R.string.no_amount_input));
            if (isAmountZero) mBudgetInputLayout.setError(getString(R.string.amount_can_not_be_zero));
            if (!isAmountLegal) mBudgetInputLayout.setError(getString(R.string.plz_input_number));

            if (isAmountOverMax && isAmountOverBalance) {
                mBudgetInputLayout.setError(getString(R.string.over_repo_balance_and_over_single_max));
            } else {
                if (isAmountOverMax) mBudgetInputLayout.setError(getString(R.string.single_amount_is_too_large));
                if (isAmountOverBalance) mBudgetInputLayout.setError(getString(R.string.amount_over_balance));
            }
            startAnimator(mBudgetInputLayout, R.animator.anim_wrong_shake);
        } else {
            mBudgetInputLayout.setError("");
        }

        if (!isNoteInLimit) {
            mNoteInputLayout.setError(getString(R.string.over_words_limit));
            startAnimator(mNoteInputLayout, R.animator.anim_wrong_shake);
        } else {
            mNoteInputLayout.setError("");
        }
    }

    @Override
    public void showDatePicker() {
        hideSoftKeyboard();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String dateYMD = DateUtil.getYearMonthDayInCHS(mCalendar);
            mDateButton.setText(dateYMD);
            startAnimator(mDateButton, R.animator.anim_downward_show);
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void showMoneyRepoPicker() {
        hideSoftKeyboard();
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_repo_type)
                .setSingleChoiceItems(repoNameArray, itemRepoSelected, (dialog, which) -> flagRepo = which)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    moneyRepoType = repoNameArray[flagRepo];
                    mMoneyRepoButton.setText(moneyRepoType);
                    itemRepoSelected = flagRepo;
                    startAnimator(mMoneyRepoButton, R.animator.anim_downward_show);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showMoneyTypePicker() {
        hideSoftKeyboard();
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_budget_type)
                .setSingleChoiceItems(moneyNameArray, itemMoneySelected, (dialog, which) -> flagMoney = which)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    moneyType = moneyNameArray[flagMoney];
                    mMoneyTypeButton.setText(moneyType);
                    itemMoneySelected = flagMoney;
                    startAnimator(mMoneyTypeButton, R.animator.anim_downward_show);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    // It doesn't belongs to View operation so I don't want to define
    // it in View interface, but it has to get data in Activity.
    public void saveRecord() {
        // update in DB
        @DrawableRes int moneyTypeImageId = mPresenter.getMoneyImageByName(moneyType);
        @DrawableRes int moneyRepoTypeImageId = mPresenter.getMoneyRepoImageByName(moneyRepoType);

        mPresenter.saveInDB(amount, note, moneyRepoType, moneyType, isExpense, mCalendar,
                moneyTypeImageId, moneyRepoTypeImageId);
        // update in memory
        AccountPO record = new AccountPO();
        record.amount = amount;
        record.note = note;
        record.moneyRepoType = moneyRepoType;
        record.moneyType = moneyType;
        record.budget = isExpense;
        record.calendar = mCalendar;
        record.moneyTypeImageId = moneyTypeImageId;
        record.moneyRepoTypeImageId = moneyRepoTypeImageId;
        EventBus.getDefault().postSticky(record);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return new AddAccountPresenter();
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    private void rotateView(View v) {
        int duration = 1000;
        int centerX = v.getWidth() / 2;
        int centerY = v.getHeight() / 2;
        Interpolator interpolator = new AnticipateOvershootInterpolator();

        // rotation
        Rotate3dAnimation animRight = new Rotate3dAnimation(0f, 360f, centerX, centerY, 0, true);
        animRight.setDuration(duration);
        animRight.setInterpolator(interpolator);
        Rotate3dAnimation animLeft = new Rotate3dAnimation(360f, 0f, centerX, centerY, 0, true);
        animLeft.setDuration(duration);
        animLeft.setInterpolator(interpolator);
        // alpha
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_show_budget);
        animator.setTarget(mTextViewBudget);

        if (isExpense) {
            v.startAnimation(animRight);
            mTextViewBudget.setText(R.string.income);
            animator.start();
            setGradientColor(v, R.color.colorRedButton, R.color.colorGreenButton, duration);
            isExpense = false;
        } else {
            v.startAnimation(animLeft);
            mTextViewBudget.setText(R.string.expense);
            animator.start();
            setGradientColor(v, R.color.colorGreenButton, R.color.colorRedButton, duration);
            isExpense = true;
        }
    }

    // TODO: useless
    private void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            im.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }
}