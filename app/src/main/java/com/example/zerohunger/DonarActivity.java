package com.example.zerohunger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DonarActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText quantityEditText;
    private EditText emailEditText;
    private EditText pinEditText;
    private EditText dateEditText;
    private EditText expiryDateEditText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar);

        Button viewInformationButton = findViewById(R.id.viewInformationButton);

        // Set up the button click listener
        viewInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ViewActivity when the button is clicked
                Intent intent = new Intent(DonarActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
        // Setup colored "Zero Hunger" text
        TextView zeroHungerText = findViewById(R.id.zeroHungerText);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // "Zero" in orange color
        SpannableString zero = new SpannableString("Donar ");
        zero.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 0, zero.length(), 0);
        builder.append(zero);

        // "Hunger" in green color
        SpannableString hunger = new SpannableString("Information");
        hunger.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)), 0, hunger.length(), 0);
        builder.append(hunger);

        zeroHungerText.setText(builder);

        // Initialize the EditTexts
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        emailEditText = findViewById(R.id.emailEditText);
        pinEditText = findViewById(R.id.pinEditText);
        dateEditText = findViewById(R.id.dateEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);

        // Automatically set the current date to the dateEditText
        setCurrentDate(dateEditText);

        // Set click listeners to show DatePickerDialog
        dateEditText.setOnClickListener(v -> showDatePickerDialog(dateEditText));
        expiryDateEditText.setOnClickListener(v -> showDatePickerDialog(expiryDateEditText));

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("donations");

        // Add functionality to store data in Firebase when required (e.g., on button click)
        findViewById(R.id.saveButton).setOnClickListener(v -> saveDataToFirebase());
    }

    // Automatically set the current date to the EditText
    private void setCurrentDate(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Format the current date as needed (day/month/year)
        String currentDate = day + "/" + (month + 1) + "/" + year;
        editText.setText(currentDate);  // Set the current date to the EditText
    }

    // Show the DatePickerDialog with the restriction of allowing only today's date and future dates
    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                DonarActivity.this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Format the selected date as needed
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            editText.setText(selectedDate); // Set the selected date to the EditText
        }, year, month, day);

        // Set the date picker to only allow selecting today's date and future dates
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); // Min date is today
        datePickerDialog.show();
    }

    // Method to save data to Firebase
    private void saveDataToFirebase() {
        String name = nameEditText.getText().toString().trim();
        String phoneNumber = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String quantity = quantityEditText.getText().toString().trim();
        String mailId = emailEditText.getText().toString().trim();
        String pincode = pinEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();

        if (!name.isEmpty() && !phoneNumber.isEmpty() && !address.isEmpty() && !quantity.isEmpty() &&
                !mailId.isEmpty() && !pincode.isEmpty() && !date.isEmpty() && !expiryDate.isEmpty()) {
            // Create a data map
            Map<String, String> donationData = new HashMap<>();
            donationData.put("name", name);
            donationData.put("phoneNumber", phoneNumber);
            donationData.put("address", address);
            donationData.put("quantity", quantity);
            donationData.put("mailId", mailId);
            donationData.put("pincode", pincode);
            donationData.put("date", date);
            donationData.put("expiryDate", expiryDate);

            // Push data to Firebase under a unique ID
            databaseReference.push().setValue(donationData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(DonarActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        // Clear the form fields after successful submission
                        clearForm();
                    })
                    .addOnFailureListener(e -> Toast.makeText(DonarActivity.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to clear all EditText fields
    private void clearForm() {
        nameEditText.setText("");
        phoneEditText.setText("");
        addressEditText.setText("");
        quantityEditText.setText("");
        emailEditText.setText("");
        pinEditText.setText("");
        dateEditText.setText("");
        expiryDateEditText.setText("");
    }

}