package com.newamber.gracebook.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.util.helper.ItemTouchActionHelper;

import java.util.Collections;
import java.util.List;

/**
 * Description: Common Adapter for RecyclerView. <br>
 * {@code E} means certain entity(like {@code String}) type depends on data source. <p>
 *
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter<ViewHolder>
        implements ItemTouchActionHelper {

    private Context mContext;
    private List<E> mEntityList;
    private @LayoutRes int mLayoutId;

    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    protected BaseRecyclerViewAdapter(@NonNull List<E> entityList, @LayoutRes int layoutId) {
        mEntityList = entityList;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

        // The instance of ViewHolder is passed to parameter "holder" at onBindViewHolder method
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // init OnItemClickListener
        initOnItemClickListener(holder);

        // init sub itemClickListener
        initSubItemClickListener(holder);

        convertView(holder, mEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mEntityList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mEntityList.remove(position);
        notifyItemRemoved(position);
    }

    // A auxiliary method to initialize (long) click listener in onCreateViewHolder(...).
    private void initOnItemClickListener(ViewHolder holder) {
        // TODO: reduce codes
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mItemClickListener.onItemClick(v, entity, holder.getLayoutPosition());
            });
        }

        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mItemLongClickListener.onItemLongClick(v, entity, holder.getLayoutPosition());
                return true;
            });
        }
    }

    /**
     * Set (long) click listener for sub view item in the method by calling <br>
     * {@link com.newamber.gracebook.model.ViewHolder#setOnClickListener(int, View.OnClickListener)} or<br>
     * {@link com.newamber.gracebook.model.ViewHolder#setOnLongClickListener(int, View.OnLongClickListener)}<br>
     *
     * @param holder view holder
     */
    protected abstract void initSubItemClickListener(ViewHolder holder);

    /**
     * Extract our operations about different data binding.<br>
     * This is a specific job of our sub adapters according to the show of different item view.<br>
     * You can set text, color or drawable resource in the method.
     *
     * @param holder the outer layer holder of sub items
     * @param entity the data source
     */
    protected abstract void convertView(ViewHolder holder, E entity);

    /**
     * Set click listener for our item of RecyclerView.
     *
     * @param clickListener a anonymous class or a lambda expression
     */
    public void setOnItemClickListener(ItemClickListener clickListener) {
        mItemClickListener = clickListener ;
    }

    /**
     * Set long click listener for our item of RecyclerView.
     *
     * @param longClickListener a anonymous class or a lambda expression
     */
    public void setOnItemLongClickListener(ItemLongClickListener longClickListener) {
        mItemLongClickListener = longClickListener ;
    }

    /**
     * The item click listener interface of RecyclerView
     * {@code T} means the type of entity, it should be the same with {@code E}
     */
    @FunctionalInterface
    public interface ItemClickListener {
        <T> void onItemClick(View view, T entity, int position) ;
    }

    /**
     * The item long click listener interface of RecyclerView
     * {@code T} means the type of entity, it should be the same with {@code E}
     */
    @FunctionalInterface
    @SuppressWarnings("all")
    public interface ItemLongClickListener {
        <T> void onItemLongClick(View view, T entity, int position) ;
    }
}
