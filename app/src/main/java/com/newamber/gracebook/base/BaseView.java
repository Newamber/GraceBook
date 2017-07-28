package com.newamber.gracebook.base;

/**
 * Description: .<p>
 * Created by Newamber on 2017/6/9.
 */

public interface BaseView {

    interface AddAccountView {

        void checkInput();

        void showDatePicker();

        void showMoneyRepoPicker();

        void showMoneyTypePicker();

        void setErrorHint();
    }

    interface TypeEditView {

        void showMoneyTypeDialog();

        void showMoneyRepoTypeDialog();

        void showTypeDeleteDialog();

        void showRepoTypeDeleteDialog();
    }

    interface MainView {

        void showErrorDialog();
    }

    interface SearchView {

    }
}
