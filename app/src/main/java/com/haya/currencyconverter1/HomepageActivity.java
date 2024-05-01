package com.haya.currencyconverter1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Initialize buttons
        Button converterButton = findViewById(R.id.converterButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set onClickListener for converterButton
        converterButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, CurrencyConversionActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for logoutButton
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, login.class);
            startActivity(intent);
        });
    }
}