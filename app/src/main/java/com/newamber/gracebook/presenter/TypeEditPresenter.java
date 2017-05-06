package com.newamber.gracebook.presenter;

import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.view.TypeEditView;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/5.
 */

public class TypeEditPresenter extends BasePresenter<TypeEditView> {
    public void newTypeDialog(int position) {
        if (position == 0) getView().showMoneyTypeDialog();
        else getView().showMoneyRepoTypeDialog();
    }
}
