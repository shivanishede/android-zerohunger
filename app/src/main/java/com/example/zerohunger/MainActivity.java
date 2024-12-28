package com.example.zerohunger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView userName;
    private Button logout;
    private CardView donarCard ;
    private CardView receiverCard;
    private ViewPager2 carousel;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup colored "Zero Hunger" text
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

        // Find logout button
        logout = findViewById(R.id.logout);

        donarCard = findViewById(R.id.donarCard);
        receiverCard = findViewById(R.id.receiverCard);
        donarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DonarActivity.class));

            }
        });
        receiverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirect to ReceiverActivity
                startActivity(new Intent(MainActivity.this, ReceiverActivity.class));

            }
        });
        // Logout button functionality
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to LoginActivity without Google sign-out logic
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });






        // BottomNavigationView setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        // Use an anonymous class instead of a lambda expression
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_home) {
                    return true;
                } else if (item.getItemId() == R.id.bottom_search) {
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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

        // ViewPager2 setup
        carousel = findViewById(R.id.carousel);

        // Add your image resources here
        List<Integer> images = Arrays.asList(
                R.drawable.carousel1,  // Replace with your actual image resource names
                R.drawable.carousel2,
                R.drawable.carousel3
        );

        // Hindi and English captions
        List<String> hindiCaptions = Arrays.asList(
                "आत्मनं प्रति यज्ञेन, अन्नं दानेन समर्पयेत्|",
                "भूतेभ्यो यज्ञः कर्मणा, दानेन च मनोगतं।",
                "सेवया त्यागेन च, आत्मा संरक्षणाय समर्पयेत्।"
        );

        List<String> englishCaptions = Arrays.asList(
                "Offer oneself through sacrifice, dedicate food through donation",
                "Perform rituals for the well-being of all beings, both through actions and mental generosity.",
                "Through service and sacrifice, dedicate oneself for the preservation of the soul."
        );

        CarouselAdapter adapter = new CarouselAdapter(this, images, hindiCaptions, englishCaptions);
        carousel.setAdapter(adapter);

        // Auto-slide logic
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = carousel.getCurrentItem();
                int nextItem = (currentItem + 1) % images.size();
                carousel.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // 3 seconds interval
            }
        };
        handler.postDelayed(runnable, 3000); // Start the initial delay

        // Add a page change callback to stop and restart the handler
        carousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000); // Reset delay after manual swipe
            }
        });

        // YouTube Video WebView setup
        WebView videoWebView = findViewById(R.id.videoWebView);
        WebSettings webSettings = videoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        videoWebView.loadUrl("https://www.youtube.com/embed/MMW6zLbBmQg?si=L-Q4zZhlQ5UUU5T8"); // Replace with your YouTube video ID
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Clean up the handler when activity is destroyed
    }
}
