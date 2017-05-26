package com.newamber.gracebook.model;

import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO;

import java.util.List;

/**
 * Description: Offer an universal interface to<br>
 * {@link MoneyTypePO} and<br>
 * {@link MoneyRepoTypePO}.<p>
 * {@code E} means entity type.<br>
 * <br>
 *
 * Created by Newamber on 2017/5/8.
 */
public interface TypeModel<E> {
    void saveData();
    void deleteAllData();
    List<E> getAllData();
}
