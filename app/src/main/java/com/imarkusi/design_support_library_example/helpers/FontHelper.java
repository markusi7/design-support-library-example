package com.imarkusi.design_support_library_example.helpers;

import com.imarkusi.design_support_library_example.App;
import com.imarkusi.design_support_library_example.R;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created on 09/10/15.
 *
 * @author markusi
 */
public class FontHelper {

    private FontHelper() {
    }

    public static void changeTabsFont(TabLayout tabLayout) {
        ViewGroup viewGroup = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = viewGroup.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup viewGroupTab = (ViewGroup) viewGroup.getChildAt(j);
            int tabChildrenCount = viewGroupTab.getChildCount();
            for (int i = 0; i < tabChildrenCount; i++) {
                View tabViewChild = viewGroupTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(getTypeface(R.string.custom_font_name));
                    ((TextView) tabViewChild).setTextColor(ContextCompat.getColor(tabViewChild.getContext(), android.R.color.white));
                }
            }
        }
    }

    public static void changeToolbarFont(Toolbar toolbar) {
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(getTypeface(R.string.custom_font_name));
    }

    public static void changeNavigationDrawerFonts(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            SubMenu subMenu = menuItem.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                MenuItem subMenuItem = subMenu.getItem();
                applyFontToMenuItem(subMenuItem);
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItemChild = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItemChild);
                }
            }
            applyFontToMenuItem(menuItem);
        }
    }

    private static void applyFontToMenuItem(MenuItem menuItem) {
        SpannableString newTitle = new SpannableString(menuItem.getTitle());
        newTitle.setSpan(new CustomTypefaceSpan("",
                getTypeface(R.string.custom_font_name)), 0, newTitle.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(newTitle);
    }

    public static void applyStyleToSnackbar(Snackbar snackbar) {
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primary_dark));
        TextView textView = ButterKnife.findById(view, android.support.design.R.id.snackbar_text);
        setTextViewStyle(textView, R.string.custom_font_name, android.R.color.white);
        Button actionTextView = ButterKnife.findById(view, android.support.design.R.id.snackbar_action);
        if (actionTextView != null) {
            setTextViewStyle(actionTextView, R.string.custom_font, android.R.color.white);
        }
    }

    private static void setTextViewStyle(TextView textView, @StringRes int fontResId, @ColorRes int colorResId) {
        SpannableString text = new SpannableString(textView.getText());
        text.setSpan(new CustomTypefaceSpan("",
                getTypeface(fontResId)), 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorResId));
    }

    private static Typeface getTypeface(@StringRes int font) {
        App app = App.getInstance();
        final String rootFontPath = "fonts/";
        final String fontPath = rootFontPath + app.getResources().getString(font);
        return Typeface.createFromAsset(app.getAssets(), fontPath);
    }

    private static class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        private static void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(@NonNull TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }
    }
}
