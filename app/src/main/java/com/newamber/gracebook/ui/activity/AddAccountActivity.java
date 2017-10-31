package com.newamber.gracebook.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.support.animation.SpringAnimation;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.AddAccountPresenter;
import com.newamber.gracebook.util.DeviceUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.util.event.UpdateDayItemEvent;
import com.newamber.gracebook.util.event.UpdateFlowItemEvent;
import com.newamber.gracebook.util.other.Rotate3dAnimation;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import static com.newamber.gracebook.util.ColorUtil.setGradientColor;
import static com.newamber.gracebook.util.DateUtil.formatTodayInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthDayInCHS;
import static com.newamber.gracebook.util.DeviceUtil.isNormalClickSpeed;
import static com.newamber.gracebook.util.DeviceUtil.isSlowlyClick;
import static com.newamber.gracebook.util.NumericUtil.formatAmount;

public class AddAccountActivity extends BaseActivity<IBaseView.AddAccountView, AddAccountPresenter>
        implements IBaseView.AddAccountView {

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

    private String[] repoNameArray;
    private String[] moneyNameArray;
    // data to save in DB
    private Double amount, initAmount;
    private String note, moneyRepoType, moneyType;
    private Calendar mCalendar = Calendar.getInstance();
    private AccountPO mRecord;
    private boolean isExpense = true, initIsExpense;

    // View
    private TextInputLayout mAmountInputLayout, mNoteInputLayout;
    private EditText mAmountEditText, mNoteEditText;
    private Button mDateButton, mMoneyRepoButton, mMoneyTypeButton;
    private TextView mTextViewBudget;
    private CardView mBudgetCardView;
    private ImageView mImageViewAmount, mImageViewNote;

    private AddAccountPresenter mPresenter;
    public boolean canSave;

    @Override
    public void initViews() {
        mPresenter = getPresenter();
        moneyNameArray = mPresenter.getArrayOfTypeName(true);
        repoNameArray = mPresenter.getArrayOfTypeName(false);

        // findViewByID
        mAmountInputLayout = findViewById(R.id.textInputLayout_amount);
        mNoteInputLayout = findViewById(R.id.textInputLayout_note);
        mAmountEditText = findViewById(R.id.editText_amount);
        mNoteEditText = findViewById(R.id.editText_note);
        mDateButton = findViewById(R.id.button_year_month_day);
        mMoneyRepoButton = findViewById(R.id.button_moneyRepoTypes);
        mMoneyTypeButton = findViewById(R.id.button_moneyTypes);
        mTextViewBudget = findViewById(R.id.textView_budget);
        mBudgetCardView = findViewById(R.id.cardview_budget_button);
        mImageViewAmount = findViewById(R.id.imageView_icon_amount);
        mImageViewNote = findViewById(R.id.imageView_icon_note);
        Toolbar toolbar = findViewById(R.id.toolbar_addAccount);
        FloatingActionButton fabSave = findViewById(R.id.fab_save);

        // Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        if (isEditMode()) toolbar.setTitle(R.string.edit_account);

        // setOnClickListener
        bindOnClickListener(fabSave, mBudgetCardView, mDateButton, mMoneyRepoButton, mMoneyTypeButton);

        // set text underline
        mDateButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mDateButton.getPaint().setAntiAlias(true);
        mMoneyRepoButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mMoneyRepoButton.getPaint().setAntiAlias(true);
        mMoneyTypeButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mMoneyTypeButton.getPaint().setAntiAlias(true);

        startAnimators(R.animator.anim_downward_show, mDateButton, mMoneyRepoButton, mMoneyTypeButton);

        // initialize default data
        moneyRepoType = repoNameArray[FIRST_ITEM];
        moneyType = moneyNameArray[FIRST_ITEM];
        mDateButton.setText(formatTodayInCHS());
        mMoneyRepoButton.setText(moneyRepoType);
        mMoneyTypeButton.setText(moneyType);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                if (isSlowlyClick(900)) mPresenter.saveRecord();
                break;
            case R.id.cardview_budget_button:
                rotateYOfView(v);
                break;
            case R.id.button_year_month_day:
                if (isNormalClickSpeed()) mPresenter.pickDate();
                break;
            case R.id.button_moneyRepoTypes:
                if (isNormalClickSpeed()) mPresenter.pickMoneyRepo();
                break;
            case R.id.button_moneyTypes:
                if (isNormalClickSpeed()) mPresenter.pickMoneyType();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.toolbar_add_account_reset) {
            mPresenter.resetPage();
        }
        return true;
    }

    @Override
    public void checkInput() {
        Double balance = mPresenter.getBalanceByName(moneyRepoType);
        if (isEditMode()) {
            balance = initIsExpense ? balance + initAmount : balance - initAmount;
        }

        // check amount
        String budgetAmount = mAmountEditText.getText().toString();
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
                isAmountOverBalance = isExpense && amount > balance;
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
                isAmountOverMax = amount > GlobalConstant.MAX_SINGLE_RECORD_AMOUNT;
                isAmountOverBalance = isExpense && amount > balance;
            }
        }

        isAmountCorrect = !isAmountEmpty && !isAmountZero && !isAmountOverBalance
                && !isAmountOverMax && isAmountLegal;

        // check note
        note = mNoteEditText.getText().toString();
        isNoteInLimit = note.length() <= mNoteInputLayout.getCounterMaxLength();

        canSave = isAmountCorrect && isNoteInLimit;
    }

    @Override
    public void showErrorHint() {
        if (!isAmountCorrect) {
            if (isAmountEmpty) mAmountInputLayout.setError(getString(R.string.no_amount_input));
            if (isAmountZero) mAmountInputLayout.setError(getString(R.string.amount_can_not_be_zero));
            if (!isAmountLegal) mAmountInputLayout.setError(getString(R.string.plz_input_number));
            if (isAmountOverMax && isAmountOverBalance) {
                mAmountInputLayout.setError(getString(R.string.over_repo_balance_and_over_single_max));
            } else {
                if (isAmountOverMax) mAmountInputLayout.setError(getString(R.string.single_amount_is_too_large));
                if (isAmountOverBalance) mAmountInputLayout.setError(getString(R.string.amount_over_balance));
            }
            bounceAnim(mAmountInputLayout);
            bounceAnim(mImageViewAmount);
        } else {
            mAmountInputLayout.setError("");
        }

        if (!isNoteInLimit) {
            mNoteInputLayout.setError(getString(R.string.over_words_limit));
            bounceAnim(mNoteInputLayout);
            bounceAnim(mImageViewNote);
        } else {
            mNoteInputLayout.setError("");
        }
    }

    @Override
    public void showDatePicker() {
        hideSoftKeyboards();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // This is a bug of DBflow. In 1974 and 1970, it can not
            // show user's account items correctly.
            if (year < 1975) {
                ToastUtil.showShort(R.string.error_can_not_choose_year_before_1975, ToastUtil.ToastMode.WARNING);
            } else {
                mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mDateButton.setText(formatYearMonthDayInCHS(mCalendar));
                startAnimator(mDateButton, R.animator.anim_downward_show);
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void showMoneyRepoPicker() {
        hideSoftKeyboards();
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_repo_type)
                .setSingleChoiceItems(repoNameArray, itemRepoSelected, (dialog, which) -> flagRepo = which)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
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
        hideSoftKeyboards();
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_budget_type)
                .setSingleChoiceItems(moneyNameArray, itemMoneySelected, (dialog, which) -> flagMoney = which)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    moneyType = moneyNameArray[flagMoney];
                    mMoneyTypeButton.setText(moneyType);
                    itemMoneySelected = flagMoney;
                    startAnimator(mMoneyTypeButton, R.animator.anim_downward_show);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void resetContent() {
        mAmountEditText.setText("");
        mNoteEditText.setText("");

        if (!isExpense) rotateYOfView(mBudgetCardView);

        mCalendar = Calendar.getInstance();
        String dateYMD = formatYearMonthDayInCHS(mCalendar);

        mDateButton.setText(dateYMD);
        startAnimator(mDateButton, R.animator.anim_downward_show);

        flagRepo = FIRST_ITEM;
        moneyRepoType = repoNameArray[flagRepo];
        mMoneyRepoButton.setText(moneyRepoType);
        itemRepoSelected = flagRepo;
        startAnimator(mMoneyRepoButton, R.animator.anim_downward_show);

        flagMoney = FIRST_ITEM;
        moneyType = moneyNameArray[flagMoney];
        mMoneyTypeButton.setText(moneyType);
        itemMoneySelected = flagMoney;
        startAnimator(mMoneyTypeButton, R.animator.anim_downward_show);
    }

    // It doesn't belongs to View operation so I don't want to define
    // it in View interface, but it has to get data in Activity.
    public void saveRecord() {
        @DrawableRes int moneyTypeImageId = mPresenter.getMoneyImageByName(moneyType);
        @DrawableRes int moneyRepoTypeImageId = mPresenter.getMoneyRepoImageByName(moneyRepoType);

        if (LocalStorage.getBoolean(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, false)) {
            // edit mode
            Double oldAmount = mRecord.isExpense ? mRecord.amount : - mRecord.amount;
            mPresenter.updateRepoBalance(mRecord.moneyRepoType, oldAmount);

            mRecord.amount = amount;
            mRecord.note = note;
            mRecord.moneyRepoType = moneyRepoType;
            mRecord.moneyType = moneyType;
            mRecord.isExpense = isExpense;
            mRecord.calendar = mCalendar;
            mRecord.moneyTypeImageId = moneyTypeImageId;
            mRecord.moneyRepoTypeImageId = moneyRepoTypeImageId;
            mRecord.update();

            Double newAmount = mRecord.isExpense ? - mRecord.amount : mRecord.amount;
            mPresenter.updateRepoBalance(mRecord.moneyRepoType, newAmount);

            postSticky(new UpdateDayItemEvent());
            postSticky(new UpdateFlowItemEvent());
            disableEditMode();
        } else {
            // new item mode
            // update in DB
            mPresenter.saveInDB(amount, note, moneyRepoType, moneyType, isExpense, mCalendar,
                    moneyTypeImageId, moneyRepoTypeImageId);

            // update in memory
            mRecord = new AccountPO();
            mRecord.amount = amount;
            mRecord.note = note;
            mRecord.moneyRepoType = moneyRepoType;
            mRecord.moneyType = moneyType;
            mRecord.isExpense = isExpense;
            mRecord.calendar = mCalendar;
            mRecord.moneyTypeImageId = moneyTypeImageId;
            mRecord.moneyRepoTypeImageId = moneyRepoTypeImageId;

            Double newAmount =  mRecord.isExpense ? - mRecord.amount : mRecord.amount;
            mPresenter.updateRepoBalance(mRecord.moneyRepoType, newAmount);

            Calendar today = Calendar.getInstance();
            Calendar recordDate = mRecord.calendar;

            int todayYear = today.get(Calendar.YEAR);
            int todayMonth = today.get(Calendar.MONTH);
            int todayDays = today.get(Calendar.DAY_OF_MONTH);
            int recordYear = recordDate.get(Calendar.YEAR);
            int recordMonth = recordDate.get(Calendar.MONTH);
            int recordDays = recordDate.get(Calendar.DAY_OF_MONTH);

            disableEditMode();
            // Only If this record belongs to today, it would update the DayFragment.
            if (todayYear == recordYear && todayMonth == recordMonth && todayDays == recordDays) {
                // To: DayFragment
                postSticky(mRecord);
            } else {
                if (LocalStorage.getBoolean(GlobalConstant.IS_FIRST_RECORD_PASS_OR_FUTURE_ACCOUNT, true)) {
                    ToastUtil.showLong(R.string.first_record_not_today_account_hint, ToastMode.SUCCESS);
                    LocalStorage.put(GlobalConstant.IS_FIRST_RECORD_PASS_OR_FUTURE_ACCOUNT, false);
                } else {
                    ToastUtil.showShort(R.string.new_account_success, ToastMode.SUCCESS);
                }
            }
            // To: FlowFragment
            postSticky(GlobalConstant.FLOW_ADD_NEW_ACCOUNT_ITEM);
            LocalStorage.put(GlobalConstant.LAST_RECORD_TIME, formatTodayInCHS());
        }
    }

    // ---------------------------------------event-------------------------------------------------
    @Subscribe(sticky = true)
    @SuppressWarnings("unused")
    public void onEditAccount(AccountPO record) {
        if (isEditMode()) {
            final int DURATION = 900;
            mRecord = record;
            this.amount= mRecord.amount;
            initAmount = mRecord.amount;
            initIsExpense = mRecord.isExpense;
            this.note = mRecord.note;
            this.mCalendar = mRecord.calendar;
            this.moneyRepoType = mRecord.moneyRepoType;
            this.moneyType = mRecord.moneyType;

            startAnimators(R.animator.anim_downward_show, mDateButton, mMoneyRepoButton, mMoneyTypeButton);

            mAmountEditText.setText(formatAmount(amount));
            mNoteEditText.setText(note);
            if (!mRecord.isExpense) {
                startAnimator(mTextViewBudget, R.animator.anim_show_budget);
                mTextViewBudget.setText(R.string.income);
                setGradientColor(mBudgetCardView, R.color.colorRedButton, R.color.colorGreenButton, DURATION);
                isExpense = false;
            }
            mDateButton.setText(formatYearMonthDayInCHS(mCalendar));
            mMoneyRepoButton.setText(moneyRepoType);
            mMoneyTypeButton.setText(moneyType);
            itemMoneySelected = mPresenter.getTypePosition(moneyType, moneyNameArray);
            itemRepoSelected = mPresenter.getTypePosition(moneyRepoType, repoNameArray);
            removeStickyEvent(record);
        }
    }
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_account, menu);
        return true;
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
    public void onBackPressed() {
        super.onBackPressed();
        LocalStorage.put(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, false);
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return new AddAccountPresenter();
    }

    // --------------------------------------private API--------------------------------------------
    private void rotateYOfView(View v) {
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

        if (isExpense) {
            v.startAnimation(animRight);
            mTextViewBudget.setText(R.string.income);
            setGradientColor(v, R.color.colorRedButton, R.color.colorGreenButton, duration);
            isExpense = false;
        } else {
            v.startAnimation(animLeft);
            mTextViewBudget.setText(R.string.expense);
            setGradientColor(v, R.color.colorGreenButton, R.color.colorRedButton, duration);
            isExpense = true;
        }
        startAnimator(mTextViewBudget, R.animator.anim_show_budget);
    }

    private void bounceAnim(View v) {
        // offset left
        startAnimator(v, R.animator.anim_wrong_shake);
        // bounce
        new Handler().postDelayed(() -> {
            SpringAnimation bounceAnim = new SpringAnimation(v, SpringAnimation.TRANSLATION_X, 0);
            bounceAnim.getSpring().setDampingRatio(0.07f);
            bounceAnim.getSpring().setStiffness(1300f);
            bounceAnim.setStartValue(DeviceUtil.dp2Px(-15));
            bounceAnim.start();
        }, 200);
    }

    private void hideSoftKeyboards() {
        hideSoftKeyboard(mNoteEditText);
        hideSoftKeyboard(mAmountEditText);
    }

    private boolean isEditMode() {
        return LocalStorage.getBoolean(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, false);
    }

    private void disableEditMode() {
        LocalStorage.put(GlobalConstant.IS_EDIT_ACCOUNT_ITEM_MODE, false);
    }

    // TODO: useless
    private void hideSoftKeyboard(EditText editText) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im.isActive()) {
            im.hideSoftInputFromInputMethod(editText.getWindowToken(), 0);
        }
    }
}