package com.example.zerohunger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<DataModel> dataList;
    private List<DataModel> filteredList;

    public DataAdapter(List<DataModel> dataList) {
        this.dataList = dataList;
        this.filteredList = new ArrayList<>(dataList); // Initially, show all data
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataModel data = filteredList.get(position);

        // Bind data to views
        holder.nameTextView.setText(data.getName());
        holder.phoneTextView.setText(data.getPhoneNumber());
        holder.emailTextView.setText(data.getMailId());
        holder.dateTextView.setText(data.getDate());
        holder.expiryDateTextView.setText(data.getExpiryDate());
        holder.addressTextView.setText(data.getAddress());
        holder.pincodeTextView.setText(data.getPincode());
        holder.quantityTextView.setText(data.getQuantity());

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("donations");
            String recordKey = data.getKey(); // Assume DataModel has a method `getKey()`
            if (recordKey != null) {
                databaseReference.child(recordKey).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataList.remove(data);
                        filteredList.remove(data);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Record deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Failed to delete record", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(v.getContext(), "Record key is null", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for edit button
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateActivity.class);
            // Pass data to UpdateActivity
            intent.putExtra("key", data.getKey());
            intent.putExtra("name", data.getName());
            intent.putExtra("phone", data.getPhoneNumber());
            intent.putExtra("email", data.getMailId());
            intent.putExtra("date", data.getDate());
            intent.putExtra("expiryDate", data.getExpiryDate());
            intent.putExtra("address", data.getAddress());
            intent.putExtra("pincode", data.getPincode());
            intent.putExtra("quantity", data.getQuantity());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void updateData(List<DataModel> newDataList) {
        this.dataList = newDataList;
        this.filteredList = new ArrayList<>(newDataList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(dataList);
        } else {
            for (DataModel data : dataList) {
                if (data.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, emailTextView, dateTextView, expiryDateTextView,
                addressTextView, pincodeTextView, quantityTextView;
        Button deleteButton, editButton;

        public DataViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            pincodeTextView = itemView.findViewById(R.id.pincodeTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton); // Initialize editButton
        }
    }
}
