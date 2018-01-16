package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.BaseViewHolder;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;

import java.util.List;

import static com.newamber.gracebook.util.NumericUtil.formatCurrency;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/24.
 */
public class MoneyRepoTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyRepoTypePO> {

    private IBaseModel.TypeModel mModel = new MoneyRepoTypeModel();

    public MoneyRepoTypeItemAdapter(@NonNull List<MoneyRepoTypePO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    public void onItemSwipeDismiss(int position) {
        super.onItemSwipeDismiss(position);
        new Thread(() -> mModel.deleteRecordById(position + 1)).start();
    }

    @Override
    public void onItemDragMove(int fromPosition, int toPosition) {
        super.onItemDragMove(fromPosition, toPosition);
        new Thread(() -> mModel.dragToSwap(fromPosition + 1, toPosition + 1)).start();
    }

    @Override
    protected void convertView(BaseViewHolder holder, MoneyRepoTypePO entity) {
        holder.setImage(R.id.imageView_typeEdit_moneyRepoType, entity.moneyRepoTypeImageId)
                .setText(R.id.textView_typeEdit_moneyRepoType, entity.moneyRepoTypeName)
                .setText(R.id.textView_typeEdit_initialAmount, getContext()
                        .getString(R.string.balance_colon) + formatCurrency(entity.balance));
    }
}
