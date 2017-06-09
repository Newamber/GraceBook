package com.newamber.gracebook.base;

/**
 * Description: .<p>
 * Created by Newamber on 2017/6/9.
 */

public interface BaseView {

    interface AddAccountView {

    }

    interface TypeEditView {

        void showMoneyTypeDialog();

        void showMoneyRepoTypeDialog();

        void showTypeDeleteDialog();

        void showRepoTypeDeleteDialog();
    }
}
