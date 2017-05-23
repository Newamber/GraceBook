package com.newamber.gracebook.model.entity;

import android.support.annotation.DrawableRes;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/9.
 */

public class SpinnerTypeIcon {
    private String placeholder = "";
    private @DrawableRes int iconId;

    public SpinnerTypeIcon(String placeholder, @DrawableRes int iconId) {
        this.placeholder = placeholder;
        this.iconId = iconId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
