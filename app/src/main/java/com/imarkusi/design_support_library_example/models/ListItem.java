package com.imarkusi.design_support_library_example.models;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListItem {

    private String title;

    private String subtitle;

    public ListItem(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
