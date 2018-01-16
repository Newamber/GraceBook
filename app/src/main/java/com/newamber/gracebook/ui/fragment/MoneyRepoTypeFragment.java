package com.newamber.gracebook.ui.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MoneyRepoTypeItemAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.other.EditTypeItemCallback;

import org.greenrobot.eventbus.Subscribe;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("unused")
public class MoneyRepoTypeFragment extends BaseFragment<TypeEditPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_repo_type;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_money_repo_type_card;

    private MoneyRepoTypeItemAdapter mAdapter;

    @Override
    public void initView() {
        getHostPresenter().isMoneyType = false;

        // data source
        RecyclerView recyclerView = findViewById(R.id.recyclerView_moneyRepoType);
        mAdapter = new MoneyRepoTypeItemAdapter(getHostPresenter().getAll(), ITEM_LAYOUT_ID);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new EditTypeItemCallback(mAdapter, false));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        setEasyItemAdapter(recyclerView, mAdapter);
    }

    // --------------------------------------event--------------------------------------------------
    @Subscribe
    public void onNewMoneyRepoType(MoneyRepoTypePO record) {
        if (LocalStorage.getBoolean(GlobalConstant.IS_EXIST_REPO_TYPE_NAMES, false)) {
            mAdapter.replace(record.id - 1, record);
        } else {
            mAdapter.add(record);
        }
        cancelEventDelivery(record);
    }

    @Subscribe
    public void onDeleteMoneyRepoType(String deleteMessage) {
        if (deleteMessage.equals(GlobalConstant.DELETE_ALL_REPO_TYPES)) {
            mAdapter.removeAll();
            cancelEventDelivery(deleteMessage);
        }
    }
    // ---------------------------------------------------------------------------------------------

    @Override
    protected boolean isEventBusEnabled() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }
}
