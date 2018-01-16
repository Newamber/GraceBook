package com.newamber.gracebook.ui.activity;

import android.annotation.SuppressLint;
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
import com.newamber.gracebook.adapter.TypeEditViewPagerAdapter;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseListViewAdapter;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.SpinnerTypeIcon;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.ui.fragment.MoneyRepoTypeFragment;
import com.newamber.gracebook.ui.fragment.MoneyTypeFragment;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.util.event.UpdateDayIconEvent;
import com.newamber.gracebook.util.event.UpdateFlowIconEvent;

import java.util.ArrayList;
import java.util.List;

import static com.newamber.gracebook.R.drawable.ic_internet_106;
import static com.newamber.gracebook.R.drawable.ic_internet_27;
import static com.newamber.gracebook.R.drawable.ic_internet_42;
import static com.newamber.gracebook.R.drawable.ic_internet_59;
import static com.newamber.gracebook.R.drawable.ic_internet_62;
import static com.newamber.gracebook.R.drawable.ic_love_02;
import static com.newamber.gracebook.R.drawable.ic_love_10;
import static com.newamber.gracebook.R.drawable.ic_love_13;
import static com.newamber.gracebook.R.drawable.ic_love_27;
import static com.newamber.gracebook.R.drawable.ic_love_39;
import static com.newamber.gracebook.R.drawable.ic_love_50;
import static com.newamber.gracebook.R.drawable.ic_love_78;
import static com.newamber.gracebook.R.drawable.ic_love_82;
import static com.newamber.gracebook.R.drawable.ic_love_83;
import static com.newamber.gracebook.R.drawable.ic_love_85;
import static com.newamber.gracebook.R.drawable.ic_office_03;
import static com.newamber.gracebook.R.drawable.ic_office_07;
import static com.newamber.gracebook.R.drawable.ic_office_102;
import static com.newamber.gracebook.R.drawable.ic_office_104;
import static com.newamber.gracebook.R.drawable.ic_office_112;
import static com.newamber.gracebook.R.drawable.ic_office_116;
import static com.newamber.gracebook.R.drawable.ic_office_119;
import static com.newamber.gracebook.R.drawable.ic_office_12;
import static com.newamber.gracebook.R.drawable.ic_office_13;
import static com.newamber.gracebook.R.drawable.ic_office_28;
import static com.newamber.gracebook.R.drawable.ic_office_38;
import static com.newamber.gracebook.R.drawable.ic_office_54;
import static com.newamber.gracebook.R.drawable.ic_office_63;
import static com.newamber.gracebook.R.drawable.ic_office_73;
import static com.newamber.gracebook.R.drawable.ic_office_90;
import static com.newamber.gracebook.R.drawable.ic_repo_internet_108;
import static com.newamber.gracebook.R.drawable.ic_repo_office_02;
import static com.newamber.gracebook.R.drawable.ic_repo_office_110;
import static com.newamber.gracebook.R.drawable.ic_repo_office_117;
import static com.newamber.gracebook.R.drawable.ic_repo_office_20;
import static com.newamber.gracebook.R.drawable.ic_repo_office_25;
import static com.newamber.gracebook.R.drawable.ic_repo_office_47;
import static com.newamber.gracebook.R.drawable.ic_repo_office_49;
import static com.newamber.gracebook.R.drawable.ic_repo_office_52;
import static com.newamber.gracebook.R.drawable.ic_repo_office_65;
import static com.newamber.gracebook.R.drawable.ic_repo_office_66;
import static com.newamber.gracebook.R.drawable.ic_repo_office_94;
import static com.newamber.gracebook.R.drawable.ic_repo_office_97;
import static com.newamber.gracebook.R.drawable.ic_repo_office_98;
import static com.newamber.gracebook.R.drawable.ic_repo_travel_09;
import static com.newamber.gracebook.R.drawable.ic_repo_travel_115;
import static com.newamber.gracebook.R.drawable.ic_repo_travel_90;
import static com.newamber.gracebook.R.drawable.ic_repo_travel_97;
import static com.newamber.gracebook.R.drawable.ic_science_03;
import static com.newamber.gracebook.R.drawable.ic_science_60;
import static com.newamber.gracebook.R.drawable.ic_science_98;
import static com.newamber.gracebook.R.drawable.ic_sport_22;
import static com.newamber.gracebook.R.drawable.ic_sport_26;
import static com.newamber.gracebook.R.drawable.ic_sport_69;
import static com.newamber.gracebook.R.drawable.ic_sport_88;
import static com.newamber.gracebook.R.drawable.ic_sport_96;
import static com.newamber.gracebook.R.drawable.ic_transport_05;
import static com.newamber.gracebook.R.drawable.ic_transport_17;
import static com.newamber.gracebook.R.drawable.ic_transport_18;
import static com.newamber.gracebook.R.drawable.ic_transport_96;
import static com.newamber.gracebook.R.drawable.ic_travel_10;
import static com.newamber.gracebook.R.drawable.ic_travel_118;
import static com.newamber.gracebook.R.drawable.ic_travel_26;
import static com.newamber.gracebook.R.drawable.ic_travel_56;
import static com.newamber.gracebook.R.drawable.ic_travel_60;
import static com.newamber.gracebook.R.drawable.ic_travel_82;
import static com.newamber.gracebook.R.drawable.ic_travel_83;
import static com.newamber.gracebook.R.drawable.ic_travel_84;
import static com.newamber.gracebook.R.drawable.ic_web_56;
import static com.newamber.gracebook.R.drawable.ic_web_74;
import static com.newamber.gracebook.util.ToastUtil.showShort;

