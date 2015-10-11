package com.imarkusi.design_support_library_example.factories;

import com.imarkusi.design_support_library_example.models.ListItem;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListItemFactory {

    private static final String TITLE = "%d. Title";

    private static final String SUBTITLE = "%d. Subtitle and some text just to fill the space";

    private ListItemFactory() {
    }

    public static ListItem createListItem(int index) {
        String title = String.format(TITLE, index);
        String subtitle = String.format(SUBTITLE, index);
        return new ListItem(title, subtitle);
    }
}
