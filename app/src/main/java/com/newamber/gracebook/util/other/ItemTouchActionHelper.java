package com.newamber.gracebook.util.other;

import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.BaseViewHolder;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/4.
 */
public interface ItemTouchActionHelper {
    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br>
     * <br>
     *
     * Implementations should call {@link BaseRecyclerViewAdapter#notifyItemMoved(int, int)} after
     * adjusting the underlying data to reflect this move.
     *
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then resolved position of the moved item.
     *
     * @see BaseViewHolder#getAdapterPosition()
     */
    void onItemDragMove(int fromPosition, int toPosition);

    /**
     * Called when an item has been dismissed by a swipe.<br>
     * <br>
     *
     * Implementations should call {@link BaseRecyclerViewAdapter#notifyItemRemoved(int)} after
     * adjusting the underlying data to reflect this removal.
     *
     * @param position The position of the item dismissed.
     *
     * @see BaseViewHolder#getAdapterPosition()
     */
    void onItemSwipeDismiss(int position);
}
