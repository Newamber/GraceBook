package com.newamber.gracebook.util;

/**
 * Description: Global constant manager.<p>
 *
 * Created by Newamber on 2017/6/28.
 */
public class GlobalConstant {
    // SharePreference local path.
    static final String PREFERENCE_FILE = "local_preference_file";

    // Currency
    static final String CURRENCY_SYMBOL = "currency_symbol";
    static final String CURRENCY_SYMBOL_CHS = "￥";
    public static final Double MAX_BALANCE = 999999999999d;
    public static final Double MAX_SINGLE_RECORD_AMOUNT = 999999999d;

    public static final String ACCOUNT_BOOK_NAME = "account_book_name";

    // Initial info judgement condition.
    public static final String IS_FIRST_ENTER_APP = "is_first_enter_application";
    public static final String IS_FIRST_ENTER_MONEY_TYPE = "is_first_enter_moneyType";
    public static final String IS_FIRST_ENTER_MONEY_REPO_TYPE = "is_first_enter_moneyRepoType";
    public static final String IS_FIRST_QUERY_SINGLE_DAY = "is_first_query_single_day";
    public static final String IS_FIRST_RECORD_PASS_OR_FUTURE_ACCOUNT = "is_first_record_pass_or_future_account";

    // Settings check info.
    public static final String IS_AUTO_RECORD_ACCOUNT = "is_auto_record_account";
    public static final String IS_ENABLE_PICTURE_PASSWORD = "is_enable_picture_password";
    public static final String IS_ENABLE_TRANSITION_ANIM = "is_enable_transition_animation";

    // Event message
    public static final String IS_EXIST_REPO_TYPE_NAMES = "is_exist_repo_type_names";
    public static final String IS_EXIST_TYPE_NAMES = "is_exist_type_names";
    public static final String DELETE_ALL_REPO_TYPES = "delete_all_repo_types";
    public static final String DELETE_ALL_MONEY_TYPES = "delete_all_money_types";
    public static final String DELETE_ALL_ACCOUNT_ITEMS = "delete_all_account_items";
    public static final String DELETE_CURRENT_PAGE_ACCOUNT_ITEMS = "delete_current_page_account_items";
    public static final String REQUEST_FLOW_DATE_AND_ACCOUNT = "request_flow_date_and_account";
    public static final String REQUEST_FLOW_DATE_RANGE = "request_flow_date_range";
    public static final String REQUEST_RESUME_CURRENT_DATE_RANGE = "request_resume_current_date_range";
    public static final String REQUEST_IS_CURRENT_PERIOD_EMPTY = "request_is_current_page_empty";
    public static final String CURRENT_PAGE_IS_EMPTY = "current_page_is_empty";
    public static final String CURRENT_PAGE_IS_NOT_EMPTY = "current_page_is_not_empty";
    public static final String FLOW_ADD_NEW_ACCOUNT_ITEM = "flow_add_new_account";

    public static final String IS_EDIT_ACCOUNT_ITEM_MODE = "is_from_day_fragment";

    public static final String FLOW_PERIOD_SELECTED_POSITION = "flow_period_selected_position";
    public static final String CHART_PERIOD_SELECTED_POSITION = "chart_trend_period_selected_position";

    public static final String LAST_RECORD_TIME = "last_record_time";

}
