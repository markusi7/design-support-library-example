package com.imarkusi.design_support_library_example.activities;

import com.imarkusi.design_support_library_example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 12/10/15.
 *
 * @author markusi
 */
public class DetailsActivity extends Activity {

    public static final String TITLE = "title";

    public static final String SUBTITLE = "subtitle";

    private static final String COLLAPSE_MODE = "collapse_mode";

    @Bind(R.id.appbar)
    AppBarLayout appBar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.title)
    TextView titleTextView;

    @Bind(R.id.subtitle)
    TextView subtitleTextView;

    @Bind(R.id.image)
    ImageView imageView;

    @Bind(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    @Bind(R.id.spinner)
    Spinner spinner;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(TITLE);
            String subtitle = getIntent().getStringExtra(SUBTITLE);
            if (title != null) {
                titleTextView.setText(title);
            }
            if (subtitle != null) {
                subtitleTextView.setText(subtitle);
            } else {
                subtitleTextView.setVisibility(View.GONE);
            }
            int collapseMode = getIntent().getIntExtra(COLLAPSE_MODE, 0);
            setCollapseMode(collapseMode);
        }
        //or bind it via ButterKnife
        editText = textInputLayout.getEditText();
        textInputLayout.setCounterMaxLength(16);
        //display title when scrim is drawn
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (collapsingToolbar.getHeight() + i < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                    collapsingToolbar.setTitle(titleTextView.getText());
                } else {
                    collapsingToolbar.setTitle("");
                }
            }
        });
    }

    private void setCollapseMode(int collapseMode) {
        CollapsingToolbarLayout.LayoutParams imageViewParams = (CollapsingToolbarLayout.LayoutParams) imageView.getLayoutParams();
        imageViewParams.setCollapseMode(collapseMode);
        CollapsingToolbarLayout.LayoutParams textViewParams = (CollapsingToolbarLayout.LayoutParams) titleTextView.getLayoutParams();
        textViewParams.setCollapseMode(collapseMode);
        switch (collapseMode){
            case CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF:
                spinner.setSelection(0);
                break;
            case CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX:
                spinner.setSelection(1);
                break;
            case CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN:
                spinner.setSelection(2);
                break;
        }

    }

    @OnClick(R.id.submit)
    void onSubmitButtonClicked() {
        if (editText.getText().toString().equals("")) {
            textInputLayout.setError(getString(R.string.error_header_title));
        } else if (editText.getText().toString().length() > textInputLayout.getCounterMaxLength()) {
            textInputLayout.setError(getString(R.string.error_text_length));
        } else {
            textInputLayout.setError(null);
            launchIntent();
        }
    }

    private void launchIntent() {
        Intent intent = new Intent(this, DetailsActivity.class);
        int collapseMode;
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF;
                break;
            case 1:
                collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX;
                break;
            default:
                collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN;
                break;
        }
        intent.putExtra(COLLAPSE_MODE, collapseMode);
        intent.putExtra(TITLE, editText.getText().toString());
        finish();
        startActivity(intent);
    }
}
