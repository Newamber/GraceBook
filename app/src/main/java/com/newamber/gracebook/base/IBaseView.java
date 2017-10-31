package com.newamber.gracebook.base;

/**
 * Description: .<p>
 * Created by Newamber on 2017/6/9.
 */

public interface IBaseView {

    interface AddAccountView {

        void checkInput();

        void showDatePicker();

        void showMoneyRepoPicker();

        void showMoneyTypePicker();

        void showErrorHint();

        void resetContent();
    }

    interface TypeEditView {

        void showMoneyTypeDialog();

        void showMoneyRepoTypeDialog();

        void showTypeDeleteDialog();

        void showRepoTypeDeleteDialog();
    }

    interface MainView {

        void showErrorDialog();

        void showNewAccountNameDialog();

        void showSummaryDialog();

        void showDeleteCurrentPageDialog();

        void showDeleteAllDialog();

        void showAccountInfoDialog(Object entity);
    }

    interface SearchView {

    }
}
