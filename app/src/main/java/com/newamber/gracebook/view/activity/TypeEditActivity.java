package com.newamber.gracebook.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseListViewAdapter;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.adapter.TypeEditViewPagerAdapter;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.SpinnerTypeIcon;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.view.fragment.MoneyRepoTypeFragment;
import com.newamber.gracebook.view.fragment.MoneyTypeFragment;

import java.util.ArrayList;
import java.util.List;

import static com.newamber.gracebook.R.drawable.ic_internet_62;
import static com.newamber.gracebook.R.drawable.ic_love_02;
import static com.newamber.gracebook.R.drawable.ic_love_10;
import static com.newamber.gracebook.R.drawable.ic_love_13;
import static com.newamber.gracebook.R.drawable.ic_love_27;
import static com.newamber.gracebook.R.drawable.ic_love_82;
import static com.newamber.gracebook.R.drawable.ic_love_83;
import static com.newamber.gracebook.R.drawable.ic_love_85;
import static com.newamber.gracebook.R.drawable.ic_office_03;
import static com.newamber.gracebook.R.drawable.ic_office_07;
import static com.newamber.gracebook.R.drawable.ic_office_102;
import static com.newamber.gracebook.R.drawable.ic_office_104;
import static com.newamber.gracebook.R.drawable.ic_office_119;
import static com.newamber.gracebook.R.drawable.ic_office_12;
import static com.newamber.gracebook.R.drawable.ic_office_13;
import static com.newamber.gracebook.R.drawable.ic_office_20;
import static com.newamber.gracebook.R.drawable.ic_office_28;
import static com.newamber.gracebook.R.drawable.ic_office_52;
import static com.newamber.gracebook.R.drawable.ic_office_54;
import static com.newamber.gracebook.R.drawable.ic_office_63;
import static com.newamber.gracebook.R.drawable.ic_office_73;
import static com.newamber.gracebook.R.drawable.ic_sport_22;
import static com.newamber.gracebook.R.drawable.ic_sport_96;
import static com.newamber.gracebook.R.drawable.ic_transport_17;
import static com.newamber.gracebook.R.drawable.ic_transport_96;
import static com.newamber.gracebook.R.drawable.ic_travel_60;

