package com.newamber.gracebook.model.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.MoneyTypeModel;
import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyTypePO;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/4.
 */

public class MoneyTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyTypePO> {

    public MoneyTypeItemAdapter(@NonNull List<MoneyTypePO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    public void onItemDismiss(int position) {
        super.onItemDismiss(position);
        new MoneyTypeModel().deleteDataByPosition(position + 1);
    }

    @Override
    protected void convertView(ViewHolder holder, MoneyTypePO entity) {
        holder.setImageResource(R.id.imageView_typeEdit_moneyType, entity.moneyTypeImageID);
        holder.setText(R.id.textView_typeEdit_moneyType, entity.moneyTypeName);
        holder.setImageResource(R.id.imageView_typeEdit_dragIcon, R.drawable.ic_type_edit_drag);
        holder.setImageResource(R.id.imageView_typeEdit_moneyType_delete, R.drawable.ic_item_delete);
    }

    @Override
    protected void initSubItemClickListener(ViewHolder holder) {
        // TODO: Initialize sub item click listener here if needed.
    }
}
