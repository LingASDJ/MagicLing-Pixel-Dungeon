package com.shatteredpixel.shatteredpixeldungeon.android;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

public class ErrorActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this, new DynamicColorsOptions.Builder().setPrecondition((activity, theme) -> true).build());
        BarUtils.transparentStatusBar(this);
        BarUtils.setStatusBarLightMode(this, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BarUtils.setNavBarColor(this, 0x00000000);
        }
        setContentView(R.layout.activity_error);
        final MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        final TextView textView = findViewById(R.id.error_info_text_view);
        textView.setText(String.format(Locale.getDefault(), getString(R.string.error_info), CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, getIntent())));
        final Button button = findViewById(R.id.error_button);
        button.setOnClickListener(v -> {
            CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(
                    getIntent()
            );
            if (config == null) {
                Snackbar.make(v, R.string.no_configuration_found, Snackbar.LENGTH_LONG).show();
                return;
            }
            CustomActivityOnCrash.restartApplication(ErrorActivity.this, config);
        });
    }
}
