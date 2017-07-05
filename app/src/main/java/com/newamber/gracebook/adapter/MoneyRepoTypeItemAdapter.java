package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseModels;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;

import java.util.List;
import java.util.Locale;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/24.
 */
public class MoneyRepoTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyRepoTypePO> {

    private BaseModels.TypeModel mModel = new MoneyRepoTypeModel();

    public MoneyRepoTypeItemAdapter(@NonNull List<MoneyRepoTypePO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    public void onItemDismiss(int position) {
        super.onItemDismiss(position);
        mModel.deleteDataById(position + 1);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        super.onItemMove(fromPosition, toPosition);
        mModel.dragSwap(fromPosition + 1, toPosition + 1);
    }

    @Override
    protected void convertView(ViewHolder holder, MoneyRepoTypePO entity) {
        holder.setImageResource(R.id.imageView_typeEdit_moneyRepoType, entity.moneyRepoTypeImageId);
        holder.setText(R.id.textView_typeEdit_moneyRepoType, entity.moneyRepoTypeName);
        holder.setText(R.id.textView_typeEdit_initial_amount, "余额：" + String.format(Locale.CHINA, "%.2f"
                , entity.balance) + "￥");
    }

    @Override
    protected void initSubItemClickListener(ViewHolder holder) {

    }

}
