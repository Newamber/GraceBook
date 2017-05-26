package com.newamber.gracebook.model.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;

import java.util.List;
import java.util.Locale;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/24.
 */
public class MoneyRepoTypeItemAdapter extends BaseRecyclerViewAdapter<MoneyRepoTypePO> {

    public MoneyRepoTypeItemAdapter(@NonNull List<MoneyRepoTypePO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
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
