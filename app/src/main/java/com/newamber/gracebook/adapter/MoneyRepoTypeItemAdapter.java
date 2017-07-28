package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseDataModel;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.util.NumericUtil;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/24.
 */
public class MoneyRepoTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyRepoTypePO> {

    private BaseDataModel.TypeModel mModel = new MoneyRepoTypeModel();

    public MoneyRepoTypeItemAdapter(@NonNull List<MoneyRepoTypePO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    public void onItemSwipeDismiss(int position) {
        super.onItemSwipeDismiss(position);
        mModel.deleteRecordById(position + 1);
    }

    @Override
    public void onItemDragMove(int fromPosition, int toPosition) {
        super.onItemDragMove(fromPosition, toPosition);
        mModel.dragSwap(fromPosition + 1, toPosition + 1);
    }

    @Override
    protected void convertView(ViewHolder holder, MoneyRepoTypePO entity) {
        holder.setImageResource(R.id.imageView_typeEdit_moneyRepoType, entity.moneyRepoTypeImageId)
                .setText(R.id.textView_typeEdit_moneyRepoType, entity.moneyRepoTypeName)
                .setText(R.id.textView_typeEdit_initialAmount, "余额：" +
                        NumericUtil.getCurrencyFormat(entity.balance));
    }
}
