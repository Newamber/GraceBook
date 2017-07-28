package com.newamber.gracebook.util;

/**
 * Description: Global constant manager.<p>
 *
 * Created by Newamber on 2017/6/28.
 */
@SuppressWarnings("all")
public class GlobalConstant {
    // SharePreference local path.
    public static final String PREFERENCE_FILE = "local_preference_file";

    // Currency
    public static final String CURRENCY_SYMBOL = "￥";
    public static final Double MAX_BALANCE = 999999999999d;
    public static final Double MAX_SINGLE_RECORD_AOMUNT = 999999999d;

    // Initial info judgement condition.
    public static final String IS_FIRST_ENTER_APP = "is_first_enter_application";
    public static final String IS_FIRST_ENTER_MONEY_TYPE = "is_first_enter_moneyType";
    public static final String IS_FIRST_ENTER_MONEY_REPO_TYPE = "is_first_enter_moneyRepoType";

    // Settings check info.
    public static final String IS_AUTO_RECORD_ACCOUNT = "is_auto_record_account";
    public static final String IS_ENABLE_PICTURE_PASSWORD = "is_enable_picture_password";
    public static final String IS_ENABLE_TRANSITION_ANIM = "is_enable_transition_animation";
    public static final String IS_ENABLE_LIST_ANIM = "is_enable_list_animation";

    // Activity intent info.
    public static final String IS_RECORD_SAVED_SUCCESS = "is_record_saved_success";

    // Event message
    public static final String IS_EXIST_REPO_TYPE_NAME = "is_exist_repo_type_name";
    public static final String IS_EXIST_TYPE_NAME = "is_exist_type_name";
    public static final String DELETE_ALL_REPO_TYPE = "delete_all_repo_type";
    public static final String DELETE_ALL_MONEY_TYPE = "delete_all_money_type";


}
