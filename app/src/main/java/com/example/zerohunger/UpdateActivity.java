package com.example.zerohunger;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, quantityEditText, emailEditText, addressEditText, pinEditText, dateEditText, expiryDateEditText;
    private Button saveButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseReference = FirebaseDatabase.getInstance().getReference("donations");

        // Link UI components
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        pinEditText = findViewById(R.id.pinEditText);
        dateEditText = findViewById(R.id.dateEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        saveButton = findViewById(R.id.saveButton);

        // Extract data from Intent
        String key = getIntent().getStringExtra("key");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        String pincode = getIntent().getStringExtra("pincode");
        String date = getIntent().getStringExtra("date");
        String expiryDate = getIntent().getStringExtra("expiryDate");
        String quantity = getIntent().getStringExtra("quantity");

        // Pre-fill fields
        nameEditText.setText(name);
        phoneEditText.setText(phone);
        quantityEditText.setText(quantity);
        emailEditText.setText(email);
        addressEditText.setText(address);
        pinEditText.setText(pincode);
        dateEditText.setText(date);
        expiryDateEditText.setText(expiryDate);

        saveButton.setOnClickListener(v -> {
            HashMap<String, Object> updates = new HashMap<>();
            updates.put("name", nameEditText.getText().toString().trim());
            updates.put("phone", phoneEditText.getText().toString().trim());
            updates.put("quantity", quantityEditText.getText().toString().trim());
            updates.put("email", emailEditText.getText().toString().trim());
            updates.put("address", addressEditText.getText().toString().trim());
            updates.put("pincode", pinEditText.getText().toString().trim());
            updates.put("date", dateEditText.getText().toString().trim());
            updates.put("expiryDate", expiryDateEditText.getText().toString().trim());

            databaseReference.child(key).updateChildren(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