public class TypeEditActivity extends BaseActivity<IBaseView.TypeEditView, TypeEditPresenter>
        implements IBaseView.TypeEditView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_type_edit;

    private @DrawableRes int iconId;
    private BaseAdapter adapterType;
    private BaseAdapter adapterRepo;
    private ViewPager mViewPager;
    private List<SpinnerTypeIcon> mSpinnerTypeIconList;
    private List<SpinnerTypeIcon> mSpinnerRepoIconList;

    private TypeEditPresenter mEditPresenter;


    @Override
    public void initViews() {
        mEditPresenter = getPresenter();

        // -----------------------------findViewById------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar_typeEdit);
        TabLayout tabLayout = findViewById(R.id.tabLayout_typeEdit);
        mViewPager = findViewById(R.id.viewPager_typeEdit);

        // ---------------------------Toolbar-------------------------------------------------------
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // -----------------------------ViewPager---------------------------------------------------
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MoneyTypeFragment());
        fragmentList.add(new MoneyRepoTypeFragment());

        TypeEditViewPagerAdapter pagerAdapter = new
                TypeEditViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        // -----------------------------SpinnerAdapter----------------------------------------------
        initSpinnerDropDown();
        adapterType = new BaseListViewAdapter<SpinnerTypeIcon>(mSpinnerTypeIconList,
                R.layout.spinner_item_icons) {
            @Override
            protected void convertView(ViewHolder holder, SpinnerTypeIcon entity) {
                holder.setImageResource(R.id.imageView_spinner_item_icon, entity.getIconId());
                holder.setText(R.id.textView_placeholder, entity.getPlaceholder());
            }
        };

        adapterRepo = new BaseListViewAdapter<SpinnerTypeIcon>(mSpinnerRepoIconList,
                R.layout.spinner_item_icons) {
            @Override
            protected void convertView(ViewHolder holder, SpinnerTypeIcon entity) {
                holder.setImageResource(R.id.imageView_spinner_item_icon, entity.getIconId());
                holder.setText(R.id.textView_placeholder, entity.getPlaceholder());
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = mViewPager.getCurrentItem();
        switch (item.getItemId()) {
            case R.id.toolbar_typeEdit_delete:
                break;
            case R.id.toolbar_typeEdit_new:
                mEditPresenter.newType(position);
                break;
            case R.id.toolbar_typeEdit_deleteAll:
                mEditPresenter.deleteAllTypes(position);
                break;
            case android.R.id.home:
                onBackPressed();
            default:
                break;
        }
        return true;
    }

    @Override
    public void showMoneyTypeDialog() {
        mEditPresenter.isMoneyType = true;
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_money_type, null);

        EditText editText = dialogView.findViewById(R.id.editText_moneyTypeName);
        ImageView imageViewIcon = dialogView.findViewById(R.id.imageView_iconSelected);
        TextInputLayout textInputLayout = dialogView
                .findViewById(R.id.textInputLayout_moneyTypeName);
        AppCompatSpinner spinnerMoneyType = dialogView
                .findViewById(R.id.spinner_typeEdit_moneyType);

        spinnerMoneyType.setAdapter(adapterType);
        spinnerMoneyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                iconId = entity.getIconId();
                startAnimator(imageViewIcon, R.animator.anim_spinner_icon_show);
                imageViewIcon.setBackgroundResource(entity.getIconId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        new AlertDialog.Builder(this).setTitle(R.string.new_type)
                .setView(dialogView)
                .setPositiveButton(R.string.ok, (dialog1, which) -> {
                    String name = editText.getText().toString();
                    if (name.isEmpty()) {
                        showShort(R.string.no_name_no_type, ToastMode.ERROR);
                    } else if (name.length() > textInputLayout.getCounterMaxLength()) {
                        showShort(R.string.over_words_limit_not_save, ToastMode.WARNING);
                    } else {
                        MoneyTypePO moneyTypePO;
                        if (mEditPresenter.isExist(name)) {
                            moneyTypePO = mEditPresenter.getRecordByName(name);
                            moneyTypePO.moneyTypeImageId = iconId;
                            LocalStorage.put(GlobalConstant.IS_EXIST_TYPE_NAMES, true);
                            postSticky(new UpdateDayIconEvent());
                            postSticky(new UpdateFlowIconEvent());
                            showShort(R.string.update_type_success, ToastMode.SUCCESS);
                        } else {
                            moneyTypePO = new MoneyTypePO();
                            moneyTypePO.moneyTypeName = name;
                            moneyTypePO.moneyTypeImageId = iconId;
                            LocalStorage.put(GlobalConstant.IS_EXIST_TYPE_NAMES, false);
                            showShort(R.string.new_type_success, ToastMode.SUCCESS);
                        }
                        // Subscriber: MoneyTypeFragment
                        post(moneyTypePO);
                        mEditPresenter.saveInDB(name, iconId, 0);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showMoneyRepoTypeDialog() {
        mEditPresenter.isMoneyType = false;
        @SuppressLint("InflateParams")
        View dialogView =  LayoutInflater.from(this).inflate(R.layout.dialog_money_repo_type, null);

        EditText editTextName = dialogView.findViewById(R.id.editText_moneyRepoTypeName);
        EditText editTextBalance = dialogView.findViewById(R.id.editText_moneyRepoTypeBalance);
        ImageView imageViewIcon = dialogView.findViewById(R.id.imageView_iconRepoSelected);
        TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInputLayout_moneyTypeRepoName);
        AppCompatSpinner spinnerMoneyRepoType = dialogView.findViewById(R.id.spinner_typeEdit_moneyRepoType);

        spinnerMoneyRepoType.setAdapter(adapterRepo);
        spinnerMoneyRepoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                iconId = entity.getIconId();
                startAnimator(imageViewIcon, R.animator.anim_spinner_icon_show);
                imageViewIcon.setBackgroundResource(entity.getIconId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.new_repo_type)
                .setView(dialogView)
                .setPositiveButton(R.string.ok, (dialog1, which) -> {
                    String name = editTextName.getText().toString();
                    String balance = editTextBalance.getText().toString();
                    if (balance.isEmpty()) balance = "0";
                    if (balance.length() == 1) {
                       if (balance.contains(".")) balance = "0";
                   }
                   Double balanceAmount = Double.valueOf(balance);

                    if (name.isEmpty()) {
                        showShort(R.string.no_name_no_type, ToastMode.ERROR);
                    } else if (name.length() > textInputLayout.getCounterMaxLength()) {
                        showShort(R.string.over_words_limit_not_save, ToastMode.WARNING);
                    } else {
                        if (balanceAmount <= GlobalConstant.MAX_BALANCE) {
                            MoneyRepoTypePO moneyRepoTypePO;
                            if (mEditPresenter.isExist(name)) {
                                moneyRepoTypePO = mEditPresenter.getRecordByName(name);
                                moneyRepoTypePO.moneyRepoTypeImageId = iconId;
                                LocalStorage.put(GlobalConstant.IS_EXIST_REPO_TYPE_NAMES, true);
                                showShort(R.string.update_type_success, ToastMode.SUCCESS);
                            } else {
                                moneyRepoTypePO = new MoneyRepoTypePO();
                                moneyRepoTypePO.moneyRepoTypeName = name;
                                moneyRepoTypePO.moneyRepoTypeImageId = iconId;
                                moneyRepoTypePO.balance = balanceAmount;
                                LocalStorage.put(GlobalConstant.IS_EXIST_REPO_TYPE_NAMES, false);
                                showShort(R.string.new_type_success, ToastMode.SUCCESS);
                            }
                            // update in memory
                            post(moneyRepoTypePO);
                            // update in DB
                            mEditPresenter.saveInDB(name, iconId, balanceAmount);
                        } else {
                            showShort(R.string.initial_balance_is_too_large, ToastMode.WARNING);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showTypeDeleteDialog() {
        mEditPresenter.isMoneyType = true;
        new AlertDialog.Builder(this).setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.delete_all_money_type_is_sure)
                .setPositiveButton(R.string.ok, (dialog1, which) -> {
                    mEditPresenter.deleteAll();
                    post(GlobalConstant.DELETE_ALL_MONEY_TYPES);
                    showShort(R.string.all_money_type_deleted, ToastMode.INFO);
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
        }

    @Override
    public void showRepoTypeDeleteDialog() {
        mEditPresenter.isMoneyType = false;
        new AlertDialog.Builder(this).setTitle(R.string.caution)
                .setIcon(R.drawable.ic_dialog_warning)
                .setMessage(R.string.delete_all_money_repo_type_is_sure)
                .setPositiveButton(R.string.ok, (dialog1, which) -> {
                    mEditPresenter.deleteAll();
                    post(GlobalConstant.DELETE_ALL_REPO_TYPES);
                    showShort(R.string.all_money_repo_type_deleted, ToastMode.INFO);
                })
                .setNegativeButton(R.string.cancel, (dialog1, which) -> {})
                .show();
    }

    @Override
    protected @LayoutRes int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_type_edit_menu, menu);
        return true;
    }

    @Override
    protected TypeEditPresenter createPresenter() {
        return new TypeEditPresenter();
    }


    // -----------------------------------private API-----------------------------------------------
    private void initSpinnerDropDown() {
        final int[] iconArraysType = {
                ic_internet_106, ic_internet_27, ic_internet_42, ic_internet_59, ic_internet_62,

                ic_love_02, ic_love_10, ic_love_13, ic_love_27, ic_love_39, ic_love_83, ic_love_50,
                ic_love_78, ic_love_82, ic_love_83, ic_love_85,

                ic_office_03, ic_office_07, ic_office_102, ic_office_104, ic_office_112, ic_office_116,
                ic_office_119, ic_office_12, ic_office_13, ic_office_28, ic_office_38, ic_office_54,
                ic_office_63, ic_office_73, ic_office_90,

                ic_sport_22, ic_sport_26, ic_sport_69, ic_sport_88, ic_sport_96,

                ic_science_03, ic_science_60, ic_science_98,

                ic_transport_05, ic_transport_17, ic_transport_18, ic_transport_96,

                ic_travel_10, ic_travel_26, ic_travel_118, ic_travel_56, ic_travel_60, ic_travel_82,
                ic_travel_83, ic_travel_84,

                ic_web_56, ic_web_74
        };

        final int[] iconArrayRepo = {
                ic_repo_internet_108,

                ic_repo_office_02, ic_repo_office_110, ic_repo_office_117, ic_repo_office_20,
                ic_repo_office_25, ic_repo_office_47, ic_repo_office_49, ic_repo_office_52, ic_repo_office_65,
                ic_repo_office_66, ic_repo_office_94, ic_repo_office_97, ic_repo_office_98,

                ic_repo_travel_09, ic_repo_travel_115, ic_repo_travel_90, ic_repo_travel_97
        };

        mSpinnerTypeIconList = new ArrayList<>();
        mSpinnerRepoIconList = new ArrayList<>();
        for (int icon : iconArraysType) mSpinnerTypeIconList.add(new SpinnerTypeIcon("", icon));
        for (int icon : iconArrayRepo) mSpinnerRepoIconList.add(new SpinnerTypeIcon("", icon));
    }
}