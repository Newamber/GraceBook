package com.newamber.gracebook.base;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/6/9.
 */

public interface BaseModels {

    /**
     * Description: Offer an universal interface to<br>
     * {@link com.newamber.gracebook.model.entity.MoneyRepoTypePO} and<br>
     * {@link com.newamber.gracebook.model.entity.MoneyTypePO}.<p>
     * {@code E} means entity type.<br>
     * <br>
     *
     * Created by Newamber on 2017/5/8.
     */
    interface TypeModel<E> {
        void saveData();
        void deleteAllData();
        List<E> getAllData();
    }
}
