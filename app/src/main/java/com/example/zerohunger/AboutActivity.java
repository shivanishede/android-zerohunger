package com.example.zerohunger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.webkit.WebView;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Add the colored text setup here
        TextView zeroHungerText = findViewById(R.id.zeroHungerText);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // "Zero" in orange color
        SpannableString zero = new SpannableString("Zero ");
        zero.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 0, zero.length(), 0);
        builder.append(zero);

        // "Hunger" in green color
        SpannableString hunger = new SpannableString("Hunger");
        hunger.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)), 0, hunger.length(), 0);
        builder.append(hunger);

        zeroHungerText.setText(builder);

        // WebView setup for YouTube video
        WebView videoWebView = findViewById(R.id.videoWebView); // Assuming you have a WebView in your XML
        videoWebView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
        videoWebView.loadUrl("https://www.youtube.com/embed/MMW6zLbBmQg?si=L-Q4zZhlQ5UUU5T8"); // Replace YOUR_VIDEO_ID with your actual YouTube video ID

        // BottomNavigationView setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.bottom_search) {
                    return true;
                } else if (item.getItemId() == R.id.bottom_settings) {
                    // Open the URL in the browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://get.mobitrash.in/"));
                    startActivity(browserIntent);
                    return true;
                } else if (item.getItemId() == R.id.bottom_profile) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