public class TypeEditActivity extends BaseActivity<BaseView.TypeEditView, TypeEditPresenter>
        implements BaseView.TypeEditView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_type_edit;

    private @DrawableRes int iconId;
    private BaseAdapter adapter;
    private ViewPager mViewPager;
    private TypeEditPresenter mEditPresenter;
    private List<SpinnerTypeIcon> mSpinnerTypeIconList;

    public MoneyTypeFragment mMoneyTypeFragment;
    public MoneyRepoTypeFragment mMoneyRepoTypeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditPresenter = getPresenter();
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void initView() {
        // -----------------------------findViewById------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_typeEdit);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_typeEdit);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_typeEdit);

        // ---------------------------Toolbar-------------------------------------------------------
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // -----------------------------ViewPager---------------------------------------------------
        List<Fragment> fragmentList = new ArrayList<>();
        mMoneyTypeFragment = new MoneyTypeFragment();
        mMoneyRepoTypeFragment = new MoneyRepoTypeFragment();
        fragmentList.add(mMoneyTypeFragment);
        fragmentList.add(mMoneyRepoTypeFragment);

        TypeEditViewPagerAdapter pagerAdapter = new TypeEditViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        // -----------------------------SpinnerAdapter----------------------------------------------
        initIconSpinnerDropDown();
        adapter = new BaseListViewAdapter<SpinnerTypeIcon>(mSpinnerTypeIconList, R.layout.spinner_item_icons) {
            @Override
            protected void bindView(ViewHolder holder, SpinnerTypeIcon entity) {
                holder.setImageResource(R.id.imageView_spinner_item_icon, entity.getIconId());
                holder.setText(R.id.textView_placeholder, entity.getPlaceholder());
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_type_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = mViewPager.getCurrentItem();
        switch (item.getItemId()) {
            case R.id.toolbar_typeEdit_delete:
                break;
            case R.id.toolbar_typeEdit_new:
                mEditPresenter.showNewTypeDialog(position);
                break;
            case R.id.toolbar_typeEdit_deleteAll:
                mEditPresenter.showDeleteAllDialog(position);
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }


    @Override
    @SuppressWarnings("all")
    public void showMoneyTypeDialog() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_bounce_show);
        mEditPresenter.isMoneyType = true;
        AlertDialog.Builder dialog = new AlertDialog.Builder(TypeEditActivity.this);
        View containerView = LayoutInflater.from(this).inflate(R.layout.dialog_money_type, null);

        EditText editText = (EditText) containerView.findViewById(R.id.editText_moneyTypeName);
        ImageView imageViewIcon = (ImageView) containerView.findViewById(R.id.imageView_iconSelected);
        TextInputLayout textInputLayout = (TextInputLayout) containerView
                .findViewById(R.id.textInputLayout_moneyTypeName);
        AppCompatSpinner spinnerMoneyType = (AppCompatSpinner) containerView
                .findViewById(R.id.spinner_typeEdit_moneyType);

        spinnerMoneyType.setAdapter(adapter);
        spinnerMoneyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                iconId = entity.getIconId();
                animator.setTarget(imageViewIcon);
                animator.start();
                imageViewIcon.setBackgroundResource(entity.getIconId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.setTitle(R.string.new_type)
                .setView(containerView)
                .setCancelable(false)
                .setPositiveButton(R.string.sure, (dialog1, which) -> {
                    String name = editText.getText().toString();
                    if (name.isEmpty()) {
                        ToastUtil.showShort(R.string.no_name_no_type, ToastMode.WARNING);
                    } else if (name.length() > textInputLayout.getCounterMaxLength()) {
                        ToastUtil.showShort(R.string.over_words_limit, ToastMode.WARNING);
                    } else {
                        mEditPresenter.saveData(name, iconId, 0);

                        MoneyTypePO moneyTypePO = new MoneyTypePO();
                        moneyTypePO.moneyTypeName = name;
                        moneyTypePO.moneyTypeImageId = iconId;
                        mMoneyTypeFragment.mPOList.add(moneyTypePO);

                        int postion = mMoneyTypeFragment.mAdapter.getItemCount();
                        mMoneyTypeFragment.mAdapter.notifyItemInserted(postion);
                        ToastUtil.showShort(R.string.new_type_success, ToastMode.SUCCESS);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
    }

    @Override
    @SuppressWarnings("all")
    public void showMoneyRepoTypeDialog() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_bounce_show);
        mEditPresenter.isMoneyType = false;
        AlertDialog.Builder dialog = new AlertDialog.Builder(TypeEditActivity.this);
        View containerView =  LayoutInflater.from(this).inflate(R.layout.dialog_money_repo_type, null);

        EditText editTextName = (EditText) containerView.findViewById(R.id.editText_moneyRepoTypeName);
        EditText editTextBalance = (EditText) containerView.findViewById(R.id.editText_moneyRepoTypeBalance);
        ImageView imageViewIcon = (ImageView) containerView.findViewById(R.id.imageView_iconRepoSelected);
        TextInputLayout textInputLayout = (TextInputLayout) containerView
                .findViewById(R.id.textInputLayout_moneyTypeRepoName);
        AppCompatSpinner spinnerMoneyRepoType = (AppCompatSpinner) containerView
                .findViewById(R.id.spinner_typeEdit_moneyRepoType);

        spinnerMoneyRepoType.setAdapter(adapter);
        spinnerMoneyRepoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                iconId = entity.getIconId();
                animator.setTarget(imageViewIcon);
                animator.start();
                imageViewIcon.setBackgroundResource(entity.getIconId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.setTitle(R.string.new_repo_type)
                .setView(containerView)
                .setPositiveButton(R.string.sure, (dialog1, which) -> {
                    String name = editTextName.getText().toString();
                    String balance = editTextBalance.getText().toString();
                    if (balance.isEmpty()) balance = "0";
                    if (balance.length() == 1) {
                       if (balance.contains(".")) balance = "0";
                   }
                   Double balanceAmount = Double.valueOf(balance);

                    if (name.isEmpty()) {
                        ToastUtil.showShort(R.string.no_name_no_type, ToastMode.WARNING);
                    } else if (name.length() > textInputLayout.getCounterMaxLength()) {
                        ToastUtil.showShort(R.string.over_words_limit, ToastMode.WARNING);
                    } else {
                        mEditPresenter.saveData(name, iconId, balanceAmount);

                        MoneyRepoTypePO moneyRepoTypePO = new MoneyRepoTypePO();
                        moneyRepoTypePO.moneyRepoTypeName = name;
                        moneyRepoTypePO.moneyRepoTypeImageId = iconId;
                        moneyRepoTypePO.balance = balanceAmount;

                        mMoneyRepoTypeFragment.mPOList.add(moneyRepoTypePO);
                        int postion = mMoneyRepoTypeFragment.mAdapter.getItemCount();
                        mMoneyRepoTypeFragment.mAdapter.notifyItemInserted(postion);
                        ToastUtil.showShort(R.string.new_type_success, ToastMode.SUCCESS);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
    }

    @Override
    public void showTypeDeleteDialog() {
        mEditPresenter.isMoneyType = true;
        AlertDialog.Builder dialog = new AlertDialog.Builder(TypeEditActivity.this);
        dialog.setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.delete_all_money_type_is_sure)
                .setPositiveButton(R.string.sure, (dialog1, which) -> {
                    // TODO: some bugs.
                    mEditPresenter.deleteAllData();
                    for (int i = 0; i <= mMoneyTypeFragment.mPOList.size() - 1; i++) {
                        mMoneyTypeFragment.mPOList.remove(i);
                        mMoneyTypeFragment.mAdapter.notifyItemRemoved(i);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
        }

    @Override
    public void showRepoTypeDeleteDialog() {
        mEditPresenter.isMoneyType = false;
        AlertDialog.Builder dialog = new AlertDialog.Builder(TypeEditActivity.this);
        dialog.setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.delete_all_money_repo_type_is_sure)
                .setPositiveButton(R.string.sure, (dialog1, which) -> {
                    mEditPresenter.deleteAllData();
                    for (int i = 0; i <= mMoneyRepoTypeFragment.mPOList.size() - 1; i++) {
                        mMoneyRepoTypeFragment.mPOList.remove(i);
                        mMoneyRepoTypeFragment.mAdapter.notifyItemRemoved(i);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
    }


    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected TypeEditPresenter createPresenter() {
        return new TypeEditPresenter();
    }

    private void initIconSpinnerDropDown() {
        final int[] iconArray = {ic_internet_62, ic_love_02, ic_love_10, ic_love_13, ic_love_27,
                ic_love_82, ic_love_83, ic_love_85, ic_office_03, ic_office_07, ic_office_102,
                ic_office_104, ic_office_119, ic_office_12, ic_office_13, ic_office_20, ic_office_28,
                ic_office_52, ic_office_54, ic_office_63, ic_office_73, ic_sport_22, ic_sport_96,
                ic_transport_17, ic_transport_96 ,ic_travel_60};
        mSpinnerTypeIconList = new ArrayList<>();
        for (int icon : iconArray) {
            mSpinnerTypeIconList.add(new SpinnerTypeIcon("", icon));
        }
    }
}