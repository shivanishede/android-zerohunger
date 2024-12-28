package com.example.zerohunger;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ReceiverActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView dataRecyclerView;
    private ReceiverAdapter receiverAdapter;  // Change to ReceiverAdapter
    private List<DataModel> dataList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        searchEditText = findViewById(R.id.search);
        dataRecyclerView = findViewById(R.id.dataRecyclerView);
        dataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();
        receiverAdapter = new ReceiverAdapter(dataList);  // Use ReceiverAdapter
        dataRecyclerView.setAdapter(receiverAdapter);  // Set ReceiverAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("donations");

        // Fetch data from Firebase
        fetchDataFromFirebase();

        // Add TextWatcher to searchEditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiverAdapter.filter(s.toString());  // Use filter from ReceiverAdapter
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataModel data = dataSnapshot.getValue(DataModel.class);
                    if (data != null) {
                        data.setKey(dataSnapshot.getKey());
                        dataList.add(data);
                    }
                }
                receiverAdapter.updateData(dataList);  // Update the data in ReceiverAdapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }
}
