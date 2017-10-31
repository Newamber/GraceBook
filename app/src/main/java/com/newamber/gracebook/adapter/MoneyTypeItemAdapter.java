package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.IBaseModel;
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

    private IBaseModel.TypeModel mModel = new MoneyTypeModel();

    public MoneyTypeItemAdapter(@NonNull List<MoneyTypePO> entityList, @LayoutRes int layoutId) {
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
    protected void convertView(ViewHolder holder, MoneyTypePO entity) {
        holder.setImage(R.id.imageView_typeEdit_moneyType, entity.moneyTypeImageId)
                .setText(R.id.textView_typeEdit_moneyType, entity.moneyTypeName);
    }
}
