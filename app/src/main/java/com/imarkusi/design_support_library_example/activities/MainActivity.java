package com.imarkusi.design_support_library_example.activities;

import com.imarkusi.design_support_library_example.R;
import com.imarkusi.design_support_library_example.custom.ViewPagerAdapter;
import com.imarkusi.design_support_library_example.fragments.ListFragment;
import com.imarkusi.design_support_library_example.helpers.FontHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 09/10/15.
 *
 * @author markusi
 */
public class MainActivity extends AppCompatActivity {

    private static final String USE_CUSTOM_FONTS = "use_custom_fonts";

    private static final int TAB_LIMIT = 8;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.button_create_tab)
    FloatingActionButton createTabButton;

    @Bind(R.id.button_delete_tab)
    FloatingActionButton deleteTabButton;

    MenuItem customFontMenuItem;
    MenuItem defaultFontMenuItem;

    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setup Navigation View
        NavigationView navigationView = ButterKnife.findById(this, R.id.navigation_view);
        assert navigationView != null;
        setupDrawerContent(navigationView);

        ViewPager viewPager = ButterKnife.findById(this, R.id.viewpager);
        assert viewPager != null;
        setupViewPager();

        if (getIntent().getBooleanExtra(USE_CUSTOM_FONTS, false)) {
            FontHelper.changeNavigationDrawerFonts(navigationView);
            customFontMenuItem.setChecked(true);
        } else {
            defaultFontMenuItem.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        defaultFontMenuItem = navigationView.getMenu().findItem(R.id.default_font);
        customFontMenuItem = navigationView.getMenu().findItem(R.id.custom_font);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                int menuItemId = menuItem.getItemId();
                if (menuItemId == R.id.default_font || menuItemId == R.id.custom_font) {
                    restartActivity(customFontMenuItem.isChecked());
                } else {
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupViewPager() {
        setupViewPager(1);
    }

    private void setupViewPager(int tabsCount) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < tabsCount; i++) {
            adapter.addFragment(new ListFragment(), getString(R.string.tab_name, i + 1));
        }
        initTabLayout(adapter);
    }

    private void initTabLayout(PagerAdapter pagerAdapter) {
        viewPager.setAdapter(pagerAdapter);

        LinearLayout slidingTabStrip = (LinearLayout) tabLayout.getChildAt(0);
        slidingTabStrip.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int stripWidth = slidingTabStrip.getMeasuredWidth();
        int tabLayoutWidth = tabLayout.getMeasuredWidth();

        if (stripWidth <= tabLayoutWidth) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0, size = tabLayout.getTabCount(); i < size; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            if ( i % 2 == 0){
                LinearLayout customTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
                TextView textView = ButterKnife.findById(customTab, R.id.text1);
                textView.setText(tab.getText());
                //because listener is not triggered when initialised
                if ( i == 0){
                    ImageView imageView = ButterKnife.findById(customTab, R.id.icon);
                    imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_tab_black));
                }
                tab.setCustomView(customTab);
            }
        }
        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getCustomView() != null) {
                    ImageView imageView = ButterKnife.findById(tab.getCustomView(), R.id.icon);
                    imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_tab_black));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    ImageView imageView = ButterKnife.findById(tab.getCustomView(), R.id.icon);
                    imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_tab_white));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        tabLayout.setOnTabSelectedListener(listener);
    }

    private void showSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        if (tabLayout.getTabCount() <= TAB_LIMIT - 1) {
            snackbar = Snackbar.make(createTabButton,
                    String.format(getString(R.string.tab_count), tabLayout.getTabCount()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.dismiss, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

        } else {
            snackbar = Snackbar.make(tabLayout, R.string.tab_limit, Snackbar.LENGTH_LONG);
        }
        snackbar.show();

    }

    private void restartActivity(boolean useCustomFonts) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USE_CUSTOM_FONTS, useCustomFonts);
        finish();
        startActivity(intent);
    }

    @OnClick({R.id.button_create_tab, R.id.button_delete_tab})
    void onFloatingActionButtonClick(FloatingActionButton floatingActionButton) {
        int numberOfTabs = tabLayout.getTabCount();
        boolean tabsCountIncremented = floatingActionButton.getId() == R.id.button_create_tab;
        numberOfTabs += tabsCountIncremented ? 1 : -1;
        boolean onlyOneTab = numberOfTabs == 1;
        if (onlyOneTab) {
            setupViewPager();
        } else if (numberOfTabs - 1 != TAB_LIMIT) {
            setupViewPager(numberOfTabs);
        }
        showSnackbar();
        deleteTabButton.setVisibility(onlyOneTab ? View.GONE : View.VISIBLE);
    }
}
