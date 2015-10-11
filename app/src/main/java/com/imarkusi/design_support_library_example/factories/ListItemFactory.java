package com.imarkusi.design_support_library_example.factories;

import com.imarkusi.design_support_library_example.R;
import com.imarkusi.design_support_library_example.models.ListItem;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public class ListItemFactory {

    private static final String TITLE = "%d. Title";

    private static final String SUBTITLE = "%d. Subtitle and some text just to fill the space";

    private static int counter;

    private ListItemFactory() {
    }

    public static ListItem createListItem() {
        counter++;
        String title = String.format(TITLE, counter);
        String subtitle = String.format(SUBTITLE, counter);
        int drawableResId = R.drawable.list_item;
        return new ListItem(title, subtitle, drawableResId);
    }
}
