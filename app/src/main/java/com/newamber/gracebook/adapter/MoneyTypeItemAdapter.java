package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseDataModel;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.impl.MoneyTypeModel;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/4.
 */

public class MoneyTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyTypePO> {

    private BaseDataModel.TypeModel mModel = new MoneyTypeModel();

    public MoneyTypeItemAdapter(@NonNull List<MoneyTypePO> entityList, @LayoutRes int layoutId) {
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
    protected void convertView(ViewHolder holder, MoneyTypePO entity) {
        holder.setImageResource(R.id.imageView_typeEdit_moneyType, entity.moneyTypeImageId)
                .setText(R.id.textView_typeEdit_moneyType, entity.moneyTypeName);
    }
}
