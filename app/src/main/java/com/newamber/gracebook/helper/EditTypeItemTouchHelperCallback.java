package com.newamber.gracebook.helper;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.model.ViewHolder;

/**
 * Description: RecyclerView item touch helper.<p>
 *
 * Created by Newamber on 2017/5/4.
 */

public class EditTypeItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter mItemTouchHelperAdapter;
    private final float ALPHA_FULL = 1.0f;

    public EditTypeItemTouchHelperCallback(ItemTouchHelperAdapter recyclerViewAdapter) {
        mItemTouchHelperAdapter = recyclerViewAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager ||
                recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final int dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.END | ItemTouchHelper.START;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // Notify the adapter of the move.
        mItemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mItemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //final ViewHolder holder = (ViewHolder) viewHolder;
        /*if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            holder.setBackgroundColor(R.id.cardView_typeEdit_moneyType, "#ffffff", "#e57373");
        }*/
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        final ViewHolder holder = (ViewHolder) viewHolder;
        final View itemContainer = holder.itemView;
       // holder.setBackgroundColor(R.id.cardView_typeEdit_moneyType, "#e57373", "#ffffff");
        itemContainer.setAlpha(ALPHA_FULL);
        itemContainer.setScaleX(1);
        itemContainer.setScaleY(1);
        itemContainer.setTranslationX(0);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        final View itemContainer = ((ViewHolder) viewHolder).itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemContainer.getWidth();
            itemContainer.setAlpha(alpha);
            itemContainer.setScaleX(alpha);
            itemContainer.setScaleY(alpha);
        }
    }
}
