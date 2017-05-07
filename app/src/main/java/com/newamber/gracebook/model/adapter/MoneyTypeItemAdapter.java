package com.newamber.gracebook.model.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyTypeTable;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/4.
 */

public class MoneyTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyTypeTable> {

    public MoneyTypeItemAdapter(@NonNull List<MoneyTypeTable> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    protected void convertView(ViewHolder holder, MoneyTypeTable entity) {
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
