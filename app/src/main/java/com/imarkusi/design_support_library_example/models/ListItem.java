package com.imarkusi.design_support_library_example.models;

import android.support.annotation.DrawableRes;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListItem {

    private String title;

    private String subtitle;

    private int drawableResId;

    public ListItem(String title, String subtitle, @DrawableRes int drawableResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.drawableResId = drawableResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getDrawableResId() {
        return drawableResId;
    }
}
