package com.imarkusi.design_support_library_example.activities;

import com.imarkusi.design_support_library_example.R;
import com.imarkusi.design_support_library_example.helpers.FontHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 09/10/15.
 *
 * @author markusi
 */
public class MainActivity extends AppCompatActivity {

    private static final String USE_CUSTOM_FONTS = "use_custom_fonts";

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    MenuItem customFontMenuItem;
    MenuItem defaultFontMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = ButterKnife.findById(this, R.id.navigation_view);
        assert navigationView != null;
        setupDrawerContent(navigationView);

        if (getIntent().getBooleanExtra(USE_CUSTOM_FONTS, false)) {
            FontHelper.changeNavigationDrawerFonts(navigationView);
            customFontMenuItem.setChecked(true);
        } else {
            defaultFontMenuItem.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
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

    private void restartActivity(boolean useCustomFonts) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USE_CUSTOM_FONTS, useCustomFonts);
        finish();
        startActivity(intent);
    }
}
