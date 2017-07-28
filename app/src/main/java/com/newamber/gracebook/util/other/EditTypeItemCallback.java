package com.newamber.gracebook.util.other;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Description: RecyclerView item touch helper.<p>
 *
 * Created by Newamber on 2017/5/4.
 */

public class EditTypeItemCallback extends ItemTouchHelper.Callback {

    private ItemTouchActionHelper mItemTouchActionHelper;
    private static final float ALPHA_FULL = 1.0f;
    private boolean isMoneyType;

    public EditTypeItemCallback(ItemTouchActionHelper recyclerViewAdapter, boolean isMoneyType) {
        mItemTouchActionHelper = recyclerViewAdapter;
        this.isMoneyType = isMoneyType;
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
            final int swipeFlags = isMoneyType ? ItemTouchHelper.END : ItemTouchHelper.START;
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
        mItemTouchActionHelper.onItemDragMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mItemTouchActionHelper.onItemSwipeDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        final View itemContainer = viewHolder.itemView;
        itemContainer.setAlpha(ALPHA_FULL);
        itemContainer.setTranslationX(0);
        //itemContainer.setBackgroundColor(Color.WHITE);
        /*((ViewHolder) viewHolder).setGradientBackgroundColor(R.id.cardView_typeEdit_moneyType
                , R.color.colorAccent, R.color.colorWhite);*/
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        final View itemContainer = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float swipeDistance = Math.abs(dX);
            float viewWidth = itemContainer.getWidth();
            float ratio = swipeDistance / viewWidth;
            final float alpha = ALPHA_FULL - ratio;
            //@ColorInt int tempColor = Color.WHITE - (int) (ratio * (Color.WHITE - Color.parseColor("#FFE51C23")));
            //((ViewHolder) viewHolder).setGradientColor(itemContainer, tempColor);

            itemContainer.setAlpha(alpha);
        }
    }
}