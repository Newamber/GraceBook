package com.newamber.gracebook.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
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
import android.widget.Toast;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseActivity;
import com.newamber.gracebook.base.BaseListViewAdapter;
import com.newamber.gracebook.model.MoneyTypeModel;
import com.newamber.gracebook.model.adapter.TypeEditViewPagerAdapter;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.SpinnerTypeIcon;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.view.TypeEditView;
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

public class TypeEditActivity extends BaseActivity<TypeEditView, TypeEditPresenter>
        implements TypeEditView {

    private static final @LayoutRes int LAYOUT_ID = R.layout.activity_type_edit;
    private ViewPager mviewPager;
    private List<SpinnerTypeIcon> mSpinnerTypeIconList;
    private AppCompatSpinner spinnerMoneyType, spinnerMoneyRepoType;
    private BaseAdapter adapter;

    private MoneyTypeFragment moneyTypeFragment;
    int iconID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void initView() {
        setContentView(LAYOUT_ID);

        // -----------------------------findViewById------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_typeEdit);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_typeEdit);
        mviewPager = (ViewPager) findViewById(R.id.viewPager_typeEdit);

        // ---------------------------Toolbar-------------------------------------------------------
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // -----------------------------ViewPager---------------------------------------------------
        List<Fragment> fragmentList = new ArrayList<>();
        moneyTypeFragment = new MoneyTypeFragment();
        fragmentList.add(moneyTypeFragment);
        fragmentList.add(new MoneyRepoTypeFragment());
        TypeEditViewPagerAdapter pagerAdapter = new TypeEditViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mviewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mviewPager);

        // -----------------------------SpinnerAdapter----------------------------------------------
        initIconSpinnerDropDown();
        adapter = new BaseListViewAdapter<SpinnerTypeIcon>(mSpinnerTypeIconList,
                R.layout.spinner_item_icons) {
            @Override
            protected void bindView(ViewHolder holder, SpinnerTypeIcon entity) {
                holder.setImageResource(R.id.imageView_spinner_item_icon, entity.getIconId());
                holder.setText(R.id.textView_placeholder, entity.getPlaceholder());
            }
        };
    }

    @Override
    protected TypeEditPresenter createPresenter() {
        return new TypeEditPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_type_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_typeEdit_delete:
                break;
            case R.id.toolbar_typeEdit_new:
                getPresenter().newTypeDialog(mviewPager.getCurrentItem());
                break;
            case R.id.toolbar_typeEdit_deleteAll:
                // TODO: extract to presenter
                AlertDialog.Builder dialog = new AlertDialog.Builder(TypeEditActivity.this);
                dialog.setTitle("注意");
                dialog.setIcon(R.drawable.ic_dialog_warning);
                if (moneyTypeFragment.mMoneyTypePOList.isEmpty()) {
                    dialog.setMessage("这里已经没有任何收支类型。");
                    dialog.setPositiveButton("确定", (dialog1, which) -> {});
                } else {
                    dialog.setMessage("您确定要删除全部“收支类型”吗？该操作将无法撤销。");
                    dialog.setPositiveButton("确定", (dialog1, which) -> {
                        new MoneyTypeModel().deleteAllData();
                        for(int i = 0; i <= moneyTypeFragment.mMoneyTypePOList.size() - 1; i++) {
                            moneyTypeFragment.mMoneyTypePOList.remove(i);
                            moneyTypeFragment.moneyTypeItemAdapter.notifyItemRemoved(i);
                        }
                    });
                    dialog.setNegativeButton("取消", (dialog1, which) -> {});
                }
                dialog.show();
                break;
            case android.R.id.home:
                finish();
            default:
        }
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public void showMoneyTypeDialog() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_record_fab_show);
        getPresenter().isMoneyType = true;
        AlertDialog.Builder dialogMoneyType = new AlertDialog.Builder(TypeEditActivity.this);
        View view =  LayoutInflater.from(this).inflate(R.layout.dialog_money_type, null);
        dialogMoneyType.setTitle(R.string.new_type);
        dialogMoneyType.setView(view);
        dialogMoneyType.setCancelable(false);
        EditText editTextmoneyTypeName = (EditText) view.findViewById(R.id.editText_moneyTypeName);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView_iconSelected);
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout_moneyTypeName);

        spinnerMoneyType = (AppCompatSpinner) view.findViewById(R.id.spinner_typeEdit_moneyType);
        spinnerMoneyType.setAdapter(adapter);
        spinnerMoneyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                iconID = entity.getIconId();
                animator.setTarget(icon);
                animator.start();
                icon.setBackgroundResource(entity.getIconId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogMoneyType.setPositiveButton(R.string.sure, (dialog, which) -> {
            // TODO: logic business
            String name = editTextmoneyTypeName.getText().toString();
            if (name.isEmpty())
                Toast.makeText(TypeEditActivity.this, "您没有输入名称，类型未保存", Toast.LENGTH_SHORT).show();
            else if(name.length() >= 10) {
                Toast.makeText(TypeEditActivity.this, "字段过长，类型未保存", Toast.LENGTH_SHORT).show();
            }
            else {
                getPresenter().saveData(name, iconID, 0);
                MoneyTypePO moneyTypePO = new MoneyTypePO();
                moneyTypePO.moneyTypeName = name;
                moneyTypePO.moneyTypeImageID = iconID;
                moneyTypeFragment.mMoneyTypePOList.add(moneyTypePO);
                int postion = moneyTypeFragment.moneyTypeItemAdapter.getItemCount();
                moneyTypeFragment.moneyTypeItemAdapter.notifyItemInserted(postion);
            }
        });
        dialogMoneyType.setNegativeButton(R.string.cancel, (dialog, which) -> {});
        dialogMoneyType.show();
    }

    @Override
    @SuppressWarnings("all")
    public void showMoneyRepoTypeDialog() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_record_fab_show);
        getPresenter().isMoneyType = false;
        AlertDialog.Builder dialogMoneyRepo = new AlertDialog.Builder(TypeEditActivity.this);
        View view =  LayoutInflater.from(this).inflate(R.layout.dialog_money_repo_type, null);
        dialogMoneyRepo.setTitle(R.string.new_repo_type);
        dialogMoneyRepo.setView(view);

        EditText moneyRepoTypeName = (EditText) view.findViewById(R.id.editText_moneyRepoTypeName);
        ImageView imageViewIconRepo = (ImageView) view.findViewById(R.id.imageView_iconRepoSelected);

        spinnerMoneyRepoType = (AppCompatSpinner) view.findViewById(R.id.spinner_typeEdit_moneyRepoType);
        spinnerMoneyRepoType.setAdapter(adapter);
        spinnerMoneyRepoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTypeIcon entity = (SpinnerTypeIcon) parent.getItemAtPosition(position);
                animator.setTarget(imageViewIconRepo);
                animator.start();
                imageViewIconRepo.setBackgroundResource(entity.getIconId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogMoneyRepo.setPositiveButton(R.string.sure, (dialog, which) -> {
            // TODO: logic business
            String a = moneyRepoTypeName.getText().toString().trim();
            Toast.makeText(TypeEditActivity.this, a, Toast.LENGTH_SHORT).show();
        });
        dialogMoneyRepo.setNegativeButton(R.string.cancel, (dialog, which) -> {});
        dialogMoneyRepo.show();
    }

    private void initIconSpinnerDropDown() {
        final int[] ICON_ARRAY = {ic_internet_62, ic_love_02, ic_love_10, ic_love_13, ic_love_27,
                ic_love_82, ic_love_83, ic_love_85, ic_office_03, ic_office_07, ic_office_102,
                ic_office_104, ic_office_119, ic_office_12, ic_office_13, ic_office_20, ic_office_28,
                ic_office_52, ic_office_54, ic_office_63, ic_office_73, ic_sport_22, ic_sport_96,
                ic_transport_17, ic_transport_96 ,ic_travel_60};
        mSpinnerTypeIconList = new ArrayList<>();
        for (int icon : ICON_ARRAY) {
            mSpinnerTypeIconList.add(new SpinnerTypeIcon("", icon));
        }
    }
}